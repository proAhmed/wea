package com.wearable.mivors.myapplication.connector;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.wearable.mivors.myapplication.R;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.controller.Utility;

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
import java.util.ArrayList;

/**
 * Created by ahmed on 2/21/2017.
 */
public class GetImageDownload extends AsyncTask<String, Void,Bitmap> {


  private ProgressDialog dialog;
  private OnLoadingComplete callback;
  private Context context;
  int id;
  String string;
  public GetImageDownload(Context context, OnLoadingComplete cb, String string) {
    dialog = new ProgressDialog(context);
    callback = cb;
    this.context = context;
    this.string = string;
  }
  ArrayList<String> arrayList;

  @Override
  protected void onPreExecute() {

  }

  @Override
  protected Bitmap doInBackground(String... params) {
    Bitmap responseJSON = null;
    try {
      responseJSON = downloadFile( );

     } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      Log.d("vvv",e.toString());

    }

    return responseJSON;

  }

  @Override
  protected void onPostExecute(Bitmap result) {


    callback.onSuccess(result);
  }




  private String requestContent(int id) throws UnsupportedEncodingException {
    HttpClient httpclient = new DefaultHttpClient();
    String result = null;
    //String url = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=SELECT%20Contacts.CustomFields.c.profile_picture%20FROM%20contacts%20WHERE%20id=%20"+id;
    String url = "https://mivors--tst1.custhelp.com/cgi-bin/mivors.cfg/php/admin/console_util/fa_get.php?p_parms=eUGsfqPNmjRN58TYg0aNiOtmk6k_O~wlBb4LaVHuyprEVkCOEi9BIfjXTztDiQNysS_P80F1GRI_jKoBd7pxWPgSnjvIDY7uQN4Kl36pH29GfXphrAwjmLYHyCWD44ijg8BByy3uqi6gSmqP2cTMSHcDi7KqWg_JUlqoVYCfa8t4serlz9EoaeqJ4h5Ndef0VBqLlIqSpJ9KOl4jFThyJkL1sCl~iRpgSeoNYIF6MWjBgku3sZfOBZ1014g~x8vsMiN7ohJPljoFsaWUfVLKlU1TgY824t55LR";
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
  public Bitmap downloadFile()
  {
    try
    {
      DefaultHttpClient client = new DefaultHttpClient();
   //   client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new UsernamePasswordCredentials("Customization", "Mivors@2016"));

   Log.d("tttnnn",string);
      HttpGet request = new HttpGet(string);
      String base64EncodedCredentials = "Basic " + Base64.encodeToString(
              ("Customization" + ":" + "Mivors@2016").getBytes(),
              Base64.NO_WRAP);
      request.setHeader("Authorization", base64EncodedCredentials);
      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();
      InputStream inputStream = entity.getContent();
      bmImg = BitmapFactory.decodeStream(inputStream);
      Log.d("ttttooo",bmImg.getHeight()+"");
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      Log.d("ttttooo2",e.toString()+"");

    }
    return bmImg;
  }
}
