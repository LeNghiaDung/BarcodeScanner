package ninh.main.mybarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.DatabaseHelper;
import ninh.main.mybarcodescanner.ui.home.HomeFragment;

public class AddProduct extends AppCompatActivity {
    EditText productName, productDetail;
    TextView productSeri;
    Button addStorage;
    ImageButton remove,add;
    EditText productQuantity;
    Intent intent;
    DBManager dbManager;
    DatabaseHelper databaseHelper;
    String seri;
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        dbManager = new DBManager(this);
        dbManager.open();
        init();

        intent = getIntent();
        seri = intent.getStringExtra(DatabaseHelper.Seri);
        productSeri.setText(seri + " ");

    }

    private void init(){
        productName = findViewById(R.id.productTitle);
        productSeri = findViewById(R.id.productSeri);
        productDetail = findViewById(R.id.productDetail);
        addStorage = findViewById(R.id.btnAdd);
        remove = findViewById(R.id.btnDecrease);
        add = findViewById(R.id.btnIncrease);
        productQuantity = findViewById(R.id.productQuantity);
    }
    public void getData(){
        Cursor data = databaseHelper.getData("SELECT * FROM "+ DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.Seri +" LIKE "+ seri);

        quantity = data.getInt(2);

        productSeri.setText(data.getInt(0));
        productName.setText(data.getString(1));
        productQuantity.setText(data.getInt(2));
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
        String seri = productSeri.getText().toString();
        String name = productName.getText().toString();
        int quantity = productQuantity.getText().length();
        if (dbManager.insert(seri, name, quantity) == 1){
            Toast.makeText(this, "ADD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        }
    }
}