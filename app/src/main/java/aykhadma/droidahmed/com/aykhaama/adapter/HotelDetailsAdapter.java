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
import aykhadma.droidahmed.com.aykhaama.interfaces.OnContactAction;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnHotelMainClick;
import aykhadma.droidahmed.com.aykhaama.model.HotelDetailsModel;
import aykhadma.droidahmed.com.aykhaama.model.HotelModel;

/**
 * Created by ahmed on 7/19/2016.
 */
public class HotelDetailsAdapter extends RecyclerView
        .Adapter<HotelDetailsAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<HotelDetailsModel> mDataset;
     Activity context;
    OnContactAction onContactAction;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        TextView TvHotelName,TvHotelEmail,TvHotelPhone,TvHotelWebsite;
        ImageView img;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgHome);
            TvHotelName = (TextView) itemView.findViewById(R.id.TvHotelName);
            TvHotelEmail = (TextView) itemView.findViewById(R.id.TvHotelEmail);
            TvHotelPhone = (TextView) itemView.findViewById(R.id.TvHotelPhone);
            TvHotelWebsite = (TextView) itemView.findViewById(R.id.TvHotelWebsite);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public HotelDetailsAdapter(ArrayList<HotelDetailsModel> myDataset, Activity context, OnContactAction onContactAction) {
        this.mDataset = myDataset;
        this.context = context;
        this.onContactAction = onContactAction;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_items, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        Picasso.with(context).load(""+(mDataset.get(position).getPicture())).into(holder.img);
        holder.TvHotelName.setText(mDataset.get(position).getName());
        holder.TvHotelEmail.setText(mDataset.get(position).getEmail());
        holder.TvHotelPhone.setText(mDataset.get(position).getPhone());
        holder.TvHotelWebsite.setText(mDataset.get(position).getWebsite());

        holder.TvHotelEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactAction.onEmailClick(position);
            }
        });

        holder.TvHotelWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactAction.onWebsiteClick(position);
            }
        });

        holder.TvHotelPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactAction.onPhoneClick(position);
            }
        });





    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
