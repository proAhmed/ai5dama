package aykhadma.droidahmed.com.aykhaama.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.HomBottomImageAdapter;
import aykhadma.droidahmed.com.aykhaama.controller.SetDataForLists;
import aykhadma.droidahmed.com.aykhaama.custom_service.CustomerServiceMain;
import aykhadma.droidahmed.com.aykhaama.home.HomeFragment;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnClickHomeBottom;
import aykhadma.droidahmed.com.aykhaama.my_account.MyProfile;
import aykhadma.droidahmed.com.aykhaama.offers.Offers;
import aykhadma.droidahmed.com.aykhaama.profile.Login;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;

public class MainActivity extends SlidingFragmentActivity implements OnClickHomeBottom{
ImageView imageToggleCategory,imageToggle,imgLogo,imgCart;
    private RecyclerView mRecyclerView;
    OnClickHomeBottom onClickHomeBottom;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SetDataForLists setDataForLists;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Utility utility = new Utility();
        utility.langChoosen(this,new StoreData(this).getLang());
        setBehindView();
        setDataForLists = new SetDataForLists();
        SlidingMenu sm = getSlidingMenu();
        sm.setMode(SlidingMenu.RIGHT);


        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
         sm.setFadeDegree(0.35f);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        setContentView(R.layout.activity_mains);
        initBottom();

        imageToggleCategory = (ImageView) findViewById(R.id.imageToggleCategory);
        imageToggle = (ImageView) findViewById(R.id.imageToggle);
        imgLogo = (ImageView) findViewById(R.id.logo);
        imageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggle();

            }
        });

        if(getIntent().getExtras()!=null){
            ShopCartMain fragmentHome = new ShopCartMain();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main, fragmentHome,"");
            ft.commit();
        }else{
            HomeFragment fragmentHome = new HomeFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main, fragmentHome,"");
            ft.commit();
        }


        imgCart = (ImageView) findViewById(R.id.imgCart);


    }
    private void setBehindView() {
        setBehindContentView(R.layout.menu_slide);

        //transaction fragment to sliding menu
        transactionFragments(MenuFragment.newInstance(), R.id.menu_slide);
    }
    public void transactionFragments(Fragment fragment, int viewResource) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(viewResource, fragment);
        ft.commit();
        toggle();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // imgCart.setVisibility(View.INVISIBLE);
    }

    private void initBottom(){
        mRecyclerView = (RecyclerView)  findViewById(R.id.reBottom);
        mRecyclerView.setHasFixedSize(true);
        onClickHomeBottom = this;
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);//new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomBottomImageAdapter(setDataForLists.viewBottom(),this,onClickHomeBottom);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBottomClick(int position) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (position){

            case 0:
                ft.add(R.id.main, new HomeFragment()).addToBackStack("");
                ft.commit();
                break;
            case 1:
                ft.add(R.id.main, new Offers()).addToBackStack("");
                ft.commit();
                break;
            case 2:
                ft.add(R.id.main, new CustomerServiceMain()).addToBackStack("");
                ft.commit();
                break;
            case 3:
                if(new StoreData(this).getActivate()==0){
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                }else  if(new StoreData(this).getActivate()==2){
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                }else  if(new StoreData(this).getActivate()==3){
                    ft.add(R.id.main, new MyProfile());
                    ft.commit();
                }else{
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                }

                break;
        }
    }
}
