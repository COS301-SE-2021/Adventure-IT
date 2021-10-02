import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:flutter/material.dart';

final authApiGetToken = kIsWeb
    ? "http://3.142.172.27:5001/auth/realms/adventure-it/protocol/openid-connect/token"
    : "http://3.142.172.27:5001/auth/realms/adventure-it/protocol/openid-connect/token";
final authApiAdmin = kIsWeb
    ? "http://3.142.172.27:5001/auth/admin/realms/adventure-it/"
    : "http://3.142.172.27:5001/auth/admin/realms/adventure-it/";
final mainApi = kIsWeb
    ? "http://app.adventure-it.co.za/api"
    : "http://app.adventure-it.co.za/api";
final googleMapsKey = "AIzaSyD8xsVljufOFTmpnVZI2KzobIdAvKjWdTE";

final darkTheme = ThemeData(
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
        bodyText2: TextStyle(color: Color(0xff20222D))));

final lightTheme = ThemeData(
    iconTheme: IconThemeData(color: Color(0xff20222D)),
    primaryColorLight: Color(0xff404662),
    primaryColorDark: Color(0xff323647),
    scaffoldBackgroundColor: Color(0xff48517D),
    accentColor: Color(0xff6A7AC7),
    colorScheme: ThemeData().colorScheme.copyWith(
          primary: Color(0xff6A7AC7),
          secondary: Color(0xff6A7AC7),
        ),
    primaryColor: Color(0xffA7AAB9),
    textSelectionTheme:
        TextSelectionThemeData(selectionColor: Color(0xff20222D)),
    textTheme: TextTheme(
        bodyText2: TextStyle(color: Color(0xff20222D)),
        bodyText1: TextStyle(color: Color(0xffA7AAB9))));
