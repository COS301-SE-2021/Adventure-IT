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
  Future<List<Adventure>>? ownerAdventuresFuture;
  Future<List<Adventure>>? attendeeAdventuresFuture;

  void initState() {
    super.initState();
    ownerAdventuresFuture = AdventureApi.getAdventuresByOwner();
    attendeeAdventuresFuture = AdventureApi.getAdventuresByAttendee();
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
                          MaterialPageRoute(builder: (context) => Login()));
                      ;
                    },
                    icon: const Icon(Icons.logout))),
            body: HomePage_Pages(
                ownerAdventuresFuture: ownerAdventuresFuture,
                attendeeAdventuresFuture: attendeeAdventuresFuture)));
  }
}