package aykhadma.droidahmed.com.aykhaama.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import aykhadma.droidahmed.com.aykhaama.adapter.ShoppingProductListAdapter;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddCart;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItems;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnListListener;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.FavoriteResponse;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.model.ResponseAddProduct;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddCartItemShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddToFavoriteShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingProductList;
import aykhadma.droidahmed.com.aykhaama.shopping_api.SearchShoppingApi;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;

/**
 * Created by ahmed on 8/2/2016.
 */
public class ShopListProduct extends Fragment implements OnShopMainClick ,OnListListener,OnAddCart,OnAddItems {

    View view;
    RecyclerView reShopCategory;
    ShoppingProductListAdapter shoppingAdapter;
     ArrayList<ShoppingProductListItem> shoppingProductListItems;
    OnShopMainClick onShopMainClick;
    OnLoadingComplete onLoadingComplete;
    ShoppingProductListMain shoppingProductListMain;
    int id;
    int quant;
    OnAddItems onAddItem;
    OnAddCart onAddCart;
    String search="";
      RelativeLayout reSearch;
    DatabaseHelper databaseHelper;
    ArrayList<CartQuantity>cartItemsModify;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shop_category, container, false);
        declare();
        databaseHelper = new DatabaseHelper(getActivity());
        Bundle bundle = getArguments();
        try{
            id =  bundle.getInt("id",1);

        }catch (Exception e){

        }
        cartItemsModify = new ArrayList<>();
        try {
            if ("search".equals(bundle.getString("key"))) {

                search = bundle.getString("key");
                Log.d("pppsss",search);
                shoppingProductListItems=bundle.getParcelableArrayList("search");
                assert shoppingProductListItems != null;
                Log.d("pppsss",shoppingProductListItems.size()+"");
            }
        }catch (Exception e){
            search="";
        }
        onAddItem = this;
        onAddCart = this;
        cartAdd();
        searchAction();
        subCategory();
        return view;
    }
    private void declare(){
        shoppingProductListItems = new ArrayList<>();
        reShopCategory = (RecyclerView) view.findViewById(R.id.reShopCategory);
         onShopMainClick = this;
        reShopCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
    public void onShopClick(int position) {
        Fragment fragment = new ShopProductDetails();
        Bundle bundle = new Bundle();
        bundle.putInt("id",shoppingProductListItems.get(position).getProductID());
        bundle.putInt("category_id",shoppingProductListItems.get(position).getCategoryID());
        bundle.putInt("quan",quant);

        Log.d("oooiidd",""+shoppingProductListItems.get(position).getProductID());
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main, fragment).addToBackStack("");
        ft.commit();
    }

    private void subCategory() {
        if(search.equals("search")){
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
            shoppingAdapter = new ShoppingProductListAdapter(cartItemsModify, getActivity(),
                    onShopMainClick, onAddCart, onAddItem);
            reShopCategory.setAdapter(shoppingAdapter);
        }else {
            if (Utility.isNetworkConnected(getActivity())) {

                onLoadingComplete = new OnLoadingComplete() {

                    @Override
                    public void onSuccess(Object result) {
                        shoppingProductListMain = (ShoppingProductListMain) result;
                        shoppingProductListItems = shoppingProductListMain.getData();

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
                        shoppingAdapter = new ShoppingProductListAdapter(cartItemsModify, getActivity(),
                                onShopMainClick, onAddCart, onAddItem);
                        reShopCategory.setAdapter(shoppingAdapter);


                    }

                    @Override
                    public void onFailure() {
                        Utility.showFailureDialog(getActivity(), false);
                    }
                };

                GetShoppingProductList task = new GetShoppingProductList(getActivity(), onLoadingComplete, id);
                task.execute();

            } else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.failure_ws),
                        getActivity());
            }
        }
    }

    private void addToCart(ItemJson itemJson){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    ResponseAddProduct favoriteResponse = (ResponseAddProduct) result;
                    String add =   favoriteResponse.getData();

                    try {
                        if(favoriteResponse.getData()!=null)
                        Toast.makeText(getActivity(), add, Toast.LENGTH_LONG).show();

                    }catch (Exception e){

                    }
                    try {
                        String adds =   favoriteResponse.getError();
                        if(favoriteResponse.getError()!=null){

                        Toast.makeText(getActivity(), adds, Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){

                    }
                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            AddCartItemShopping task= new  AddCartItemShopping(getActivity(), onLoadingComplete);
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
    public void btnBuy(int id,double value) {
            if (!new StoreData(getActivity()).getToken().equals("")) {
                ItemJson itemJson = new ItemJson(id, value);

                addToCart(itemJson);
            } else {
                Toast.makeText(getActivity(),getResources().getString(R.string.need_login), Toast.LENGTH_LONG).show();
            }



    }

    @Override
    public void addFav(int id) {
        addToFavorite(id);
    }

    @Override
    public void addCart(int id) {
        if(!new StoreData(getActivity()).getToken().equals("")){
            //     getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main,new MyShoppingCart()).commit();

        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.need_login),Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onAddCart(int pos,int quant) {

          //  if (!new StoreData(getActivity()).getToken().equals("")) {
                if(quant<=shoppingProductListItems.get(pos).getQuantity()) {
                    if (!new StoreData(getActivity()).getToken().equals("")) {
                     ItemJson itemJson = new ItemJson(shoppingProductListItems.get(pos).getProductID(), quant);
                    addToCart(itemJson);
                    }else{
                     if(databaseHelper.getCartItem(shoppingProductListItems.get(pos).getID())!=null){
                        long cre =    databaseHelper.updateCart(shoppingProductListItems.get(pos),quant);
                        Toast.makeText(getActivity(),getResources().getString(R.string.product_already_add),Toast.LENGTH_LONG).show();

                        Log.d("ooo1",""+cre);
                    }else {
                        long cre =  databaseHelper.createCart(shoppingProductListItems.get(pos),quant);
                        if(cre!=0){
                            Toast.makeText(getActivity(),getResources().getString(R.string.product_add),Toast.LENGTH_LONG).show();
                        }
                        Log.d("ooo2",""+cre);
                    }


                }
                } else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();

                }
//            }
//    else {
//                Toast.makeText(getActivity(),getResources().getString(R.string.need_login), Toast.LENGTH_LONG).show();
//
//        }
        }

    @Override
    public void add(int num,CartQuantity cartQuantity) {
        cartQuantity.setcQuantity(num);
        cartQuantity.setAdded(1);

        if(databaseHelper.getCartItemAdd(cartQuantity.getID())!=null){
            cartQuantity.setcQuantity(num);
            cartQuantity.setAdded(1);
            databaseHelper.updateCartAdd(cartQuantity);
            databaseHelper.updateCart(cartQuantity);

        }else{
            databaseHelper.createCartAdd(cartQuantity);
            databaseHelper.createCart(cartQuantity);
        }

        quant = num;
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
                    ShoppingProductListMain shoppingProductListMain = (ShoppingProductListMain) result;
                    ArrayList<ShoppingProductListItem> shoppingProductListItems =   shoppingProductListMain.getData();
                    Fragment fragment = new ShopListProduct();
                    Bundle bundle = new Bundle();
                    bundle.putString("key","search");
                    bundle.putParcelableArrayList("search",shoppingProductListItems);
                    fragment.setArguments(bundle);
                    reSearch.setVisibility(View.GONE);
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main,fragment).addToBackStack("").commitAllowingStateLoss();


                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            SearchShoppingApi task = new SearchShoppingApi(getActivity(), onLoadingComplete,search);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getActivity().getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    private void cartAdd(){
        MainActivity mainActivity = (MainActivity) getActivity();
        ImageView imgCart = (ImageView) mainActivity.findViewById(R.id.imgCart);
        imgCart.setVisibility(View.VISIBLE);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.main, new ShopCartMain()).addToBackStack("").commit();

            }
        });
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
