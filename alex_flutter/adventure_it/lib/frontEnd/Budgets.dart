import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';

class Adventure_Budgets extends StatelessWidget {
  Future<List<Budget>> budgetsFuture;
  Adventure adventure;
  Adventure_Budgets({required this.budgetsFuture, required this.adventure});

  Widget build(BuildContext context) {
    return FutureBuilder(
        future: budgetsFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasData) {
            var budgets = snapshot.data as List<Budget>;
            return Scaffold(
                appBar: AppBar(
                    title: Text('Budgets'),
                    leading: IconButton(
                        onPressed: () {
                          Navigator.pushReplacement(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      HomepageStartupCaller()));
                        },
                        icon: Icon(Icons.arrow_back))),
                body: Stack(children: <Widget>[
                  ListView(children: [
                    ...List.generate(
                        budgets.length,
                            (index) => Card(
                            child: ListTile(
                                title: Text(budgets.elementAt(index).name),
                                trailing: IconButton(
                                  icon: Icon(Icons.delete),
                                  onPressed: () {
                                    BudgetApi.softDeleteBudget(
                                        budgets.elementAt(index).id);
                                    budgets.remove(budgets.elementAt(index));
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                Adventure_Budgets(
                                                    budgetsFuture:
                                                    Future.value(budgets),
                                                    adventure:
                                                    this.adventure)));
                                  },
                                ))))
                  ]),
                  Align(
                      alignment: Alignment.bottomCenter,
                      child: ElevatedButton(
                          child: Text("Create Budget"),
                          onPressed: () {
                            BudgetApi.createBudget("New Budget");

                            Navigator.pushReplacement(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => Adventure_Budgets(
                                        budgetsFuture: budgetsFuture,
                                        adventure: this.adventure)));
                            ;
                          }))
                ]),
                floatingActionButton: FloatingActionButton(
                    onPressed: () {
                      Future<List<Budget>>? deletedBudgets =
                      BudgetApi.getDeletedBudgets(
                          this.adventure.adventureId);
                      Navigator.pushReplacement(
                          context,
                          MaterialPageRoute(
                              builder: (context) => DeletedBudgets(
                                budgetsFuture: deletedBudgets,
                                adventure: this.adventure,
                              )));
                    },
                    child: Icon(Icons.delete)));
          } else {
            return Scaffold(
                appBar: AppBar(
                    title: Text('No budgets found'),
                    leading: IconButton(
                        onPressed: () {
                          Navigator.pop(context);
                        },
                        icon: Icon(Icons.arrow_back))));
          }
        });
  }
}

class DeletedBudgets extends StatelessWidget {
  Future<List<Budget>> budgetsFuture;
  Adventure adventure;
  DeletedBudgets({required this.budgetsFuture, required this.adventure});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: budgetsFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasData) {
            var budgets = snapshot.data as List<Budget>;
            return Scaffold(
                appBar: AppBar(
                    title: Text('Recycle Bin for ' + this.adventure.name),
                    leading: IconButton(
                        onPressed: () {
                          Future<List<Budget>> budgetsFuture2 =
                          BudgetApi.getBudgets(adventure);
                          Navigator.pushReplacement(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => Adventure_Budgets(
                                      budgetsFuture: budgetsFuture2,
                                      adventure: this.adventure)));
                          ;
                        },
                        icon: Icon(Icons.arrow_back))),
                body: ListView(children: [
                  ...List.generate(
                      budgets.length,
                          (index) => Card(
                          child: ListTile(
                              title: Text(budgets.elementAt(index).name),
                              trailing: IconButton(
                                  icon: Icon(Icons.restore_outlined),
                                  onPressed: () {
                                    BudgetApi.restoreBudget(
                                        budgets.elementAt(index).id);
                                    budgets.remove(budgets.elementAt(index));
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                DeletedBudgets(
                                                    budgetsFuture:
                                                    Future.value(budgets),
                                                    adventure:
                                                    this.adventure)));
                                  }))))
                ]));
          } else {
            return Scaffold(
                appBar: AppBar(
                    title: Text('No budgets found'),
                    leading: IconButton(
                        onPressed: () {
                          Navigator.pop(context);
                        },
                        icon: Icon(Icons.arrow_back))));
          }
        });
  }
}
