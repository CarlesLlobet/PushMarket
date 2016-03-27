package xyz.carlesllobet.pushmarket.UI;

/**
 * Created by CarlesLlobet on 01/02/2016.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.Domain.Product;
import xyz.carlesllobet.pushmarket.R;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.AdapterViewHolder>{

    private Context context;
    UserFunctions userFunctions = new UserFunctions();
    ArrayList<HashMap<String, String>> products;

    public ProductsAdapter(Context con) {
        products = userFunctions.getAllProducts(con);
    }

    @Override
    public ProductsAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.product_row, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsAdapter.AdapterViewHolder adapterViewholder, int position) {
        adapterViewholder.name.setText(products.get(position).getName());
        adapterViewholder.punt.setText(products.get(position).getPunt().toString());
        adapterViewholder.foto.setImageURI(products.get(position).getFoto());
    }

    @Override
    public int getItemCount(){ return products.size(); }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        public ImageView foto;
        public TextView name;
        public TextView punt;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.foto = (ImageView) itemView.findViewById(R.id.foto);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.punt = (TextView) itemView.findViewById(R.id.punt);
        }
    }
}