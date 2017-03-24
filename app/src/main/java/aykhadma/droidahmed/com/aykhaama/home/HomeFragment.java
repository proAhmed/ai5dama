package aykhadma.droidahmed.com.aykhaama.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.CustomPagerAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.HomBottomImageAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.HomSmallImageAdapter;
import aykhadma.droidahmed.com.aykhaama.adapter.HomeFeaturedtListAdapter;
import aykhadma.droidahmed.com.aykhaama.airlines.AirLineScreen;
import aykhadma.droidahmed.com.aykhaama.api.GetHomeFeatured;
import aykhadma.droidahmed.com.aykhaama.api.GetSlide;
import aykhadma.droidahmed.com.aykhaama.companies.Companies;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.controller.SetDataForLists;
import aykhadma.droidahmed.com.aykhaama.custom_service.CustomerServiceMain;
import aykhadma.droidahmed.com.aykhaama.hotels.HotelMain;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddCart;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItems;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnClickHomeBottom;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnClickHomeMenu;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnShopMainClick;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.FeatureResponse;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.model.ResponseAddProduct;
import aykhadma.droidahmed.com.aykhaama.model.SlideShow;
import aykhadma.droidahmed.com.aykhaama.model.SlideShowResponse;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;
import aykhadma.droidahmed.com.aykhaama.my_account.MyProfile;
import aykhadma.droidahmed.com.aykhaama.offers.Offers;
import aykhadma.droidahmed.com.aykhaama.open_market.OpenMarket;
import aykhadma.droidahmed.com.aykhaama.profile.Login;
import aykhadma.droidahmed.com.aykhaama.shopping.ShopCategory;
import aykhadma.droidahmed.com.aykhaama.shopping.ShopProductDetails;
import aykhadma.droidahmed.com.aykhaama.shopping_api.AddCartItemShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;
import aykhadma.droidahmed.com.aykhaama.videos.VideoMain;
import aykhadma.droidahmed.com.aykhaama.whosale.WholeSaleMain;
import aykhadma.droidahmed.com.aykhaama.shop_cart.ShopCartMain;


public class HomeFragment extends Fragment implements OnClickHomeMenu,OnClickHomeBottom,OnShopMainClick,OnAddCart,OnAddItems {
    private RecyclerView mRecyclerView,reHomeMain;
    private RecyclerView.Adapter mAdapter;
    ArrayList<WholeSaleModel> wholeSaleArrayList;
    OnLoadingComplete onLoadingComplete;
    private RecyclerView.LayoutManager mLayoutManager;
    SetDataForLists setDataForLists;
    OnClickHomeMenu onClickHomeMenu;
    OnClickHomeBottom onClickHomeBottom;
    OnShopMainClick onWholeSaleClick;
    ArrayList<ShoppingProductListItem>featuredHomes;
    ArrayList<SlideShow>slideShowArrayList;
    ViewPager viewPager;
    OnAddItems onAddItem;
    OnAddCart onAddCart;
    int quant;
    DatabaseHelper databaseHelper;
    ArrayList<CartQuantity>cartItemsModify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setDataForLists = new SetDataForLists();
        onAddItem = this;
        onAddCart = this;
        databaseHelper = new DatabaseHelper(getActivity());
        cartItemsModify = new ArrayList<>();

        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d("pppp",token);
            assert token != null;
            if(!token.equals("")) {
                new StoreData(getActivity()).setDeviceId(token);
            }
        }catch (Exception e){
            Log.d("lllooo",e.toString());
        }
        Log.d("pppuu", new StoreData(getActivity()).getDeviceId());

         initPager(view);
        initMenu(view);
        initBottom(view);
        //imageArrayIcon
        initMain(view);
        homeFeatured();
        return view;
    }

    private void initMenu(View view){
        mRecyclerView = (RecyclerView)  view.findViewById(R.id.viewPagers);
        mRecyclerView.setHasFixedSize(true);
        onClickHomeMenu = this;


        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);//new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomSmallImageAdapter(setDataForLists.homeSmallImageList(),getActivity(),onClickHomeMenu);
        mRecyclerView.setAdapter(mAdapter);

    }
    private void initMain(View view){
        reHomeMain = (RecyclerView)  view.findViewById(R.id.reHomeMain);
        mRecyclerView.setHasFixedSize(true);
        onClickHomeMenu = this;
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        onWholeSaleClick = this;
        reHomeMain.setLayoutManager(mLayoutManager);
//        mLayoutManager = new LinearLayoutManager(getActivity());//new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void initBottom(View view){
        mRecyclerView = (RecyclerView)  view.findViewById(R.id.reBottom);
//        mRecyclerView.setHasFixedSize(true);
//        onClickHomeBottom = this;
//        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);//new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new HomBottomImageAdapter(setDataForLists.viewBottom(),getActivity(),onClickHomeBottom);
//        mRecyclerView.setAdapter(mAdapter);

    }
    private void initPager(View view){
          viewPager = (ViewPager) view.findViewById(R.id.pager);

        homeSlideShow();
    }
    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
         } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onClick(int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
         switch (position){
            case 0:
                ft.add(R.id.main, new ShopCategory()).addToBackStack("");
                ft.commit();

                 break;
            case 1:
                ft.add(R.id.main, new WholeSaleMain()).addToBackStack("");
                ft.commit();

                break;
            case 2:
                ft.add(R.id.main, new OpenMarket()).addToBackStack("");
                ft.commit();

                break;
            case 3:

                ft.add(R.id.main, new Companies()).addToBackStack("");
                ft.commit();
                break;

            case 4:
                ft.replace(R.id.main, new VideoMain()).addToBackStack("s");
                ft.commit();
                break;
            case 5:
                ft.replace(R.id.main, new AirLineScreen());
                ft.commit();
                break;
            case 6:
                ft.replace(R.id.main, new HotelMain());
                ft.commit();
                break;
        }
    }

    @Override
    public void onBottomClick(int position) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
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
            if(new StoreData(getActivity()).getActivate()==0){
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }else  if(new StoreData(getActivity()).getActivate()==2){
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }else  if(new StoreData(getActivity()).getActivate()==3){
                ft.add(R.id.main, new MyProfile());
                ft.commit();
            }else{
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }

            break;
      }
     }



    private void homeFeatured(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    FeatureResponse    FeatureResponse = (FeatureResponse) result;
                    featuredHomes =   FeatureResponse.getData();
Log.d("uuuooo",featuredHomes.size()+"");
                    if(featuredHomes.size()>0) {

                        for(int i =0;i<featuredHomes.size();i++){

                            ShoppingProductListItem allProducts = featuredHomes.get(i);
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
                       HomeFeaturedtListAdapter homeFeaturedtListAdapter =
                               new HomeFeaturedtListAdapter(cartItemsModify, getActivity(),
                               onWholeSaleClick,onAddCart,onAddItem);
                       reHomeMain.setAdapter(homeFeaturedtListAdapter);
                   }


                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetHomeFeatured task = new GetHomeFeatured(getActivity(), onLoadingComplete);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    private void homeSlideShow(){
        if (Utility.isNetworkConnected(getActivity())) {

            onLoadingComplete = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    SlideShowResponse slideShowResponse = (SlideShowResponse) result;
                    slideShowArrayList =   slideShowResponse.getData();

                if(slideShowArrayList.size()>0){
                    CustomPagerAdapter adapter = new CustomPagerAdapter(getActivity(),slideShowArrayList);

                    viewPager.setAdapter(adapter);
                }
//                    wholeSaleAdapter = new WholeSaleAdapter(wholeSaleCategoryItems,getActivity(),onWholeSaleClick);
//                    reWholeSaleMain.setAdapter(wholeSaleAdapter);



                }

                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            GetSlide task = new GetSlide(getActivity(), onLoadingComplete);
            task.execute();

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }

    @Override
    public void onAddCart(int pos, int quan) {
        Log.d("ooo0",""+quan);

            if(quan<=featuredHomes.get(pos).getQuantity()) {
                if (!new StoreData(getActivity()).getToken().equals("")) {
                    ItemJson itemJson = new ItemJson((int) featuredHomes.get(pos).getProductID(), quan);
                    addToCart(itemJson);
                } else{
            if(databaseHelper.getCartItem(featuredHomes.get(pos).getID())!=null){
                long cre =    databaseHelper.updateCart(featuredHomes.get(pos),quan);
                Toast.makeText(getActivity(),getResources().getString(R.string.product_already_add),Toast.LENGTH_LONG).show();

                Log.d("ooo1",""+cre);
            }else {
              long cre =  databaseHelper.createCart(featuredHomes.get(pos),quan);
                if(cre!=0){
                    Toast.makeText(getActivity(),getResources().getString(R.string.product_add),Toast.LENGTH_LONG).show();
                }
                Log.d("ooo2",""+cre);
            }



        }
            }

            else {
            Toast.makeText(getActivity(), getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void add(int num, CartQuantity cartQuantity) {
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
        }      quant = num;

    }

    @Override
    public void onShopClick(int position) {
        Fragment fragment = new ShopProductDetails();
        Bundle bundle = new Bundle();
        bundle.putInt("id", (int) featuredHomes.get(position).getProductID());
        bundle.putInt("category_id", (int) featuredHomes.get(position).getCategoryID());
        bundle.putInt("quan",quant);

        Log.d("oooiidd", "" + featuredHomes.get(position).getProductID());
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main, fragment).addToBackStack("");
        ft.commit();
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

            AddCartItemShopping task= new  AddCartItemShopping(getActivity(), onLoadingComplete);
            task.execute(itemJson);

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }




}
