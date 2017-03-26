package com.wearable.mivors.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wearable.mivors.myapplication.controller.StoreData;
import com.wearable.mivors.myapplication.controller.Utility;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if(new StoreData(this).getSplash()!=0){
            Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                   startActivity(intent);
        }else {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Utility utility;
                    utility = new Utility(SplashScreen.this);
                    utility.startAtTime();
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    new StoreData(SplashScreen.this).setSplash(5);

                    // close this activity
                    finish();
                }
            }, 2000);
        }
    }
}
