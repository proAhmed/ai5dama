package aykhadma.droidahmed.com.aykhaama.favorite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import aykhadma.droidahmed.com.aykhaama.adapter.ShoppingProductListAdapter;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddCart;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItems;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.FavoriteResponse;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.model.ResponseAddProduct;
import aykhadma.droidahmed.com.aykhaama.shopping.ShopProductDetails;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddCartItemShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddToFavoriteShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingFavorite;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


/**
 * Created by ahmed on 8/20/2016.
 */
public class MyFavouritesShopping extends Fragment implements OnShopMainClick,OnAddCart,OnAddItems {
    RecyclerView reShopCategory;
    ShoppingProductListAdapter shoppingAdapter;
    ArrayList<ShoppingProductListItem> shoppingProductListItems;
    ArrayList<CartQuantity>cartItemsModify;
    DatabaseHelper databaseHelper;

    OnShopMainClick onShopMainClick;
     ShoppingProductListMain shoppingProductListMain;
    OnLoadingComplete favouriteListener;
    int quant;
    OnAddItems onAddItem;
    OnAddCart onAddCart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_category,container,false);
        onAddItem = this;
        onAddCart = this;
        declare(view);
        favourite();
        return view;
    }

    private void declare(View view){
        onShopMainClick = this;
        databaseHelper = new DatabaseHelper(getActivity());
        cartItemsModify = new ArrayList<>();
        reShopCategory = (RecyclerView) view.findViewById(R.id.reShopCategory);
        reShopCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoppingProductListItems = new ArrayList<>();
    }

    private void favourite(){
        if (Utility.isNetworkConnected(getActivity())) {

            favouriteListener = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    shoppingProductListMain = (ShoppingProductListMain) result;
                    shoppingProductListItems =   shoppingProductListMain.getData();
                    if(shoppingProductListItems.size()>0)
                        for(int i =0;i<shoppingProductListItems.size();i++){

                            ShoppingProductListItem allProducts = shoppingProductListItems.get(i);
                            CartQuantity cartItem = new CartQuantity(allProducts.getID(),allProducts.getCode(),allProducts.getCategoryID(),
                                    allProducts.getBrandID(),allProducts.getPrice(), allProducts.getQuantity(), allProducts.getPicture(),allProducts.getSliderPictures(),
                                    allProducts.getCreatedDate(),allProducts.getModifiedDate(),allProducts.getViewed(), allProducts.getFeatured(),allProducts.getState(),
                                    allProducts.getProductID(),allProducts.getLanguageID(),allProducts.getName(),allProducts.getAlias(),
                                    allProducts.getContents(),allProducts.getDescription(), allProducts.getKeywords(),allProducts.getCategoryName(),allProducts.getBrandName(),
                                    1);

                            cartItemsModify.add(cartItem);

                        }


                    try{
                        if( databaseHelper.getCartAdd()!=null){
                            for(int i=0;i<cartItemsModify.size();i++) {
                                for (int ii=0;ii<databaseHelper.getCartAdd().size();ii++) {
                                    if( databaseHelper.getCartAdd().get(ii).getID()==cartItemsModify.get(i).getID()){
                                        //   cartItemsModify.get(i).setcQuantity(databaseHelper.getCartAdd().get(ii).getcQuantity());
                                        cartItemsModify.get(i).setAdded(databaseHelper.getCartAdd().get(ii).getAdded());
                                    }
                                }
                            }
                        }
                    }catch (Exception e){

                    }

                    try{
                        if( databaseHelper.getCart()!=null){
                            for(int i=0;i<cartItemsModify.size();i++) {
                                for(int ii=0;ii<databaseHelper.getCart().size();ii++) {

                                    if( databaseHelper.getCart().get(ii).getID()==cartItemsModify.get(i).getID()){
                                        cartItemsModify.get(i).setSeen(databaseHelper.getCart().get(ii).getSeen());
                                        if(0<databaseHelper.getCart().get(ii).getcQuantity())
                                            cartItemsModify.get(i).setcQuantity(databaseHelper.getCart().get(ii).getcQuantity());

                                    }
                                }
                            }
                        }
                    }catch (Exception e){

                    }
                    shoppingAdapter =   new ShoppingProductListAdapter(cartItemsModify,getActivity(), onShopMainClick,onAddCart,onAddItem);
                    reShopCategory.setAdapter(shoppingAdapter);


                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetShoppingFavorite task = new GetShoppingFavorite(getActivity(), favouriteListener);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    @Override
    public void onShopClick(int position) {
        Fragment fragment = new ShopProductDetails();
        Bundle bundle = new Bundle();
        bundle.putInt("id",shoppingProductListItems.get(position).getProductID());
        Log.d("oooiidd", "" + shoppingProductListItems.get(position).getProductID());
        bundle.putInt("category_id",shoppingProductListItems.get(position).getCategoryID());

        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main, fragment).addToBackStack("");
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

            favouriteListener = new OnLoadingComplete() {

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

            AddCartItemShopping task= new  AddCartItemShopping(getActivity(), favouriteListener);
            task.execute(itemJson);

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }
    private void addToFavorite(int id){
        if (Utility.isNetworkConnected(getActivity())) {

            favouriteListener = new OnLoadingComplete() {

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

            AddToFavoriteShopping task= new  AddToFavoriteShopping(getActivity(), favouriteListener);
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
            if(quant<=shoppingProductListItems.get(pos).getQuantity()) {
                ItemJson itemJson = new ItemJson(shoppingProductListItems.get(pos).getProductID(), quant);

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
