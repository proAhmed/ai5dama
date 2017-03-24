package aykhadma.droidahmed.com.aykhaama.hotels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.HotelAdapter;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnHotelMainClick;
import aykhadma.droidahmed.com.aykhaama.model.HotelModel;

/**
 * Created by ahmed on 8/4/2016.
 */
public class HotelMain extends Fragment implements OnHotelMainClick{
    View view;
    RecyclerView reHotel;
     HotelAdapter hotelAdapter;
    ArrayList<HotelModel> hotelModelArrayList;
    OnHotelMainClick onHotelMainClick;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_main,container,false);
        reHotel = (RecyclerView) view.findViewById(R.id.reHotel);
        declare();
        return view;
    }
    private void declare(){
        hotelModelArrayList = new ArrayList<>();
        reHotel = (RecyclerView) view.findViewById(R.id.reHotel);
        reHotel.setLayoutManager(new LinearLayoutManager(getActivity()));
        addData();
        onHotelMainClick =this;
        hotelAdapter = new HotelAdapter(hotelModelArrayList,getActivity(),onHotelMainClick);
        reHotel.setAdapter(hotelAdapter);
    }
    private void addData(){
        hotelModelArrayList.add(new HotelModel(R.drawable.one_star));
        hotelModelArrayList.add(new HotelModel(R.drawable.two_star));
        hotelModelArrayList.add(new HotelModel(R.drawable.three_stars));
        hotelModelArrayList.add(new HotelModel(R.drawable.four_stars));
        hotelModelArrayList.add(new HotelModel(R.drawable.five_stars));

    }

    @Override
    public void onHotelClick(int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HotelDetails hotelDetails = new HotelDetails();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        hotelDetails.setArguments(bundle);
        ft.add(R.id.main, new HotelDetails()).addToBackStack("");
        ft.commit();
    }
}

