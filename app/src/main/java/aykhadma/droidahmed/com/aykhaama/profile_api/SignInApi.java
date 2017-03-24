package aykhadma.droidahmed.com.aykhaama.profile_api;

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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.UserLoginResponse;
import aykhadma.droidahmed.com.aykhaama.utility.StoreData;
import aykhadma.droidahmed.com.aykhaama.utility.Utility;


public class SignInApi extends AsyncTask<Object, Void, UserLoginResponse> {

	private final static String URL = "http://ai5adma.com/API/ar/profile/signin";
	private ProgressDialog dialog;
	private OnLoadingComplete callback;
	private Context context;

	public SignInApi(Context context, OnLoadingComplete cb) {
		dialog = new ProgressDialog(context);
		callback = cb;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage(context.getResources().getString(
				R.string.loading));
		this.dialog.setCancelable(false);
		this.dialog.show();
	}

	@Override
	protected UserLoginResponse doInBackground(Object... params) {
		String responseJSON = null;
		UserLoginResponse obj = null;

		try {
			responseJSON = makeRequest((String) params[0], (String)params[1],(ArrayList<CartQuantity>) params[2]);
			Log.d("ooo",responseJSON);
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
				obj = gson.fromJson(responseJSON, UserLoginResponse.class);
			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}
		}

		return obj;
	}

	@Override
	protected void onPostExecute(UserLoginResponse result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null&&((UserLoginResponse)result).isSuccess()) {
			callback.onSuccess(result);
		} else if(result != null&&!((UserLoginResponse)result).isSuccess()) {
			callback.onSuccess(  result );
		}else{
			callback.onFailure();
		}
	}

	public String makeRequest(String usename, String password, ArrayList<CartQuantity> cartQuantities)
			throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(URL);
		StringBuilder total = new StringBuilder();
		JSONObject json = new JSONObject();

		json.put("UserName", usename);
		json.put("Password", password);
		json.put("DeviceToken",new StoreData(context).getDeviceId());
		json.put("DeviceType",1);
		JSONArray jsonArray = new JSONArray();
//		if(cartQuantities.size()>0) {
//			for (int ii = 0; cartQuantities.size() > ii; ii++) {
//				JSONObject jsons = new JSONObject();
//				jsons.put("ID", cartQuantities.get(ii).getProductID());
//				jsons.put("Quantity", cartQuantities.get(ii).getcQuantity());
//				jsons.put("CreatedDate", Utility.getCurrentTimeStamp());
//				jsonArray.put(jsons);
//			}
//
//			json.put("CartItems", jsonArray);
//		}
		Log.d("iiisss",json.toString());
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
