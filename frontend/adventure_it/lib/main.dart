// @dart=2.9
import 'package:adventure_it/constants.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_downloader/flutter_downloader.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';
import 'package:theme_provider/theme_provider.dart';

import 'Providers/location_model.dart';
import 'api/mediaAPI.dart';
import 'frontEnd/Login.dart';

void main() async {
  if (!kIsWeb) {
    await FlutterDownloader.initialize();
    FlutterDownloader.registerCallback(MediaApi.downloadCallback);
    await Firebase.initializeApp();
    runApp(
        MyApp(),
    );
  } else {
    runApp(MyApp());
  }
}
//
class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => LocationModel(context),
        child: MaterialAppWithTheme(),
    );
  }
}

class MaterialAppWithTheme extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ThemeProvider(
      defaultThemeId: 'dark_theme',
      loadThemeOnInit: true,
      themes: [
        AppTheme(id: 'light_theme', description: 'Adventure-IT_Light',
        data: lightTheme),
        AppTheme(id: 'dark_theme', description: 'Adventure-IT_Dark',
            data: darkTheme)
      ],
        child: ThemeConsumer(
          child: Builder(
            builder: (themeContext) => MaterialApp(
            home: LoginCaller(),
            theme: ThemeProvider.themeOf(themeContext).data,
            )
          )
        ),
    );
  }
}