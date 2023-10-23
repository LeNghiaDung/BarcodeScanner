package ninh.main.mybarcodescanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottom_dialog extends BottomSheetDialogFragment {
    private TextView title, seri, content;
    private Button btnAdd;
    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bottom_dialog,container,false);

        title = view.findViewById(R.id.productTitle);
        seri = view.findViewById(R.id.productSeri);
        content = view.findViewById(R.id.content);
        btnAdd = view.findViewById(R.id.btnAdd);
        image = view.findViewById(R.id.productImage);

        return view;
    }

    public void setSeriTitle(String rawData){
        title.setText(rawData);
    }
}