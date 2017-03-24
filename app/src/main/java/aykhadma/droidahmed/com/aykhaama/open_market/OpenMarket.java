package aykhadma.droidahmed.com.aykhaama.open_market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.OpenMarketAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.ShoppingAdapter;
import aykhadma.droidahmed.com.aykhaama.model.OpenMarketModel;
import aykhadma.droidahmed.com.aykhaama.model.ShoppingModel;

/**
 * Created by ahmed on 8/3/2016.
 */
public class OpenMarket extends Fragment {
    View view;
    RecyclerView reOpenMarket;
    OpenMarketAdapter openMarketAdapter;
    ArrayList<OpenMarketModel> openMarketModelArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.open_market, container, false);
         declare();
        return view;
    }
    private void declare(){
        openMarketModelArrayList = new ArrayList<>();
        reOpenMarket = (RecyclerView) view.findViewById(R.id.reOpenMarket);
        reOpenMarket.setLayoutManager(new GridLayoutManager(getActivity(),3));
        openMarketAdapter = new OpenMarketAdapter(openMarketModelArrayList,getActivity());
        reOpenMarket.setAdapter(openMarketAdapter);
    }
}
