package ninh.main.mybarcodescanner.list;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import ninh.main.mybarcodescanner.ListAdapter;
import ninh.main.mybarcodescanner.Product;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.databinding.FragmentListBinding;
import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.ModifyProductActivity;

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
        getData(); // Lay du lieu tu SQLite -> Cho vao products
        adapter = new ListAdapter(getActivity(),products,R.layout.fragment_list_list);
        listView.setAdapter(adapter);

//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent modify = new Intent(getActivity(), ModifyProductActivity.class);
//                modify.putExtra("seri",);
//            }
//        });
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
            products.add(new Product(seri,name,quantity));
        }
    }
}
