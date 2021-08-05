import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/services.dart';
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
    this.currentAdventure=a;
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
            actions: [
              IconButton(
                  onPressed: () {
                    {}
                  },
                  icon: const Icon(Icons.edit),
                  color: Theme.of(context).textTheme.bodyText1!.color),
            ],
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
                            Navigator.pushReplacement(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => Budgets(
                                       currentAdventure)));
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
                Expanded(
                  flex: 1,
                  child: Container(), //Your widget here,
                ),
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
  String payer = "";
  String payee = "";
  Adventure? currentAdventure;

  List<UserProfile> users=List.empty();
  List<String> usernames=List.empty();
  List<String> usernamesAndOther=List.empty();

  AlertBox(Budget budget, Adventure a) {
    this.b = budget;
    this.currentAdventure = a;
    AdventureApi.getAttendeesOfAdventure(a.adventureId)
        .then((value) => users = value);

    for (int i = 0; i < users!.length; i++) {

        usernames!.add(users
            !.elementAt(i)
            .username);
        usernamesAndOther!.add(users
            !.elementAt(i)
            .username);

    }
    usernamesAndOther!.add("Other");
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  List<DropdownMenuItem<String>> getUsernames() {
    List<DropdownMenuItem<String>> returnList=List.empty();
    for (int i = 0; i < usernames!.length; i++) {
      DropdownMenuItem<String> d = new DropdownMenuItem(
          child: Text(usernames!.elementAt(i),
              style: TextStyle(
                color: Theme.of(context).textTheme.bodyText1!.color,
              )),
          value: usernames!.elementAt(i));
      returnList.add(d);
    }
    return returnList;
  }

  List<DropdownMenuItem<String>> getUsernamesWithOther() {
    List<DropdownMenuItem<String>> returnList= List.empty();
    for (int i = 0; i < usernamesAndOther.length; i++) {
      DropdownMenuItem<String> d = new DropdownMenuItem(
          child: Text(usernamesAndOther.elementAt(i),
              style: TextStyle(
                color: Theme.of(context).textTheme.bodyText1!.color,
              )),
          value: usernamesAndOther.elementAt(i));
      returnList.add(d);
    }
    return returnList;
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
                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    Spacer(),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
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
                    SizedBox(height: MediaQuery.of(context).size.height * 0.01),
                    Container(//
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLines: 3,
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
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
                    SizedBox(height: MediaQuery.of(context).size.height * 0.01),
                    Row(children: [
                      Spacer(),
                      DropdownButton(
                        dropdownColor: Theme.of(context).primaryColorDark,
                        hint: Text(
                          "Payer",
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                        ),
                        selectedItemBuilder: (BuildContext context) {
                          return getUsernames();
                        },
                        icon: Icon(Icons.arrow_drop_down,
                            color:
                                Theme.of(context).textTheme.bodyText1!.color),
                        iconDisabledColor:
                            Theme.of(context).scaffoldBackgroundColor,
                        iconEnabledColor: Theme.of(context).accentColor,
                        underline: Container(
                          height: 0,
                        ),
                        value: payer,
                        items: getUsernames(),
                        onChanged: (String? value) {
                          setState(() {
                            payer = value!;
                          });
                        },
                      ),
                      Spacer(),
                      Text("Pays",
                          style: TextStyle(
                              color: Theme.of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color)),
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
                                fillColor: Theme.of(context).primaryColorLight,
                                focusedBorder: OutlineInputBorder(
                                    borderSide: new BorderSide(
                                        color: Theme.of(context).accentColor)),
                                hintText: 'Amount')),
                      ),
                      Spacer(),
                      Text("To",
                          style: TextStyle(
                              color: Theme.of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color)),
                      Spacer(),
                      DropdownButton(
                        dropdownColor: Theme.of(context).primaryColorDark,
                        hint: Text(
                          "Recipient",
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
                        value: payee,
                        selectedItemBuilder: (BuildContext context) {
                          return categoryList;
                        },
                        items: getUsernamesWithOther(),
                        onChanged: (String? value) {
                          setState(() {
                            payee = value!;
                          });
                        },
                      ),
                      Spacer(),
                    ]),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    DropdownButton(
                      dropdownColor: Theme.of(context).primaryColorDark,
                      hint: Text(
                        "Select a category",
                        style: TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color),
                      ),
                      icon: Icon(Icons.arrow_drop_down,
                          color: Theme.of(context).textTheme.bodyText1!.color),
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
                          horizontal: MediaQuery.of(context).size.width * 0.02),
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