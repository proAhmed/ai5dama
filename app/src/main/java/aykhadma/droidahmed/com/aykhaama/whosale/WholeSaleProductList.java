package aykhadma.droidahmed.com.aykhaama.whosale;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.WholeSaleAdapterList;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddCart;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItems;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnListListener;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnWholeSaleClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.FavoriteResponse;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.model.ResponseAddProduct;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddToFavoriteShopping;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.AddCartItemWholeSale;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.GetWholeSaleProductList;
import aykhadma.droidahmed.com.aykhaama.wholesale_api.SearchWholeSaleApi;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListItem;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListMain;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;

/**
 * Created by ahmed on 8/4/2016.
 */
public class WholeSaleProductList  extends Fragment implements OnWholeSaleClick ,OnListListener,OnAddCart,OnAddItems {
    View view;
    RecyclerView reWholeSaleProductList;
    WholeSaleAdapterList wholeSaleAdapter;
    ArrayList<WholeSaleModel> wholeSaleArrayList;
    OnWholeSaleClick onWholeSaleClick;
    OnLoadingComplete onLoadingComplete;
    ArrayList<WholeSaleProductListItem> wholeSaleProductListItems;
    int quant;
    OnAddItems onAddItem;
    OnAddCart onAddCart;
    String search="";
    RelativeLayout reSearch;
    DatabaseHelper databaseHelper;
    int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.whole_sale_list_product,container,false);
        Bundle bundle = getArguments();
         try{
            id =  bundle.getInt("id",1);

        }catch (Exception e){

        }
        databaseHelper = new DatabaseHelper(getActivity());
        try {
            if ("search".equals(bundle.getString("key"))) {

                search = bundle.getString("key");
                Log.d("pppsss",search);
                wholeSaleProductListItems = bundle.getParcelableArrayList("search");
                Log.d("pppsss",wholeSaleProductListItems.size()+"");
            }
        }catch (Exception e){
            search="";
        }
        cartAdd();
        onAddItem = this;
        onAddCart = this;
        declare();
        searchAction();
        return view;
    }
    private void declare(){
        wholeSaleArrayList = new ArrayList<>();
        reWholeSaleProductList = (RecyclerView) view.findViewById(R.id.reWholeSaleListProduct);
        reWholeSaleProductList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        onWholeSaleClick = this;
        productList();
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


    private void productList() {
        if (search.equals("search")) {
            wholeSaleAdapter = new WholeSaleAdapterList(wholeSaleProductListItems, getActivity(), onWholeSaleClick,onAddCart,onAddItem);

            reWholeSaleProductList.setAdapter(wholeSaleAdapter);
        } else {
            if (Utility.isNetworkConnected(getActivity())) {

                onLoadingComplete = new OnLoadingComplete() {

                    @Override
                    public void onSuccess(Object result) {
                        WholeSaleProductListMain shoppingProductListMain = (WholeSaleProductListMain) result;
                        wholeSaleProductListItems = shoppingProductListMain.getData();

                        if (wholeSaleProductListItems.size() > 0)
                            wholeSaleAdapter = new WholeSaleAdapterList(wholeSaleProductListItems, getActivity(), onWholeSaleClick,onAddCart,onAddItem);
                        reWholeSaleProductList.setAdapter(wholeSaleAdapter);


                    }

                    @Override
                    public void onFailure() {
                        Utility.showFailureDialog(getActivity(), false);
                    }
                };

                GetWholeSaleProductList task = new GetWholeSaleProductList(getActivity(), onLoadingComplete, id);
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
        if (!new StoreData(getActivity()).getToken().equals("")) {
            ItemJson itemJson = new ItemJson(id, value);

            addToCart(itemJson);


        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.need_login), Toast.LENGTH_LONG).show();
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

//        if (!new StoreData(getActivity()).getToken().equals("")) {
            if(quant<=wholeSaleProductListItems.get(pos).getQuantity()) {
                if (!new StoreData(getActivity()).getToken().equals("")) {
                    ItemJson itemJson = new ItemJson(wholeSaleProductListItems.get(pos).getProductID(), quant);
                    addToCart(itemJson);

                }else {
                    Log.d("ooo1",""+wholeSaleProductListItems.get(pos).getID());

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
            } else{
                Toast.makeText(getActivity(), getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();

            }
//        }

//        else {
//            Toast.makeText(getActivity(),getResources().getString(R.string.need_login), Toast.LENGTH_LONG).show();
//
//        }
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
