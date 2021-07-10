import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/frontEnd/CreateItinerary.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';
import 'AdventurePage.dart';

import '../api/budget.dart';

class Itineraries extends StatelessWidget {
  Adventure? currentAdventure;

  Itineraries(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          Itineraries_List(currentAdventure),
        ]);
  }
}

class Itineraries_List extends StatelessWidget {
  Adventure? a;

  Itineraries_List(Adventure? a) {
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
                child: Text("Itineraries",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
            child: Column(children: <Widget>[
          SizedBox(height: MediaQuery.of(context).size.height / 60),
          Expanded(
              child: Align(
            alignment: FractionalOffset.bottomCenter,
            child: Container(
                decoration: BoxDecoration(
                    color: Theme.of(context).accentColor,
                    shape: BoxShape.circle),
                child: IconButton(
                    onPressed: () {
                      {
                        Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(
                                builder: (context) =>
                                    ItineraryCreationCaller()));
                      }
                    },
                    icon: const Icon(Icons.add),
                    color: Theme.of(context).primaryColorDark)),
          ) //Your widget here,
              ),
          SizedBox(height: MediaQuery.of(context).size.height / 60),
        ])));
  }
}
