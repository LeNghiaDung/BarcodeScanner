package ninh.main.mybarcodescanner.bin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ninh.main.mybarcodescanner.Product;
import ninh.main.mybarcodescanner.R;
import ninh.main.mybarcodescanner.sqlite.DBManager;

public class BinAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> products;
    int layout;

    public BinAdapter(Context context, ArrayList<Product> products, int layout) {
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
        ImageView imgProductBin;
        TextView tvSeriBin, tvNameBin;
        ImageButton imgDeleteBin;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imgProductBin = convertView.findViewById(R.id.imageProductBin);
            holder.tvSeriBin = convertView.findViewById(R.id.tvSeriBin);
            holder.tvNameBin = convertView.findViewById(R.id.tvNameProductBin);
            holder.imgDeleteBin = convertView.findViewById(R.id.imgDeleteBin);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        String seri = products.get(position).getSeri();
        String name = products.get(position).getNameProduct();
        holder.tvNameBin.setText(name);
        holder.tvSeriBin.setText(seri);

        holder.imgDeleteBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager db = new DBManager(context);
                db.delete(seri);
            }
        });
        return convertView;
    }
}
