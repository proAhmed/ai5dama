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
import aykhadma.droidahmed.com.aykhaama.interfaces.OnClickHomeMenu;

/**
 * Created by ahmed on 7/19/2016.
 */
public class HomSmallImageAdapter extends RecyclerView
        .Adapter<HomSmallImageAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<Integer> mDataset;
     Activity context;
    OnClickHomeMenu onClickHomeMenu;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        public DataObjectHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imgHome);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public HomSmallImageAdapter(ArrayList<Integer> myDataset, Activity context,OnClickHomeMenu onClickHomeMenu) {
        this.mDataset = myDataset;
        this.context = context;
        this.onClickHomeMenu = onClickHomeMenu;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_img_bottom_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {




        if (mDataset.size()>0) {

            holder.img.setImageResource(mDataset.get(position));
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickHomeMenu.onClick(position);
                }
            });
         }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
