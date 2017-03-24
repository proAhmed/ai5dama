package aykhadma.droidahmed.com.aykhaama.shopping_api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListMain;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;


/**
 * Created by ahmed on 4/3/2016.
 */
public class GetShoppingFavorite extends AsyncTask<String,Void,ShoppingProductListMain> {
    private ProgressDialog dialog;
     String finalUrl;
    private OnLoadingComplete callback;
    private Context context;
    String responseJSON;
    public GetShoppingFavorite(Context context, OnLoadingComplete cb) {
        dialog = new ProgressDialog(context);
        callback = cb;
        this.context = context;
        finalUrl =   "http://ai5adma.com/API/ar/wishlist?token="+ new StoreData(context).getToken();
        Log.d("urlll",finalUrl);
     }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage(context.getResources().getString(
                R.string.loading));
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected ShoppingProductListMain doInBackground(String... params) {

        ShoppingProductListMain obj = null;

        try {
            responseJSON = invokeJSONWS();
            Log.d("ggg",responseJSON);
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Gson gson = new Gson();
        if (responseJSON != null && responseJSON.length() > 1) {

            GsonBuilder gb = new GsonBuilder();
            gb.serializeNulls();
            gson = gb.create();
            try {
                obj = gson.fromJson(responseJSON, ShoppingProductListMain.class);
            } catch (com.google.gson.JsonSyntaxException ex) {
                ex.printStackTrace();

            }

        }
        return obj;
    }

    @Override
    protected void onPostExecute(ShoppingProductListMain responseHome) {
        super.onPostExecute(responseHome);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (responseHome != null ) {
            callback.onSuccess(responseHome);
        } else {
            callback.onFailure();
        }
    }

    private String invokeJSONWS() throws IOException {
        HttpURLConnection httpConn = null;
        InputStream in = null;
        int response = -1;
        String responseJSON;
        URL url = new URL(finalUrl);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try {
              httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(10000);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);

            httpConn.connect();

            response = httpConn.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            responseJSON = out.toString();

        } catch (Exception e) {
            throw new IOException("Error connecting");
        }finally {

            httpConn.disconnect();
        }
        return responseJSON;
    }
}
