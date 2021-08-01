import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class InitializeFireFlutter extends StatefulWidget {
  late final Widget nextWidget;
  InitializeFireFlutter(Widget nextWidget) {
    this.nextWidget = nextWidget;
  }

  @override
  _AppState createState() => _AppState(this.nextWidget);
}

class _AppState extends State<InitializeFireFlutter> {
  late final Widget nextWidget;
  _AppState(Widget nextWidget) {
    this.nextWidget = nextWidget;
  }
  final Future<FirebaseApp> _initialization = Firebase.initializeApp();

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _initialization,
      builder: (context, snapshot) {
        if (snapshot.hasError) {
          // Display Error
        }

        // Once complete, display input widget
        if (snapshot.connectionState == ConnectionState.done) {
          FirebaseMessaging.instance
              .getToken()
              .then((value) => print("Local app's token: $value"));
          FirebaseMessaging.onMessage.listen(_foregroundHandler);
          FirebaseMessaging.onBackgroundMessage(_backgroundHandler);
          return this.nextWidget;
        }

        // Otherwise, show something whilst waiting for initialization to complete
        return Scaffold(
          body: new CircularProgressIndicator(),
        );
      },
    );
  }
}

Future<void> _backgroundHandler(RemoteMessage message) async {
  print("Handling background message: ${message}");
}

void _foregroundHandler(RemoteMessage message) {
  print("Handling foreground message: ${message}");
}
