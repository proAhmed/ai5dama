package aykhadma.droidahmed.com.aykhaama.hotels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.HotelDetailsAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.ImageAdater;
import aykhadma.droidahmed.com.aykhaama.adapter.ImageAdaters;
import aykhadma.droidahmed.com.aykhaama.adapter.OfferViewPagerAdapter;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnContactAction;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnImageClick;

/**
 * Created by ahmed on 8/4/2016.
 */
public class HotelDetails extends Fragment implements OnContactAction {
    View view;
    HotelDetailsAdapter imageAdapter;
    ArrayList<Integer> arrayList;
    RecyclerView pager;
    ImageView imgFav;
    int hotelPos;
    OnContactAction onContactAction;
     @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_details,container,false);
         imgFav = (ImageView) view.findViewById(R.id.imgFav);
         pager = (RecyclerView) view.findViewById(R.id.pager);
         arrayList = new ArrayList<>();
         Bundle bundle = getArguments();
         bundle.getInt("position",0);
         onContactAction = this;
         arrayList.add(R.drawable.sliderr);
         arrayList.add(R.drawable.sliderr);
         arrayList.add(R.drawable.sliderr);
         arrayList.add(R.drawable.sliderr);
         arrayList.add(R.drawable.sliderr);
//         imageAdapter = new HotelDetailsAdapter(getActivity(), arrayList);
//         pager.setAdapter(imageAdapter);
imgFav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        imgFav.setImageResource(R.drawable.favorite_icon_1);
    }
});
        return view;
    }



    @Override
    public void onWebsiteClick(int pos) {

    }

    @Override
    public void onPhoneClick(int pos) {

    }

    @Override
    public void onEmailClick(int pos) {

    }
}
