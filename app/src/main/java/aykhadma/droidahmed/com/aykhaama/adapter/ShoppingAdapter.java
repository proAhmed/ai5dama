package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.model.HotelModel;
import aykhadma.droidahmed.com.aykhaama.model.ShoppingModel;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingCategoryItems;
import aykhadma.droidahmed.com.aykhaama.utility.RoundRectCornerImageView;

/**
 * Created by ahmed on 7/19/2016.
 */
public class ShoppingAdapter extends RecyclerView
        .Adapter<ShoppingAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<ShoppingCategoryItems> mDataset;
     Activity context;
    OnShopMainClick onShopMainClick;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        RoundedImageView  img;
        TextView tvCategory;
        RelativeLayout linCategory;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (RoundedImageView) itemView.findViewById(R.id.imgHome);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            linCategory = (RelativeLayout) itemView.findViewById(R.id.linCategory);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public ShoppingAdapter(ArrayList<ShoppingCategoryItems> myDataset, Activity context,OnShopMainClick onShopMainClick) {
        this.mDataset = myDataset;
        this.context = context;
        this.onShopMainClick = onShopMainClick;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_main_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.tvCategory.setText(mDataset.get(position).getName());
        if(mDataset.get(position).getName().length()>13){
            holder.tvCategory.setTextSize(12);
        }
        Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" + mDataset.get(position).getPicture() + "&width=140&height=135").into(holder.img);
Log.d("iii","http://ai5adma.com/API/ar/general/thumb?url=" + mDataset.get(position).getPicture() + "&width=160&height=120");
        holder.linCategory.setOnClickListener(new View.OnClickListener() {
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
