import 'package:adventure_it/Providers/budget_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'BudgetList.dart';
import 'Navbar.dart';

//Page to restore/ hard delete
class BudgetTrash extends StatelessWidget {
  late final Adventure? adventure;

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
            iconTheme: IconThemeData(
                color: Theme.of(context).textTheme.bodyText1!.color),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(
          mainAxisSize: MainAxisSize.max,
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height / 60),
              Expanded(child: DeletedBudgetList(adventure)),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
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
  late final Adventure? a;
  BuildContext? c;

  DeletedBudgetList(Adventure? adventure) {
    this.a = adventure;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => DeletedBudgetModel(a!,context),
        child: Consumer<DeletedBudgetModel>(
            builder: (context, deletedBudgetModel, child) {
          this.c = context;
          if (deletedBudgetModel.deletedBudgets == null ||
              deletedBudgetModel.deletedBudgets!.length !=
                  deletedBudgetModel.creators!.length) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          }
          if (deletedBudgetModel.deletedBudgets!.length > 0) {
            return Column(children: [
              Expanded(
                  flex: 2,
                  child: Container(
                      width: MediaQuery.of(context).size.width <= 500
                          ? MediaQuery.of(context).size.width
                          : MediaQuery.of(context).size.width * 0.9,
                      padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width <= 500
                          ? 0
                          : MediaQuery.of(context).size.width * 0.05),
                      child:ListView(children: [
                    ...List.generate(
                        deletedBudgetModel.deletedBudgets!.length,
                        (index) => Card(
                            color: Theme.of(context).primaryColorDark,
                            child: InkWell(
                              hoverColor: Theme.of(context).primaryColorLight,
                              child: Container(
                                  child: Row(children: <Widget>[
                                Expanded(
                                  flex: 4,
                                  child: ListTile(
                                    trailing: Text(
                                        "Created by: " +
                                            deletedBudgetModel.creators!
                                                .elementAt(index)!
                                                .username,
                                        style: TextStyle(
                                            fontSize: 10,
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color)),
                                    title: Text(
                                        deletedBudgetModel.deletedBudgets!
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
                                        deletedBudgetModel.deletedBudgets!
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
                                PopupMenuButton(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color,
                                    onSelected: (value) {
                                      if (value == 1) {
                                        showDialog(
                                            context: context,
                                            builder: (BuildContext context) {
                                              return AlertDialog(
                                                  backgroundColor:
                                                      Theme.of(context)
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
                                                              color: Theme.of(
                                                                      context)
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
                                                        Navigator.of(context)
                                                            .pop();
                                                      },
                                                    ),
                                                    TextButton(
                                                      child: Text('Cancel',
                                                          style: TextStyle(
                                                              color: Theme.of(
                                                                      context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)),
                                                      onPressed: () {
                                                        Navigator.of(context)
                                                            .pop();
                                                      },
                                                    ),
                                                  ]);
                                            });
                                      }
                                      if (value == 2) {
                                        showDialog(
                                            context: context,
                                            builder: (BuildContext context) {
                                              return AlertDialog(
                                                  backgroundColor:
                                                      Theme.of(context)
                                                          .primaryColorDark,
                                                  title: Text(
                                                    'Confirm Removal',
                                                    style: TextStyle(
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color),
                                                  ),
                                                  content: Text(
                                                    'Are you sure you want to remove this budget forever?',
                                                    style: TextStyle(
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color),
                                                  ),
                                                  actions: <Widget>[
                                                    TextButton(
                                                      child: Text('Remove',
                                                          style: TextStyle(
                                                              color: Theme.of(
                                                                      context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)),
                                                      onPressed: () {
                                                        Provider.of<DeletedBudgetModel>(
                                                                c!,
                                                                listen: false)
                                                            .hardDeleteBudget(
                                                                deletedBudgetModel
                                                                    .deletedBudgets!
                                                                    .elementAt(
                                                                        index));
                                                        Navigator.of(context)
                                                            .pop();
                                                      },
                                                    ),
                                                    TextButton(
                                                      child: Text('Cancel',
                                                          style: TextStyle(
                                                              color: Theme.of(
                                                                      context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)),
                                                      onPressed: () {
                                                        Navigator.of(context)
                                                            .pop();
                                                      },
                                                    ),
                                                  ]);
                                            });
                                      }
                                    },
                                    itemBuilder: (context) => [
                                          PopupMenuItem(
                                              value: 1,
                                              child: Row(
                                                children: <Widget>[
                                                  Padding(
                                                    padding:
                                                        const EdgeInsets.all(5),
                                                    child: Icon(
                                                        Icons
                                                            .restore_from_trash_rounded,
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText2!
                                                            .color),
                                                  ),
                                                  Text("Restore",
                                                      style: TextStyle(
                                                          color:
                                                              Theme.of(context)
                                                                  .textTheme
                                                                  .bodyText2!
                                                                  .color))
                                                ],
                                              )),
                                          PopupMenuItem(
                                              value: 2,
                                              child: Row(
                                                children: <Widget>[
                                                  Padding(
                                                    padding:
                                                        const EdgeInsets.all(5),
                                                    child: Icon(
                                                        Icons
                                                            .delete_forever_rounded,
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText2!
                                                            .color),
                                                  ),
                                                  Text("Delete",
                                                      style: TextStyle(
                                                          color:
                                                              Theme.of(context)
                                                                  .textTheme
                                                                  .bodyText2!
                                                                  .color))
                                                ],
                                              ))
                                        ]),
                              ])),
                            )))
                  ]))
              )]);
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
