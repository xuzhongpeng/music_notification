import 'dart:async';

import 'package:flutter/services.dart';

class MusicNotification {
  static const MethodChannel _channel =
      const MethodChannel('music_notification');

  static Future<String> platformVersion() async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
