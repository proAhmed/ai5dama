package aykhadma.droidahmed.com.aykhaama.shopping_api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.DeleteProduct;
import aykhadma.droidahmed.com.aykhaama.model.ItemJson;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;


public class UpdateItemShopping extends AsyncTask<ItemJson, Void, DeleteProduct> {

	private  String url ;
	private ProgressDialog dialog;
	private OnLoadingComplete callback;
	private Context context;

	public UpdateItemShopping(Context context, OnLoadingComplete cb) {
		dialog = new ProgressDialog(context);
		callback = cb;
		this.context = context;
		url = "http://ai5adma.com/API/ar/cart/update_quantity?token="+new StoreData(context).getToken();
		Log.d("uuuooo0",url);
	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage(context.getResources().getString(
				R.string.loading)
		);

		this.dialog.setCancelable(false);
		this.dialog.show();
	}

	@Override
	protected DeleteProduct doInBackground(ItemJson... params) {
		String responseJSON = null;
		DeleteProduct obj = null;

		try {
			responseJSON = makeRequest(params[0]);
			Log.d("uuuooo1",responseJSON);

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
				obj = gson.fromJson(responseJSON, DeleteProduct.class);
			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}

		}

		return obj;
	}

	@Override
	protected void onPostExecute(DeleteProduct result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null) {
			callback.onSuccess(result);
		} else {
			callback.onFailure();
		}
	}

	public   String makeRequest(ItemJson hashSet) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		StringBuilder total = new StringBuilder();
		JSONObject json = new JSONObject();
 		json.put("Product", hashSet.getIdItem());
		json.put("Quantity",hashSet.getQuantityItem());

		Log.d("uuuooo2", json.toString());

		InputStreamEntity entity = null;
		try {
			InputStream is = new ByteArrayInputStream(json.toString().getBytes(
					"UTF-8"));

			entity = new InputStreamEntity(is, is.available());

		} catch (IOException e) {

			e.printStackTrace();
		}

		httpPost.setEntity(entity);

		httpPost.setHeader("Content-type", "application/json");
		HttpResponse response = httpclient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			InputStream instream = response.getEntity().getContent();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					instream), 8000);
			String line;
			while ((line = r.readLine()) != null) {
				total.append(line);
			}
			instream.close();
		}
		return total.toString();

	}
}
