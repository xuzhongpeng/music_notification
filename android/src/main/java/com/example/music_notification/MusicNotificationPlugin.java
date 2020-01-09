package com.example.music_notification;

import android.content.Context;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** MusicNotificationPlugin */
public class MusicNotificationPlugin implements MethodCallHandler {
  /** Plugin registration. */
  Context context;
  MusicNotificationPlugin(Context context){
    this.context=context;
  };
  public static void registerWith(Registrar registrar) {
    registrar.context();
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "music_notification");
    channel.setMethodCallHandler(new MusicNotificationPlugin(registrar.context()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
//      result.success("Android " + android.os.Build.VERSION.RELEASE);
      NotificationUtil.showFullScreen(this.context);
    } else {
      result.notImplemented();
    }
  }
}
