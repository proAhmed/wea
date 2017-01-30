package com.wearable.abobakr.myapplication.connector;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.wearable.abobakr.myapplication.controller.Constant;
import com.wearable.abobakr.myapplication.controller.HttpRequest;
import com.wearable.abobakr.myapplication.controller.Utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static java.lang.System.setErr;

/**
 * Created by abobakr on 29/01/17.
 */

public class Connector {
   private static String response = "mm";
    private static String responses = "mm";

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
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        response = sb.toString();
        Log.d("eee22", response);
    } catch (Exception e) {
        Log.d("eeee1", "Error converting result " + e.toString());
    }
//        byte[] byteBuf = new byte[1024];
//        FileOutputStream outStream = new FileOutputStream(outFile);
//        InputStream resStream = httpUrlConnection.getInputStream();
//        int resLen = 0;
//        int len = resStream.read(byteBuf);
//        while (len > -1) {
//            resLen += len;
//            outStream.write(byteBuf,0,len);
//            len = resStream.read(byteBuf);
//        }
//        outStream.close();
//
//        reqStream.close();
//        resStream.close();

// Output counts

        return response;
    }

    public static String getServiceResponse(String nameSpace, String methodName,
                                     String soapAction, String Url, List<PropertyInfo> mPropertyInfo) {

        String mResponse = "";
        SoapObject request = new SoapObject(nameSpace, methodName);

        request.addProperty("Content-Length", Constant.PASSWORD);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        if (mPropertyInfo != null) {
            for (PropertyInfo propertyInfo : mPropertyInfo) {
                request.addProperty(propertyInfo);
            }
        }
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Url);
        try
        {
            List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
            headerList.add(new HeaderProperty("username",Constant.USERNAME));
            headerList.add(new HeaderProperty("password",Constant.PASSWORD));
            androidHttpTransport.call(soapAction, envelope, headerList);
            SoapObject response = (SoapObject)envelope.getResponse();
           responses= response.getName();
        }
        catch(Exception e)
        {
            Log.d("ooomm",e.toString());
            e.printStackTrace();
        }


//        HttpTransportSE ht = new HttpTransportSE(Url);
//        ht.debug = true;
//        try {
//            ht.call(soapAction, envelope);
//        } catch (Exception e) {
//            Log.d("ooo",e.toString());
//            e.printStackTrace();
//        }
//        try {
//            mResponse = envelope.getResponse().toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return responses;
    }
    static String  s;
    public static String serv(){

        String responses = "kk";

       // System.out.println("json Response was: " + responses);
//        String base64EncodedCredential = null;
//        try {
//            base64EncodedCredential = Base64.encodeToString(
//                    ("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\">\n" +
//                            "   <soap:Header/>\n" +
//                            "   <soap:Body>\n" +
//                            "      <pub:getReportSampleData>\n" +
//                            "         <pub:reportAbsolutePath>/BI Reports/HCM Reports/Final Vaildation HCM Report.xdo</pub:reportAbsolutePath>\n" +
//                            "      </pub:getReportSampleData>\n" +
//                            "   </soap:Body>\n" +
//                            "</soap:Envelope>").getBytes("UTF-8"),
//             Base64.NO_WRAP);
//             responses = HttpRequest.post(Constant.URL).basic("fin_impl", "Bazai#2016")
//                    .body(base64EncodedCredential);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            Log.d("eeee",e.toString());
//        }
       
//
        final DefaultHttpClient httpClient = new DefaultHttpClient();
// request parameters
        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);
        HttpConnectionParams.setSoTimeout(params, 15000);
// set paramete
// POST the envelope
        HttpPost httppost = new HttpPost(Constant.URL);


            String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                    ("fin_impl" + ":" + "Bazai#2016").getBytes(),
             Base64.NO_WRAP);
        httppost.setHeader("soapaction", "http://xmlns.oracle.com/oxp/service/PublicReportService/ExternalReportWSSService/getReportSampleData");
        httppost.setHeader("Content-Type", "application/soap+xml; charset=utf-8");
           httppost.setHeader("Authorization", base64EncodedCredentials);



        String responseString = "";
        try {
            // the entity holds the request
            HttpEntity entity = new StringEntity("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:pub=\"http://xmlns.oracle.com/oxp/service/PublicReportService\">\n" +
                    "   <soap:Header/>\n" +
                    "   <soap:Body>\n" +
                    "      <pub:getReportSampleData>\n" +
                    "         <pub:reportAbsolutePath>/BI Reports/HCM Reports/Final Vaildation HCM Report.xdo</pub:reportAbsolutePath>\n" +
                    "      </pub:getReportSampleData>\n" +
                    "   </soap:Body>\n" +
                    "</soap:Envelope>","UTF-8");
            httppost.setEntity(entity);
            // Response handler
            final ResponseHandler<String> rh = new ResponseHandler<String>() {
                // invoked when client receives response
                public String handleResponse(HttpResponse response)
                        throws ClientProtocolException, IOException {
                    // get response entity
                    HttpEntity entity = response.getEntity();
                    // read the response as byte array
                    StringBuffer out = new StringBuffer();
                    byte[] b = EntityUtils.toByteArray(entity);
                    // write the response byte array to a string buffer
                    out.append(new String(b, 0, b.length));
                    return out.toString();
                }
            };

            responseString = httpClient.execute(httppost, rh);
            try
            {

               String str = "Service\"><ns2:getReportSampleDataReturn>";
               String ss = responseString.substring(responseString.indexOf(str) + 40 , responseString.length()-97);
                Log.d("iiuuuooo",ss);


                s = new String(responseString.getBytes(), "UTF-8");

            }
            catch (UnsupportedEncodingException e)
            {
                Log.e("utf8", e.toString());
            }
            Log.d("Reponse", s);
            //  ParseCreatRespone(responseString);
            System.out.println(s);

        } catch (Exception e) {
            Log.v("exception", e.toString());
            Log.d("exception", e.toString());

        }
//// close the connection
//        httpClient.getConnectionManager().shutdown();
        return s;
    }


}
