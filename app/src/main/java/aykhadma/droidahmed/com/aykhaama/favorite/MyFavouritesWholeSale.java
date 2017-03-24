package aykhadma.droidahmed.com.aykhaama.favorite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.ShoppingProductAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.WholeSaleAdapterList;
import aykhadma.droidahmed.com.aykhaama.adapter.WholeSaleAdapterListFav;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddCart;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItems;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnWholeSaleClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.FavoriteResponse;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.model.ResponseAddProduct;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;
import aykhadma.droidahmed.com.aykhaama.shopping.ShopProductDetails;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddToFavoriteShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingFavorite;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.AddCartItemWholeSale;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.GetWholeSaleFavorite;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListItem;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListMain;
import aykhadma.droidahmed.com.aykhaama.whosale.WholeSaleProductDetails;


/**
 * Created by ahmed on 8/20/2016.
 */
public class MyFavouritesWholeSale extends Fragment implements OnWholeSaleClick ,OnAddCart,OnAddItems {
    RecyclerView reWholeSaleProductList;
    WholeSaleAdapterListFav wholeSaleAdapter;
    ArrayList<WholeSaleModel> wholeSaleArrayList;
    OnWholeSaleClick onWholeSaleClick;
    ArrayList<WholeSaleProductListItem> wholeSaleProductListItems;
    OnLoadingComplete onLoadingComplete;
    int quant;
    OnAddItems onAddItem;
    OnAddCart onAddCart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.whole_sale_list_product,container,false);
        onAddItem = this;
        onAddCart = this;
        declare(view);
        favourite();
        return view;
    }

    private void declare(View view){
        wholeSaleArrayList = new ArrayList<>();
        reWholeSaleProductList = (RecyclerView) view.findViewById(R.id.reWholeSaleListProduct);
        reWholeSaleProductList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        onWholeSaleClick = this;
        favourite();

    }

    private void favourite(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    WholeSaleProductListMain shoppingProductListMain = (WholeSaleProductListMain) result;
                    wholeSaleProductListItems = shoppingProductListMain.getData();

                    if(wholeSaleProductListItems.size()>0)
                        wholeSaleAdapter = new WholeSaleAdapterListFav(wholeSaleProductListItems, getActivity(), onWholeSaleClick);
                    reWholeSaleProductList.setAdapter(wholeSaleAdapter);

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetWholeSaleFavorite task = new GetWholeSaleFavorite(getActivity(), onLoadingComplete);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    @Override
    public void onWholeSaleClick(int position) {
        Fragment fragment = new WholeSaleProductDetails();
        Bundle bundle = new Bundle();
        bundle.putInt("id",wholeSaleProductListItems.get(position).getProductID());
        bundle.putInt("category_id",wholeSaleProductListItems.get(position).getCategoryID());
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main,fragment).addToBackStack("");
        ft.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) getActivity();
//        final TextView tvToolbarTitle = (TextView) mainActivity. findViewById(R.id.tvToolbarTitle);
//        tvToolbarTitle.setText(getResources().getString(R.string.my_favourite));
//        final ImageView imgSearchAction = (ImageView) mainActivity. findViewById(R.id.imageSearchAction);
//        imgSearchAction.setVisibility(View.INVISIBLE);
//        final ImageView imgBack = (ImageView) mainActivity. findViewById(R.id.imageBack);
//        imgBack.setVisibility(View.VISIBLE);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                    fm.popBackStack();
//                }
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .add(R.id.mainFragment, new Home()).addToBackStack("")
//                        .commit();
//                imgBack.setVisibility(View.GONE);
//                imgSearchAction.setVisibility(View.VISIBLE);
//                tvToolbarTitle.setText(getResources().getString(R.string.home));
//
//
//
//            }
//
//        });

    }

    private void addToCart(ItemJson itemJson){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    ResponseAddProduct favoriteResponse = (ResponseAddProduct) result;
                    String add =   favoriteResponse.getData();
                    try {
                        Toast.makeText(getActivity(), add, Toast.LENGTH_LONG).show();

                    }catch (Exception e){

                    }

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            AddCartItemWholeSale task= new  AddCartItemWholeSale(getActivity(), onLoadingComplete);
            task.execute(itemJson);

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }
    private void addToFavorite(int id){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    FavoriteResponse favoriteResponse = (FavoriteResponse) result;
                    String fav =   favoriteResponse.getData();
                    try {
                        Toast.makeText(getActivity(), fav, Toast.LENGTH_LONG).show();

                    }catch (Exception e){
                        try {
                            Toast.makeText(getActivity(), favoriteResponse.getError(), Toast.LENGTH_LONG).show();

                        }catch (Exception ee){
                        }
                    }

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            AddToFavoriteShopping task= new  AddToFavoriteShopping(getActivity(), onLoadingComplete);
            task.execute(id);

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    @Override
    public void onAddCart(int pos,int quant) {

        if (!new StoreData(getActivity()).getToken().equals("")) {
            if(quant<=wholeSaleProductListItems.get(pos).getQuantity()) {
                ItemJson itemJson = new ItemJson(wholeSaleProductListItems.get(pos).getProductID(), quant);

                addToCart(itemJson); } else{
                Toast.makeText(getActivity(), "amount is more than available", Toast.LENGTH_LONG).show();

            }
        } else {
            Toast.makeText(getActivity(), "you need to login first", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void add(int num, CartQuantity cartQuantity) {

        quant = num;
    }
}
