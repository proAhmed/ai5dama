package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnClickHomeBottom;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnHotelMainClick;
import aykhadma.droidahmed.com.aykhaama.model.AirLineModel;
import aykhadma.droidahmed.com.aykhaama.model.HotelModel;

/**
 * Created by ahmed on 7/19/2016.
 */
public class HotelAdapter extends RecyclerView
        .Adapter<HotelAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<HotelModel> mDataset;
     Activity context;
    OnHotelMainClick onHotelMainClick;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgHome);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public HotelAdapter(ArrayList<HotelModel> myDataset, Activity context, OnHotelMainClick onHotelMainClick) {
        this.mDataset = myDataset;
        this.context = context;
        this.onHotelMainClick = onHotelMainClick;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_main_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.img.setImageResource(mDataset.get(position).getImage());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHotelMainClick.onHotelClick(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
