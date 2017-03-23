package com.wearable.mivors.myapplication.connector;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wearable.mivors.myapplication.R;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.controller.StoreData;
import com.wearable.mivors.myapplication.controller.Utility;
import com.wearable.mivors.myapplication.model.Row;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ahmed on 2/21/2017.
 */
public class GetImage  extends AsyncTask<String, Void, String> {


  private ProgressDialog dialog;
  private OnLoadingComplete callback;
  private Context context;
  int id;

  public GetImage(Context context, OnLoadingComplete cb,int id) {
    dialog = new ProgressDialog(context);
    callback = cb;
    this.context = context;
    this.id = id;
  }
  String arrayList;

  @Override
  protected void onPreExecute() {
    this.dialog.setMessage(context.getResources().getString(
            R.string.loading));
    this.dialog.setCancelable(false);
    this.dialog.show();
  }

  @Override
  protected String doInBackground(String... params) {
    String responseJSON = null;
    try {
      responseJSON = requestContent(id);
      Log.d("oooii",responseJSON);

      Utility utility = new Utility();
      arrayList = utility.getImage(responseJSON);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      Log.d("vvv",e.toString());

    }

    return arrayList;

  }

  @Override
  protected void onPostExecute(String result) {

    if (dialog.isShowing()) {
      dialog.dismiss();
    }
    callback.onSuccess(result);
  }




  private String requestContent(int id) throws UnsupportedEncodingException {
    HttpClient httpclient = new DefaultHttpClient();
    String result = null;
    String url = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=SELECT%20Contacts.CustomFields.c.profile_picture%20FROM%20contacts%20WHERE%20id=%20"+id;
Log.d("uuu",url);
//        String query = URLEncoder.encode("Select Incident.ID,Incident.PrimaryContact.Contact.ID as 'c_id',Incident.PrimaryContact.Contact.LookupName,Incident.Subject,Incident.AssignedTo.Account.LookupName as 'c_lookup' from Incident", "utf-8");
//        String url = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=" + query;
    String base64EncodedCredentials = "Basic " + Base64.encodeToString(
            ("Customization" + ":" + "Mivors@2016").getBytes(),
            Base64.NO_WRAP);
    HttpGet httpget = new HttpGet(url);
    httpget.setHeader("Authorization", base64EncodedCredentials);
    HttpResponse response;
    InputStream instream = null;

    try {
      response = httpclient.execute(httpget);
      HttpEntity entity = response.getEntity();

      if (entity != null) {
        instream = entity.getContent();
        result = convertStreamToString(instream);
      }

    } catch (Exception e) {
      // manage exceptions
    } finally {
      if (instream != null) {
        try {
          instream.close();
        } catch (Exception exc) {

        }
      }
    }

    return result;
  }

  public String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line = null;

    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
    } finally {
      try {
        is.close();
      } catch (IOException e) {
      }
    }

    return sb.toString();
  }



  Bitmap bmImg;
  public Bitmap downloadFile(String stringURL)
  {
    try
    {
      DefaultHttpClient client = new DefaultHttpClient();
      client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials("Customization", "Mivors@2016"));
      HttpGet request = new HttpGet(stringURL);
      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();
      InputStream inputStream = entity.getContent();
      bmImg = BitmapFactory.decodeStream(inputStream);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return bmImg;
  }






}
