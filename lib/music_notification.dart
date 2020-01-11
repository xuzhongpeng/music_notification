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
      print('MusicNotification Unexpected error : $ex');
    }
  }

  static _doHandlePlatformCall(call) {
    final Map<dynamic, dynamic> callArgs = call.arguments as Map;
    print('_platformCallHandler call ${call.method} $callArgs');
    switch (call.method) {
      case "playing":
        {
          if (callArgs['isPlay'] == true) {
            _stateChange.add(PlayerState.Playing);
          } else {
            _stateChange.add(PlayerState.Stopping);
          }
          break;
        }
      case "next":
        {
          _nextController.add(null);
          break;
        }
      case "last":
        {
          _lastController.add(null);
          break;
        }
    }
  }

  static final StreamController<PlayerState> _stateChange =
      StreamController<PlayerState>.broadcast();
  static final StreamController<void> _nextController =
      StreamController<void>.broadcast();
  static final StreamController<void> _lastController =
      StreamController<void>.broadcast();

  static Stream<PlayerState> get stateChangeStream => _stateChange.stream;
  static Stream<void> get nextStream => _nextController.stream;
  static Stream<void> get lastStream => _lastController.stream;

  Future<void> dispose() async {
    List<Future> futures = [];
    if (!_stateChange.isClosed) futures.add(_stateChange.close());
    if (!_nextController.isClosed) futures.add(_nextController.close());
    if (!_lastController.isClosed) futures.add(_lastController.close());
    await Future.wait(futures);
  }
}

enum PlayerState { Playing, Stopping }

class NotifiParams {
  Uint8List imgUrl;
  String musicName;
  String singer;
  bool isPlaying;
  NotifiParams({this.imgUrl, this.musicName, this.singer, this.isPlaying});
  Map<String, dynamic> toJson() {
    return {
      "imgUrl": this.imgUrl,
      "musicName": this.musicName,
      "singer": this.singer,
      "isPlaying": this.isPlaying
    };
  }
}
