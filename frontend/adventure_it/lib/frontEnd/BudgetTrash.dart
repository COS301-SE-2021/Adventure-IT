import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:adventure_it/Providers/budget_model.dart';
import 'package:adventure_it/api/adventure.dart';

import 'BudgetList.dart';
import 'Navbar.dart';

//Page to restore/ hard delete
class BudgetTrash extends StatelessWidget {
  Adventure? adventure;

  BudgetTrash(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Deleted Budgets",
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
                  child: DeletedBudgetList(adventure)),
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
                                    builder: (context) => Budgets(adventure)));
                          },
                          icon: const Icon(Icons.arrow_back_ios_new_rounded),
                          color: Theme.of(context).primaryColorDark)),
                ),
                Expanded(
                  flex: 1,
                  child: Container(),
                ),
                Expanded(
                  flex: 1,
                  child: Container(), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class DeletedBudgetList extends StatelessWidget {
  Adventure? a;
  BuildContext? c;

  DeletedBudgetList(Adventure? adventure) {
    this.a = adventure;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => DeletedBudgetModel(a!),
        child: Consumer<DeletedBudgetModel>(
            builder: (context, deletedBudgetModel, child) {
          this.c = context;
          if (deletedBudgetModel.deletedBudgets == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          }
          if (deletedBudgetModel.deletedBudgets!.length > 0) {
            return Expanded(
                flex: 2,
                child: ListView(children: [
                  ...List.generate(
                      deletedBudgetModel.deletedBudgets!.length,
                      (index) => Dismissible(
                          background: Container(
                            // color: Theme.of(context).primaryColor,
                            //   margin: const EdgeInsets.all(5),
                            padding: EdgeInsets.all(
                                MediaQuery.of(context).size.height / 60),
                            child: Row(
                              children: [
                                new Spacer(),
                                Icon(Icons.delete,
                                    color: Theme.of(context).accentColor,
                                    size: 35 *
                                        MediaQuery.of(context).textScaleFactor),
                              ],
                            ),
                          ),
                          direction: DismissDirection.endToStart,
                          key: Key(deletedBudgetModel.deletedBudgets!
                              .elementAt(index)
                              .id),
                          child: Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                  onTap: () {
                                    showDialog(
                                        context: context,
                                        builder: (BuildContext context) {
                                          return AlertDialog(
                                              backgroundColor: Theme.of(context)
                                                  .primaryColorDark,
                                              title: Text(
                                                'Confirm Restoration',
                                                style: TextStyle(
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color),
                                              ),
                                              content: Text(
                                                'Are you sure you want to restore this budget to your adventure?',
                                                style: TextStyle(
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color),
                                              ),
                                              actions: <Widget>[
                                                TextButton(
                                                  child: Text('Restore',
                                                      style: TextStyle(
                                                          color:
                                                              Theme.of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)),
                                                  onPressed: () {
                                                    Provider.of<DeletedBudgetModel>(
                                                            c!,
                                                            listen: false)
                                                        .restoreBudget(
                                                            deletedBudgetModel
                                                                .deletedBudgets!
                                                                .elementAt(
                                                                    index));
                                                    Navigator.of(context).pop();
                                                  },
                                                ),
                                                TextButton(
                                                  child: Text('Cancel',
                                                      style: TextStyle(
                                                          color:
                                                              Theme.of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)),
                                                  onPressed: () {
                                                    Navigator.of(context).pop();
                                                  },
                                                ),
                                              ]);
                                        });
                                  },
                                  hoverColor:
                                      Theme.of(context).primaryColorLight,
                                  child: Container(
                                    child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 4,
                                          child: ListTile(
                                            title: Text(
                                                deletedBudgetModel
                                                    .deletedBudgets!
                                                    .elementAt(index)
                                                    .name,
                                                style: TextStyle(
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                            // subtitle:Text(adventures.elementAt(index).description),
                                            subtitle: Text(
                                                deletedBudgetModel
                                                    .deletedBudgets!
                                                    .elementAt(index)
                                                    .description,
                                                style: TextStyle(
                                                    fontSize: 15 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ))),
                          confirmDismiss: (DismissDirection direction) async {
                            return await showDialog(
                              context: context,
                              builder: (BuildContext context) {
                                return AlertDialog(
                                  backgroundColor:
                                      Theme.of(context).primaryColorDark,
                                  title: Text("Confirm Removal",
                                      style: TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                  content: Text(
                                      "Are you sure you want to remove this budget for definite?",
                                      style: TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                  actions: <Widget>[
                                    TextButton(
                                        onPressed: () =>
                                            Navigator.of(context).pop(true),
                                        child: Text("Remove",
                                            style: TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color))),
                                    TextButton(
                                      onPressed: () =>
                                          Navigator.of(context).pop(false),
                                      child: Text("Cancel",
                                          style: TextStyle(
                                              color: Theme.of(context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color)),
                                    ),
                                  ],
                                );
                              },
                            );
                          },
                          onDismissed: (direction) {
                            Provider.of<DeletedBudgetModel>(context,
                                    listen: false)
                                .hardDeleteBudget(deletedBudgetModel
                                    .deletedBudgets!
                                    .elementAt(index));
                          }))
                ]));
          } else {
            return Center(
                child: Text("It seems you're not one for recycling...",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        }));
  }
}
