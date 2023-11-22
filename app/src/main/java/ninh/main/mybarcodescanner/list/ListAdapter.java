package ninh.main.mybarcodescanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
        TextView tvSeri, tvName, tvQuantity;
        ImageButton imgEdit, imgDelete;
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
            holder.tvQuantity = convertView.findViewById(R.id.tvQuantityProduct);
            holder.imgEdit = convertView.findViewById(R.id.imgEdit);
            holder.imgDelete = convertView.findViewById(R.id.imgDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String seri = products.get(position).getSeri();
        String name = products.get(position).getNameProduct();
        String quantity = String.valueOf(products.get(position).getQuantity());
        holder.tvSeri.setText(seri);
        holder.tvName.setText(name);
        holder.tvQuantity.setText(quantity);

        return convertView;
    }
}
