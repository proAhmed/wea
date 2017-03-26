package com.wearable.mivors.myapplication.connector;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.model.MangerModel;

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



public class UpdateStatus extends AsyncTask<MangerModel, Void, String> {

	private  String URL = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/incidents/";
	private ProgressDialog dialog;
	private OnLoadingComplete onLoadingComplete;
	private Context context;

	public UpdateStatus(Context context, OnLoadingComplete onLoadingComplete,int id) {
		dialog = new ProgressDialog(context);
		this.onLoadingComplete = onLoadingComplete;
		this.context = context;
		URL = URL+id;
		Log.d("oookkiii",URL);
	}
	//////// update status by id ,subject,status

	@Override
	protected void onPreExecute() {
//		this.dialog.setMessage(context.getResources().getString(
//				R.string.add_cart_laoding)
//		);

		this.dialog.setCancelable(false);
		this.dialog.show();
	}

	@Override
	protected String doInBackground(MangerModel... params) {
		String responseJSON = null;
		String obj = null;

		try {
			responseJSON = makeRequest(params[0]);
			Log.d("ooovvv",responseJSON);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("ooo11",e.toString());
		}

		Gson gson = new Gson();
		if (responseJSON != null && responseJSON.length() > 1) {

			GsonBuilder gb = new GsonBuilder();
			gb.serializeNulls();
			gson = gb.create();
			try {
				obj = gson.fromJson(responseJSON, String.class);
			} catch (com.google.gson.JsonSyntaxException ex) {
				ex.printStackTrace();
			}

		}

		return obj;
	}

	@Override
	protected void onPostExecute(String result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		if (result != null) {
			onLoadingComplete.onSuccess(result);
		} else {
			onLoadingComplete.onFailure();
		}
	}

	public   String makeRequest(MangerModel mangerModel) throws Exception {
		String base64EncodedCredentials = "Basic " + Base64.encodeToString(
				("Customization" + ":" + "Mivors@2016").getBytes(),
				Base64.NO_WRAP);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);
		httpPost.setHeader("X-HTTP-Method-Override","PATCH");
		httpPost.setHeader("Authorization",base64EncodedCredentials);


		StringBuilder total = new StringBuilder();
		JSONObject json = new JSONObject();

			JSONObject primaryContact = new JSONObject();
			primaryContact.put("id",mangerModel.getPrimaryId());
		Log.d("ooo2",primaryContact.toString());
		JSONObject statusWithType = new JSONObject();
			JSONObject status= new JSONObject();
			status.put("id",mangerModel.getStatus());
			statusWithType.put("status",status);
			JSONObject statusType = new JSONObject();
			statusType.put("id",mangerModel.getStatus());
			statusWithType.put("statusType",statusType);


		json.put("primaryContact",primaryContact);
		json.put("statusWithType",statusWithType);
		Log.d("oookk",json.toString());

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
