package com.wearable.mivors.myapplication.connector;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wearable.mivors.myapplication.R;
import com.wearable.mivors.myapplication.controller.OnLoadingComplete;
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
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by ahmed on 2/21/2017.
 */
public class GetData  extends AsyncTask<String, Void, ArrayList<Row>> {


    private ProgressDialog dialog;
    private OnLoadingComplete callback;
    private Context context;
    int id;

    public GetData(Context context, OnLoadingComplete cb,int id) {
        dialog = new ProgressDialog(context);
        callback = cb;
        this.context = context;
        this.id = id;
    }
    ArrayList<Row> arrayList;

    @Override
    protected void onPreExecute() {
//        this.dialog.setMessage(context.getResources().getString(
//                R.string.loading));
//        this.dialog.setCancelable(false);
//        this.dialog.show();
    }

    @Override
    protected ArrayList<Row> doInBackground(String... params) {
        String responseJSON = null;
        try {
            responseJSON = requestContent(id);
            Utility utility = new Utility();
             arrayList = utility.getArray(responseJSON);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

         return arrayList;

    }

    @Override
    protected void onPostExecute(ArrayList<Row> result) {
 //        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }
        callback.onSuccess(result);
    }



    private String invokeJSONWS() throws IOException {

      String urll = "";
//        String query = URLEncoder.encode("Select Incident.ID,Incident.PrimaryContact.Contact.ID as 'c_id',Incident.PrimaryContact.Contact.LookupName,Incident.Subject,Incident.AssignedTo.Account.LookupName as 'c_lookup' from Incident", "utf-8");
//        String urll = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=" + query;
         HttpURLConnection httpConn = null;
        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                ("Yasmin" + ":" + "Yasmin@2016").getBytes(),
                Base64.NO_WRAP);
        InputStream in = null;
        int response = -1;
        String responseJSON;
        URL url = new URL(urll);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try {
            httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.addRequestProperty("Authorization", base64EncodedCredentials);

            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);

            httpConn.connect();

            response = httpConn.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }

            responseJSON = out.toString();

        } catch (Exception e) {
            Log.d("uuu3",e.toString());
            throw new IOException("Error connecting");
        }finally {
            httpConn.disconnect();
        }
        return responseJSON;
    }

    public String requestContent(int id) throws UnsupportedEncodingException {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        String url = "https://mivors--tst1.custhelp.com/services/rest/connect/v1.3/queryResults/?query=Select%20Incidents.ID,Incidents.PrimaryContact.Contact.ID%20as%20%27c_id%27,Incidents.PrimaryContact.Contact.LookupName,Incidents.Subject,Incidents.CreatedTime,Incidents.StatusWithType.Status.LookupName%20as%20%27status%27,Incidents.AssignedTo.Account.LookupName%20as%20%27c_lookup%27%20from%20Incidents%20where%20Incidents.OtherContacts.Contact.id%20=%20"+id;

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
}
