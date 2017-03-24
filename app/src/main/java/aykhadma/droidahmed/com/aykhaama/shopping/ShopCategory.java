package aykhadma.droidahmed.com.aykhaama.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
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
import aykhadma.droidahmed.com.aykhaama.adapter.ShoppingAdapter;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingCategory;
import aykhadma.droidahmed.com.aykhaama.shopping_api.SearchShoppingApi;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingCategoryItems;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingCategoryMain;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;

/**
 * Created by ahmed on 8/2/2016.
 */
public class ShopCategory extends Fragment implements OnShopMainClick {

    View view;
    RecyclerView reShopMain;
    ShoppingAdapter shoppingAdapter;
    ArrayList<ShoppingCategoryItems> shoppingCategoryItemses;
    OnShopMainClick onShopMainClick;
    OnLoadingComplete onLoadingComplete;
    ShoppingCategoryMain shoppingCategoryMain;
    RelativeLayout reSearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shop_main,container,false);
        declare();
        category();
        searchAction();
         return view;
    }
    private void declare(){
        shoppingCategoryItemses = new ArrayList<>();
        reShopMain = (RecyclerView) view.findViewById(R.id.reShopMain);
        reShopMain.setLayoutManager(new GridLayoutManager(getActivity(),3));
        onShopMainClick =this;
        final MainActivity mainActivity = (MainActivity) getActivity();
        ImageView  imageToggle = (ImageView) mainActivity.findViewById(R.id.imageToggle);
        imageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity. toggle();

            }
        });

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

    @Override
    public void onShopClick(int position) {
        Fragment subCategory = new ShopSubCategory();
        Bundle bundle =new Bundle();
        bundle.putInt("id",shoppingCategoryItemses.get(position).getCategoryID());
        subCategory.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main, subCategory).addToBackStack("");
        ft.commit();
    }

    private void category(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    shoppingCategoryMain = (ShoppingCategoryMain) result;
                    shoppingCategoryItemses =   shoppingCategoryMain.getData();

                    reSearch.setVisibility(View.GONE);
                    shoppingAdapter = new ShoppingAdapter(shoppingCategoryItemses,getActivity(),onShopMainClick);
                    reShopMain.setAdapter(shoppingAdapter);



                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetShoppingCategory task = new GetShoppingCategory(getActivity(), onLoadingComplete,1);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    private void search(String search){


        if (Utility.isNetworkConnected( getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    ShoppingProductListMain shoppingProductListMain = (ShoppingProductListMain) result;
                    ArrayList<ShoppingProductListItem> shoppingProductListItems =   shoppingProductListMain.getData();
            Fragment fragment = new ShopListProduct();
                    Bundle bundle = new Bundle();
                    bundle.putString("key","search");
                    bundle.putParcelableArrayList("search",shoppingProductListItems);
                    fragment.setArguments(bundle);
                 getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main,fragment).addToBackStack("").commitAllowingStateLoss();


                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog( getActivity(), false);
                }
            };

            SearchShoppingApi task = new SearchShoppingApi( getActivity(), onLoadingComplete,search);
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
