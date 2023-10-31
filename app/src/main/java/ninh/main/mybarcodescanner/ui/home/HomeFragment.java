package ninh.main.mybarcodescanner.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.Image;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ninh.main.mybarcodescanner.AddProduct;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private ListenableFuture cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private ImageAnalyzer analyzer;
    private FragmentHomeBinding binding;
    boolean barcodeDetected = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        previewView = root.findViewById(R.id.previewView);
        this.getActivity().getWindow().setFlags(1024,1024);

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());

        analyzer = new ImageAnalyzer(getActivity().getSupportFragmentManager());

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

    public class ImageAnalyzer implements ImageAnalysis.Analyzer {
        private FragmentManager fragmentManager;

        public ImageAnalyzer(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        @Override
        public void analyze(@NonNull ImageProxy image) {
            scanbarcode(image);
        }

        // MAIN - SCANNER
        @OptIn(markerClass = ExperimentalGetImage.class)
        private void scanbarcode(ImageProxy image) {
            Image image1 = image.getImage();
            assert image1 != null;
            InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());
            BarcodeScannerOptions options =
                    new BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                    Barcode.FORMAT_ALL_FORMATS).build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);
            Task<List<Barcode>> result = scanner.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {

                            if (!barcodeDetected && barcodes.size() > 0) {
                                // Handle the first detected barcode
                                Barcode firstBarcode = barcodes.get(0);

                                // Set the flag to true to stop further scanning
                                barcodeDetected = true;

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
                String checkSeri = null;
                Rect bounds = barcode.getBoundingBox();
                Point[] corners = barcode.getCornerPoints();

                String rawValue = barcode.getRawValue();

                String check = barcode.getRawValue().toString();

                Intent productIntent = new Intent(getActivity(), AddProduct.class);
                productIntent.putExtra("seri",check);
                startActivity(productIntent);
                }
            }
        }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}