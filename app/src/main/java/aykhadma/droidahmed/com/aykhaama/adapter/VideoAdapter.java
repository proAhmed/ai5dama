package aykhadma.droidahmed.com.aykhaama.adapter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnVideoAction;
import aykhadma.droidahmed.com.aykhaama.model.VideoModel;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;

/**
 * Created by ahmed on 7/19/2016.
 */
public class VideoAdapter extends RecyclerView
        .Adapter<VideoAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
     private ArrayList<VideoModel> mDataset;
     Activity context;
    OnVideoAction onVideoAction;
     public static class DataObjectHolder extends RecyclerView.ViewHolder
    {
         Button btnSeeMore;
        TextView tvVideoName,tvVideoViews,tvLike,tvDislike;
        ImageView imgFavorite,imgShare,imgDislike,imgLike;
        VideoView vdItem;
        public DataObjectHolder(View itemView) {
            super(itemView);
             Log.i(LOG_TAG, "Adding Listener");
            btnSeeMore = (Button) itemView.findViewById(R.id.btnSeeMore);
            tvVideoName = (Button) itemView.findViewById(R.id.tvVideoName);
            tvVideoViews = (Button) itemView.findViewById(R.id.tvVideoLikes);
            tvLike = (Button) itemView.findViewById(R.id.tvLike);
            tvDislike = (Button) itemView.findViewById(R.id.tvDislike);
            imgFavorite = (ImageView) itemView.findViewById(R.id.imgFavorite);
            imgShare = (ImageView) itemView.findViewById(R.id.imgShare);
            imgDislike = (ImageView) itemView.findViewById(R.id.imgDislike);
            imgLike = (ImageView) itemView.findViewById(R.id.imgLike);
            vdItem = (VideoView) itemView.findViewById(R.id.vdItem);
        }
    }

    public VideoAdapter( Activity context,ArrayList<VideoModel> myDataset,OnVideoAction onVideoAction) {
        this.mDataset = myDataset;
        this.context = context;
        this.onVideoAction = onVideoAction;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_items, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

       holder.tvVideoName.setText(mDataset.get(position).getName());
        holder.tvVideoViews.setText(mDataset.get(position).getViewed());
        holder.vdItem.setVideoPath(mDataset.get(position).getUrl());
        holder.vdItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVideoAction.onVideoClick(position);
            }
        });
        holder.vdItem.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // This is just to show image when loaded
                mp.start();
                mp.pause();
            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
