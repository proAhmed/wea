package com.wearable.mivors.myapplication.controller;

import com.wearable.mivors.myapplication.R;
import com.wearable.mivors.myapplication.model.UserData;

import java.util.ArrayList;

/**
 * Created by mivors on 30/01/17.
 */

public class Data {

    public  ArrayList<UserData> addData(){
        ArrayList<UserData> userDatas = new ArrayList<>();
        userDatas.add(new UserData("Angelo Mancini","Internal Support","absent", R.drawable.dan_1));
        userDatas.add(new UserData("Brian Hines","Development Projects","late  60 min", R.drawable.angelo_mancini));
        userDatas.add(new UserData("Bill Lattner","Accounting","absent", R.drawable.brian_gill));
        userDatas.add(new UserData("Nick Ahamed","Top Management","late  80 min", R.drawable.marshal_miller));
        userDatas.add(new UserData("Marshall Miller","Development Projects","absent", R.drawable.nick_ahamed));
        userDatas.add(new UserData("Bernard Bergmann","Accounting","absent", R.drawable.bernard_bergmann));
        userDatas.add(new UserData("Brian Gill","Internal Support","late 20 min", R.drawable.andrew_caponi));

        return userDatas;
    }
}
