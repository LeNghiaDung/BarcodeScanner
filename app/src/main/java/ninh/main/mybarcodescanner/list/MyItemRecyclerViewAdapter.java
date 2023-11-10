package ninh.main.mybarcodescanner.list;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ninh.main.mybarcodescanner.list.placeholder.PlaceholderContent.PlaceholderItem;
import ninh.main.mybarcodescanner.databinding.FragmentListBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mSeri.setText(mValues.get(position).seri);
        holder.mName.setText(mValues.get(position).name);
        holder.mName.setText(mValues.get(position).quantity);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mSeri;
        public final TextView mName;
        public final TextView mQuantity;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentListBinding binding) {
            super(binding.getRoot());
            mSeri = binding.tvSeri;
            mName = binding.tvNameProduct;
            mQuantity = binding.tvQuantityProduct;
        }
    }
}