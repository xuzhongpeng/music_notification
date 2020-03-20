import 'dart:typed_data';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:music_notification/music_notification.dart';
import 'package:dio/dio.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
    MusicNotification.lastStream.listen((_) {
      print("****上一首");
    });
    MusicNotification.nextStream.listen((_) {
      print("****下一首");
    });
    MusicNotification.stateChangeStream.listen((_) {
      print("****播放" + _.toString());
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = '1';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(children: [
          Center(
            child: Text('Running on: $_platformVersion\n'),
          ),
          Container(
            width: 80,
            color: Colors.blue,
            height: 40,
            child: GestureDetector(
                child: Text("点击"),
                onTap: () async {
                  print('yeyeye**************');
                  Response res = await Dio().get(
                      "http://p1.music.126.net/t27-G71EOXTl1HTbveeC9A==/109951164554706909.jpg?param=300y300",
                      options: Options(responseType: ResponseType.stream));
                  Uint8List list =
                      await consolidateHttpClientResponseBytes(res.data.stream);
                  MusicNotification.start(NotifiParams(
                      imgUrl: list,
                      singer: "周杰伦",
                      musicName: "听妈妈的话",
                      isPlaying: true));
                }),
          ),
          Container(
            width: 80,
            color: Colors.blue,
            height: 40,
            child: GestureDetector(
                child: Text("点击"),
                onTap: () async {
                  print('yeyeye**************');
                  Response res = await Dio().get(
                      "http://y.gtimg.cn/music/photo_new/T002R150x150M000001hR4Tj4WnsKV.jpg?n=1?param=200y200",
                      options: Options(responseType: ResponseType.stream));
                  Uint8List list =
                      await consolidateHttpClientResponseBytes(res.data.stream);
                  MusicNotification.start(NotifiParams(
                      imgUrl: list,
                      singer: "林俊杰",
                      musicName: "江南",
                      isPlaying: false));
                }),
          )
        ]),
      ),
    );
  }
}
