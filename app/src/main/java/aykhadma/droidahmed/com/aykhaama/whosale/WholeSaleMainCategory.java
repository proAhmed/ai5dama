package aykhadma.droidahmed.com.aykhaama.whosale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.WholeSaleAdapter;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnWholeSaleClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.GetWholeSaleSubCategory;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.SearchWholeSaleApi;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleCategoryItems;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleCategoryMain;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListItem;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListMain;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;

/**
 * Created by ahmed on 8/4/2016.
 */
public class WholeSaleMainCategory extends Fragment implements OnWholeSaleClick {
    View view;
    RecyclerView reWholeSaleMainCategory;
    WholeSaleAdapter wholeSaleAdapter;
    ArrayList<WholeSaleModel> wholeSaleArrayList;
    OnWholeSaleClick onWholeSaleClick;
    OnLoadingComplete onLoadingComplete;
    int id;
    WholeSaleCategoryMain wholeSaleCategoryMain;
    ArrayList<WholeSaleCategoryItems> wholeSaleCategoryItems;
    RelativeLayout reSearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.whole_sale_main_category,container,false);
        Bundle bundle = getArguments();
       id = bundle.getInt("id",0);
        declare();
         searchAction();
        return view;
    }
    private void declare(){
        wholeSaleArrayList = new ArrayList<>();
        reWholeSaleMainCategory = (RecyclerView) view.findViewById(R.id.reWholeSaleMainCategory);
        reWholeSaleMainCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        onWholeSaleClick = this;

        subCategory();
        final MainActivity mainActivity = (MainActivity) getActivity();
        ImageView  imageToggle = (ImageView) mainActivity.findViewById(R.id.imageToggle);
        imageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity. toggle();

            }
        });
    }

    @Override
    public void onWholeSaleClick(int position) {
        Fragment fragment ;
        if(wholeSaleCategoryItems.get(position).isParent()){
            fragment = new WholeSaleSubCategory();

        }else {
            fragment = new WholeSaleProductList();

        }
        Bundle bundle =new Bundle();
        bundle.putInt("id",wholeSaleCategoryItems.get(position).getCategoryID());
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main, fragment).addToBackStack("");
        ft.commit();
    }

    private void subCategory(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    wholeSaleCategoryMain = (WholeSaleCategoryMain) result;
                    wholeSaleCategoryItems =   wholeSaleCategoryMain.getData();


                    wholeSaleAdapter = new WholeSaleAdapter(wholeSaleCategoryItems,getActivity(),onWholeSaleClick);
                    reWholeSaleMainCategory.setAdapter(wholeSaleAdapter);


                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetWholeSaleSubCategory task = new GetWholeSaleSubCategory(getActivity(), onLoadingComplete,id);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
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
                search(edSearch.getText().toString());

            }
        });
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(edSearch.getText().toString());
                    return true;
                }
                return false;
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

    private void search( String search){


        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    WholeSaleProductListMain shoppingProductListMain = (WholeSaleProductListMain) result;
                    ArrayList<WholeSaleProductListItem> shoppingProductListItems =   shoppingProductListMain.getData();
                    Fragment fragment = new WholeSaleProductList();
                    Bundle bundle = new Bundle();
                    bundle.putString("key","search");
                    bundle.putParcelableArrayList("search",shoppingProductListItems);
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main,fragment).addToBackStack("").commitAllowingStateLoss();


                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            SearchWholeSaleApi task = new SearchWholeSaleApi(getActivity(), onLoadingComplete,search);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        final MainActivity mainActivity = (MainActivity) getActivity();
        ImageView  imageToggle = (ImageView) mainActivity.findViewById(R.id.imageToggle);
        imageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity. toggle();

            }
        });
    }
}
