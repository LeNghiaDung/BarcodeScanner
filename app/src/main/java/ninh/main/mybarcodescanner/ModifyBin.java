package ninh.main.mybarcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.DatabaseHelper;
import ninh.main.mybarcodescanner.ui.home.HomeFragment;

public class ModifyBin extends Activity {
    private TextView nameProductText_bin, seriText_bin, dateText_bin,quantityText_bin;
    private Button khoiphuc;
    private ImageButton deleteBtn_bin;
    ImageButton remove,add;
    private DBManager dbManager;
    private DatabaseHelper helper;
    String seri;
    String name;
    int quantity = 0;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_bin);
        setTitle("Modify Bin Record");
        dbManager = new DBManager(this);
        dbManager.open();
        helper = new DatabaseHelper(this);  // Add this line to initialize the DatabaseHelper
        init();

        Intent intent = getIntent();
        seri = intent.getStringExtra(DatabaseHelper.Seri);
        seriText_bin.setText(seri);
        getData();
    }
    private void init(){
        seriText_bin = findViewById(R.id.seri_edittext_bin);
        dateText_bin = findViewById(R.id.tvDateModifyBin);
        nameProductText_bin =  findViewById(R.id.nameProduct_edittext_bin);
        quantityText_bin = findViewById(R.id.quantity_edittext_bin);
        khoiphuc = (Button) findViewById(R.id.btn_khoiphuc);
        add = findViewById(R.id.btnIncrease_bin);
        remove = findViewById(R.id.btnDecrease_bin);
        deleteBtn_bin = (ImageButton) findViewById(R.id.btn_delete_bin);

        add.setEnabled(false);
        remove.setEnabled(false);
    }

    public void getData(){
        try (Cursor data = dbManager.getData_bin(seri)) {
            if (data != null && data.moveToNext()) {
                // Check if the Cursor has data and move to the first row
                Toast.makeText(this, "Existed", Toast.LENGTH_SHORT).show();
                // Retrieve data from the Cursor
                name = data.getString(1);
                quantity = data.getInt(2);
                date = data.getString(3);

                // Set the values to the corresponding views
                nameProductText_bin.setText(name);
                quantityText_bin.setText(String.valueOf(quantity));
                dateText_bin.setText(date);
            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show();
        }
    }
//
//    public void removeQuantityModify_bin(View view) {
//        if (quantity == 0){
//            remove.setEnabled(false);
//        } else {
//            remove.setEnabled(true);
//            quantity -= 5;
//            quantityText_bin.setText(quantity+"");
//        }
//    }
//    public void addQuantityModify_bin(View view) {
//        quantity +=5;
//        quantityText_bin.setText(quantity+"");
//    }


    public void khoiPhuc(View view) {
        String seri = seriText_bin.getText().toString();
        String name = nameProductText_bin.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm' - 'dd.MM.yyyy");
        String date = sdf.format(new Date());

        dbManager.insert(seri, name, quantity,date);
        Toast.makeText(this, "ADD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        dbManager.delete_bin(seri);
        this.returnScanModify_bin(view);
    }

    public void deleteData_bin(View view) {
        dbManager.delete_bin(seri);
        this.returnScanModify_bin(view);
    }

    public void returnScanModify_bin(View view) {
        Intent home_intent = new Intent(getApplicationContext(), HomeFragment.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
