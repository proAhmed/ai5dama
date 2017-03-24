package aykhadma.droidahmed.com.aykhaama.shopping_api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.CartItemResponse;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


public class ShowAfterDeletedCartItem extends AsyncTask<ArrayList<CartQuantity>, Void, CartItemResponse> {

	private final static String URL = "http://ai5adma.com/API/ar/cart";
	private ProgressDialog dialog;
	private OnLoadingComplete callback;
	private Context context;

	public ShowAfterDeletedCartItem(Context context, OnLoadingComplete cb) {
		dialog = new ProgressDialog(context);
		callback = cb;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage(context.getResources().getString(
				R.string.delete_cart_laoding));
		this.dialog.setCancelable(false);
		this.dialog.show();
	}

	@SafeVarargs
	@Override
	protected final CartItemResponse doInBackground(ArrayList<CartQuantity>... params) {
		String responseJSON = null;
		CartItemResponse obj = null;

		try {
			responseJSON = makeRequest(params[0]);

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
				obj = gson.fromJson(responseJSON, CartItemResponse.class);
			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}

		}

		return obj;
	}

	@Override
	protected void onPostExecute(CartItemResponse result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null) {
			callback.onSuccess(result);
		} else {
			callback.onFailure();
		}
	}

	public static String makeRequest(ArrayList<CartQuantity> cartQuantities) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(URL);
		StringBuilder total = new StringBuilder();
 		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		try {
			if(cartQuantities!=null)
		for(int i=0;i<cartQuantities.size();i++){
		JSONObject jsonObject = new JSONObject();

			jsonObject.put("ID", cartQuantities.get(i).getID());

			jsonObject.put("Quantity", cartQuantities.get(i).getcQuantity());
			jsonObject.put("CreatedDate", Utility.getCurrentTimeStamp());
			jsonArray.put(jsonObject);

	}} catch (JSONException e) {
		e.printStackTrace();
	}
 		json.put("CartItems", jsonArray);


		InputStreamEntity entity = null;
		try {
			InputStream is = new ByteArrayInputStream(json.toString().getBytes(
					"UTF-8"));

			entity = new InputStreamEntity(is, is.available());

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		httpost.setEntity(entity);

		httpost.setHeader("Content-type", "application/json");
		HttpResponse response = (HttpResponse) httpclient.execute(httpost);

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
