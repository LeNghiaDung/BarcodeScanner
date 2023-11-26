package ninh.main.mybarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ninh.main.mybarcodescanner.list.ListFragment;
import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.DatabaseHelper;
import ninh.main.mybarcodescanner.ui.home.HomeFragment;

public class AddProductManually extends AppCompatActivity {
    DBManager db;
    EditText edName, edSeri, edQuantity;
    ImageButton btnInc, btnDec;
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_manually);
        db = new DBManager(this);
        db.open();
        init();

    }

    public void init(){
        edName = findViewById(R.id.productNameM);
        edSeri = findViewById(R.id.productSeriM);
        edQuantity = findViewById(R.id.productQuantityM);
        btnInc = findViewById(R.id.btnIncreaseM);
        btnDec = findViewById(R.id.btnDecreaseM);
    }

    public void addQuantityM(View view) {
        quantity += 5;
        edQuantity.setText(quantity+" ");
    }

    public void removeQuantityM(View view) {
        if (quantity == 0){
            btnDec.setEnabled(false);
        } else {
            quantity -= 5;
            edQuantity.setText(quantity+" ");
        }
    }

    public void addToDatabaseM(View view) {
        String name = String.valueOf(edName.getText());
        String seri = String.valueOf(edSeri.getText());
        int _quantity = edQuantity.length();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm' - 'dd.MM.yyyy");
        String date = sdf.format(new Date());
        db.insert(seri,name,_quantity,date);

        Toast.makeText(this, "ADD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        Intent list = new Intent(getApplicationContext(), ListFragment.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(list);
    }

    public void returnHome(View view) {
        Intent home_intent = new Intent(getApplicationContext(), HomeFragment.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}