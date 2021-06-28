import 'dart:html';

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
  Future<List<Adventure>>? adventuresFuture;

  void initState() {
    super.initState();
    adventuresFuture = AdventureApi.getAdventuresByUUID();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: Theme.of(context),
        home: Scaffold(
            appBar: AppBar(
                backgroundColor: Theme.of(context).primaryColorDark,
                centerTitle: true,
                title: Text("Adventure-IT"),
                leading: IconButton(
                    onPressed: () {
                      Navigator.pushReplacement(context,
                          MaterialPageRoute(builder: (context) => LoginCaller()));
                      ;
                    },
                    icon: const Icon(Icons.logout))),
            body: HomePage_Pages(
              adventuresFuture: adventuresFuture,)));
  }
}