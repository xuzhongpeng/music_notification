package com.example.music_notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.FlutterException;
import io.flutter.plugin.common.MethodCall;

/**
 * 显示通知栏工具类 Created by jsshou on 2020/01/09.
 */

public class NotificationUtil {
    public static void showFullScreen(Context context, MethodCall params) {
        byte[] imgUrl = params.argument("imgUrl");
        String musicName = params.argument("musicName").toString();
        String singer = params.argument("singer").toString();
        Bool isPlaying = params.argument("isPlaying");
        // big
        Intent button1I = new Intent(MyBroadcastReceiver.ACTION_1);
        PendingIntent button1PI = PendingIntent.getBroadcast(context, 0, button1I, 0);
        Intent button2I = new Intent(MyBroadcastReceiver.ACTION_2);
        PendingIntent button2PI = PendingIntent.getBroadcast(context, 0, button2I, 0);
        Intent button3I = new Intent(MyBroadcastReceiver.ACTION_3);
        PendingIntent button3PI = PendingIntent.getBroadcast(context, 0, button3I, 0);
        RemoteViews remoteViews = new RemoteViews(context.getApplicationContext().getPackageName(),
                R.layout.notification);
        remoteViews.setOnClickPendingIntent(R.id.last, button1PI);
        remoteViews.setOnClickPendingIntent(R.id.next, button2PI);
        remoteViews.setOnClickPendingIntent(R.id.play, button3PI);

        Bitmap bitmap = BitmapFactory.decodeByteArray(imgUrl, 0, imgUrl.length);
        remoteViews.setImageViewBitmap(R.id.notificationImageView, bitmap);
        remoteViews.setTextViewText(R.id.musicName, musicName);
        remoteViews.setTextViewText(R.id.singer, singer);

        // small
        RemoteViews sremoteViews = new RemoteViews(context.getApplicationContext().getPackageName(),
                R.layout.notificationsmal);
        Intent sbutton2I = new Intent(MyBroadcastReceiver.ACTION_2);
        PendingIntent sbutton2PI = PendingIntent.getBroadcast(context, 0, sbutton2I, 0);
        sremoteViews.setOnClickPendingIntent(R.id.next1, sbutton2PI);

        sremoteViews.setImageViewBitmap(R.id.notificationImageView, bitmap);
        sremoteViews.setTextViewText(R.id.musicName, musicName);
        sremoteViews.setTextViewText(R.id.singer, singer);

        Intent sbutton3I = new Intent(MyBroadcastReceiver.ACTION_3);
        PendingIntent sbutton3PI = PendingIntent.getBroadcast(context, 0, sbutton3I, 0);
        sremoteViews.setOnClickPendingIntent(R.id.play1, sbutton3PI);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Create channel in which to send push notifications
        String CHANNEL_ID = "musicNotification";
        // only use NotificationChannel when Api Level >= 26
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            CharSequence name = "musicNotification";
            String Description = "musicNotification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[] { 100, 200, 300, 400, 500, 400, 300, 200, 400 });
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        // Send push notification
        Notification notify = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setContentTitle("musicNotification").setContentText("musicNotification").setSmallIcon(R.raw.play)
                .setCustomBigContentView(remoteViews).setContent(sremoteViews).setAutoCancel(false).setOngoing(true)
                .build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notify);
    }

    /**
     * 从服务器取图片
     *
     * @param url
     * @return
     */

    static DisplayImageOptions options = new DisplayImageOptions.Builder()
            // .cacheInMemory(true) //设置下载的图片是否缓存在内存中
            // .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
            // .showImageOnLoading(R.drawable.zhanwei_avater)
            // .showImageOnFail(R.drawable.zhanwei_avater)
            .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(false)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
            .resetViewBeforeLoading(false)// 设置图片在下载前是否重置，复位
            .build();

}
