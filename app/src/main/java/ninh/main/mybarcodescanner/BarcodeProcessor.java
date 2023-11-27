package ninh.main.mybarcodescanner;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.HashMap;
import java.util.Map;

//chuyển đổi ảnh sang dạng khác -> xử lí mã chuyển vào result
public class BarcodeProcessor {
    public static String decodeBarcode(Bitmap bitmap) {
        MultiFormatReader reader = new MultiFormatReader();

        try {
            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);

            // Sử dụng RGBLuminanceSource để chuyển đổi Bitmap thành nguồn luminance
            int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

            RGBLuminanceSource luminanceSource = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));

            Result result = reader.decode(binaryBitmap, hints);
            return result.getText();
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
