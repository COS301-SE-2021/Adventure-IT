
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
class BudgetPage extends StatelessWidget {
  Budget? currentBudget;

  BudgetPage(Budget? b) {
    this.currentBudget = b;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text(currentBudget!.name,
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            actions:[IconButton(
                onPressed: () {
                  {


                  }
                },
                icon: const Icon(Icons.edit),
                color: Theme.of(context).textTheme.bodyText1!.color),],
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height / 60),
              Container(
                  height: MediaQuery.of(context).size.height * 0.75,
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
                            Navigator.of(context).pop();
                          },
                          icon: const Icon(Icons.arrow_back_ios_new_rounded),
                          color: Theme.of(context).primaryColorDark)),
                ),
                Expanded(
                  flex: 1,
                  child: Container(
                      decoration: BoxDecoration(
                          color: Theme.of(context).accentColor,
                          shape: BoxShape.circle),
                      child: IconButton(
                          onPressed: () {
                            {
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertBox(currentBudget!);
                                  });
                            }
                          },
                          icon: const Icon(Icons.add),
                          color: Theme.of(context).primaryColorDark)),
                ),
                Expanded(
                  flex: 1,
                  child: Container(
                    ), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class AlertBox extends StatelessWidget {
  Budget? b;

  AlertBox(Budget budget) {
    this.b = budget;
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }
  @override
  Widget build(BuildContext context) {
    return AlertDialog(
    );
  }
}