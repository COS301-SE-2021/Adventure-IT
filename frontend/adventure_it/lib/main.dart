// @dart=2.9
import 'package:flutter/foundation.dart';
import 'package:flutter_downloader/flutter_downloader.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';

import 'Providers/location_model.dart';
import 'api/mediaAPI.dart';
import 'frontEnd/Login.dart';

void main() async {
  if (!kIsWeb) {
    await FlutterDownloader.initialize();
    FlutterDownloader.registerCallback(MediaApi.downloadCallback);
  }
  runApp(
    MyApp(true),
  );
}
//
class MyApp extends StatelessWidget {
  bool theme = false;

  MyApp(bool value) {
    theme = value;
  }
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => LocationModel(context),
        child: MaterialApp(
            theme: theme?ThemeData(
                iconTheme: IconThemeData(color: Color(0xffA7AAB9)),
                primaryColorLight: Color(0xff484D64),
                primaryColorDark: Color(0xff323647),
                scaffoldBackgroundColor: Color(0xff20222D),
                accentColor: Color(0xff6A7AC7),
                colorScheme: ThemeData().colorScheme.copyWith(
                  primary: Color(0xff6A7AC7),
                  secondary: Color(0xff6A7AC7),
                ),
                primaryColor: Color(0xff808080),
                textSelectionTheme:
                TextSelectionThemeData(selectionColor: Color(0xffA7AAB9)),
                textTheme: TextTheme(
                    bodyText1: TextStyle(color: Color(0xffA7AAB9)),
                    bodyText2: TextStyle(color: Color(0xff20222D)))):ThemeData(
                iconTheme: IconThemeData(color: Color(0xff20222D)),
                primaryColorLight: Color(0xff323647),
                primaryColorDark: Color(0xff20222D),
                scaffoldBackgroundColor: Color(0xff484D64),
                accentColor: Color(0xff6A7AC7),
                colorScheme: ThemeData().colorScheme.copyWith(
                  primary: Color(0xff6A7AC7),
                  secondary: Color(0xff6A7AC7),
                ),
                primaryColor: Color(0xffA7AAB9),
                textSelectionTheme:
                TextSelectionThemeData(selectionColor: Color(0xff20222D)),
                textTheme: TextTheme(
                    bodyText2: TextStyle(color: Color(0xffA7AAB9)),
                    bodyText1: TextStyle(color: Color(0xffA7AAB9)))),
            home: LoginCaller()));
  }
}
