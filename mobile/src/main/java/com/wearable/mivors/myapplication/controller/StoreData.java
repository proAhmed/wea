package com.wearable.mivors.myapplication.controller;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ahmed on 17/01/17.
 */
public class StoreData {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String DATABASE_NAME = "mivors.attendance";


    public StoreData(Context context)
    {
        prefs = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }
    public void setUserName(String userName)
    {
        editor.putString("userName", userName);
        editor.commit();
    }

    public String getUserName()
    {
        return prefs.getString("userName","0");
    }

    public void setUserEmail(String userEmail)
    {
        editor.putString("userEmail", userEmail);
        editor.commit();
    }

    public String getUserEmail()
    {
        return prefs.getString("userEmail","0");
    }

    public void setUserId(int userId)
    {
        editor.putInt("userId", userId);
        editor.commit();
    }

    public int getUserId()
    {
        return prefs.getInt("userId",0);
    }


    public void setSplash(int userId)
    {
        editor.putInt("splash", userId);
        editor.commit();
    }

    public int getSplash()
    {
        return prefs.getInt("splash",0);
    }


    public void setOtherUserId(int userId)
    {
        editor.putInt("other_userId", userId);
        editor.commit();
    }

    public int getOtherUserId()
    {
        return prefs.getInt("other_userId",0);
    }
}
