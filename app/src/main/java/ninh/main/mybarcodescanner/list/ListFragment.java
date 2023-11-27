package ninh.main.mybarcodescanner.list;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ninh.main.mybarcodescanner.Product;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.databinding.FragmentListBinding;
import ninh.main.mybarcodescanner.sqlite.DBManager;

public class ListFragment extends Fragment {
    private FragmentListBinding binding;
    private ListView listView;
    private ListAdapter adapter;
    private ArrayList<Product> products;
    private DBManager manager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.list);
        products = new ArrayList<>();
        getData();
        adapter = new ListAdapter(getActivity(),products,R.layout.fragment_list_list);
        listView.setAdapter(adapter);
        return root;

    }

    private void getData(){
        manager = new DBManager(getActivity());
        manager.open();
        Cursor data = manager.getAllData();
        while(data.moveToNext()){
            String seri = data.getString(0);
            String name = data.getString(1);
            int quantity = data.getInt(2);
            String date = data.getString(3);
            products.add(new Product(seri,name,quantity,date));
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.SoftName){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {

                    return o1.getNameProduct().compareToIgnoreCase(o2.getNameProduct());
                }
            });
            adapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Soft", Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.Search){
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<Product> filteredProducts = new ArrayList<>();
                    for (Product product : products) {
                        if (product.getNameProduct().toLowerCase().contains(newText.toLowerCase())) {
                            filteredProducts.add(product);
                        }
                    }
                    adapter = new ListAdapter(getActivity(), filteredProducts, R.layout.fragment_list_list);
                    listView.setAdapter(adapter);
                    return true;
                }
            });
            Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
