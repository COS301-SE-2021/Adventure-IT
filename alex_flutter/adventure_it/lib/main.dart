// @dart=2.9
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';

import 'Providers/adventure_model.dart';
import 'api/budget.dart';
import 'frontEnd/Login.dart';
import 'frontEnd/HomepageStartup.dart';


void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (context) => AdventuresModel(),
        ),
      ],
      child: MyApp(),
    ),
  );
}


class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme:  ThemeData(
          primaryColorLight: Color(0xff484D64),
          primaryColorDark: Color(0xff323647),
          scaffoldBackgroundColor: Color(0xff20222D),
          accentColor: Color(0xff6A7AC7),
          primaryColor: Color(0xff808080) ,
          textSelectionTheme: TextSelectionThemeData(selectionColor: Color(0xffA7AAB9)),
          textTheme: TextTheme(
            bodyText1: TextStyle(color: Color(0xffA7AAB9)),
            bodyText2: TextStyle(color:Color(0xff20222D))
          )),
        home: LoginCaller());
  }
}







