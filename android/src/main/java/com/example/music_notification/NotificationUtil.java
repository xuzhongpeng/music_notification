package com.example.music_notification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * 显示通知栏工具类
 * Created by Administrator on 2020/01/09.
 */

public class NotificationUtil {
    public static void showFullScreen(Context context) {
        Intent button1I = new Intent("PressPauseOrPlayButton");
        PendingIntent button1PI = PendingIntent.getBroadcast(context, 0, button1I, 0);
        Intent button2I = new Intent("PressNextButton");
        PendingIntent button2PI = PendingIntent.getBroadcast(context, 0, button2I, 0);
        RemoteViews remoteViews = new RemoteViews(context.getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setOnClickPendingIntent(R.id.play, button1PI);
        remoteViews.setOnClickPendingIntent(R.id.next, button2PI);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

//Create channel in which to send push notifications
        String CHANNEL_ID = "my_channel_01";

// only use NotificationChannel when Api Level >= 26
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

//Send push notification
        Notification notify = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setContentTitle("Listener available on pSquared!")
                .setContentText("You can now talk about your day in a pSquared chatbox with a Listener")
                .setSmallIcon(R.raw.play)
                .setCustomBigContentView(remoteViews)
                .setContent(remoteViews)
                .build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notify);
    }
}

