package com.wearable.mivors.myapplication.connector;

import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import com.wearable.mivors.myapplication.controller.OnLoadingComplete;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mivors on 01/03/17.
 */

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context context;
     OnLoadingComplete onLoadingComplete;
    String fileName;
    String urlls;
    public DownloadTask(Context context, OnLoadingComplete onLoadingComplete,String fileName) {
        this.context = context;
         this.onLoadingComplete = onLoadingComplete;
        this.fileName = fileName;
     }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {

//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://mivors--tst1.custhelp.com/cc/ImageCustomController/getProfileImage/c_id/107"));
//                request.setTitle("Some title");
             URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
             if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            Log.d("nnnooofff",""+fileLength);

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream("/sdcard/"+fileName.replace(".","").trim()+".png");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            Log.d("ooo",""+e.toString() );

            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return fileName.replace(".","").trim();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onLoadingComplete.onSuccess(s);
    }
}

