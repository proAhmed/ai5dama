package aykhadma.droidahmed.com.aykhaama.controller;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;

/**
 * Created by ahmed on 7/19/2016.
 */
public class SetDataForLists {
    public ArrayList<Integer> homeSmallImageList(){
        ArrayList<Integer> home = new ArrayList<>();
        home.add(R.drawable.shopping_icon);
        home.add(R.drawable.wholesale_icon);
        home.add(R.drawable.open_market_icon);

        home.add(R.drawable.companies);
        home.add(R.drawable.videos);
        home.add(R.drawable.airlines);
        home.add(R.drawable.hotels);
        return home;
    }

    public ArrayList<Integer> viewPagerList(){
        ArrayList<Integer> home = new ArrayList<>();
        home.add(R.drawable.slide_1_op2);
        home.add(R.drawable.slide_2_op2);
        home.add(R.drawable.slide_3_op2);
        home.add(R.drawable.slide_4_op2);

        return home;
    }
    public ArrayList<Integer> viewBottom(){
        ArrayList<Integer> home = new ArrayList<>();
        home.add(R.drawable.home_icon);
        home.add(R.drawable.offers_icon);
        home.add(R.drawable.shoppingcar);
        home.add(R.drawable.customer_service);
        home.add(R.drawable.profile_icon);

        return home;
    }
    public ArrayList<Integer> viewBottom(int position){
        ArrayList<Integer> home = new ArrayList<>();
        switch (position){
            case 0:
                home.add(R.drawable.clicked_home_icon);
                home.add(R.drawable.offers_icon);
                home.add(R.drawable.customer_service);
                home.add(R.drawable.profile_icon);
                break;
            case 1:
                home.add(R.drawable.home_icon);
                home.add(R.drawable.clicked_offers_icon);
                home.add(R.drawable.customer_service);
                home.add(R.drawable.profile_icon);
                break;
            case 2:
                home.add(R.drawable.home_icon);
                home.add(R.drawable.offers_icon);
                home.add(R.drawable.clicked_customer_service);
                home.add(R.drawable.profile_icon);
                break;
            case 3:
                home.add(R.drawable.home_icon);
                home.add(R.drawable.offers_icon);
                home.add(R.drawable.customer_service);
                home.add(R.drawable.clicked_profile_icon);
                break;
        }



        return home;
    }
}
