package aykhadma.droidahmed.com.aykhaama.shopping;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
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
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddCartItemShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddToFavoriteShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingRelated;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingSingleProduct;
import aykhadma.droidahmed.com.aykhaama.shopping_api.SearchShoppingApi;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ProductCartShopping;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;

/**
 * Created by ahmed on 8/2/2016.
 */
public class ShopProductDetails extends Fragment implements OnShopMainClick,OnAddCart,OnAddItems {

    View view;
    OnLoadingComplete onLoadingComplete;
    int id;
    int categoryId;
    ShoppingProductMain shoppingProductListMain;
    ShoppingProductListItem productModel;
    TextView tvProductName,tvCategory,tvCode,tvPrice,tvQuantity;
    ImageView thumbnail;
    ImageView imgDec,imgInc;
    int value;
    Button btnBuyNow;
    ImageView imgCart,imgFavorite;
    RecyclerView reShopCategory;
    ShoppingProductListAdapter shoppingAdapter;
    ArrayList<ShoppingProductListItem> shoppingProductListItems;
    OnShopMainClick onShopMainClick;
    ShoppingProductListMain shoppingProductListMains;
    int quan;
    OnAddCart onAddCart;
    OnAddItems onAddItems;
    int quant;
    String search="";
    RelativeLayout reSearch;
    RelativeLayout relative;
    DatabaseHelper databaseHelper;
    ArrayList<CartQuantity>cartItemsModify;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Utility utility = new Utility();
        utility.langChoosen(getActivity(),new StoreData(getActivity()).getLang());
        view = inflater.inflate(R.layout.shop_product_details, container, false);
        databaseHelper = new DatabaseHelper(getActivity());
        cartItemsModify = new ArrayList<>();
        Bundle bundle= getArguments();
        id =bundle.getInt("id",0);
        cartAdd();

        try {
            if ("search".equals(bundle.getString("key"))) {
                search = bundle.getString("key");
                shoppingProductListItems=bundle.getParcelableArrayList("search");
            }
        }catch (Exception e){
            search="";
        }
        onAddCart = this;
        onAddItems = this;
        categoryId =bundle.getInt("category_id",0);
        quan=bundle.getInt("quan",0);
        declare(view);
        product();
        declares(view);
        searchAction();
        return view;
    }

    private void product(){


        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    shoppingProductListMain = (ShoppingProductMain) result;
                    productModel =   shoppingProductListMain.getData();
                    try {

                    tvProductName.setText(productModel.getName());
                    tvCategory.setText(productModel.getCategoryName());
                    tvCode.setText(productModel.getCode()+"");
                    tvQuantity.setText(quan+"");
                    tvPrice.setText(productModel.getPrice()+" KD");
               //     setUiPageViewController();
                        Picasso.with(getActivity()).load("http://ai5adma.com/API/ar/general/thumb?url=" + productModel.getPicture() + "&width=360&height=220").into(thumbnail);

                }catch (Exception e){

                }

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetShoppingSingleProduct task = new GetShoppingSingleProduct(getActivity(), onLoadingComplete,id);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
         }

    }
    private void declare(View view){
        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        tvCode = (TextView) view.findViewById(R.id.tvCode);
        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        imgDec = (ImageView) view.findViewById(R.id.imgDec);
        imgInc = (ImageView) view.findViewById(R.id.imgInc);
        value=    Integer.parseInt( tvQuantity.getText().toString());;
        imgCart = (ImageView) view.findViewById(R.id.imgCart);
        imgFavorite = (ImageView) view.findViewById(R.id.imgFavorite);
        btnBuyNow = (Button) view.findViewById(R.id.btnBuyNow);
        relative = (RelativeLayout) view.findViewById(R.id.relative);
        action();
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FullImage.class);
                intent.putExtra("img", productModel.getPicture());
                getActivity().startActivity(intent);
            }
        });
    }

    private void action(){
        value = Integer.parseInt(tvQuantity.getText().toString());
        imgInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value= value+1;
                tvQuantity.setText(""+value);
            }
        });

        imgDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value > 0) {
                    value = value - 1;
                    tvQuantity.setText("" + value);
                }
            }
        });

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!new StoreData(getActivity()).getToken().equals("")) {

                    addToFavorite();

                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.need_login),Toast.LENGTH_LONG).show();

                }
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main,new ProductCartShopping()).addToBackStack("").commit();


             }
        });


        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(value>0) {
                    if (value <= productModel.getQuantity()) {
                        if (new StoreData(getActivity()).getToken().equals("")) {

                            if (databaseHelper.getCartItem(productModel.getID()) != null) {
                                long cre = databaseHelper.updateCart(productModel, quan);
                                Toast.makeText(getActivity(), getResources().getString(R.string.product_already_add), Toast.LENGTH_LONG).show();

                                Log.d("ooo1", "" + cre);
                            } else {
                                long cre = databaseHelper.createCart(productModel, quan);
                                if (cre != 0) {
                                    Toast.makeText(getActivity(), getResources().getString(R.string.product_add), Toast.LENGTH_LONG).show();
                                }
                                Log.d("ooo2", "" + cre);
                            }

                        } else {
                            if (!new StoreData(getActivity()).getToken().equals("")) {
                                ItemJson itemJson = new ItemJson(productModel.getProductID(), quant);
                                addToCart(itemJson);
                            }
                        }

                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


    }

    private void addToFavorite(){
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

    private void addToCart(ItemJson itemJson){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    ResponseAddProduct favoriteResponse = (ResponseAddProduct) result;
                    String add =   favoriteResponse.getData();
                    try {
                        Toast.makeText(getActivity(),add,Toast.LENGTH_LONG).show();

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

    private void getRelated(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    shoppingProductListMains = (ShoppingProductListMain) result;
                    shoppingProductListItems = shoppingProductListMains.getData();

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
                    shoppingAdapter = new ShoppingProductListAdapter(cartItemsModify, getActivity(), onShopMainClick,onAddCart,onAddItems);
                    reShopCategory.setAdapter(shoppingAdapter);

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetShoppingRelated task = new GetShoppingRelated(getActivity(), onLoadingComplete,categoryId);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }
    private void declares(View view){
        shoppingProductListItems = new ArrayList<>();
        reShopCategory = (RecyclerView) view.findViewById(R.id.reShopCategory);
        onShopMainClick = this;
        reShopCategory.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getRelated();
        reShopCategory.setAdapter(shoppingAdapter);
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
        Log.d("oooiidd",""+shoppingProductListItems.get(position).getProductID());
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main, fragment).addToBackStack("");
        ft.commit();
    }

    @Override
    public void onAddCart(int pos, int quan) {
        if(quant<=shoppingProductListItems.get(pos).getQuantity()) {
            if (!new StoreData(getActivity()).getToken().equals("")) {

                if(databaseHelper.getCartItem(shoppingProductListItems.get(pos).getID())!=null){
                    long cre =    databaseHelper.updateCart(shoppingProductListItems.get(pos),quan);
                    Toast.makeText(getActivity(),getResources().getString(R.string.product_already_add),Toast.LENGTH_LONG).show();

                    Log.d("ooo1",""+cre);
                }else {
                    long cre =  databaseHelper.createCart(shoppingProductListItems.get(pos),quan);
                    if(cre!=0){
                        Toast.makeText(getActivity(),getResources().getString(R.string.product_add),Toast.LENGTH_LONG).show();
                    }
                    Log.d("ooo2",""+cre);
                }


            }else {
                if (!new StoreData(getActivity()).getToken().equals("")) {
                    ItemJson itemJson = new ItemJson(shoppingProductListItems.get(pos).getProductID(), quant);
                    addToCart(itemJson);
                }
            }

    } else{
        Toast.makeText(getActivity(), getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();
    }
    }

    @Override
    public void add(int num, CartQuantity cartQuantity) {
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
