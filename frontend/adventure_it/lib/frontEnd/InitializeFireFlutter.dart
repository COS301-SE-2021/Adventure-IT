import 'dart:ui';

import 'package:adventure_it/Providers/chat_model.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
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

  @override
  Widget build(BuildContext context) {
    print("Attempting to get firebase token");
    FirebaseMessaging.instance.getToken(vapidKey:
    'BIVkd4clRIJSem9cocqtxlSy9fDHmw3KbNcT2-BBgITMtJ8ygQZYnFFGXGf06ZMfM6BxBe5KHAx_Up6N7bSCY38').then((value) {
      print("Inside getToken()");
      print("Got token: " + value!);
      UserApi.getInstance().setFirebaseID(value!, context);
    });
    print("Finished attempt to get firebase token");
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
          webBgColor: "linear-gradient(to right, #6A7AC7, #484D64)",
          webPosition: "center",
          toastLength: Toast.LENGTH_LONG,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 2,
          backgroundColor: Theme.of(context).accentColor,
          textColor: Theme.of(context).textTheme.bodyText1!.color,
          fontSize: 15.0,
        );

        FlutterMessagingChangeNotifier.notifyListeners();
      }
    });

    FirebaseMessaging.onBackgroundMessage(backgroundHandler);
    return this.nextWidget;
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
    print("Attempting to get firebase token");
      FirebaseMessaging.instance
          .getToken(
              vapidKey:
                  'BIVkd4clRIJSem9cocqtxlSy9fDHmw3KbNcT2-BBgITMtJ8ygQZYnFFGXGf06ZMfM6BxBe5KHAx_Up6N7bSCY38')
          .then((value) {
        print("Inside getToken()");
        print("Got token: " + value!);
        UserApi.getInstance().setFirebaseID(value!, context);
      });
      print("Finished attempt to get firebase token");

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
          webBgColor: "linear-gradient(to right, #6A7AC7, #484D64)",
          webPosition: "center",
          toastLength: Toast.LENGTH_LONG,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          backgroundColor: Theme.of(context).accentColor,
          textColor: Theme.of(context).textTheme.bodyText1!.color,
          fontSize: 15.0,
        );

        FlutterMessagingChangeNotifier.notifyListeners();
      }
    });

    FirebaseMessaging.onBackgroundMessage(backgroundHandler);
    return this.nextWidget;
  }
}

Future<void> backgroundHandler(RemoteMessage message) async {
  print("Handling background message: ${message.toString()}");
}

void foregroundHandler(RemoteMessage? message) {
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
  static GroupChatModel? groupChatChangeNotifier;
  static DirectChatModel? directChatChangeNotifier;

  static void setGroupChatChangeNotifier(GroupChatModel? x) {
    groupChatChangeNotifier = x;
  }

  static void setDirectChatChangeNotifier(DirectChatModel? x) {
    directChatChangeNotifier = x;
  }

  static void getChangeNotifier(GroupChatModel? x) {
    groupChatChangeNotifier = x;
  }

  static void notifyListeners() {
    if (groupChatChangeNotifier != null) {
      groupChatChangeNotifier!.fetchAllMessages();
    }
    if (directChatChangeNotifier != null) {
      directChatChangeNotifier!.fetchAllMessages();
    }
  }
}
