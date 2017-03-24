package aykhadma.droidahmed.com.aykhaama.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aykhadma.droidahmed.com.aykhaama.R;

/**
 * Created by ahmed on 12/25/2016.
 */
public class FavoriteMain extends Fragment {
    private FragmentTabHost mTabHost;
    public FavoriteMain() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.shop_cart_main, container, false);

        mTabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.frShoppingCart);

        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.shopping_favorite))
                .setIndicator(getResources().getString(R.string.shopping_favorite)),
                MyFavouritesShopping.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.wholesale_favorite))
                .setIndicator(getResources().getString(R.string.wholesale_favorite)),
                MyFavouritesWholeSale.class, null);


        return view;
    }
}
