package ninh.main.mybarcodescanner.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ninh.main.mybarcodescanner.ModifyProductActivity;
import ninh.main.mybarcodescanner.Product;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.sqlite.DBManager;
import ninh.main.mybarcodescanner.sqlite.DatabaseHelper;
import ninh.main.mybarcodescanner.ui.home.HomeFragment;

public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> products;
    int layout;

    public ListAdapter(Context context, ArrayList<Product> products, int layout) {
        this.context = context;
        this.products = products;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        ImageView imgProduct;
        TextView tvSeri, tvName, tvQuantity,tvDate;
        ImageButton imgEdit;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imgProduct = convertView.findViewById(R.id.imageProduct);
            holder.tvSeri = convertView.findViewById(R.id.tvSeri);
            holder.tvName = convertView.findViewById(R.id.tvNameProduct);
            holder.tvDate = convertView.findViewById(R.id.tvDateProduct);
            holder.tvQuantity = convertView.findViewById(R.id.tvQuantityProduct);
            holder.imgEdit = convertView.findViewById(R.id.imgEdit);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String seri = products.get(position).getSeri();
        String name = products.get(position).getNameProduct();
        String quantity = String.valueOf(products.get(position).getQuantity());
        String date = products.get(position).getDate();
        holder.tvSeri.setText(seri);
        holder.tvName.setText(name);
        holder.tvQuantity.setText(quantity);
        holder.tvDate.setText(date);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modify = new Intent(context, ModifyProductActivity.class);
                modify.putExtra(DatabaseHelper.Seri,seri);
                context.startActivity(modify);
            }
        });
        return convertView;
    }
}
