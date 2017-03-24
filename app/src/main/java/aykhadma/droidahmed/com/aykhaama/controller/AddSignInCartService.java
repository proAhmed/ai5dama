package aykhadma.droidahmed.com.aykhaama.controller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import aykhadma.droidahmed.com.aykhaama.api.AddCartSignApi;
import aykhadma.droidahmed.com.aykhaama.api.AddWholeSaleCartSignApi;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.main.MainActivity;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


/**
 * Created by ahmed on 12/20/2016.
 */
public class AddSignInCartService extends Service {

    Context context;
    OnLoadingComplete productListener;
    DatabaseHelper databaseHelper;
   static int ii=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
         databaseHelper = new DatabaseHelper(this);
         if( databaseHelper.getCart()!=null)
            if( databaseHelper.getCart().size()!=0)
                for(int i =0;i< databaseHelper.getCart().size();i++){
           ++ii;
           add( databaseHelper.getCart().get(i));

       }
        if( databaseHelper.getWholeSaleCart()!=null)
            if( databaseHelper.getWholeSaleCart().size()!=0)
                for(int i =0;i< databaseHelper.getWholeSaleCart().size();i++){
            ++ii;
            addWholeSale( databaseHelper.getWholeSaleCart().get(i));

        }
    }

    private void add(CartQuantity cartQuantity){
        if (Utility.isNetworkConnected(this)) {

            productListener = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    int ss =0;
                    if(databaseHelper.getCart()!=null){
                        ss = databaseHelper.getCart().size();
                    }
                        if(databaseHelper.getWholeSaleCart()!=null){
                            ss +=  databaseHelper.getWholeSaleCart().size();
                        }

                            if(ii==ss){
                         stopSelf();
                        Intent intents = new Intent(context, MainActivity.class);
                        intents.putExtra("cart","cart");
                        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intents);
                    }
                }

                @Override
                public void onFailure() {
                }
            };

            AddCartSignApi task = new AddCartSignApi(context, productListener);
            task.execute(cartQuantity);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    private void addWholeSale(CartQuantity cartQuantity){
        if (Utility.isNetworkConnected(this)) {

            productListener = new OnLoadingComplete() {

                @Override
                public void onSuccess(Object result) {
                    int ss =0;
                    if(databaseHelper.getCart()!=null){
                        ss = databaseHelper.getCart().size();
                    }
                    if(databaseHelper.getWholeSaleCart()!=null){
                        ss +=  databaseHelper.getWholeSaleCart().size();
                    }

                    if(ii==ss){
                        stopSelf();
                        Intent intents = new Intent(context, MainActivity.class);
                        intents.putExtra("cart","cart");
                        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intents);
                    }
                }

                @Override
                public void onFailure() {
                }
            };

            AddWholeSaleCartSignApi task = new AddWholeSaleCartSignApi(context, productListener);
            task.execute(cartQuantity);

        }

    }


}
