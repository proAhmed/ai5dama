package aykhadma.droidahmed.com.aykhaama.api;

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
import java.io.InputStream;
import java.io.InputStreamReader;

import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.UserLoginResponse;
import aykhadma.droidahmed.com.aykhaama.model.UserLoginResponseCart;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;


public class AddWholeSaleCartSignApi extends AsyncTask<CartQuantity, Void, UserLoginResponseCart> {

	private   String url ;
	private OnLoadingComplete callback;
	private Context context;

	public AddWholeSaleCartSignApi(Context context, OnLoadingComplete cb) {
		callback = cb;
		this.context = context;
		url = "http://ai5adma.com/API/ar/wscart/add?token="+new StoreData(context).getToken();
	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected UserLoginResponseCart doInBackground(CartQuantity... params) {
		String responseJSON = null;
		UserLoginResponseCart obj = null;

		try {
			responseJSON = makeRequest(params[0]);
			Log.d("iiioooo",responseJSON);
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
				obj = gson.fromJson(responseJSON, UserLoginResponseCart.class);
			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}
		}

		return obj;
	}

	@Override
	protected void onPostExecute(UserLoginResponseCart result) {


			callback.onSuccess(result);

	}

	public  String makeRequest(CartQuantity  param)
			throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		StringBuilder total = new StringBuilder();
		JSONObject json = new JSONObject();

		json.put("Product",param.getID());
		json.put("Quantity",param.getcQuantity());

Log.d("iiibbb",json.toString());
		InputStream is = new ByteArrayInputStream(json.toString().getBytes(
				"UTF-8"));

		InputStreamEntity entity = new InputStreamEntity(is, is.available());

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
