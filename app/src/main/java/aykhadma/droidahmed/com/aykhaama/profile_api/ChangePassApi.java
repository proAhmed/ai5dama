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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import aykhadma.droidahmed.com.aykhaama.R;
import aykhadma.droidahmed.com.aykhaama.interfaces.OnLoadingComplete;


public class ChangePassApi extends AsyncTask<String, Void, changePassMain> {

 	private ProgressDialog dialog;
	private OnLoadingComplete callback;
	private Context context;
  String finalUrl ;
	public ChangePassApi(Context context, OnLoadingComplete cb) {
		dialog = new ProgressDialog(context);
		callback = cb;
		this.context = context;

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
	protected changePassMain doInBackground(String... params) {
		String responseJSON = null;
		changePassMain obj = null;

		try {
		 	responseJSON = makeRequest(params[0]);
		 	Log.d("ooo11",responseJSON);
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
				obj = gson.fromJson(responseJSON, changePassMain.class);

			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}

		}

		return obj;
	}

	@Override
	protected void onPostExecute(changePassMain result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null) {
			callback.onSuccess(result);
		} else {
			callback.onFailure();
		}
	}
 	public  String makeRequest(String email) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();


		HttpPost httpPost = new HttpPost("http://ai5adma.com/API/ar/profile/forgotpassword");
		StringBuilder total = new StringBuilder();
		JSONObject json = new JSONObject();

		json.put("Email",email);

		Log.d("vvv1", json.toString());
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

//		httpPost.setHeader("Accept-Encoding", "application/json");
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
