package com.example.music_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MyBroadcastReceiver extends BroadcastReceiver {

    MyBroadcastReceiver(MethodChannel channel) {
        this.channel = channel;
    }

    public MethodChannel channel;
    public static final String ACTION_1 = "last";
    public static final String ACTION_2 = "next";
    public static final String ACTION_3 = "play";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String toastStr = "你点击了";
        if (action.equals(ACTION_1)) {
            System.out.println("上一首");
            channel.invokeMethod("last",new HashMap<>());
//            Toast.makeText(context, toastStr + "上一首", Toast.LENGTH_SHORT).show();
        } else if (action.equals(ACTION_2)) {
            channel.invokeMethod("next",new HashMap<>());
            System.out.println("下一曲按钮");
//            Toast.makeText(context, toastStr + "下一曲按钮", Toast.LENGTH_SHORT).show();
        } else if (action.equals(ACTION_3)) {
            Map<String, Object> result = new HashMap<>();
            result.put("isPlay", true);
            channel.invokeMethod("playing",result);
            System.out.println("播放暂停");
//            Toast.makeText(context, toastStr + "播放暂停", Toast.LENGTH_SHORT).show();
        }
    }
}
