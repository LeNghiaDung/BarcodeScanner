package ninh.main.mybarcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.DatabaseHelper;
import ninh.main.mybarcodescanner.ui.home.HomeFragment;

public class  AddProduct extends AppCompatActivity {
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

    public void addToDatabase(View view) {
        String seri = productSeri.getText().toString();
        String name = productName.getText().toString();
        dbManager.insert(seri, name, quantity);
        Toast.makeText(this, "ADD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(this, HomeFragment.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        hideSoftKeyboard(this);
//        Intent intent1 = new Intent(this, List.class);
//        startActivity(intent1);
        }
//        this.returnHome();

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
    public void returnHome(View view) {
        Intent home_intent = new Intent(getApplicationContext(), HomeFragment.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}