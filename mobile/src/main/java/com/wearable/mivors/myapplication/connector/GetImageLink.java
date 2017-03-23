package com.wearable.mivors.myapplication.connector;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.controller.Utility;
import com.wearable.mivors.myapplication.model.Row;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ahmed on 2/21/2017.
 */
public class GetImageLink extends AsyncTask<String, Void,String> {


    private ProgressDialog dialog;
    private OnLoadingComplete callback;
    private Context context;

    public GetImageLink(Context context, OnLoadingComplete cb) {
        dialog = new ProgressDialog(context);
        callback = cb;
        this.context = context;
     }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        String responseJSON = null;
        try {
              responseJSON = requestContent(params[0]);
            Log.d("ppplll",responseJSON);
           } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("pppeee",e.toString());
        }

         return responseJSON;

    }

    @Override
    protected void onPostExecute(String result) {
       try{
           callback.onSuccess(result);

       }catch (Exception e){
Log.d("pppp",e.toString());
       }
    }


    private String requestContent(String url) throws UnsupportedEncodingException {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

//        String query = URLEncoder.encode("Select Incident.ID,Incident.PrimaryContact.Contact.ID as 'c_id',Incident.PrimaryContact.Contact.LookupName,Incident.Subject,Incident.AssignedTo.Account.LookupName as 'c_lookup' from Incident", "utf-8");
//        String url = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=" + query;
        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                ("Customization" + ":" + "Mivors@2016").getBytes(),
                Base64.NO_WRAP);
        HttpGet httpget = new HttpGet(url);
      //  httpget.setHeader("Authorization", base64EncodedCredentials);
        httpget.setHeader(HTTP.CONTENT_TYPE,"text/html; charset=UTF-8");
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


}
