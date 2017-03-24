package aykhadma.droidahmed.com.aykhaama.offer_api;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.OfferMain;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListMain;


/**
 * Created by ahmed on 4/3/2016.
 */
public class SearchOfferApi extends AsyncTask<String,Void,OfferMain> {
    private ProgressDialog dialog;
    private final static String URL =  "http://ai5adma.com/API/ar/offer/search?q=";
    private OnLoadingComplete callback;
    private Context context;
    String finalUrl;
    public SearchOfferApi(Context context, OnLoadingComplete cb, String word) {
        dialog = new ProgressDialog(context);
        callback = cb;
        this.context = context;
        String query ;
        Log.d("pppooo",word);
        try {
            query = URLEncoder.encode(word, "utf-8");
            finalUrl = URL + query;
            Log.d("ppp",finalUrl);
          //  +"&page="+pag
        } catch (UnsupportedEncodingException e) {
            Log.d("pppo",e.toString());
        e.printStackTrace();
    }
     }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage(context.getResources().getString(
                R.string.loading_search));
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected OfferMain doInBackground(String... params) {
        String responseJSON = null;
        OfferMain obj = null;

        try {
            responseJSON = invokeJSONWS();
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
                obj = gson.fromJson(responseJSON, OfferMain.class);
            } catch (com.google.gson.JsonSyntaxException ex) {
                ex.printStackTrace();

 
            }

        }
        return obj;
    }

    @Override
    protected void onPostExecute(OfferMain responseHome) {
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
        java.net.URL url = new URL(finalUrl);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try {
              httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.setConnectTimeout(6000);
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
