package ninh.main.mybarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.DatabaseHelper;
import ninh.main.mybarcodescanner.ui.home.HomeFragment;

public class AddProduct extends AppCompatActivity {
    ImageView productImage;
    TextView productName, productSeri, productDetail;
    Button addStorage;
    ImageButton remove,add;
    EditText productQuantity;
    Intent intent;
    DBManager dbManager;
    DatabaseHelper databaseHelper;

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        init();
        intent = getIntent();
        Long seri = Long.valueOf(intent.getStringExtra("seri"));
        productSeri.setText(seri + " ");

    }

    private void init(){
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productTitle);
        productSeri = findViewById(R.id.productSeri);
        productDetail = findViewById(R.id.productDetail);
        addStorage = findViewById(R.id.btnAdd);
        remove = findViewById(R.id.btnDecrease);
        add = findViewById(R.id.btnIncrease);
        productQuantity = findViewById(R.id.productQuantity);
    }

    public void removeQuantity(View view) {
        if (quantity == 0){
            remove.setEnabled(false);
        } else {
            remove.setEnabled(true);
            quantity -= 5;
            productQuantity.setText(quantity+"");
        }
    }

    public void addQuantity(View view) {
        quantity +=5;
        productQuantity.setText(quantity+"");
    }

    public void returnScan(View view) {
        finish();
    }

    public void addToDatabase(View view) {
        ContentValues values = new ContentValues();
        values.put(databaseHelper.seri,);
    }
}