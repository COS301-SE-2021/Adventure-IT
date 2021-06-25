import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'Budgets.dart';

import '../api/budget.dart';

class HomePage_Pages extends StatelessWidget {
  Future<List<Adventure>>? ownerAdventuresFuture;
  Future<List<Adventure>>? attendeeAdventuresFuture;

  HomePage_Pages(
      {@required this.ownerAdventuresFuture,
        @required this.attendeeAdventuresFuture});
  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          HomePage_Pages_Adventures(
              ownerAdventuresFuture: ownerAdventuresFuture,
              attendeeAdventuresFuture: attendeeAdventuresFuture),
          Text("Some Other Page 1"),
          Text("Some Other Page 2")
        ]);
  }
}

class HomePage_Pages_Adventures extends StatelessWidget {
  Future<List<Adventure>>? ownerAdventuresFuture;
  Future<List<Adventure>>? attendeeAdventuresFuture;
  HomePage_Pages_Adventures(
      {@required this.ownerAdventuresFuture,
        @required this.attendeeAdventuresFuture});
  @override
  Widget build(BuildContext context) {
    return Column(children: <Widget>[
      Container(
          alignment: Alignment.center,
          height: 100,
          child: Text("Adventures",
              style: TextStyle(fontSize: 35, fontWeight: FontWeight.bold))),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Created Adventures", style: TextStyle(fontSize: 20))),
      AdventureFutureBuilder(adventuresFuture: ownerAdventuresFuture),
      SizedBox(height: 50),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Shared Adventures", style: TextStyle(fontSize: 20))),
      AdventureFutureBuilder(adventuresFuture: attendeeAdventuresFuture),
    ]);
  }
}

class AdventureFutureBuilder extends StatelessWidget {
  Future<List<Adventure>>? adventuresFuture;
  AdventureFutureBuilder({@required this.adventuresFuture});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: adventuresFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasData) {
            var adventures = snapshot.data as List<Adventure>;
            print(adventures);
            return Expanded(
                child: ListView(children: [
                  ...List.generate(
                      adventures.length,
                          (index) => Card(
                          child: ListTile(
                              title: Text(adventures.elementAt(index).name),
                              trailing: IconButton(
                                icon: Icon(Icons.more_vert),
                                onPressed: () {
                                  Future<List<Budget>> budgetsFuture =
                                  BudgetApi.getBudgets(
                                      adventures.elementAt(index));
                                  Navigator.pushReplacement(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) => Adventure_Budgets(
                                              budgetsFuture: budgetsFuture,
                                              adventure:
                                              adventures.elementAt(index))));
                                },
                              ))))
                ]));
          } else {
            return Center(child: Text("Something went wrong"));
          }
        });
  }
}