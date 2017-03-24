package aykhadma.droidahmed.com.aykhaama.whosale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import aykhadma.droidahmed.com.aykhaama.adapter.ViewPagerAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.WholeSaleAdapterList;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddCart;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItems;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnListListener;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnViewPagerClick;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnWholeSaleClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.FavoriteResponse;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.model.ResponseAddProduct;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;
import aykhadma.droidahmed.com.aykhaama.my_account.MyShoppingCart;
import aykhadma.droidahmed.com.aykhaama.shopping.FullImage;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddToFavoriteShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingRelated;
import aykhadma.droidahmed.com.aykhaama.shopping_api.GetShoppingSingleProduct;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.AddCartItemWholeSale;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.AddToFavoriteWholeSale;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.GetWholeSaleProductList;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.GetWholeSaleRelated;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.GetWholeSaleSingleProduct;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.SearchWholeSaleApi;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListItem;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListMain;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductMain;

/**
 * Created by ahmed on 8/4/2016.
 */
public class WholeSaleProductDetails  extends Fragment implements OnViewPagerClick,OnWholeSaleClick ,OnListListener,OnAddCart,OnAddItems {
    View view;

    WholeSaleProductMain shoppingProductListMain;
    WholeSaleProductListItem productModel;
    TextView tvProductName,tvCategory,tvCode,tvPrice,tvQuantity;
    ImageView thumbnail;
    ViewPager pager;
    OnViewPagerClick onViewPagerClick;
    ImageView imgDec,imgInc;
    int value;
    Button btnBuyNow;
    ImageView imgCart,imgFavorite;

    int categoryId;
    int quan;
    RecyclerView reWholeSaleProductList;
    WholeSaleAdapterList wholeSaleAdapter;
    ArrayList<WholeSaleModel> wholeSaleArrayList;
    OnWholeSaleClick onWholeSaleClick;
    OnLoadingComplete onLoadingComplete;
    ArrayList<WholeSaleProductListItem> wholeSaleProductListItems;
    RelativeLayout relative;
    int quant;
    OnAddItems onAddItem;
    OnAddCart onAddCart;
    String search="";
    RelativeLayout reSearch;
    OnWholeSaleClick onShopMainClick;
    DatabaseHelper databaseHelper;
    int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Utility utility = new Utility();
        utility.langChoosen(getActivity(),new StoreData(getActivity()).getLang());
        view = inflater.inflate(R.layout.whole_sale_product_details,container,false);
        Bundle bundle = getArguments();
        databaseHelper = new DatabaseHelper(getActivity());
        onAddItem =this;
        onAddCart = this;
        id = bundle.getInt("id");
        categoryId =bundle.getInt("category_id",0);
        quan=bundle.getInt("quan",0);
        declare(view);
        onShopMainClick =this;

        product();
        searchAction();
        try {
            if ("search".equals(bundle.getString("key"))) {

                search = bundle.getString("key");
                wholeSaleProductListItems=bundle.getParcelableArrayList("search");
            }
        }catch (Exception e){
            search="";
        }
        return view;
    }

    private void declare(View view){
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        imgDec = (ImageView) view.findViewById(R.id.imgDec);
        relative = (RelativeLayout) view.findViewById(R.id.relative);
        imgInc = (ImageView) view.findViewById(R.id.imgInc);
        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        tvCode = (TextView) view.findViewById(R.id.tvCode);
        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
        imgCart = (ImageView) view.findViewById(R.id.imgCart);
        imgFavorite = (ImageView) view.findViewById(R.id.imgFavorite);
        btnBuyNow = (Button) view.findViewById(R.id.btnBuyNow);
        wholeSaleArrayList = new ArrayList<>();
        reWholeSaleProductList = (RecyclerView) view.findViewById(R.id.reWholeSaleRelatedProduct);
        reWholeSaleProductList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        onWholeSaleClick = this;
        pager = (ViewPager) view.findViewById(R.id.pager);
        onViewPagerClick = this;
          value = Integer.parseInt( tvQuantity.getText().toString());;
        action();
        getRelated();
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FullImage.class);
                intent.putExtra("img", productModel.getPicture());
                getActivity().startActivity(intent);
            }
        });

        final MainActivity mainActivity = (MainActivity) getActivity();
        ImageView  imageToggle = (ImageView) mainActivity.findViewById(R.id.imageToggle);
        imageToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity. toggle();

            }
        });

    }
    private void action(){
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
              if(value>0){
                  value= value-1;
                  tvQuantity.setText(""+value);
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
                     getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main,new MyShoppingCart()).commit();
                    imgCart.setBackgroundColor(getResources().getColor(R.color.orange));


            }
        });

        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(value<= productModel.getQuantity()) {
                    if (!new StoreData(getActivity()).getToken().equals("")) {
                        ItemJson itemJson = new ItemJson(id, value);
                        addToCart(itemJson);
                        btnBuyNow.setBackgroundColor(getResources().getColor(R.color.orange));

                    }  else{

                        if(databaseHelper.getWholeSaleCartItem(productModel.getID())!=null){
                            long cre =    databaseHelper.updateWholeSaleCart(productModel,value);
                            Toast.makeText(getActivity(),getResources().getString(R.string.product_already_add),Toast.LENGTH_LONG).show();

                            Log.d("ooo1",""+cre);
                        }else {
                            long cre =  databaseHelper.createWholeSaleCart(productModel,value);
                            if(cre!=0){
                                Toast.makeText(getActivity(),getResources().getString(R.string.product_add),Toast.LENGTH_LONG).show();
                                btnBuyNow.setBackgroundColor(getResources().getColor(R.color.orange));
                            }
                            Log.d("ooo2",""+cre);
                        }

                    }


                }else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();

                }


            }
        });
    }
    private void product(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    shoppingProductListMain = (WholeSaleProductMain) result;
                    productModel =   shoppingProductListMain.getData();
                    try {

                        tvProductName.setText(productModel.getName());
                        tvCategory.setText(productModel.getCategoryName());
                        tvCode.setText(productModel.getCode()+"");
                        tvPrice.setText(productModel.getPrice()+" KD");
                        //     setUiPageViewController();
                      //  pager.setAdapter(new ViewPagerAdapter(getActivity(),productModel.getSliderPictures(),onViewPagerClick));
                        Picasso.with(getActivity()).load("http://ai5adma.com/API/ar/general/thumb?url=" + productModel.getPicture() + "&width=360&height=220").into(thumbnail);
                        Log.d("ooo","http://ai5adma.com/API/ar/general/thumb?url=" + productModel.getPicture() + "&width=360&height=220");
                    }catch (Exception e){

                    }

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetWholeSaleSingleProduct task= new  GetWholeSaleSingleProduct(getActivity(), onLoadingComplete,id);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    @Override
    public void onClick(int pos) {

    }

    private void addToFavorite(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    FavoriteResponse  favoriteResponse = (FavoriteResponse) result;
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

            AddToFavoriteWholeSale task= new  AddToFavoriteWholeSale(getActivity(), onLoadingComplete);
            task.execute(id);

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }



    private void search(String search){


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
    private void getRelated(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    WholeSaleProductListMain wholeSaleProductListMain = (WholeSaleProductListMain) result;
                    wholeSaleProductListItems = wholeSaleProductListMain.getData();


                    wholeSaleAdapter = new WholeSaleAdapterList(wholeSaleProductListItems, getActivity(), onShopMainClick,onAddCart,onAddItem);
                    reWholeSaleProductList.setAdapter(wholeSaleAdapter);

                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetWholeSaleRelated task = new GetWholeSaleRelated(getActivity(), onLoadingComplete,categoryId);
            task.execute();

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
    public void btnBuy(int id,double value) {
        if(quant<= wholeSaleProductListItems.get(id).getQuantity()) {
            if (!new StoreData(getActivity()).getToken().equals("")) {
                ItemJson itemJson = new ItemJson(wholeSaleProductListItems.get(id).getProductID(), quant);
                addToCart(itemJson);

            }  else{

                if(databaseHelper.getWholeSaleCartItem(wholeSaleProductListItems.get(id).getID())!=null){
                    long cre =    databaseHelper.updateWholeSaleCart(wholeSaleProductListItems.get(id),quant);
                    Toast.makeText(getActivity(),getResources().getString(R.string.product_already_add),Toast.LENGTH_LONG).show();

                    Log.d("ooo1",""+cre);
                }else {
                    long cre =  databaseHelper.createWholeSaleCart(wholeSaleProductListItems.get(id),quant);
                    if(cre!=0){
                        Toast.makeText(getActivity(),getResources().getString(R.string.product_add),Toast.LENGTH_LONG).show();
                    }
                    Log.d("ooo2",""+cre);
                }

            }


        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();

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


            if(quant<=wholeSaleProductListItems.get(pos).getQuantity()) {
                if (!new StoreData(getActivity()).getToken().equals("")) {
                    ItemJson itemJson = new ItemJson(wholeSaleProductListItems.get(pos).getProductID(), quant);
                    addToCart(itemJson);

                }  else{

                    if(databaseHelper.getWholeSaleCartItem(wholeSaleProductListItems.get(pos).getID())!=null){
                        long cre =    databaseHelper.updateWholeSaleCart(wholeSaleProductListItems.get(pos),quant);
                        Toast.makeText(getActivity(),getResources().getString(R.string.product_already_add),Toast.LENGTH_LONG).show();

                        Log.d("ooo1",""+cre);
                    }else {
                        long cre =  databaseHelper.createWholeSaleCart(wholeSaleProductListItems.get(pos),quant);
                        if(cre!=0){
                            Toast.makeText(getActivity(),getResources().getString(R.string.product_add),Toast.LENGTH_LONG).show();
                        }
                        Log.d("ooo2",""+cre);
                    }

                }

            }else{
                Toast.makeText(getActivity(),getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();

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

    private void search(final Activity activity, String search){


        if (Utility.isNetworkConnected(activity)) {

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
                    Utility.showFailureDialog(activity, false);
                }
            };

            SearchWholeSaleApi task = new SearchWholeSaleApi(activity, onLoadingComplete,search);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    activity.getResources().getString(R.string.failure_ws),
                    activity);
        }
    }

    @Override
    public void onWholeSaleClick(int position) {

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
