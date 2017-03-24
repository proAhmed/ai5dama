package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnWholeSaleClick;
import aykhadma.droidahmed.com.aykhaama.model.HotelModel;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleCategoryItems;

/**
 * Created by ahmed on 7/19/2016.
 */
public class WholeSaleAdapter extends RecyclerView
        .Adapter<WholeSaleAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<WholeSaleCategoryItems> mDataset;
     Activity context;
    OnWholeSaleClick onWholeSaleClick;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        LinearLayout linWhole;
        TextView tvWholeItems;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgWholeSale);
            linWhole = (LinearLayout) itemView.findViewById(R.id.linWhole);
            tvWholeItems = (TextView) itemView.findViewById(R.id.tvWholeItems);

            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public WholeSaleAdapter(ArrayList<WholeSaleCategoryItems> myDataset, Activity context, OnWholeSaleClick onWholeSaleClick) {
        this.mDataset = myDataset;
        this.context = context;
        this.onWholeSaleClick = onWholeSaleClick;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.whole_sale_items, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" +
                mDataset.get(position).getPicture() + "&width="+ Utility.widthScrens(context)[0]+"&height="+Utility.widthScrens(context)[1]).into(holder.img);
            Log.d("wwww",Utility.widthScrens(context)[0]+"  "+Utility.widthScrens(context)[1]);
        holder.linWhole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWholeSaleClick.onWholeSaleClick(position);
            }
        });
        holder.tvWholeItems.setText(mDataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
