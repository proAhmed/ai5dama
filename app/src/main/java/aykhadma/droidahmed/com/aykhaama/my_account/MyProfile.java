package aykhadma.droidahmed.com.aykhaama.my_account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.MyProfileViewPagerAdapter;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.UserSignOutResponse;
import aykhadma.droidahmed.com.aykhaama.profile.EditProfile;
import aykhadma.droidahmed.com.aykhaama.profile_api.SignOutApi;
import aykhadma.droidahmed.com.aykhaama.shopping.ShopListProduct;
import aykhadma.droidahmed.com.aykhaama.shopping_api.SearchShoppingApi;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;

/**
 * Created by ahmed on 8/8/2016.
 */
public class MyProfile extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tvEditProfile,tvProfileName,tvSignOut;
    ImageView profilePic;
    OnLoadingComplete onLoadingComplete;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_main_page,container,false);
        tvEditProfile = (TextView) view.findViewById(R.id.tvEditProfile);
        tvProfileName = (TextView) view.findViewById(R.id.tvProfileName);
        tvSignOut = (TextView) view.findViewById(R.id.tvSignOut);
        profilePic = (ImageView) view.findViewById(R.id.profilePic);
        addTabPager(view);
        action();
        return view;
    }
    private void addTabPager(View view){
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_order)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.my_favourite)));

            viewPager = (ViewPager) view.findViewById(R.id.pager);
        final MyProfileViewPagerAdapter adapter = new MyProfileViewPagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void action(){
        tvProfileName.setText(new StoreData(getActivity()).getFullName());
        Picasso.with(getActivity()).load("http://ai5adma.com/API/ar/general/thumb?url=" +new StoreData(getActivity()).getPic()+"&width=130&height=130").into(profilePic);
        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });
        tvSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
    }
    private void signOut(){
        if (Utility.isNetworkConnected( getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    UserSignOutResponse shoppingProductListMain = (UserSignOutResponse) result;
                  String data =  shoppingProductListMain.getData();
                    new StoreData(getActivity()).setToken("");
                    Toast.makeText(getActivity(),data,Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog( getActivity(), false);
                }
            };

            SignOutApi task = new SignOutApi( getActivity(), onLoadingComplete);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }
}
