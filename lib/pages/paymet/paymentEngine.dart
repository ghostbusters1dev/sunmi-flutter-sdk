// ignore_for_file: avoid_print
import 'package:flutter/services.dart';

class PaymentEngine {

  static const MethodChannel _channel = MethodChannel("payment-engine");

  static Future<Map> startPayment() async {
    try {
      return await _channel.invokeMethod("startScan");
    } on PlatformException catch (e) {
      print(e);
      return {};
    }
  }

}