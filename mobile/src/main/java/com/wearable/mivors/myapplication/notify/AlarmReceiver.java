package com.wearable.mivors.myapplication.notify;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.wearable.mivors.myapplication.MainActivity;
import com.wearable.mivors.myapplication.R;

/**
 * Created by mivors on 25/01/17.
 */

public class AlarmReceiver  extends BroadcastReceiver {
    PendingIntent contentIntent;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

         Intent intent1;
try {
//    PackageManager pm = context.getPackageManager();
//    intent1 = pm.getLaunchIntentForPackage("com.oracle.apps.oracle_tap");
//    //**edit this line to put requestID as requestCode**
    intent1= new Intent(context,MainActivity.class);
    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_SINGLE_TOP);
}catch (Exception e){
     intent1 =  intent = new Intent(Intent.ACTION_VIEW);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setData(Uri.parse("market://details?id=" + "com.oracle.apps.oracle_tap"));
}
         contentIntent = PendingIntent.getActivity(context, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = builder.setContentTitle("Please, Check for any pending attendance approvals")
                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentIntent(contentIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }

}
