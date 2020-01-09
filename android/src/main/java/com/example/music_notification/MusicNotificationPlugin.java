package com.example.music_notification;

import android.content.Context;
import android.content.IntentFilter;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** MusicNotificationPlugin */
public class MusicNotificationPlugin implements MethodCallHandler {
  /** Plugin registration. */
  private static MyBroadcastReceiver myBroadcastReceiver = null;
  Context context;
  MusicNotificationPlugin(Context context){
    this.context=context;
  };
  public static void registerWith(Registrar registrar) {
    registrar.context();
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "music_notification");
    channel.setMethodCallHandler(new MusicNotificationPlugin(registrar.context()));

    //注册广播
    myBroadcastReceiver = new MyBroadcastReceiver(channel);
    IntentFilter intentFilter = new IntentFilter(MyBroadcastReceiver.ACTION_1);
    intentFilter.addAction(MyBroadcastReceiver.ACTION_2);
    intentFilter.addAction(MyBroadcastReceiver.ACTION_3);
    registrar.context().registerReceiver(myBroadcastReceiver, intentFilter);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("start")) {
//      result.success("Android " + android.os.Build.VERSION.RELEASE);
      NotificationUtil.showFullScreen(this.context,call);
      result.success(true);
    }else if(call.method.equals("start1")){
      //
    } else {
      result.notImplemented();
    }
  }
}
