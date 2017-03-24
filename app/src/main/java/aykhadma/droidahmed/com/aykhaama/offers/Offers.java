package aykhadma.droidahmed.com.aykhaama.offers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.OfferViewPagerAdapter;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnImageClick;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.OfferItems;
import aykhadma.droidahmed.com.aykhaama.model.OfferMain;
import aykhadma.droidahmed.com.aykhaama.offer_api.GetOffer;
import aykhadma.droidahmed.com.aykhaama.offer_api.SearchOfferApi;
import aykhadma.droidahmed.com.aykhaama.shopping.ShopListProduct;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


/**
 * Created by ahmed on 8/20/2016.
 */
public class Offers extends Fragment
       implements ViewPager.OnPageChangeListener,OnImageClick
{
    RelativeLayout reSearch;

    ViewPager viewPager;
    LinearLayout page_indicator;
    int dotscount;
    ImageView[] dots;
    OfferViewPagerAdapter viewPagerAdapter;
    ViewPager.OnPageChangeListener vOnPageChangeListener;
    //int[] imgs = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher };
   //  public  static int[] imgs;
  OnLoadingComplete onLoadingComplete;
    OfferMain offerMain;
    ArrayList<OfferItems> offerItems;
    OnImageClick onImageClick;
    ImageView imgCall,imgShare;
    TextView tvOffersViews;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       /* fragmentclass = new Fragmentclass();
        imgs = fragmentclass.imgs;*/

        View view = inflater.inflate(R.layout.offers_fragment, container, false);
        offerItems = new ArrayList<>();
        viewPager = (ViewPager)view.findViewById(R.id.vp_indicator);
        page_indicator = (LinearLayout)view.findViewById(R.id.viewPagerCountDots);
        imgCall = (ImageView) view.findViewById(R.id.imgCall);
         imgShare = (ImageView) view.findViewById(R.id.imgShare);
        tvOffersViews = (TextView) view.findViewById(R.id.tvOffersViews);
        vOnPageChangeListener = this;
        onImageClick = this;
        offers();
        return view;
    }
    private void action(final int pos){


        tvOffersViews.setText(offerItems.get(pos).getViewed()+"");
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             String number = offerItems.get(pos).getMobile();
                Utility utility = new Utility();
                utility.confirmDialog(getContext(),number);
                Intent intent = new Intent(Intent.ACTION_CALL);
              //  intent.setData(Uri.parse("tel:" + number));
               // startActivity(intent);
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, offerItems.get(pos).getTitle());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }
    private void setUiPageViewController() {
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i< dotscount; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.circle_2));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(2, 0, 2, 0);

            page_indicator.addView(dots[i], params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.circle_1));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        action(position);
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotscount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.circle_2));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.circle_1));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void searchAction(){
        MainActivity mainActivity = (MainActivity) getActivity();
        final EditText edSearch = (EditText) mainActivity.findViewById(R.id.edSearch);
        edSearch.setVisibility(View.VISIBLE);
        reSearch = (RelativeLayout) mainActivity.findViewById(R.id.reSearch);
        ImageView imgSearch = (ImageView) mainActivity.findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(getActivity(),edSearch.getText().toString());

            }
        });
        mainActivity.findViewById(R.id.imageToggleCategory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.searchVal==0){
                    reSearch.setVisibility(View.VISIBLE);
                    Utility.searchVal = 1;
                }else{
                    reSearch.setVisibility(View.GONE);
                    Utility.searchVal = 0;
                }
            }
        });
    }

    private void offers(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    offerMain = (OfferMain) result;
                    offerItems =   offerMain.getData();
                  //  CachData.offerCache = offerMain.getData();
                    viewPagerAdapter = new OfferViewPagerAdapter(getActivity(), offerItems,onImageClick);
                    viewPager.setAdapter(viewPagerAdapter);
                    viewPager.setCurrentItem(0);
                    viewPager.setOnPageChangeListener(vOnPageChangeListener);
                    setUiPageViewController();

                    Log.d("ffff",offerItems.toString());
                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetOffer task = new GetOffer(getActivity(), onLoadingComplete);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }

    }

    @Override
    public void onImageClick(int pos) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = new OfferDetails();
             Bundle bundle = new Bundle();
            bundle.putInt("id", offerItems.get(pos).getID());
          //   fragment.setArguments(bundle);
         //   ft.replace(R.id.main, fragment).addToBackStack("");
       //     ft.commit();

    }

    private void search(final Activity activity, String search){


        if (Utility.isNetworkConnected(activity)) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    OfferMain shoppingProductListMain = (OfferMain) result;
                    ArrayList<OfferItems> offerItemses =   shoppingProductListMain.getData();
                    Fragment fragment = new ShopListProduct();
                    Bundle bundle = new Bundle();
                    bundle.putString("key","search");
                    bundle.putParcelableArrayList("search",offerItemses);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main,fragment).commitAllowingStateLoss();


                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(activity, false);
                }
            };

            SearchOfferApi task = new SearchOfferApi(activity, onLoadingComplete,search);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    activity.getResources().getString(R.string.failure_ws),
                    activity);
        }
    }
}
