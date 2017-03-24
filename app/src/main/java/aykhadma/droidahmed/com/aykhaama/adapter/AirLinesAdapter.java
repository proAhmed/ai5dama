package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAirLineClick;
import aykhadma.droidahmed.com.aykhaama.model.AirLineModel;
import aykhadma.droidahmed.com.aykhaama.model.CompaniesModel;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 7/19/2016.
 */
public class AirLinesAdapter extends RecyclerView
        .Adapter<AirLinesAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<AirLineModel> mDataset;
     Activity context;
    OnAirLineClick onAirLineClick;

     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        LinearLayout linAirLine;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgAirLine);
            linAirLine = (LinearLayout) itemView.findViewById(R.id.linAirLine);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public AirLinesAdapter( Activity context,ArrayList<AirLineModel> myDataset,OnAirLineClick onAirLineClick) {
        this.mDataset = myDataset;
        this.context = context;
        this.onAirLineClick = onAirLineClick;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.airline_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        Picasso.with(context).load("http://ai5adma.com/API/ar/general/thumb?url=" +
                mDataset.get(position).getPicture() + "&width="+ Utility.widthScrens(context)[0]/2+"&height="+180).into(holder.img);
Log.d("uuuiiimm","http://ai5adma.com/API/ar/general/thumb?url=" +
        mDataset.get(position).getPicture() + "&width="+ Utility.widthScrens(context)[0]/2+"&height="+180);

        Picasso.with(context).load(mDataset.get(position).getPicture()).into(holder.img);
        holder.linAirLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAirLineClick.onAirLineClick(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
