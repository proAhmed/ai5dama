package aykhadma.droidahmed.com.aykhaama.shop_cart;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.adapter.CartGridAdapters;
import aykhadma.droidahmed.com.aykhaama.controller.DatabaseHelper;
import aykhadma.droidahmed.com.aykhaama.home.HomeFragment;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnAddItem;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnCancelOrder;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnCartListener;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartItem;
import aykhadma.droidahmed.com.aykhaama.model.CartItemResponse;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.CheckOutData;
import aykhadma.droidahmed.com.aykhaama.model.DeleteProduct;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.model.ResponseOfCheckOut;
import aykhadma.droidahmed.com.aykhaama.shopping_api.CheckOutShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.DeleteCartItemShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.ShowCartItemShopping;
import aykhadma.droidahmed.com.aykhaama.shopping_api.UpdateItemShopping;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


/**
 * Created by ahmed on 3/15/2016.
 */
public class ProductCartShopping extends Fragment
     implements OnCartListener  ,OnCancelOrder,OnAddItem

{
     ListView lstProduct;
//
    ArrayList<CartItem> pro;
     Button btnRequest, btnCancel;
     TextView tvDeliver, tvTotal, tvFinalTotal;
    private OnLoadingComplete ProductListener;
//     AllProducts[] pros;
//ArrayList<ProductCart>productCarts;
  //  ArrayList<ProductCart>productCartItems;
    CartItemResponse cartItemResponse;
     ResponseOfCheckOut checkResponse;
     CheckOutData checkOutDatas;
     ArrayList<CartItem> checkCart;
  ArrayList<ItemJson>itemJsonArrayList;
      ArrayList<CartQuantity>cartItemArrayList;
    static double pricessss;
    private JSONArray jsonArrayItem;
      OnCartListener onCartListener;
    OnCancelOrder onCancelOrder;
//    SaveAuth saveAuth;
    ProductCartShopping fragmentProductCart;
     OnAddItem onAddItem;
     int checkAdd = 0;
    int checkEnter = 0;
    Utility utility;
//     ArrayList<ItemAddedAlready>itemAddedAlreadies;
    DatabaseHelper databaseHelper;
     @Nullable
  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart_mod, container, false);
        lstProduct = (ListView) view.findViewById(R.id.cart);
        pro = new ArrayList<>();
         databaseHelper = new DatabaseHelper(getActivity());
         onCartListener = (OnCartListener) this;
        onCancelOrder = (OnCancelOrder) this;
        onAddItem = this;
        fragmentProductCart = this;
//        productCarts = new ArrayList<>();
//        productCartItems = new ArrayList<>();
        checkCart = new ArrayList<>();
        checkOutDatas = new CheckOutData();
        itemJsonArrayList = new ArrayList<>();
        cartItemArrayList = new ArrayList<>();
        final Bundle bundle = getArguments();
        jsonArrayItem = new JSONArray();
        btnRequest = (Button) view.findViewById(R.id.btnRequest);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        tvDeliver = (TextView) view.findViewById(R.id.tvDeliver);
        utility = new Utility();
//        itemAddedAlreadies = new ArrayList<>();

//        if( Locale.getDefault().getDisplayLanguage().equals("العربية")){
//        tvDeliver.setText( utility.arabicNumaricCode(String.valueOf("1 دك")));
//        }else{
            tvDeliver.setText("1 دك");

//        }
         tvTotal = (TextView) view.findViewById(R.id.tvTotal);
         tvFinalTotal = (TextView) view.findViewById(R.id.tvFinalTotal);
        showCart();

         final MainActivity mainActivity = (MainActivity) getActivity();
         ImageView  imageToggle = (ImageView) mainActivity.findViewById(R.id.imageToggle);
         imageToggle.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 mainActivity. toggle();

             }
         });
        //        tvTotal.setText(""+pricessss);
//        saveAuth = (SaveAuth) getActivity().getApplicationContext();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main, new HomeFragment(),"").commit();

            }
        });
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


        if (priceProduct(cartItemArrayList) > 10) {
            callCheckPay();

        } else {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.price_total), Toast.LENGTH_LONG).show();

    }


            }catch (Exception e){

                    }
            }
        });

         return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
try {
    getActivity().findViewById(R.id.imageToggle).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showSecondaryMenu();
            } catch (Exception e) {

            }
        }
    });

    getActivity().findViewById(R.id.imageToggleCategory).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null)
                mainActivity.toggle();
        }
    });

        }catch (Exception e){

        }
       }


//
//    @Override
//    public void cancel(CartQuantity cartQuantity) {
//
//    }


//
    @Override
    public void onAddCart(CartQuantity cartQuantity, int num,boolean watch,double price) {
        pricessss +=price;
          if(watch==true){
              ProductCartShopping fragment =   new ProductCartShopping();
            Bundle bundle = new Bundle();
             bundle.putDouble("price",pricessss);
        if(bundle.getDouble("price")==0){
         tvTotal.setText(String.format("%.3f", price));

            }
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("")
                    .replace(R.id.main, fragment).commit();
        }
     }
    private void dialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.cart_dialog);
        dialog.setTitle(getResources().getString(R.string.dialog_buy));

        // set the custom dialog components - text, image and button

        Button btnNotAuth = (Button) dialog.findViewById(R.id.btnNotAuth);
        // if button is clicked, close the custom dialog
//        btnNotAuth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), CartNotAuth.class);
//                 startActivity(intent);
//
//                dialog.dismiss();
//            }
//        });
        Button btnAuth = (Button) dialog.findViewById(R.id.btnAuthCart);
        // if button is clicked, close the custom dialog
        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(!new StoreData(getActivity()).getAuthName().equals("")){
//                    callCheck();
//                }else{
//                    Intent intent = new Intent(getActivity(), SignIn.class);
//                    intent.putExtra("CartAuth","CartAuth");
//                    startActivity(intent);
//                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void dialogPay(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pay_dialog);
        dialog.setTitle(getResources().getString(R.string.dialog_pay));

        // set the custom dialog components - text, image and button

        Button btnKnet = (Button) dialog.findViewById(R.id.btnKnet);
        // if button is clicked, close the custom dialog
        btnKnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  callCheck();
                dialog.dismiss();
            }
        });
        Button btnDirect = (Button) dialog.findViewById(R.id.btnDirect);
        // if button is clicked, close the custom dialog
        btnDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCheckPay();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

////        private void callCheck(){
////    if (Utility.isNetworkConnected(getActivity())) {
////
////        ProductListener = new OnProcessCompleteListener() {
////
////            @Override
////            public void onSuccess(Object result) {
////          //      checkResponse = (ResponseOfCheckOut) result;
////         //       checkOutDatas =   checkResponse.getData();
////                try {
////                 //   checkCart = checkOutDatas.getProducts();
////               //     Toast.makeText(getActivity(), checkOutDatas.getMessage(), Toast.LENGTH_LONG).show();
////                    Fragment aboutFragments = new WebPaymentFragment();
////
////                    getActivity().getSupportFragmentManager().beginTransaction()
////                            .replace(R.id.mainFragment, aboutFragments).addToBackStack("")
////                            .commitAllowingStateLoss();
////                }catch (Exception e){
////
////                }
//////                databaseHelper.delete();
//////                databaseHelper.deleteCart();
//////                databaseHelper.deleteCartAdd();
//////                databaseHelper.remove();
//////                databaseHelper.removeCart();
//////                databaseHelper.removeCartAdd();
//////                new StoreData(getActivity()).saveCartAdded(0);
//////                tvFinalTotal.setText("");
//////                tvTotal.setText("");
//////                new StoreData(getActivity()).setDialogType("");
////
////            }
////
////            @Override
////            public void onFailure() {
////                Utility.showFailureDialog(getActivity(), false);
////            }
////        };
////
////        CheckOutForSignUserKnet task = new CheckOutForSignUserKnet(getActivity(), ProductListener);
////        task.execute(new StoreData(getActivity()).getAuthName(),new StoreData(getActivity()).getAuthPass());
////
////    } else {
////        Utility.showValidateDialog(
////                getResources().getString(R.string.failure_ws),
////                getActivity());
////    }
////
////}
////
    private void callCheckPay(){
        if(new StoreData(getActivity()).getToken().equals("")){
           Utility utility = new Utility();
            utility.loginDialog(getActivity(),"");
        }else {
            if (Utility.isNetworkConnected(getActivity())) {

                ProductListener = new OnLoadingComplete() {

                    @Override
                    public void onSuccess(Object result) {
                        ResponseOfCheckOut checkResponse = (ResponseOfCheckOut) result;
                        checkOutDatas = checkResponse.getData();
                        try {
                            //    checkCart = checkOutDatas.getProducts();
                            databaseHelper.removeCart();
                            Toast.makeText(getActivity(), checkOutDatas.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("iii", checkOutDatas.getMessage());
                            showCart();
                        } catch (Exception e) {
                            Log.d("iii", e.toString());
                        }

                        tvFinalTotal.setText("");
                        tvTotal.setText("");

                    }

                    @Override
                    public void onFailure() {
                        Utility.showFailureDialog(getActivity(), false);
                    }
                };

                CheckOutShopping task = new CheckOutShopping(getActivity(), ProductListener);
                task.execute(cartItemArrayList);

            } else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.failure_ws),
                        getActivity());
            }
        }
    }

    public double priceProduct(ArrayList<CartQuantity> arrayList){
        double price=0;
        for (int i=0;i<arrayList.size();i++){
            price +=   arrayList.get(i).getPrice()*arrayList.get(i).getcQuantity();
        }
        return price;
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            getActivity().findViewById(R.id.imageToggle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.showSecondaryMenu();
                    } catch (Exception e) {

                    }
                }
            });
            TextView tv = (TextView) getActivity().findViewById(R.id.textTitle);
            tv.setVisibility(View.GONE);
            ImageView img = (ImageView) getActivity().findViewById(R.id.logo);
            img.setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.imageToggleCategory).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainActivity mainActivity = (MainActivity) getActivity();
                    if (mainActivity != null)
                        mainActivity.toggle();
                }
            });
        }catch (Exception e){

        }


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
    public void cancel(CartQuantity cartQuantity) {
            cancelApiAuth(cartQuantity.getID());

    }
    private void cancelApiAuth(final int id){

        if(new StoreData(getActivity()).getToken()!=null|new StoreData(getActivity()).getToken().equals("")){
            long i =  databaseHelper.deleteCart(id);
            if(i!=0){
                Toast.makeText(getActivity(),getResources().getString(R.string.product_deleted), Toast.LENGTH_LONG).show();

            }

            cartItemArrayList = databaseHelper.getCart();
            if (priceProduct(cartItemArrayList) > 0) {
                priceProduct(cartItemArrayList);

                tvTotal.setText(String.format("%.3f", (priceProduct(cartItemArrayList))) + " " + getActivity().getResources().getString(R.string.dr));
                tvFinalTotal.setText(String.format("%.3f", (priceProduct(cartItemArrayList) + 1)) + " " + getActivity().getResources().getString(R.string.dr));
            } else {
                tvFinalTotal.setText("");
                tvTotal.setText("");

            }

            CartGridAdapters cartGridAdapter = new CartGridAdapters(getActivity(), cartItemArrayList, onCartListener, onCancelOrder, onAddItem);
            cartGridAdapter.notifyDataSetChanged();
            lstProduct.setAdapter(cartGridAdapter);
        }else {

            if (Utility.isNetworkConnected(getActivity())) {

                ProductListener = new OnLoadingComplete() {

                    @Override
                    public void onSuccess(Object result) {
                        DeleteProduct deleteProduct = (DeleteProduct) result;
                        try {
                            Toast.makeText(getActivity(), deleteProduct.getData(), Toast.LENGTH_LONG).show();

                        } catch (Exception e) {

                        }
                        ProductListener = new OnLoadingComplete() {

                            @Override
                            public void onSuccess(Object result) {
                                CartItemResponse cartItemResponse = (CartItemResponse) result;
                                cartItemArrayList = cartItemResponse.getData();
//                            cartItemArrayList.remove(saveAuth.getCancelPosition());

                                if (priceProduct(cartItemArrayList) > 0) {
                                    priceProduct(cartItemArrayList);

                                    tvTotal.setText(String.format("%.3f", (priceProduct(cartItemArrayList))) + " " + getActivity().getResources().getString(R.string.dr));
                                    tvFinalTotal.setText(String.format("%.3f", (priceProduct(cartItemArrayList) + 1)) + " " + getActivity().getResources().getString(R.string.dr));
                                } else {
                                    tvFinalTotal.setText("");
                                    tvTotal.setText("");

                                }

                                CartGridAdapters cartGridAdapter = new CartGridAdapters(getActivity(), cartItemArrayList, onCartListener, onCancelOrder, onAddItem);
                                cartGridAdapter.notifyDataSetChanged();
                                lstProduct.setAdapter(cartGridAdapter);

                            }

                            @Override
                            public void onFailure() {
                                Utility.showFailureDialog(getActivity(), false);
                            }
                        };

                        ShowCartItemShopping task = new ShowCartItemShopping(getActivity(), ProductListener);
                        task.execute();
                    }

                    @Override
                    public void onFailure() {
                        Utility.showFailureDialog(getActivity(), false);
                    }
                };

                DeleteCartItemShopping task = new DeleteCartItemShopping(getActivity(), ProductListener);
                task.execute(id + "");

            } else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.failure_ws),
                        getActivity());
            }
        }
    }
    private void showCart(){
        if(new StoreData(getActivity()).getToken()==null|new StoreData(getActivity()).getToken().equals("")){
            cartItemArrayList = databaseHelper.getCart();
            if(cartItemArrayList!=null) {
                if (priceProduct(cartItemArrayList) > 0) {
                    priceProduct(cartItemArrayList);

                    tvTotal.setText(String.format("%.3f", (priceProduct(cartItemArrayList))) + " " + getActivity().getResources().getString(R.string.dr));
                    tvFinalTotal.setText(String.format("%.3f", (priceProduct(cartItemArrayList) + 1)) + " " + getActivity().getResources().getString(R.string.dr));
                } else {
                    tvFinalTotal.setText("");
                    tvTotal.setText("");

                }

                CartGridAdapters cartGridAdapter = new CartGridAdapters(getActivity(), cartItemArrayList, onCartListener, onCancelOrder, onAddItem);
                cartGridAdapter.notifyDataSetChanged();
                lstProduct.setAdapter(cartGridAdapter);
            }
        }else {
            if (Utility.isNetworkConnected(getActivity())) {
                Log.d("ooo",new StoreData(getActivity()).getToken());

                ProductListener = new OnLoadingComplete() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            CartItemResponse cartItemResponse = (CartItemResponse) result;
                            cartItemArrayList = cartItemResponse.getData();
                            double pric = priceProduct(cartItemArrayList);
                            lstProduct.setAdapter(new CartGridAdapters(getActivity(), cartItemArrayList, onCartListener, onCancelOrder, onAddItem
                            ));

                            tvTotal.setText(String.format("%.3f", pric) + " " + getActivity().getResources().getString(R.string.dr));
                            tvFinalTotal.setText(String.format("%.3f", pric + 1) + " " + getResources().getString(R.string.dr));


                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onFailure() {
                        Utility.showFailureDialog(getActivity(), false);
                    }
                };

                ShowCartItemShopping task = new ShowCartItemShopping(getActivity(), ProductListener);
                task.execute();

            } else {
                Utility.showValidateDialog(
                        getResources().getString(R.string.failure_ws),
                        getActivity());
            }
        }
    }


    @Override
    public void add(int num, CartQuantity cartQuantity) {
         cartQuantity.setcQuantity(num);
        cartQuantity.setAdded(1);

        if(num<=cartQuantity.getQuantity()) {

            change(cartQuantity);

            cartQuantity.setcQuantity(num);





//         try
//        {
//            cartItemArrayList.remove(cartQuantity);
//            cartItemArrayList.add(cartQuantity);
   double  pric=   priceProduct(cartItemArrayList) ;
        tvTotal.setText(String.format("%.3f", pric )+ " " + getActivity().getResources().getString(R.string.dr));
        tvFinalTotal.setText(String.format("%.3f", pric + 1) + " " +getResources().getString(R.string.dr));
//       }  catch (Exception e){
//    }
    } else{
            Toast.makeText(getActivity(),getResources().getString(R.string.amount), Toast.LENGTH_LONG).show();

        }
    }
    DeleteProduct updateItem;
    private void change(CartQuantity item){
        ItemJson itemJson = new ItemJson(item.getID(),item.getcQuantity());
        if (Utility.isNetworkConnected(getActivity())) {

            ProductListener = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    updateItem = (DeleteProduct) result;
                    String SucessupdateItem = updateItem.getData();
                    try{
                        Toast.makeText(getActivity(), SucessupdateItem, Toast.LENGTH_LONG).show();

                    }catch (Exception e){

                    }

                        ProductListener = new OnLoadingComplete() {

                            @Override
                            public void onSuccess(Object result) {
                                CartItemResponse  cartItemResponse = (CartItemResponse) result;
                                cartItemArrayList=   cartItemResponse.getData();
//                            cartItemArrayList.remove(saveAuth.getCancelPosition());


                                if(priceProduct(cartItemArrayList)>0){
                                    priceProduct(cartItemArrayList);

                                    tvTotal.setText(String.format("%.3f", (priceProduct(cartItemArrayList)))+" "+getActivity().getResources().getString(R.string.dr));
                                    tvFinalTotal.setText(  String.format("%.3f", (priceProduct(cartItemArrayList) + 1))+" "+getActivity().getResources().getString(R.string.dr) );
                                }

                                else{
                                    tvFinalTotal.setText("");
                                    tvTotal.setText("");

                                }

                                CartGridAdapters cartGridAdapter = new CartGridAdapters(getActivity(), cartItemArrayList, onCartListener, onCancelOrder,onAddItem);
                                cartGridAdapter.notifyDataSetChanged();
                                lstProduct.setAdapter(cartGridAdapter);

                            }

                            @Override
                            public void onFailure() {
                                Utility.showFailureDialog(getActivity(), false);
                            }
                        };

                        ShowCartItemShopping task = new ShowCartItemShopping(getActivity(), ProductListener);
                        task.execute();
                    }



                @Override
                public void onFailure() {
                    Utility.showFailureDialog(getActivity(), false);
                }
            };

            UpdateItemShopping task = new UpdateItemShopping(getActivity(), ProductListener);
            task.execute(itemJson);

        } else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.failure_ws),
                    getActivity());
        }
    }


}
