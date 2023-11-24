package ninh.main.mybarcodescanner.bin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ninh.main.mybarcodescanner.Product;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.databinding.FragmentBinListBinding;
import ninh.main.mybarcodescanner.sqlite.DBManager;

public class BinFragment extends Fragment {
    private FragmentBinListBinding binding;
    private ListView listView;
    private BinAdapter adapter;
    private ArrayList<Product> products;
    private DBManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBinListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listBin);
        products = new ArrayList<>();
        getDataBin();
        adapter = new BinAdapter(getActivity(),products,R.layout.fragment_bin);
        listView.setAdapter(adapter);
        products.add(new Product("033432020203" , "bàn" , 10));

        return root;
    }
    private void getDataBin(){
        manager = new DBManager(getActivity());
        manager.open();
        Cursor data = manager.getAllData_bin();
        while (data.moveToNext()){
            String seri = data.getString(0);
            String name = data.getString(1);
            int quantity = data.getInt(2);
            products.add(new Product(seri,name,quantity));
        }
    }
}
