package aykhadma.droidahmed.com.aykhaama.videos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.VideoAdapter;
 import aykhadma.droidahmed.com.aykhaama.api.GetVideos;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.VideoModel;
import aykhadma.droidahmed.com.aykhaama.model.VideoModelMain;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 8/4/2016.
 */
public class VideoMain extends Fragment {
    View view;
    RecyclerView reVideo;
     VideoAdapter videoAdapter;
    ArrayList<VideoModel> videoArrayList;
    OnLoadingComplete onLoadingComplete;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.video_main,container,false);
         declare();
        return view;
    }
    private void declare(){
        videoArrayList = new ArrayList<>();
        reVideo = (RecyclerView) view.findViewById(R.id.reVideo);
        reVideo.setLayoutManager(new LinearLayoutManager(getActivity()));
        addData();
     }
    private void addData(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    VideoModelMain companiesMain = (VideoModelMain) result;
                    videoArrayList = companiesMain.getData();

                    if (videoArrayList.size() > 0) {
//                        videoAdapter = new VideoAdapter( getActivity(),videoArrayList);
//                        reVideo.setAdapter(videoAdapter);
                    }

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetVideos task = new GetVideos(getActivity(), onLoadingComplete);
            task.execute();

        }

    }



}
