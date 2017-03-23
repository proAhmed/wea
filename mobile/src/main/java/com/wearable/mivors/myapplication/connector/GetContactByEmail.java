package com.wearable.mivors.myapplication.connector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.wearable.mivors.myapplication.R;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
import com.wearable.mivors.myapplication.controller.StoreData;
import com.wearable.mivors.myapplication.controller.Utility;
import com.wearable.mivors.myapplication.model.Row;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import java.util.ArrayList;

/**
 * Created by ahmed on 2/21/2017.
 */
public class GetContactByEmail extends AsyncTask<String, Void, ArrayList<Integer>> {


    private ProgressDialog dialog;
    private OnLoadingComplete callback;
    private Context context;

    public GetContactByEmail(Context context, OnLoadingComplete cb) {
        dialog = new ProgressDialog(context);
        callback = cb;
        this.context = context;
        //URL = URL+id;
    }
    ArrayList<Integer> arrayList;

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ArrayList<Integer> doInBackground(String... params) {
        String responseJSON = null;
        try {
            responseJSON = requestContent(params[0]);
            Log.d("ppp",responseJSON);
            Utility utility = new Utility(context);
            arrayList = utility.getArrays(responseJSON);
            Log.d("pppsss",arrayList.size()+"");
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

         return arrayList;

    }

    @Override
    protected void onPostExecute(ArrayList<Integer> result) {

        callback.onSuccess(result);
    }


    public String requestContent(String email) throws UnsupportedEncodingException {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
     //   String url = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=Select%20Incidents.ID,Incidents.PrimaryContact.Contact.ID%20as%20%27c_id%27,Incidents.PrimaryContact.Contact.LookupName,Incidents.Subject,Incidents.AssignedTo.Account.LookupName%20as%20%27c_lookup%27%20from%20Incidents";
        String emails ;

        if(email.equals("")){
            emails = new StoreData(context).getUserEmail()+"%27";
  }else {
            emails = email+"%27";
  }
        String url = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=SELECT%20contacts.id%20FROM%20contacts%20WHERE%20emails.address=%27"+emails;
        Log.d("nnnn",url);

//        //String query = URLEncoder.encode("Select Incident.ID,Incident.PrimaryContact.Contact.ID as 'c_id',Incident.PrimaryContact.Contact.LookupName,Incident.Subject,Incident.AssignedTo.Account.LookupName as 'c_lookup' from Incident", "utf-8");
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
}
