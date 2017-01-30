package com.wearable.abobakr.myapplication.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.widget.Toast;

import com.wearable.abobakr.myapplication.MainActivity;
import com.wearable.abobakr.myapplication.R;
import com.wearable.abobakr.myapplication.model.UserData;
import com.wearable.abobakr.myapplication.notify.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by abobakr on 25/01/17.
 */

public class Utility {
    private PendingIntent pendingIntent;
    private Activity activity;


   public Utility(Activity activity, UserData userData){
       this.activity = activity;
       Intent alarmIntent = new Intent(activity, AlarmReceiver.class);
       alarmIntent.putExtra("name",userData.getUserName());
       alarmIntent.putExtra("violation",userData.getViolation());
       alarmIntent.putExtra("late",userData.getLate());
       pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0);
    }
    public void start() {
        AlarmManager manager = (AlarmManager)activity. getSystemService(Context.ALARM_SERVICE);
        int interval = 8000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(activity, "Alarms Set", Toast.LENGTH_SHORT).show();
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager)activity. getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(activity, "Alarms Canceled", Toast.LENGTH_SHORT).show();
    }

    public void startAt10() {
        AlarmManager manager = (AlarmManager)activity. getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

//        Intent notificationIntent = new Intent(activity, AlarmReceiver.class);
//        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, getNotification(string));
        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 3);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                10 * 60 * 20, pendingIntent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(activity);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }
}
