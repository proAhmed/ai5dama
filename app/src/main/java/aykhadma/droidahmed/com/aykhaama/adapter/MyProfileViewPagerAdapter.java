package aykhadma.droidahmed.com.aykhaama.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import aykhadma.droidahmed.com.aykhaama.my_account.MyFavourite;
import aykhadma.droidahmed.com.aykhaama.my_account.MyProfile;
import aykhadma.droidahmed.com.aykhaama.my_account.MyShoppingCart;

/**
 * Created by ahmed on 8/17/2016.
 */
public class MyProfileViewPagerAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MyProfileViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MyFavourite tab1 = new MyFavourite();
                return tab1;
            case 1:
                MyShoppingCart tab2 = new MyShoppingCart();
                return tab2;
            case 2:
                MyFavourite tab3 = new MyFavourite();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
