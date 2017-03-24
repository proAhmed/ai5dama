package aykhadma.droidahmed.com.aykhaama.hotels;

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
import aykhadma.droidahmed.com.aykhaama.adapter.HotelAdapter;
import aykhadma.droidahmed.com.aykhaama.model.AirLineModel;
import aykhadma.droidahmed.com.aykhaama.model.HotelModel;

/**
 * Created by ahmed on 8/4/2016.
 */
public class HotelListName extends Fragment {
    RecyclerView reHotelNameList;
    View view;
     HotelAdapter airLinesAdapter;
    ArrayList<HotelModel> hotelArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_name_list,container,false);
         declare();
        return view;
    }

    private void declare(){
        hotelArrayList = new ArrayList<>();
        reHotelNameList = (RecyclerView) view.findViewById(R.id.reHotelNameList);
        reHotelNameList.setLayoutManager(new LinearLayoutManager(getActivity()));
      //  airLinesAdapter = new HotelAdapter(hotelArrayList,getActivity());
        //reHotelNameList.setAdapter(airLinesAdapter);
    }
}
