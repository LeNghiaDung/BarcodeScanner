package ninh.main.mybarcodescanner.sqlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import ninh.main.mybarcodescanner.R;

public class ModifyProductActivity extends Activity implements OnClickListener{
    private TextView seriText;
    private TextView nameProductText;
    private Button saveBtn;
    private EditText quantityText;

    private long _seri;
    private int _quantity;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_product);

        dbManager = new DBManager(this);
        dbManager.open();

        seriText = findViewById(R.id.seri_edittext);
        nameProductText =  findViewById(R.id.nameProduct_edittext);
        quantityText = findViewById(R.id.quantity_edittext);
        saveBtn = (Button) findViewById(R.id.btn_save);
//        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        Long seri = Long.valueOf(intent.getStringExtra("seri"));
        String nameProduct = intent.getStringExtra("nameProduct");
        Integer quantity = Integer.valueOf(intent.getStringExtra("quantity"));

//        _seri = Long.parseLong(String.valueOf(seri));
//        nameProductText.setText(nameProduct);
        _quantity = Integer.parseInt(String.valueOf(quantity));

        saveBtn.setOnClickListener(this);
//        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_save):
//                Long _seri = Long.parseLong((String.valueOf(seri)));
                String nameProduct = nameProductText.getText().toString();
//                Integer quantity = Integer.parseInt(String.valueOf(_quantity));

                dbManager.update(_seri, nameProduct, _quantity);
                this.returnHome();
                break;

//            case (R.id.btn_delete):
//                dbManager.delete(_seri);
//                this.returnHome();
//                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ProductListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
