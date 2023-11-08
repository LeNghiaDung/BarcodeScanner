package ninh.main.mybarcodescanner.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ninh.main.mybarcodescanner.AddProduct;
import ninh.main.mybarcodescanner.R;

public class ProductListActivity extends AppCompatActivity{
    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper.Seri,
            DatabaseHelper.NameProduct, DatabaseHelper.Quantity };

    final int[] to = new int[] { R.id.tvSeri, R.id.tvNameProduct, R.id.tvQuantityProduct };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_list_list);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list);
        //listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.fragment_list, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView seriTextView = (TextView) view.findViewById(R.id.tvSeri);
                TextView nameProductTextView = (TextView) view.findViewById(R.id.tvNameProduct);
                TextView quantityTextView = (TextView) view.findViewById(R.id.tvQuantityProduct);

                String seri = seriTextView.getText().toString();
                String nameProduct = nameProductTextView.getText().toString();
                String quantity = quantityTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyProductActivity.class);
                modify_intent.putExtra("nameProduct", nameProduct);
                modify_intent.putExtra("quantity", quantity);
                modify_intent.putExtra("seri", seri);

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == R.id.btnAdd) {
//            Intent add_mem = new Intent(this, AddProduct.class);
//            startActivity(add_mem);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
