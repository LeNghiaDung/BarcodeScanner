package ninh.main.mybarcodescanner.bin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ninh.main.mybarcodescanner.databinding.FragmentBinBinding;
import ninh.main.mybarcodescanner.databinding.FragmentBinListBinding;
import ninh.main.mybarcodescanner.databinding.FragmentListBinding;

public class BinFragment extends Fragment {
    private FragmentBinListBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBinListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
}
