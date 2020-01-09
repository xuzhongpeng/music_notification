import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';

class MusicNotification {
  static final MethodChannel _channel =
      const MethodChannel('music_notification')
        ..setMethodCallHandler(platformCallHandler);

  static Future<bool> start(NotifiParams params) async {
    final bool version = await _channel.invokeMethod('start', params.toJson());
    return version;
  }

  static Future<void> platformCallHandler(MethodCall call) async {
    try {
      _doHandlePlatformCall(call);
    } catch (ex) {
      print('Unexpected error: $ex');
    }
  }

  static _doHandlePlatformCall(call) {
    final Map<dynamic, dynamic> callArgs = call.arguments as Map;
    print('_platformCallHandler call ${call.method} $callArgs');
    switch (call.method) {
    }
  }

  final StreamController<Duration> stateChange =
      StreamController<Duration>.broadcast();
}

class NotifiParams {
  Uint8List imgUrl;
  String musicName;
  String singer;
  NotifiParams({this.imgUrl, this.musicName, this.singer});
  Map<String, dynamic> toJson() {
    return {
      "imgUrl": this.imgUrl,
      "musicName": this.musicName,
      "singer": this.singer
    };
  }
}
