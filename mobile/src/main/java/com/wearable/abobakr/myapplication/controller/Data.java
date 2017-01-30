package com.wearable.abobakr.myapplication.controller;

import com.wearable.abobakr.myapplication.model.UserData;

import java.util.ArrayList;

/**
 * Created by abobakr on 30/01/17.
 */

public class Data {

    public  ArrayList<UserData> addData(){
        ArrayList<UserData> userDatas = new ArrayList<>();
        userDatas.add(new UserData("Mohamed","sale","late","40 min"));
        userDatas.add(new UserData("Ahmed","manufacturing","late","60 min"));
        userDatas.add(new UserData("Karim","HR","late","80 min"));
        userDatas.add(new UserData("Tarek","sale","late","20 min"));
        return userDatas;
    }
}
