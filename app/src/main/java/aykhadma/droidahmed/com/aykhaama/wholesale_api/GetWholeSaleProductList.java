package aykhadma.droidahmed.com.aykhaama.wholesale_api;

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
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListMain;


public class GetWholeSaleProductList extends AsyncTask<String, Void, WholeSaleProductListMain> {

	private   String URL =  "http://ai5adma.com/API/ar/wsproduct/c/";

	private ProgressDialog dialog;
	private OnLoadingComplete callback;
	private Context context;

	public GetWholeSaleProductList(Context context, OnLoadingComplete cb, int id) {
		dialog = new ProgressDialog(context);
		callback = cb;
		this.context = context;
		String iid= (id+"").trim();
		URL = URL+iid;
		Log.d("iiill",URL.trim());
  	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage(context.getResources().getString(
				R.string.loading_proudct));
		this.dialog.setCancelable(false);
		this.dialog.show();
	}

	@Override
	protected WholeSaleProductListMain doInBackground(String... params) {

		String responseJSON = null;
		try {
			responseJSON = invokeJSONWS();
			Log.d("iiigg",responseJSON);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Gson gson = new Gson();

		WholeSaleProductListMain obj = null;
		if (responseJSON != null && responseJSON.length() > 1) {

			GsonBuilder gb = new GsonBuilder();
			gb.serializeNulls();
			gson = gb.create();
			try {
				obj = gson.fromJson(responseJSON, WholeSaleProductListMain.class);
			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}
		}
		return obj;

	}

	@Override
	protected void onPostExecute(WholeSaleProductListMain result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null) {
			callback.onSuccess(result);
		} else {
			callback.onFailure();
		}
	}

	private String invokeJSONWS() throws IOException {
		HttpURLConnection httpConn = null;

		InputStream in = null;
		int response = -1;
		String responseJSON;
		URL url = new URL(URL);
		URLConnection conn = url.openConnection();
		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			  httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod("GET");
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
