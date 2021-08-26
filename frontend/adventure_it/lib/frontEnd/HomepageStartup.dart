
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';

import 'Adventures.dart';
import 'Navbar.dart';

//Homepage shows list of adventures
class HomepageStartupCaller extends StatefulWidget {
  @override
  HomePage createState() => HomePage();
}

class HomePage extends State<HomepageStartupCaller> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: Theme.of(context),
        home: Scaffold(
            drawer: NavDrawer(),
            appBar: AppBar(
              backgroundColor: Theme.of(context).primaryColorDark,
              centerTitle: true,
              title: Text("Adventure-IT",
                  style: new TextStyle(
                      color: Theme.of(context).textTheme.bodyText1!.color)),
            ),
            body: HomePage_Pages()));
  }
}
