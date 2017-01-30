package com.wearable.abobakr.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.wearable.abobakr.myapplication.adapter.UserAdapter;
import com.wearable.abobakr.myapplication.connector.Connector;
import com.wearable.abobakr.myapplication.controller.Data;
import com.wearable.abobakr.myapplication.controller.OnUserClick;
import com.wearable.abobakr.myapplication.controller.Utility;
import com.wearable.abobakr.myapplication.model.UserData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnUserClick{

    RecyclerView recycle;
    ArrayList<UserData> arrayList;
    OnUserClick onUserClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycle.setLayoutManager(layoutManager);
        UserData userData = new UserData("ahmed", "late", "25 min");
        Utility utility = new Utility(this, userData);
        utility.startAt10();
        arrayList = new ArrayList<>();
        onUserClick = this;
        new AAA().execute();
        if(getIntent().getExtras()!=null){

        }
  }

    @Override
    public void reject(int pos) {
        //arrayList.remove(pos);
        Toast.makeText(MainActivity.this,"Reject",Toast.LENGTH_LONG).show();

    }

    @Override
    public void accept(int pos) {
        //arrayList.remove(pos);
        Toast.makeText(MainActivity.this,"Accept",Toast.LENGTH_LONG).show();

    }

    private class AAA extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... strings) {
    //  return   Connector.getServiceResponse(Constant.NAME_SPACE,Constant.METHOD_NAME, Constant.METHOD_ACTION, Constant.URL,null);
        Connector.serv();
        return  Connector.serv();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        Log.d("ooo",s);
//        System.out.println(s);
        Data data = new Data();
        arrayList = data.addData();
        UserAdapter userAdapter = new UserAdapter(MainActivity.this,arrayList,onUserClick);

         recycle.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
        try{
            recycle.notify();
        }catch (Exception e){

        }

    }
}
}
