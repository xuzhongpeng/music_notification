import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
// import 'package:music_notification/music_notification.dart';

void main() {
  const MethodChannel channel = MethodChannel('music_notification');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    // expect(await MusicNotification.platformVersion, '42');
  });
}
