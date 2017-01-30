package com.wearable.abobakr.myapplication.notify;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.wearable.abobakr.myapplication.MainActivity;
import com.wearable.abobakr.myapplication.R;

/**
 * Created by abobakr on 25/01/17.
 */

public class AlarmReceiver  extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

       // Notification.Builder builder = new Notification.Builder(context);
//        builder.setContentTitle("Scheduled Notification");
//        builder.setContentText(intent.getExtras().getString("name"));
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.build();

        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.putExtra("data","data");
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

//**edit this line to put requestID as requestCode**
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle(intent.getExtras().getString("name"))
                .setContentText(intent.getExtras().getString("late"))
                .setTicker(intent.getExtras().getString("violation"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
