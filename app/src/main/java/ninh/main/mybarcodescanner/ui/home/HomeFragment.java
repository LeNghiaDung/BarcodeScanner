package ninh.main.mybarcodescanner.ui.home;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.Image;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import ninh.main.mybarcodescanner.AddProduct;
import ninh.main.mybarcodescanner.BarcodeProcessor;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.databinding.FragmentHomeBinding;
import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.DatabaseHelper;
import ninh.main.mybarcodescanner.ModifyProductActivity;

public class HomeFragment extends Fragment {
    private ListenableFuture cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private ImageAnalyzer analyzer;
    private FragmentHomeBinding binding;
    boolean barcodeDetected = false;
    private DBManager dbManager;
    public DatabaseHelper helper;
    public SQLiteDatabase database;
    ImageView btnLibr;
    int REQUEST_FOLDER_CODE=456;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //KHAI BÁO DATABASE
        dbManager = new DBManager(getActivity());
        dbManager.open();
        dbManager.open();

        // Activity = this / MainActivity.this
        // Fragment = getActivity();
        previewView = root.findViewById(R.id.previewView);
        this.getActivity().getWindow().setFlags(1024,1024);

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());

        analyzer = new ImageAnalyzer(getActivity().getSupportFragmentManager());

        //ánh xạ nút Thư viện để truy cập vào ảnh

        btnLibr=root.findViewById(R.id.gallery);
        btnLibr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_FOLDER_CODE);
            }
        });



        // gọi camera
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    if((PackageManager.PERMISSION_GRANTED) != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)){
                        ActivityCompat.requestPermissions(getActivity() , new String[] {Manifest.permission.CAMERA} , 101);
                    }else {
                        ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
                        bindpreview(processCameraProvider);
                    }
                }catch (ExecutionException e){
                    e.printStackTrace();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(getActivity()) );

        return root;
    }

    //yêu cầu cấp quyền cho camera
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && grantResults.length > 0) {
            ProcessCameraProvider processCameraProvider = null;
            try {
                processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bindpreview(processCameraProvider);
        }
    }

    //hàm y/c các tác vụ của camera
    private void bindpreview(ProcessCameraProvider processCameraProvider) {
        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageCapture imageCapture = new ImageCapture.Builder().build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1280 , 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(cameraExecutor , analyzer);

        processCameraProvider.unbind();

        processCameraProvider.bindToLifecycle(this , cameraSelector , preview , imageCapture , imageAnalysis);
    }



    //Lấy ảnh + xử lí ảnh
       @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        /////////////////////////////// LIBRARY
        if (requestCode == REQUEST_FOLDER_CODE && resultCode == RESULT_OK && intent != null) {
            Uri uri = intent.getData();
            try {

                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                String barcodeResult = BarcodeProcessor.decodeBarcode(bitmap);
                if (barcodeResult != null) {
                    ////////// Xử lý mã vạch ở đây (cho vào database)
                    if(dbManager.checkExisted(barcodeResult)==true)
                    {
                        barcodeResult = barcodeResult +" ";
                        Intent productIntent = new Intent(getActivity(), ModifyProductActivity.class);
                        productIntent.putExtra(DatabaseHelper.Seri,barcodeResult);
                        startActivity(productIntent);
                    }
                    else {
                        Intent productIntent = new Intent(getActivity(), AddProduct.class);
                        productIntent.putExtra(DatabaseHelper.Seri,barcodeResult);
                        startActivity(productIntent);

                    }
                    Toast.makeText(getActivity(), "Mã vạch: " + barcodeResult, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Không thể đọc mã vạch từ ảnh", Toast.LENGTH_SHORT).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }



    //cho vào arraylist
    public class ImageAnalyzer implements ImageAnalysis.Analyzer {
        private FragmentManager fragmentManager;

        public ImageAnalyzer(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        //cho mã vào array
        @Override
        public void analyze(@NonNull ImageProxy image) {
            scanbarcode(image);
        }


//         MAIN - SCANNER
        @OptIn(markerClass = ExperimentalGetImage.class)
        private void scanbarcode(ImageProxy image) {
            //Lay anh
            Image image1 = image.getImage();
            assert image1 != null;
            InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());

            //Filter: Loc anh co Barcode
            BarcodeScannerOptions options =
                    new BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                    Barcode.FORMAT_ALL_FORMATS).build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);

            //Cho barcode vao List
            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {

                            if (!barcodeDetected && barcodes.size() > 0) {
                                // Handle the first detected barcode
                                Barcode firstBarcode = barcodes.get(0);

                                // Set the flag to true to stop further scanning
                                //LOI HIEN TAI DO CAI DUOI
                                barcodeDetected = true;

                                // Tao am thanh TITttttt
                                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);

                                readerBarcodeData(Collections.singletonList(firstBarcode),scanner);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    }).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Barcode>> task) {
                            image.close();
                        }
                    });
        }

        private void readerBarcodeData(List<Barcode> barcodes, BarcodeScanner scanner) {
            for (Barcode barcode : barcodes) {
                Rect bounds = barcode.getBoundingBox();

                String rawValue = barcode.getRawValue();

                String check = barcode.getRawValue();
                boolean checkExisted = dbManager.checkExisted(check);
                if (checkExisted){
                    check = check + " ";
                    Intent productIntent = new Intent(getActivity(), ModifyProductActivity.class);
                    productIntent.putExtra(DatabaseHelper.Seri,check);
                    startActivity(productIntent);
                } else {
                    Intent productIntent = new Intent(getActivity(), AddProduct.class);
                    productIntent.putExtra(DatabaseHelper.Seri,check);
                    startActivity(productIntent);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}