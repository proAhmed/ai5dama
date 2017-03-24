package aykhadma.droidahmed.com.aykhaama.profile_api;

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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;
import aykhadma.droidahmed.com.aykhaama.model.UserSignOutResponse;


public class SignOutApi extends AsyncTask<String, Void, UserSignOutResponse> {

	private final static String URL = "http://ai5adma.com/API/ar/profile/signout";
	private ProgressDialog dialog;
	private OnLoadingComplete callback;
	private Context context;

	public SignOutApi(Context context, OnLoadingComplete cb) {
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
	protected UserSignOutResponse doInBackground(String... params) {
		String responseJSON = null;
		UserSignOutResponse obj = null;

		try {
			responseJSON = makeRequest();
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
				obj = gson.fromJson(responseJSON, UserSignOutResponse.class);
			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}
		}

		return obj;
	}

	@Override
	protected void onPostExecute(UserSignOutResponse result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null&&((UserSignOutResponse)result).isSuccess()) {
			callback.onSuccess(result);
		} else if(result != null&&!((UserSignOutResponse)result).isSuccess()) {
			callback.onSuccess(  result );
		}else{
			callback.onFailure();
		}
	}

	public static String makeRequest()
			throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(URL);
		StringBuilder total = new StringBuilder();
		JSONObject json = new JSONObject();



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
