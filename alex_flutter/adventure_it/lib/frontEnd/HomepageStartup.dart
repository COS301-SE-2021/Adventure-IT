
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'Login.dart';
import 'Adventures.dart';

import '../api/budget.dart';

class HomepageStartupCaller extends StatefulWidget {
  @override
  HomePage createState() => HomePage();
}

class HomePage extends State<HomepageStartupCaller> {
  // Future<List<Adventure>>? adventuresFuture;
  //
  // void initState() {
  //   super.initState();
  //   adventuresFuture = AdventureApi.getAdventuresByUUID("1660bd85-1c13-42c0-955c-63b1eda4e90b");
  // }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: Theme.of(context),
        home: Scaffold(
            appBar: AppBar(
                backgroundColor: Theme.of(context).primaryColorDark,
                centerTitle: true,
                title: Text("Adventure-IT", style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                leading: IconButton(
                    onPressed: () {
                      Navigator.pushReplacement(context,
                          MaterialPageRoute(builder: (context) => LoginCaller()));
                      ;
                    },
                    icon: const Icon(Icons.logout, color: Color(0xffA7AAB9),))),
            body: HomePage_Pages(
             )));
  }
}