package aykhadma.droidahmed.com.aykhaama.airlines;

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
import aykhadma.droidahmed.com.aykhaama.adapter.AirLinesAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.CustomPagerAdapter;
import aykhadma.droidahmed.com.aykhaama.api.GetAirLine;
import aykhadma.droidahmed.com.aykhaama.api.GetSlide;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAirLineClick;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.AirLineMain;
import aykhadma.droidahmed.com.aykhaama.model.AirLineModel;
 import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 8/4/2016.
 */
public class AirLineScreen extends Fragment implements OnAirLineClick {
    View view;
    RecyclerView reAirLine;
     ArrayList<AirLineModel> airLineModelArrayList;
    OnLoadingComplete onLoadingComplete;
    OnAirLineClick onAirLineClick;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.air_line_main,container,false);
        declare();
        return view;
    }
    private void declare(){
        airLineModelArrayList = new ArrayList<>();
        reAirLine = (RecyclerView) view.findViewById(R.id.reAirLine);
        reAirLine.setLayoutManager(new LinearLayoutManager(getActivity()));
        onAirLineClick = this;
        addData();

    }
    private void addData() {
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    AirLineMain airLineMain = (AirLineMain) result;
                    airLineModelArrayList = airLineMain.getData();

                    if (airLineModelArrayList.size() > 0) {
                        AirLinesAdapter adapter = new AirLinesAdapter(getActivity(), airLineModelArrayList,onAirLineClick);

                         reAirLine.setAdapter(adapter);                    }

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetAirLine task = new GetAirLine(getActivity(), onLoadingComplete);
            task.execute();

        }
    }

    @Override
    public void onAirLineClick(int pos) {
        Utility.openBrowser(getActivity(),airLineModelArrayList.get(pos).getWebsite());

    }
}
