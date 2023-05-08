import 'package:flutter/material.dart';
import 'package:sun_mi_flutter_sdk/pages/paymet/paymentEngine.dart';
import 'package:sun_mi_flutter_sdk/plugins/ScanEngine.dart';

class PaymentPage extends StatelessWidget {

  const PaymentPage( { Key ? key } ) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Scan'), ),
      body: const ScanWidget(),
    );
  }

}

class ScanWidget extends StatefulWidget {

  const ScanWidget( { Key ? key } ) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return ScanState();
  }

}

class ScanState extends State<ScanWidget> {

  String _type = "";
  String _value = "";

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.fromLTRB(16, 16, 16, 0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          MaterialButton(
            onPressed: () { _startPayment(); },
            color: Colors.blueAccent,
            child: const Text("Start Payment", style: TextStyle(color: Colors.white, fontSize: 14), ),
          ),
          const SizedBox(height: 8),
          Text("Type: $_type", style: const TextStyle(color: Colors.black, fontSize: 14), ),
          const SizedBox(height: 4),
          Text("Value: $_value", style: const TextStyle(color: Colors.black, fontSize: 14), ),
        ],
      ),
    );
  }

  _startPayment() {
    PaymentEngine.startPayment().then(
            (map) => {
          setState(
                  () => { _update(map) }
          )
        }
    );
  }

  _update(Map map) {
    _type = map['type'];
    _value = map['value'];
  }

}