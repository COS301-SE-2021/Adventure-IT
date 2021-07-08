
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';
import 'AdventurePage.dart';

import '../api/budget.dart';

class Checklists extends StatelessWidget {
  Adventure? currentAdventure;

  Checklists(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          Checklist_List(currentAdventure),
        ]);
  }
}

class Checklist_List extends StatelessWidget {
  Adventure? a;

  Checklist_List(Adventure? a) {
    this.a = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            leading: IconButton(
                onPressed: () {
                  {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => (AdventurePage(a))),
                    );
                  }
                },
                icon: const Icon(Icons.arrow_back_ios),
                color: Theme.of(context).textTheme.bodyText1!.color),
            title: Center(
                child: Text("Checklists",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Center());
  }
}