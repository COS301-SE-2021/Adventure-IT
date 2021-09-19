import 'package:adventure_it/Providers/chat_model.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:provider/provider.dart';
import 'package:fluttertoast/fluttertoast.dart';

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
            SnackBar snackBar = SnackBar(
                content: Text('Failed to initialise Firebase!',
                    style: TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color,
                        fontWeight: FontWeight.bold)),
                backgroundColor: Theme.of(context).primaryColorDark);
            ScaffoldMessenger.of(context).showSnackBar(snackBar);
          }

          // Once complete, display input widget
          if (snapshot.connectionState != ConnectionState.done) {
            Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          }

          FirebaseMessaging.instance.getToken().then(
              (value) => UserApi.getInstance().setFirebaseID(value!, context));
          FirebaseMessaging.onMessage.listen(_foregroundHandler);
          FirebaseMessaging.onBackgroundMessage(_backgroundHandler);
          return this.nextWidget;
        });
  }
}

class InitializeFireFlutterWeb extends StatefulWidget {
  late final Widget nextWidget;

  InitializeFireFlutterWeb(Widget nextWidget) {
    this.nextWidget = nextWidget;
  }

  @override
  _AppStateWeb createState() => _AppStateWeb(this.nextWidget);
}

class _AppStateWeb extends State<InitializeFireFlutterWeb> {
  late final Widget nextWidget;

  _AppStateWeb(Widget nextWidget) {
    this.nextWidget = nextWidget;
  }

  @override
  Widget build(BuildContext context) {
    FirebaseMessaging.instance
        .getToken()
        .then((value) => UserApi.getInstance().setFirebaseID(value!, context));

    FirebaseMessaging.onMessage.listen((RemoteMessage? message) {
      if (message != null && message.notification != null) {
        final title = message.notification!.title;
        final body = message.notification!.body;
        final data = message.data;
        print("Handling message title: ${title}");
        print("Handling message body: ${body}");
        print("Handling message data: ${data.toString()}");
        Fluttertoast.showToast(
            msg: body!,
            toastLength: Toast.LENGTH_SHORT,
            gravity: ToastGravity.CENTER,
            timeInSecForIosWeb: 1,
            backgroundColor: Colors.red,
            textColor: Colors.white,
            fontSize: 16.0);

        FlutterMessagingChangeNotifier.notifyListeners();
      }
    });

    FirebaseMessaging.onBackgroundMessage(_backgroundHandler);
    return this.nextWidget;
  }
}

Future<void> _backgroundHandler(RemoteMessage message) async {
  print("Handling background message: ${message.toString()}");
}

void _foregroundHandler(RemoteMessage? message) {
  if (message != null && message.notification != null) {
    final title = message.notification!.title;
    final body = message.notification!.body;
    final data = message.data;
    print("Handling foreground message title: ${title}");
    print("Handling foreground message body: ${body}");
    print("Handling foreground message data: ${data.toString()}");
  }
}

class FlutterMessagingChangeNotifier {
  static GroupChatModel? _changeNotifier;

  static void setChangeNotifier(GroupChatModel? x) {
    _changeNotifier = x;
  }

  static void getChangeNotifier(GroupChatModel? x) {
    _changeNotifier = x;
  }

  static void notifyListeners() {
    if (_changeNotifier != null) {
      _changeNotifier!.fetchAllMessages();
    }
  }
}
