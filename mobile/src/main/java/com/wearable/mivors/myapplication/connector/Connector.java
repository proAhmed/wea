package com.wearable.mivors.myapplication.connector;

import android.net.Uri;
import android.util.Log;

import com.wearable.mivors.myapplication.controller.Constant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mivors on 29/01/17.
 */

public class Connector {
   private static String response = "mm";
    private static String responses = "mm";
    static String  text;
    public static String connect ()  {
        try {
            InputStream is;

        String uri = Uri.parse(Constant.URL)
                .buildUpon()
                .appendQueryParameter("username", "fin_impl")
                .appendQueryParameter("password", "Bazai#2016")
                .build().toString();
        URL url = new URL(uri);

        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setDoOutput(true);
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Authorization","Basic \\(base64LoginString)");
        httpUrlConnection.setRequestProperty("Content-Length", Constant.METHOD_URL);
        httpUrlConnection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        httpUrlConnection.setRequestProperty("SOAPAction", "http://xmlns.oracle.com/oxp/service/PublicReportService/ExternalReportWSSService/getReportSampleData");
        httpUrlConnection.setUseCaches (false);
        httpUrlConnection.connect();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpUrlConnection.getOutputStream()));
        writer.flush();
        writer.close();

        File objFile = new File(Constant.METHOD_URL);
        int reqLen = (int) objFile.length();
        byte[] reqBytes = new byte[reqLen];
        FileInputStream inStream = new FileInputStream(objFile);
        inStream.read(reqBytes);
        inStream.close();
        OutputStream reqStream = httpUrlConnection.getOutputStream();
        reqStream.write(reqBytes);
        reqStream.flush();
        if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            is = httpUrlConnection.getInputStream();// is is inputstream
        } else {
            is = httpUrlConnection.getErrorStream();
        }



        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, "UTF-8"), 8);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        response = sb.toString();
        Log.d("eee22", response);
    } catch (Exception e) {
        Log.d("eeee1", "Error converting result " + e.toString());
    }

        return response;
    }

    static String  s;
//    public static String serv() throws UnsupportedEncodingException {
//
//
//        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
//                ("fin_impl" + ":" + "Bazai#2016").getBytes(),
//                Base64.NO_WRAP);
//        AndroidNetworking.post("https://fierce-cove-29863.herokuapp.com/createAnUser")
//                .setContentType("application/soap+xml; charset=utf-8")
//                .addHeaders("soapaction","http://xmlns.oracle.com/oxp/service/PublicReportService/ExternalReportWSSService/getReportSampleData")
//                .addHeaders("Authorization", base64EncodedCredentials)
//                .setPriority(Priority.MEDIUM).addQueryParameter("")
//                .build().getAsString(new StringRequestListener() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//
//            @Override
//            public void onError(ANError anError) {
//
//            }
//        });
//
//        final DefaultHttpClient httpClient = new DefaultHttpClient();
//         HttpParams params = httpClient.getParams();
//        HttpConnectionParams.setConnectionTimeout(params, 10000);
//        HttpConnectionParams.setSoTimeout(params, 15000);
//
//        HttpPost httppost = new HttpPost(Constant.URL);
//
//      //  httppost.setHeader("soapaction", "http://xmlns.oracle.com/oxp/service/PublicReportService/ExternalReportWSSService/getReportSampleData");
//      //  httppost.setHeader("Content-Type", "application/soap+xml; charset=utf-8");
//           httppost.setHeader("Authorization", base64EncodedCredentials);
//
//
//
//        String responseString = "";
//        try {
//            // the entity holds the request
//            HttpEntity entity = new StringEntity("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\">\n" +
//                    "   <soap:Header/>\n" +
//                    "   <soap:Body>\n" +
//                    "      <pub:getReportSampleData>\n" +
//                    "         <pub:reportAbsolutePath>/BI Reports/HCM Reports/Final Vaildation HCM Report.xdo</pub:reportAbsolutePath>\n" +
//                    "      </pub:getReportSampleData>\n" +
//                    "   </soap:Body>\n" +
//                    "</soap:Envelope>","UTF-8");
//            httppost.setEntity(entity);
//            // Response handler
//            final ResponseHandler<String> rh = new ResponseHandler<String>() {
//                // invoked when client receives response
//                public String handleResponse(HttpResponse response)
//                        throws ClientProtocolException, IOException {
//                    // get response entity
//                    HttpEntity entity = response.getEntity();
//
//                    StringBuffer out = new StringBuffer();
//                    byte[] b = EntityUtils.toByteArray(entity);
//
//                    out.append(new String(b, 0, b.length));
//                    return out.toString();
//                }
//            };
//
//            responseString = httpClient.execute(httppost, rh);
//            try
//            {
//
//
//
//                s = new String(responseString.getBytes(), "UTF-8");
//                String str = "Service\"><ns2:getReportSampleDataReturn>";
//                String  ss = s.substring(responseString.indexOf(str) + 40 , responseString.length()-92);
//                byte[] data = Base64.decode(ss, Base64.DEFAULT);
//                 text = new String(data, "UTF-8");
//                System.out.println(text);
//            }
//            catch (UnsupportedEncodingException e)
//            {
//            }
//             System.out.println(s);
//
//        } catch (Exception e) {
//            Log.v("exception", e.toString());
//            Log.d("exception", e.toString());
//
//        }
//
//        httpClient.getConnectionManager().shutdown();
//
//        return text;
//    }


}
