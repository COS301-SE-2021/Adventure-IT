import 'package:adventure_it/Providers/budget_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'BudgetList.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';

class BudgetPage extends StatelessWidget {
  Budget? currentBudget;
  Adventure? currentAdventure;

  BudgetPage(Budget? b, Adventure? a) {
    this.currentBudget = b;
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text(currentBudget!.name,
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height / 60),
              Container(
                height: MediaQuery.of(context).size.height * 0.3,
                child:getReport(),
              ),
              Spacer(),
              Container(
                height: MediaQuery.of(context).size.height * 0.5,
                child: getBudgetEntries(this.currentBudget),
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
                                        Budgets(currentAdventure)));
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
                                    return _AlertBox(
                                        currentBudget!, currentAdventure!);
                                  });
                            }
                          },
                          icon: const Icon(Icons.add),
                          color: Theme.of(context).primaryColorDark)),
                ),
              Spacer(),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class _AlertBox extends StatefulWidget {
  Budget? b;
  Adventure? a;

  _AlertBox(Budget budget, Adventure a) {
    this.b = budget;
    this.a = a;
  }

  @override
  AlertBox createState() => AlertBox(b!, a!);
}

class AlertBox extends State<_AlertBox> {
  Budget? b;
  int? selectedCategory;
  String? payer;
  String? payee;
  Adventure? currentAdventure;

  List<UserProfile> users = List.empty();
  List<String>? userNames;
  List<String>? userNamesAndOther;

  AlertBox(Budget budget, Adventure a) {
    this.b = budget;
    this.currentAdventure = a;
    AdventureApi.getAttendeesOfAdventure(a.adventureId).then((value) {
      setState(() {
        users = value;
      });
      var temp1 = List<String>.filled(users.length, "", growable: true);
      temp1.removeRange(0, users.length);

      var temp2 = List<String>.filled(users.length, "", growable: true);
      temp2.removeRange(0, users.length);

      for (int i = 0; i < users.length; i++) {
        temp1.add(value.elementAt(i).username);
      }
      for (int i = 0; i < users.length; i++) {
        temp2.add(value.elementAt(i).username);
      }
      temp2.add("Other");

      setState(() {
        print(temp1.toString());
        print(temp2.toString());
        userNames = temp1;
        userNamesAndOther = temp2;
      });
    });
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.8;
    } else {
      return MediaQuery.of(context).size.height * 0.9;
    }
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> categoryList = [
      Text(
        "Accommodation",
        style: TextStyle(color: Theme.of(context).textTheme.bodyText1!.color),
      ),
      Text(
        "Activities",
        style: TextStyle(color: Theme.of(context).textTheme.bodyText1!.color),
      ),
      Text(
        "Food",
        style: TextStyle(color: Theme.of(context).textTheme.bodyText1!.color),
      ),
      Text(
        "Transport",
        style: TextStyle(color: Theme.of(context).textTheme.bodyText1!.color),
      ),
      Text(
        "Other",
        style: TextStyle(color: Theme.of(context).textTheme.bodyText1!.color),
      )
    ];

    if (userNames==null||userNames!.length == 0 || userNamesAndOther==null||userNamesAndOther!.length == 0) {
      return AlertDialog(
          backgroundColor: Theme.of(context).primaryColorDark,
          content: Container(
              height: getSize(context),
              child: Center(
                  child: CircularProgressIndicator(
                      valueColor: new AlwaysStoppedAnimation<Color>(
                          Theme.of(context).accentColor)))));
    } else {
      return AlertDialog(
          backgroundColor: Theme.of(context).primaryColorDark,
          content: Container(
            height: getSize(context),
            child: Stack(
              overflow: Overflow.visible,
              children: <Widget>[
                Positioned(
                  right: -40.0,
                  top: -40.0,
                  child: InkResponse(
                    onTap: () {
                      Navigator.of(context).pop();
                    },
                    child: CircleAvatar(
                      child: Icon(Icons.close,
                          color: Theme.of(context).primaryColorDark),
                      backgroundColor: Theme.of(context).accentColor,
                    ),
                  ),
                ),
                Center(
                  child: Column(
                    // mainAxisSize: MainAxisSize.min,
                    children: <Widget>[
                      Text("Add Item To Budget",
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            color: Theme.of(context).textTheme.bodyText1!.color,
                            fontSize:
                                25 * MediaQuery.of(context).textScaleFactor,
                            fontWeight: FontWeight.bold,
                          )),
                      Spacer(),
                      Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                                MediaQuery.of(context).size.width * 0.02),
                        child: TextField(
                            style: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color),
                            decoration: InputDecoration(
                                hintStyle: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText2!
                                        .color),
                                filled: true,
                                enabledBorder: InputBorder.none,
                                errorBorder: InputBorder.none,
                                disabledBorder: InputBorder.none,
                                fillColor: Theme.of(context).primaryColorLight,
                                focusedBorder: OutlineInputBorder(
                                    borderSide: new BorderSide(
                                        color: Theme.of(context).accentColor)),
                                hintText: 'Title')),
                      ),
                      SizedBox(
                          height: MediaQuery.of(context).size.height * 0.01),
                      Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                                MediaQuery.of(context).size.width * 0.02),
                        child: TextField(
                            maxLength: 255,
                            maxLines: 3,
                            style: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color),
                            decoration: InputDecoration(
                                hintStyle: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText2!
                                        .color),
                                filled: true,
                                enabledBorder: InputBorder.none,
                                errorBorder: InputBorder.none,
                                disabledBorder: InputBorder.none,
                                fillColor: Theme.of(context).primaryColorLight,
                                focusedBorder: OutlineInputBorder(
                                    borderSide: new BorderSide(
                                        color: Theme.of(context).accentColor)),
                                hintText: 'Description')),
                      ),
                      Spacer(),
                      Row(children: [
                        Spacer(),
                        DropdownButton<String>(
                            dropdownColor: Theme.of(context).primaryColorDark,
                            hint: new Text("Select a sender",
                                style: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color)),
                            value: payer,
                            onChanged: (String? newValue) {
                              setState(() {
                                payer = newValue!;
                              });
                            },
                            items: userNames!.map((String user) {
                              return new DropdownMenuItem<String>(
                                value: user,
                                child: new Text(
                                  user,
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              );
                            }).toList()),
                        Spacer(),
                      ]),
                      Spacer(),
                  Row(children: [
                    Spacer(),
                        Container(
                          width: MediaQuery.of(context).size.width * 0.2,
                          padding: EdgeInsets.symmetric(
                              horizontal:
                                  MediaQuery.of(context).size.width * 0.02),
                          child: TextField(
                              inputFormatters: <TextInputFormatter>[
                                WhitelistingTextInputFormatter.digitsOnly
                              ],
                              keyboardType: TextInputType.number,
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color),
                              decoration: InputDecoration(
                                  hintStyle: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText2!
                                          .color),
                                  filled: true,
                                  enabledBorder: InputBorder.none,
                                  errorBorder: InputBorder.none,
                                  disabledBorder: InputBorder.none,
                                  fillColor:
                                      Theme.of(context).primaryColorLight,
                                  focusedBorder: OutlineInputBorder(
                                      borderSide: new BorderSide(
                                          color:
                                              Theme.of(context).accentColor)),
                                  hintText: 'Amount')),
                        ),
                        Spacer(),
              ]),
                      Spacer(),
                      Row(children: [
                        Spacer(),
                        DropdownButton<String>(
                            dropdownColor: Theme.of(context).primaryColorDark,
                            hint: new Text("Select a recipient",
                                style: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color)),
                            value: payee,
                            onChanged: (String? newValue) {
                              setState(() {
                                payee = newValue!;
                              });
                            },
                            items: userNamesAndOther!.map((String user) {
                              return new DropdownMenuItem<String>(
                                value: user,
                                child: new Text(
                                  user,
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              );
                            }).toList()),
                        Spacer(),
                      ]),
                      SizedBox(
                          height: MediaQuery.of(context).size.height * 0.01),
                      Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                            MediaQuery.of(context).size.width * 0.02),
                        child: TextField(
                          enabled: payee!=null&&payee!.compareTo("Other")==0,
                            style: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color),
                            decoration: InputDecoration(
                                hintStyle: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText2!
                                        .color),
                                filled: true,
                                enabledBorder: InputBorder.none,
                                errorBorder: InputBorder.none,
                                disabledBorder: InputBorder.none,
                                fillColor: Theme.of(context).primaryColorLight,
                                focusedBorder: OutlineInputBorder(
                                    borderSide: new BorderSide(
                                        color: Theme.of(context).accentColor)),
                                hintText: 'Insert name for Other')),
                      ),
                      Spacer(),
                      DropdownButton(
                        dropdownColor: Theme.of(context).primaryColorDark,
                        hint: Text(
                          "Select a category",
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                        ),
                        icon: Icon(Icons.arrow_drop_down,
                            color:
                                Theme.of(context).textTheme.bodyText1!.color),
                        iconDisabledColor:
                            Theme.of(context).scaffoldBackgroundColor,
                        iconEnabledColor: Theme.of(context).accentColor,
                        underline: Container(
                          height: 0,
                        ),
                        value: selectedCategory,
                        selectedItemBuilder: (BuildContext context) {
                          return categoryList;
                        },
                        items: [
                          DropdownMenuItem(
                            child: Text(
                              "Accommodation",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color),
                            ),
                            value: 1,
                          ),
                          DropdownMenuItem(
                            child: Text(
                              "Activities",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color),
                            ),
                            value: 2,
                          ),
                          DropdownMenuItem(
                            child: Text(
                              "Food",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color),
                            ),
                            value: 3,
                          ),
                          DropdownMenuItem(
                            child: Text(
                              "Transport",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color),
                            ),
                            value: 4,
                          ),
                          DropdownMenuItem(
                            child: Text(
                              "Other",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color),
                            ),
                            value: 5,
                          )
                        ],
                        onChanged: (int? value) {
                          setState(() {
                            selectedCategory = value;
                          });
                        },
                      ),
                      Spacer(),
                      Padding(
                        padding: EdgeInsets.symmetric(
                            horizontal:
                                MediaQuery.of(context).size.width * 0.02),
                        child: RaisedButton(
                          color: Theme.of(context).accentColor,
                          child: Text("Create",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color)),
                          onPressed: () {
                            Navigator.of(context).pop();
                          },
                        ),
                      ),
                      Spacer(),
                    ],
                  ),
                )
              ],
            ),
          ));
    }
  }
}

class GetReport extends StatelessWidget
{
  Budget? currentBudget;
  GetReport(Budget b)
  {
    this.currentBudget=b;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => BudgetEntryModel(currentBudget!),
    child: Consumer<BudgetEntryModel>(
    builder: (context, budgetEntryModel, child) {
      if (budgetEntryModel.entries == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      }
      if (budgetEntryModel.entries!.length > 0) {
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
                                            FlatButton(
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
                                            FlatButton(
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
                                FlatButton(
                                    onPressed: () =>
                                        Navigator.of(context).pop(true),
                                    child: Text("Remove",
                                        style: TextStyle(
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color))),
                                FlatButton(
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
