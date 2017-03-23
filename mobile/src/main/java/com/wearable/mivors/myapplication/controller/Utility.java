package com.wearable.mivors.myapplication.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.wearable.mivors.myapplication.MainActivity;
import com.wearable.mivors.myapplication.R;
import com.wearable.mivors.myapplication.model.Row;
import com.wearable.mivors.myapplication.model.UserData;
import com.wearable.mivors.myapplication.notify.AlarmReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by mivors on 25/01/17.
 */

public class Utility {
    private PendingIntent pendingIntent;
    private Context activity;

    public Utility(){
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

   public Utility(Context activity){
       this.activity = activity;
       Intent alarmIntent = new Intent(activity, AlarmReceiver.class);

       pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0);
    }

    public void startAtTime() {
        AlarmManager manager = (AlarmManager)activity. getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 60*24;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 58);
        calendar.set(Calendar.SECOND,21);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, pendingIntent);
    }
    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }
    private   String getElementValue(Node elem) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public ArrayList<Row> getArray(String json){
        ArrayList<Row> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject jsonObjects = jsonArray.getJSONObject(0);

            JSONArray jsonArrays = jsonObjects.getJSONArray("rows");

            Log.d("oooppp55",jsonArrays.length()+"");
            for(int i=0;i<jsonArrays.length();i++){

               JSONArray jj = jsonArrays.getJSONArray(i);
                 int id = jj.getInt(0);
                int c_id = jj.getInt(1);
                String lookup = jj.getString(2);
                String subject = jj.getString(3);
                String date = jj.getString(4);
                String status = jj.getString(5);
                Row row = new Row(id,c_id,lookup,subject,date,status);
                arrayList.add(row);
                Log.d("oooppp66",jj.toString()+"");
                Log.d("oooppp55566",status+"   "  +c_id +"   "+id);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("ooopppee",e.toString()+"");
        }

        return arrayList;
    }

    public ArrayList<Integer> getArrays(String json){
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject jsonObjects = jsonArray.getJSONObject(0);

            JSONArray jsonArrays = jsonObjects.getJSONArray("rows");

             for(int i=0;i<jsonArrays.length();i++){

                JSONArray jj = jsonArrays.getJSONArray(i);

                int id = jj.getInt(0);
                new StoreData(activity).setOtherUserId(id);

//                int c_id = jj.getInt(1);
//                String lookup = jj.getString(2);
//                String subject = jj.getString(3);
//                String status = jj.getString(4);
//                Row row = new Row(id,c_id,lookup,subject,status);
                arrayList.add(id);
                Log.d("oooppp66",jj.toString()+"");

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("ooopppee",e.toString()+"");
        }

        return arrayList;
    }

    public String getImage(String json){
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            JSONObject jsonObjects = jsonArray.getJSONObject(0);

            JSONArray jsonArrays = jsonObjects.getJSONArray("rows");

            Log.d("oooppp55",jsonArrays.length()+"");
            for(int i=0;i<jsonArrays.length();i++){

                JSONArray jj = jsonArrays.getJSONArray(i);
                String image = jj.getString(0);
            //    String  myUrl = image.replace("\\","");
                String  myUrl = image.replace("\\/", "/");
                // String str = image.replaceAll("\\\\", "");
//                int c_id = jj.getInt(1);
//                String lookup = jj.getString(2);
//                String subject = jj.getString(3);
//                String status = jj.getString(4);
//                Row row = new Row(id,c_id,lookup,subject,status);
                arrayList.add(myUrl);
                Log.d("uuuuu",myUrl+"");

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("ooopppee",e.toString()+"");
        }

        return arrayList.get(0);
    }
    public ArrayList<Row> formatDate(ArrayList<Row> data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time=sdf.format(new Date());
        ArrayList<Row> rr = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            for(int i =0;i<data.size();i++){
                Log.d("pppoooss",""+data.get(i).getCreated());

                newDate = format.parse(data.get(i).getCreated());
                String dates = format.format(newDate);
                Log.d("pppoooss",""+dates+" .. "+time);
                Log.d("pppoooss22",""+data.get(i).getStatus().equalsIgnoreCase("Waiting"));
//                dates.equals(time)&&
                if(data.get(i).getStatus().equalsIgnoreCase("Waiting")){
                    rr.add(data.get(i));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return rr;
    }
    public boolean formatDate(String data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String time=sdf.format(new Date());


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd-MM-yyyy");
        String dates = format.format(newDate);
        return time.equals(dates);
    }
    public static String forma(String data){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(newDate);
    }
}
