package ninh.main.mybarcodescanner.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ninh.main.mybarcodescanner.R;

public class AddProductActivity extends Activity implements OnClickListener {
    private Button addTodoBtn;
    private TextView seriTextView;
    private TextView nameProductTextView;
    private EditText quantityEditText;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");

        setContentView(R.layout.activity_add_product);
        seriTextView = (TextView) findViewById(R.id.productSeri);
        nameProductTextView = (TextView) findViewById(R.id.productTitle);
        quantityEditText = (EditText) findViewById(R.id.productQuantity);

        addTodoBtn = (Button) findViewById(R.id.btnAdd);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:

                final long seri = Long.parseLong(seriTextView.getText().toString());
                final String name = nameProductTextView.getText().toString();
                final int quantity = Integer.parseInt(quantityEditText.getText().toString());

                dbManager.insert(seri , name, quantity);

                Intent main = new Intent(AddProductActivity.this, ProductListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
        }
    }
    public void returnHome() {
        Intent home_intent = new Intent(this, ProductListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
