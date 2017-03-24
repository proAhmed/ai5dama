package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingCategoryItems;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;

/**
 * Created by ahmed on 7/19/2016.
 */
public class ShoppingProductAdapter extends RecyclerView
        .Adapter<ShoppingProductAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<ShoppingProductListItem> mDataset;
    Activity context;
    OnShopMainClick onShopMainClick;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView imgProduct;
        TextView tvProductName;
        public DataObjectHolder(View itemView) {
            super(itemView);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public ShoppingProductAdapter(ArrayList<ShoppingProductListItem> myDataset, Activity context, OnShopMainClick onShopMainClick) {
        this.mDataset = myDataset;
        this.context = context;
        this.onShopMainClick = onShopMainClick;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shoping_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.tvProductName.setText(mDataset.get(position).getName());
        Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" + mDataset.get(position).getPicture() + "&width=160&height=120").into(holder.imgProduct);

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShopMainClick.onShopClick(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
