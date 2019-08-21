package com.vectorcoder.androidwoocommerce.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;


/**
 * NotificationHelper is used to create new Notifications
 **/

public class NotificationHelper {
    
    
    public static final int NOTIFICATION_REQUEST_CODE = 100;
    
    
    //*********** Used to create Notifications ********//
    
    public static void showNewNotification(Context context, Intent intent, String title, String msg) {
        
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        Intent notificationIntent;
        
        if (intent != null) {
            notificationIntent = intent;
        }
        else {
            notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        
        PendingIntent pendingIntent = PendingIntent.getActivity((context), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_ONE_SHOT);
        
        
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        
        
        Notification.Builder builder = new Notification.Builder(context);
    
    
        Log.i("VC_Shop", "showNewNotification_Title="+title);
        Log.i("VC_Shop", "showNewNotification_Body="+msg);
        
        // Create Notification
        Notification notification = builder
                .setContentTitle(title)
                .setContentText(msg)
                .setTicker(context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_logo)
                .setSound(notificationSound)
                .setLights(Color.RED, 3000, 3000)
                .setVibrate(new long[] { 1000, 1000 })
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        
        
        notificationManager.notify(NOTIFICATION_REQUEST_CODE, notification);
        
    }
    
    
}

