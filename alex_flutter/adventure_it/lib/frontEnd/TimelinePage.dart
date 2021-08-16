import 'package:adventure_it/Providers/timeline_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';
class TimePage extends StatelessWidget {
  Adventure? currentAdventure;

  TimePage(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          TimeLine(currentAdventure),
        ]);
  }
}

class TimeLine extends StatelessWidget {
  Adventure? a;

  TimeLine(Adventure? a) {
    this.a = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => TimelineModel(a!),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text("Timeline",
                        style: new TextStyle(
                            color: Theme.of(context).textTheme.bodyText1!.color))),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Container(
                      height: MediaQuery.of(context).size.height * 0.75,
                      //child: GetChecklistEntries(currentChecklist!)
                  ),
                  Spacer(),
                  Row(children: [
                    Expanded(
                      flex: 1,
                      child: Container(
                          decoration: BoxDecoration(
                              color: Theme.of(context).accentColor,
                              shape: BoxShape.circle),
                          child: IconButton(
                              onPressed: () {
                                Navigator.pushReplacement(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) =>
                                            AdventurePage(a)));
                              },
                              icon: const Icon(Icons.arrow_back_ios_new_rounded),
                              color: Theme.of(context).primaryColorDark)),
                    ),
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ]
                )
        )
    );
  }
}
