import 'package:adventure_it/api/budgetEntry.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/Providers/budget_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventureAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/api/budget.dart';
import 'BudgetList.dart';
import '../api/budget.dart';
import 'Navbar.dart';

//A single budget with entries

class BudgetPage extends StatefulWidget {
  late final UserProfile? creator;
  late final Budget? currentBudget;
  late final Adventure? currentAdventure;

  BudgetPage(Budget? b, Adventure? a, UserProfile c) {
    this.currentBudget = b;
    this.currentAdventure = a;
    this.creator = c;
  }

  @override
  _BudgetPage createState() =>
      _BudgetPage(this.currentBudget, this.currentAdventure, this.creator!);
}

class _BudgetPage extends State<BudgetPage>
    with SingleTickerProviderStateMixin {
  late final UserProfile? creator;
  late final Budget? currentBudget;
  late final Adventure? currentAdventure;

  TabController? tabs;

  @override
  void initState() {
    super.initState();
    tabs = new TabController(vsync: this, length: 2);
  }

  _BudgetPage(Budget? b, Adventure? a, UserProfile c) {
    this.currentBudget = b;
    this.currentAdventure = a;
    this.creator = c;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => BudgetEntryModel(currentBudget!,context),
        builder: (context, widget) =>
            Scaffold(
                drawer: NavDrawer(),
                backgroundColor: Theme
                    .of(context)
                    .scaffoldBackgroundColor,
                appBar: AppBar(
                    iconTheme: IconThemeData(
                        color: Theme.of(context).textTheme.bodyText1!.color),
                    actions: [
                      Center(
                          child: creator != null ? Text(
                              "Created by: " + this.creator!.username,
                              style: new TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color, fontSize: 10)) : Text(
                              "Created by: unknown", style: new TextStyle(
                              color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color, fontSize: 10))
                      ), SizedBox(width: MediaQuery
                          .of(context)
                          .size
                          .width * 0.02)
                    ],
                    title: Center(
                        child: Text(currentBudget!.name,
                            style: new TextStyle(
                                color: Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color))),
                    backgroundColor: Theme
                        .of(context)
                        .primaryColorDark),
                body: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Container(
                          child: TabBar(
                            controller: tabs,
                            labelColor: Theme
                                .of(context)
                                .accentColor,
                            unselectedLabelColor:
                            Theme
                                .of(context)
                                .textTheme
                                .bodyText1!
                                .color,
                            indicatorSize: TabBarIndicatorSize.tab,
                            tabs: [
                              Tab(icon: Icon(Icons.payments), text: "Transactions"),
                              Tab(icon: Icon(Icons.insert_chart_rounded), text: "Report"),
                            ],
                          )),
                      SizedBox(height: MediaQuery
                          .of(context)
                          .size
                          .height / 40),
                      Expanded(
                          child: TabBarView(
                              controller: tabs, children: <Widget>[
                            Container(
                              child: _GetBudgetEntries(
                                  this.currentBudget!, this.currentAdventure!, context),
                            ),
                            Container(
                              child: GetReport(this.currentBudget!),
                            )
                          ])),
                      SizedBox(height: MediaQuery
                          .of(context)
                          .size
                          .height / 60),
                      Row(children: [
                        Expanded(
                          flex: 1,
                          child: Container(
                              decoration: BoxDecoration(
                                  color: Theme
                                      .of(context)
                                      .accentColor,
                                  shape: BoxShape.circle),
                              child: IconButton(
                                  onPressed: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                Budgets(currentAdventure)));
                                  },
                                  icon: const Icon(
                                      Icons.arrow_back_ios_new_rounded),
                                  color: Theme
                                      .of(context)
                                      .primaryColorDark)),
                        ),
                        Expanded(
                          flex: 1,
                          child: Container(
                              decoration: BoxDecoration(
                                  color: Theme
                                      .of(context)
                                      .accentColor,
                                  shape: BoxShape.circle),
                              child: IconButton(
                                  onPressed: () {
                                    {
                                      var provider = Provider.of<
                                          BudgetEntryModel>(
                                          context, listen: false);
                                      showDialog(
                                          context: context,
                                          builder: (BuildContext context) {
                                            return _AlertBox(
                                                currentBudget!,
                                                currentAdventure!, provider,context);
                                          });
                                    }
                                  },
                                  icon: const Icon(Icons.add),
                                  color: Theme
                                      .of(context)
                                      .primaryColorDark)),
                        ),
                        Spacer(),
                      ]),
                      SizedBox(height: MediaQuery
                          .of(context)
                          .size
                          .height / 60),
                    ]))
    );
  }
}

class _AlertBox extends StatefulWidget {
  late final Budget? b;
  late final Adventure? a;
  final BudgetEntryModel budgetEntryModel;
  late final BuildContext? context;

  _AlertBox(this.b, this.a, this.budgetEntryModel,this.context);

  @override
  AlertBox createState() => AlertBox(b!, a!,context!);
}

class AlertBox extends State<_AlertBox> {
  Budget? b;
  int? selectedCategory;
  String? payer;
  String? payee;
  Adventure? currentAdventure;

  final otherController = TextEditingController();
  final amountController = TextEditingController();
  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final centsController = TextEditingController();

  List<UserProfile> users = List.empty();
  List<String>? userNames;
  List<String>? userNamesAndOther;

  AlertBox(Budget budget, Adventure a, BuildContext context) {
    this.b = budget;
    this.currentAdventure = a;

    AdventureApi.getAttendeesOfAdventure(a.adventureId, context).then((value) {
      setState(() {
        users = value;
      });
      var temp1 = List<String>.filled(users.length, "", growable: true);
      temp1.removeRange(0, users.length);

      var temp2 = List<String>.filled(users.length, "", growable: true);
      temp2.removeRange(0, users.length);

      for (int i = 0; i < users.length; i++) {
        temp1.add(value
            .elementAt(i)
            .username);
      }
      for (int i = 0; i < users.length; i++) {
        temp2.add(value
            .elementAt(i)
            .username);
      }
      temp2.add("Other");

      setState(() {
        userNames = temp1;
        userNamesAndOther = temp2;
      });
    });
  }

  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.8;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.9;
    }
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> categoryList = [
      Text(
        "Accommodation",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Activities",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Food",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Transport",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Other",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      )
    ];

    final categoryNames = [
      "ACCOMMODATION",
      "ACTIVITIES",
      "FOOD",
      "TRANSPORT",
      "OTHER"
    ];

    if (userNames == null || userNames!.length == 0 ||
        userNamesAndOther == null || userNamesAndOther!.length == 0) {
      return AlertDialog(
          backgroundColor: Theme
              .of(context)
              .primaryColorDark,
          content: Container(
              height: getSize(context),
              child: Center(
                  child: CircularProgressIndicator(
                      valueColor: new AlwaysStoppedAnimation<Color>(
                          Theme
                              .of(context)
                              .accentColor)))));
    } else {
      return AlertDialog(
          backgroundColor: Theme
              .of(context)
              .primaryColorDark,
          content: Container(
            height: getSize(context),
            child: Stack(
              clipBehavior: Clip.none, children: <Widget>[
              Positioned(
                right: -40.0,
                top: -40.0,
                child: InkResponse(
                  onTap: () {
                    Navigator.pop(context);
                  },
                  child: CircleAvatar(
                    child: Icon(Icons.close,
                        color: Theme
                            .of(context)
                            .primaryColorDark),
                    backgroundColor: Theme
                        .of(context)
                        .accentColor,
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
                          color: Theme
                              .of(context)
                              .textTheme
                              .bodyText1!
                              .color,
                          fontSize:
                          25 * MediaQuery
                              .of(context)
                              .textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    Spacer(),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal:
                          MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: titleController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Title')),
                    ),
                    SizedBox(
                        height: MediaQuery
                            .of(context)
                            .size
                            .height * 0.01),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal:
                          MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLines: 3,
                          style: TextStyle(
                              color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: descriptionController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Description')),
                    ),
                    Spacer(),
                    Row(children: [
                      Spacer(),
                      DropdownButton<String>(
                          dropdownColor: Theme
                              .of(context)
                              .primaryColorDark,
                          hint: new Text("Select a payer",
                              style: TextStyle(
                                  color: Theme
                                      .of(context)
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
                                    color: Theme
                                        .of(context)
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
                        width: 125,
                        padding: EdgeInsets
                            .symmetric(
                            horizontal:
                            MediaQuery
                                .of(
                                context)
                                .size
                                .width *
                                0.02),
                        child: TextField(
                            inputFormatters: <
                                TextInputFormatter>[
                              FilteringTextInputFormatter
                                  .allow(
                                  RegExp(
                                      r'[0-9]')),
                            ],
                            keyboardType: TextInputType
                                .number,
                            style: TextStyle(
                                color: Theme
                                    .of(
                                    context)
                                    .textTheme
                                    .bodyText1!
                                    .color),
                            controller: amountController,
                            decoration: InputDecoration(
                                hintStyle: TextStyle(
                                    color: Theme
                                        .of(
                                        context)
                                        .textTheme
                                        .bodyText2!
                                        .color),
                                filled: true,
                                enabledBorder: InputBorder
                                    .none,
                                errorBorder: InputBorder
                                    .none,
                                disabledBorder: InputBorder
                                    .none,
                                fillColor:
                                Theme
                                    .of(
                                    context)
                                    .primaryColorLight,
                                focusedBorder: OutlineInputBorder(
                                    borderSide: new BorderSide(
                                        color:
                                        Theme
                                            .of(
                                            context)
                                            .accentColor)),
                                hintText: 'Amount')),
                      ),
                      Container(
                          width: 50,
                          child: Text(".",
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                color: Theme
                                    .of(
                                    context)
                                    .textTheme
                                    .bodyText1!
                                    .color),)
                      ),
                      Container(
                        width: 125,
                        padding: EdgeInsets
                            .symmetric(
                            horizontal:
                            MediaQuery
                                .of(
                                context)
                                .size
                                .width *
                                0.02),
                        child: TextField(
                            inputFormatters: <
                                TextInputFormatter>[
                              FilteringTextInputFormatter
                                  .allow(
                                  RegExp(
                                      r'[0-9]')),
                            ],
                            keyboardType: TextInputType
                                .number,
                            style: TextStyle(
                                color: Theme
                                    .of(
                                    context)
                                    .textTheme
                                    .bodyText1!
                                    .color),
                            controller:centsController,
                            decoration: InputDecoration(
                                hintStyle: TextStyle(
                                    color: Theme
                                        .of(
                                        context)
                                        .textTheme
                                        .bodyText2!
                                        .color),
                                filled: true,
                                enabledBorder: InputBorder
                                    .none,
                                errorBorder: InputBorder
                                    .none,
                                disabledBorder: InputBorder
                                    .none,
                                fillColor:
                                Theme
                                    .of(
                                    context)
                                    .primaryColorLight,
                                focusedBorder: OutlineInputBorder(
                                    borderSide: new BorderSide(
                                        color:
                                        Theme
                                            .of(
                                            context)
                                            .accentColor)),
                                hintText: 'Amount')),
                      ),
                      Spacer(),
                    ]),
                    Spacer(),
                    Row(children: [
                      Spacer(),
                      DropdownButton<String>(
                          dropdownColor: Theme
                              .of(context)
                              .primaryColorDark,
                          hint: new Text("Select a payee",
                              style: TextStyle(
                                  color: Theme
                                      .of(context)
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
                                    color: Theme
                                        .of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color),
                              ),
                            );
                          }).toList()),
                      Spacer(),
                    ]),
                    SizedBox(
                        height: MediaQuery
                            .of(context)
                            .size
                            .height * 0.01),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal:
                          MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: TextField(
                          controller: otherController,
                          enabled:
                          payee != null && payee!.compareTo("Other") == 0,
                          style: TextStyle(
                              color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Insert name for Other')),
                    ),
                    Spacer(),
                    DropdownButton(
                      dropdownColor: Theme
                          .of(context)
                          .primaryColorDark,
                      hint: Text(
                        "Select a category",
                        style: TextStyle(
                            color:
                            Theme
                                .of(context)
                                .textTheme
                                .bodyText1!
                                .color),
                      ),
                      icon: Icon(Icons.arrow_drop_down,
                          color:
                          Theme
                              .of(context)
                              .textTheme
                              .bodyText1!
                              .color),
                      iconDisabledColor:
                      Theme
                          .of(context)
                          .scaffoldBackgroundColor,
                      iconEnabledColor: Theme
                          .of(context)
                          .accentColor,
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
                                color: Theme
                                    .of(context)
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
                                color: Theme
                                    .of(context)
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
                                color: Theme
                                    .of(context)
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
                                color: Theme
                                    .of(context)
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
                                color: Theme
                                    .of(context)
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
                          MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            primary: Theme
                                .of(context)
                                .accentColor),
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () async {
                          if (payee != "Other") {
                            if (centsController.text == "") {
                              await widget.budgetEntryModel.addUTUBudgetEntry(
                                  b!,
                                  b!.id,
                                  payer!,
                                  amountController.text + ".00",
                                  titleController.text,
                                  descriptionController.text,
                                  categoryNames[selectedCategory! - 1],
                                  payee!);
                            }
                            else {
                              await widget.budgetEntryModel.addUTUBudgetEntry(
                                  b!,
                                  b!.id,
                                  payer!,
                                  amountController.text + "." +
                                      centsController.text,
                                  titleController.text,
                                  descriptionController.text,
                                  categoryNames[selectedCategory! - 1],
                                  payee!);
                            }
                            Navigator.pop(context);
                          }

                          else {
                            if (centsController.text == "") {
                              await widget.budgetEntryModel.addUTOBudgetEntry(
                                  b!,
                                  b!.id,
                                  payer!,
                                  amountController.text + "." + "00",
                                  titleController.text,
                                  descriptionController.text,
                                  categoryNames[selectedCategory! - 1],
                                  otherController.text,context);
                            }
                            else {
                              await widget.budgetEntryModel.addUTOBudgetEntry(
                                  b!,
                                  b!.id,
                                  payer!,
                                  amountController.text + "." +
                                      centsController.text,
                                  titleController.text,
                                  descriptionController.text,
                                  categoryNames[selectedCategory! - 1],
                                  otherController.text,context);
                            }
                            Navigator.pop(context);
                          }
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

class _GetBudgetEntries extends StatefulWidget {
  late final Budget? currentBudget;
  late final Adventure? currentAdventure;
  late final BuildContext context;

  _GetBudgetEntries(Budget b, Adventure a, context) {
    this.currentBudget = b;
    this.currentAdventure = a;
    this.context = context;
  }

  @override
  GetBudgetEntries createState() =>
      GetBudgetEntries(currentBudget!, currentAdventure!, context);
}

class GetBudgetEntries extends State<_GetBudgetEntries> {
  Budget? currentBudget;
  Adventure? currentAdventure;
  List<UserProfile> users = List.empty();

  GetBudgetEntries(Budget b, Adventure a, BuildContext context) {
    this.currentBudget = b;
    this.currentAdventure = a;
    AdventureApi.getAttendeesOfAdventure(a.adventureId,context).then((value) {
      setState(() {
        users = value;
      });
      var temp1 = List<String>.filled(users.length, "", growable: true);
      temp1.removeRange(0, users.length);

      var temp2 = List<String>.filled(users.length, "", growable: true);
      temp2.removeRange(0, users.length);

      for (int i = 0; i < users.length; i++) {
        temp1.add(value
            .elementAt(i)
            .username);
      }
      for (int i = 0; i < users.length; i++) {
        temp2.add(value
            .elementAt(i)
            .username);
      }
      temp2.add("Other");

      setState(() {
        userNames = temp1;
        userNamesAndOther = temp2;
      });
    });
  }


  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.8;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.9;
    }
  }

  int? selectedCategory;
  String? payer;
  String? payee;

  final categoryNames = [
    "ACCOMMODATION",
    "ACTIVITIES",
    "FOOD",
    "TRANSPORT",
    "OTHER"
  ];
  final BudgetApi api = new BudgetApi();
  final amountController = TextEditingController();
  final centsController = TextEditingController();
  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final otherController = TextEditingController();
  List<String>? userNames;
  List<String>? userNamesAndOther;

  @override
  Widget build(BuildContext context) {
    return Consumer<BudgetEntryModel>(
        builder: (context, budgetEntryModel, child) {
          BuildContext c=context;
          if (budgetEntryModel.entries == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme
                            .of(context)
                            .accentColor)));
          }
          if (budgetEntryModel.entries!.length > 0) {
            return ListView.builder(
                itemCount: budgetEntryModel.entries!.length,
                itemBuilder: (context, index) =>
                    Card(
                            color: Theme
                                .of(context)
                                .primaryColorDark,
                            child: Material(
                                color: Colors.transparent,
                                child: InkWell(
                                    hoverColor:
                                    Theme
                                        .of(context)
                                        .primaryColorLight,
                                    child: Container(
                                      child: Row(
                                        children: <Widget>[
                                          Expanded(
                                            flex: 4,
                                            child: ListTile(
                                                title: Column(
                                                    mainAxisAlignment: MainAxisAlignment
                                                        .center,
                                                    children: [
                                                      Row(
                                                          mainAxisAlignment: MainAxisAlignment
                                                              .center,
                                                          children: [Text(
                                                              budgetEntryModel
                                                                  .entries!
                                                                  .elementAt(
                                                                  index)
                                                                  .title,
                                                              textAlign: TextAlign
                                                                  .center,
                                                              style: TextStyle(
                                                                  fontSize: 30 *
                                                                      MediaQuery
                                                                          .of(
                                                                          context)
                                                                          .textScaleFactor,
                                                                  fontWeight:
                                                                  FontWeight
                                                                      .bold,
                                                                  color: Theme
                                                                      .of(
                                                                      context)
                                                                      .textTheme
                                                                      .bodyText1!
                                                                      .color)),
                                                          ]),
                                                      Row(
                                                          mainAxisAlignment: MainAxisAlignment
                                                              .center,
                                                          children: [Text(
                                                              budgetEntryModel
                                                                  .entries!
                                                                  .elementAt(
                                                                  index)
                                                                  .description,
                                                              textAlign: TextAlign
                                                                  .center,
                                                              style: TextStyle(
                                                                  fontSize: 20 *
                                                                      MediaQuery
                                                                          .of(
                                                                          context)
                                                                          .textScaleFactor,
                                                                  color: Theme
                                                                      .of(
                                                                      context)
                                                                      .textTheme
                                                                      .bodyText1!
                                                                      .color))
                                                          ]),
                                                    ]),
                                                subtitle: Row(children: [
                                                  Container(
                                                    padding: EdgeInsets.symmetric(vertical: 5),
                                                    width: MediaQuery
                                                        .of(context)
                                                        .size
                                                        .width / 3.5,
                                                    child: Text(
                                                        budgetEntryModel
                                                            .entries!
                                                            .elementAt(index)
                                                            .payer,
                                                        style: TextStyle(
                                                            fontSize: 15 *
                                                                MediaQuery
                                                                    .of(
                                                                    context)
                                                                    .textScaleFactor,
                                                            color: Theme
                                                                .of(context)
                                                                .textTheme
                                                                .bodyText1!
                                                                .color)),),
                                                  Spacer(),
                                                  Container(
                                                    width: MediaQuery
                                                        .of(context)
                                                        .size
                                                        .width / 3.5,
                                                    child: Text(
                                                        "\$"+budgetEntryModel
                                                            .entries!
                                                            .elementAt(index)
                                                            .amount.toStringAsFixed(2),
                                                        textAlign: TextAlign
                                                            .center,
                                                        style: TextStyle(
                                                            fontSize: 25 *
                                                                MediaQuery
                                                                    .of(
                                                                    context)
                                                                    .textScaleFactor,
                                                            fontWeight:
                                                            FontWeight.bold,
                                                            color: Theme
                                                                .of(context)
                                                                .textTheme
                                                                .bodyText1!
                                                                .color)),),
                                                  Spacer(),
                                                  Container(
                                                      width: MediaQuery
                                                          .of(context)
                                                          .size
                                                          .width / 3.5,
                                                      child: Text(
                                                          budgetEntryModel
                                                              .entries!
                                                              .elementAt(index)
                                                              .payee,
                                                          textAlign: TextAlign
                                                              .right,
                                                          style: TextStyle(
                                                              fontSize: 15 *
                                                                  MediaQuery
                                                                      .of(
                                                                      context)
                                                                      .textScaleFactor,
                                                              color: Theme
                                                                  .of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)))
                                                ])),
                                          ),
                                          PopupMenuButton(
                                              color: Theme.of(context).textTheme.bodyText1!.color,
                                              onSelected: (value) {
                                                if(value==1) {
                                                  showDialog(
                                                      context: c,
                                                      builder: (BuildContext context) {
                                                        return _EditAlertBox(currentBudget!, currentAdventure!, budgetEntryModel, budgetEntryModel.entries!.elementAt(index), context);
                                                      }
                                                  );
                                                }
                                                if (value == 2) {
                                                  showDialog(
                                                      context: context,
                                                      builder: (BuildContext context) {
                                                    return AlertDialog(
                                                      backgroundColor:
                                                      Theme
                                                          .of(context)
                                                          .primaryColorDark,
                                                      title: Text("Confirm Removal",
                                                          style: TextStyle(
                                                              color: Theme
                                                                  .of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)),
                                                      content: Text(
                                                          "Are you sure you want to remove this budget item for definite?",
                                                          style: TextStyle(
                                                              color: Theme
                                                                  .of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color)),
                                                      actions: <Widget>[
                                                        TextButton(
                                                            onPressed: (){
                                                              Provider.of<BudgetEntryModel>(c,
                                                                  listen: false)
                                                                  .deleteBudgetEntry(
                                                                  budgetEntryModel.entries!.elementAt(index));
                                                              Provider.of<BudgetEntryModel>(c,
                                                                  listen: false).fetchAllEntries(currentBudget!);
                                                                Navigator.pop(context);},
                                                            child: Text("Remove",
                                                                style: TextStyle(
                                                                    color: Theme
                                                                        .of(context)
                                                                        .textTheme
                                                                        .bodyText1!
                                                                        .color))),
                                                        TextButton(
                                                          onPressed: () =>
                                                              Navigator.pop(context),
                                                          child: Text("Cancel",
                                                              style: TextStyle(
                                                                  color: Theme
                                                                      .of(context)
                                                                      .textTheme
                                                                      .bodyText1!
                                                                      .color)),
                                                        ),
                                                      ],
                                                    );
                                                  },);}},
                                              itemBuilder: (context) => [
                                                PopupMenuItem(
                                                    value: 1,
                                                    child: Row(
                                                      children: <Widget>[
                                                        Padding(
                                                          padding:
                                                          const EdgeInsets
                                                              .all(5),
                                                          child: Icon(Icons
                                                              .edit_rounded,color: Theme.of(context).textTheme.bodyText2!.color),
                                                        ),
                                                        Text("Edit", style: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color))
                                                      ],
                                                    )),
                                                PopupMenuItem(
                                                    value: 2,
                                                    child: Row(
                                                      children: <Widget>[
                                                        Padding(
                                                          padding:
                                                          const EdgeInsets
                                                              .all(5),
                                                          child: Icon(Icons
                                                              .delete,color: Theme.of(context).textTheme.bodyText2!.color),
                                                        ),
                                                        Text("Delete", style: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color))
                                                      ],
                                                    ))
                                              ]),
                                        ],
                                      ),
                                    )))),


                            );
          } else {
            return Center(
                child: Text("Well done! You owe no one money!",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery
                            .of(context)
                            .textScaleFactor,
                        color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color)));
          }
        });
  }
}

class _EditAlertBox extends StatefulWidget {
  late final Budget? b;
  late final Adventure? a;
  late final BudgetEntryModel? budgetEntryModel;
  late final BuildContext? context;
  late final BudgetEntry? be;

  _EditAlertBox(this.b, this.a, this.budgetEntryModel, this.be, this.context);

  @override
  EditAlertBox createState() => EditAlertBox(b!, a!,budgetEntryModel!, be!,context!);
}

class EditAlertBox extends State<_EditAlertBox> {
  Budget? currentBudget;
  int? selectedCategory;
  String? payer;
  String? payee;
  Adventure? currentAdventure;
  BudgetEntry? budgetE;
  BudgetEntryModel? budgetEntryModel;

  final otherController = TextEditingController();
  final amountController = TextEditingController();
  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final centsController = TextEditingController();

  List<UserProfile> users = List.empty();
  List<String>? userNames;
  List<String>? userNamesAndOther;

  EditAlertBox(Budget budget, Adventure a, BudgetEntryModel budgetEntryModel, BudgetEntry e, context) {
    this.currentBudget = budget;
    this.currentAdventure = a;
    this.budgetE = e;
    this.budgetEntryModel = budgetEntryModel;

    AdventureApi.getAttendeesOfAdventure(a.adventureId, context).then((value) {
      setState(() {
        users = value;
      });
      var temp1 = List<String>.filled(users.length, "", growable: true);
      temp1.removeRange(0, users.length);

      var temp2 = List<String>.filled(users.length, "", growable: true);
      temp2.removeRange(0, users.length);

      for (int i = 0; i < users.length; i++) {
        temp1.add(value
            .elementAt(i)
            .username);
      }
      for (int i = 0; i < users.length; i++) {
        temp2.add(value
            .elementAt(i)
            .username);
      }
      temp2.add("Other");

      setState(() {
        userNames = temp1;
        userNamesAndOther = temp2;

        //SETTING PAYEE
        for(int i = 0; i < userNamesAndOther!.length; i++) {
          if(userNamesAndOther!.elementAt(i).compareTo(budgetE!.payee) == 0) {
            payee = budgetE!.payee;
            otherController.text = "";
            break;
          }
          else {
            payee = "Other";
            otherController.text = budgetE!.payee;
          }
        }
      });
    });

    final catNames = [
      "ACCOMMODATION",
      "ACTIVITIES",
      "FOOD",
      "TRANSPORT",
      "OTHER"
    ];

    //SETTING THE CONTROLLERS
    titleController.text = budgetE!.title;
    descriptionController.text = budgetE!.description;
    payer = budgetE!.payer;
    if(budgetE!.amount.toString().contains('.')) {
      amountController.text = budgetE!.amount.toString().substring(0, budgetE!
          .amount
          .toString()
          .length-3);
      centsController.text = budgetE!.amount.toString().substring(budgetE!
          .amount
          .toString()
          .length-2,
          budgetE!.amount
              .toString()
              .length);
    }
    else {
      amountController.text = budgetE!.amount.toString().substring(0, budgetE!
          .amount
          .toString()
          .length);
      centsController.text = '00';
    }
    for(int i = 0; i < 4; i++) {
      if(catNames[i].compareTo(budgetE!.category) == 0) {
        selectedCategory = i+1;
      }
    }
  }

  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.8;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.9;
    }
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> categoryList = [
      Text(
        "Accommodation",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Activities",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Food",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Transport",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      ),
      Text(
        "Other",
        style: TextStyle(color: Theme
            .of(context)
            .textTheme
            .bodyText1!
            .color),
      )
    ];

    final categoryNames = [
      "ACCOMMODATION",
      "ACTIVITIES",
      "FOOD",
      "TRANSPORT",
      "OTHER"
    ];

      if (userNames == null || userNames!.length == 0 ||
          userNamesAndOther == null || userNamesAndOther!.length == 0) {
        return AlertDialog(
            backgroundColor: Theme
                .of(context)
                .primaryColorDark,
            content: Container(
                height: getSize(context),
                child: Center(
                    child: CircularProgressIndicator(
                        valueColor: new AlwaysStoppedAnimation<Color>(
                            Theme
                                .of(context)
                                .accentColor)))));
      }
      else {
        return AlertDialog(
            backgroundColor: Theme
                .of(context)
                .primaryColorDark,
            content: Container(
                height: getSize(context),
                child: Stack(
                    clipBehavior: Clip.none,
                    children: <Widget>[
                      Positioned(
                        right: -40.0,
                        top: -40.0,
                        child: InkResponse(
                          onTap: () {
                            Navigator.pop(context);
                          },
                          child: CircleAvatar(
                            child: Icon(Icons.close,
                                color: Theme
                                    .of(context)
                                    .primaryColorDark),
                            backgroundColor: Theme
                                .of(context)
                                .accentColor,
                          ),
                        ),
                      ),
                      Center(
                          child: Column(
                              children: <Widget>[
                                Text("Edit: " + budgetE!
                                    .title,
                                    textAlign: TextAlign
                                        .center,
                                    style: TextStyle(
                                      color: Theme
                                          .of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color,
                                      fontSize:
                                      25 * MediaQuery
                                          .of(context)
                                          .textScaleFactor,
                                      fontWeight: FontWeight
                                          .bold,
                                    )),
                                Spacer(),
                                Container(
                                  width: 300,
                                  padding: EdgeInsets
                                      .symmetric(
                                      horizontal:
                                      MediaQuery
                                          .of(context)
                                          .size
                                          .width *
                                          0.02),
                                  child: TextField(
                                      style: TextStyle(
                                          color: Theme
                                              .of(
                                              context)
                                              .textTheme
                                              .bodyText1!
                                              .color),
                                      controller: titleController,
                                      decoration: InputDecoration(
                                          hintStyle: TextStyle(
                                              color: Theme
                                                  .of(
                                                  context)
                                                  .textTheme
                                                  .bodyText2!
                                                  .color),
                                          filled: true,
                                          enabledBorder: InputBorder
                                              .none,
                                          errorBorder: InputBorder
                                              .none,
                                          disabledBorder: InputBorder
                                              .none,
                                          fillColor: Theme
                                              .of(
                                              context)
                                              .primaryColorLight,
                                          focusedBorder: OutlineInputBorder(
                                              borderSide: new BorderSide(
                                                  color: Theme
                                                      .of(
                                                      context)
                                                      .accentColor)),
                                          hintText: 'Title')),
                                ),
                                SizedBox(
                                    height: MediaQuery
                                        .of(context)
                                        .size
                                        .height *
                                        0.01),
                                Container(
                                  width: 300,
                                  padding: EdgeInsets
                                      .symmetric(
                                      horizontal:
                                      MediaQuery
                                          .of(context)
                                          .size
                                          .width *
                                          0.02),
                                  child: TextField(
                                      maxLength: 255,
                                      maxLines: 3,
                                      style: TextStyle(
                                          color: Theme
                                              .of(
                                              context)
                                              .textTheme
                                              .bodyText1!
                                              .color),
                                      controller: descriptionController,
                                      decoration: InputDecoration(
                                          hintStyle: TextStyle(
                                              color: Theme
                                                  .of(
                                                  context)
                                                  .textTheme
                                                  .bodyText2!
                                                  .color),
                                          filled: true,
                                          enabledBorder: InputBorder
                                              .none,
                                          errorBorder: InputBorder
                                              .none,
                                          disabledBorder: InputBorder
                                              .none,
                                          fillColor: Theme
                                              .of(
                                              context)
                                              .primaryColorLight,
                                          focusedBorder: OutlineInputBorder(
                                              borderSide: new BorderSide(
                                                  color: Theme
                                                      .of(
                                                      context)
                                                      .accentColor)),
                                          hintText: 'Description')),
                                ),
                                Spacer(),
                                Row(children: [
                                  Spacer(),
                                  DropdownButton<
                                      String>(
                                      dropdownColor: Theme
                                          .of(context)
                                          .primaryColorDark,
                                      hint: new Text(
                                          "Select a payer",
                                          style: TextStyle(
                                              color: Theme
                                                  .of(
                                                  context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color)),
                                      value: payer,
                                      onChanged: (String? newValue) {
                                        setState(() {
                                          payer =
                                          newValue!;
                                        });
                                      },
                                      items: userNames!
                                          .map((String user) {
                                        return new DropdownMenuItem<
                                            String>(
                                          value: user,
                                          child: new Text(
                                            user,
                                            style: TextStyle(
                                                color: Theme
                                                    .of(
                                                    context)
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
                                    width: 125,
                                    padding: EdgeInsets
                                        .symmetric(
                                        horizontal:
                                        MediaQuery
                                            .of(
                                            context)
                                            .size
                                            .width *
                                            0.02),
                                    child: TextField(
                                        inputFormatters: <
                                            TextInputFormatter>[
                                          FilteringTextInputFormatter
                                              .allow(
                                              RegExp(
                                                  r'[0-9]')),
                                        ],
                                        keyboardType: TextInputType
                                            .number,
                                        style: TextStyle(
                                            color: Theme
                                                .of(
                                                context)
                                                .textTheme
                                                .bodyText1!
                                                .color),
                                        controller: amountController,
                                        decoration: InputDecoration(
                                            hintStyle: TextStyle(
                                                color: Theme
                                                    .of(
                                                    context)
                                                    .textTheme
                                                    .bodyText2!
                                                    .color),
                                            filled: true,
                                            enabledBorder: InputBorder
                                                .none,
                                            errorBorder: InputBorder
                                                .none,
                                            disabledBorder: InputBorder
                                                .none,
                                            fillColor:
                                            Theme
                                                .of(
                                                context)
                                                .primaryColorLight,
                                            focusedBorder: OutlineInputBorder(
                                                borderSide: new BorderSide(
                                                    color:
                                                    Theme
                                                        .of(
                                                        context)
                                                        .accentColor)),
                                            hintText: 'Amount')),
                                  ),
                                  Container(
                                      width: 50,
                                      child: Text(".",
                                        textAlign: TextAlign
                                            .center,
                                        style: TextStyle(
                                            color: Theme
                                                .of(
                                                context)
                                                .textTheme
                                                .bodyText1!
                                                .color),)
                                  ),
                                  Container(
                                    width: 125,
                                    padding: EdgeInsets
                                        .symmetric(
                                        horizontal:
                                        MediaQuery
                                            .of(
                                            context)
                                            .size
                                            .width *
                                            0.02),
                                    child: TextField(
                                        inputFormatters: <
                                            TextInputFormatter>[
                                          FilteringTextInputFormatter
                                              .allow(
                                              RegExp(
                                                  r'[0-9]')),
                                        ],
                                        keyboardType: TextInputType
                                            .number,
                                        style: TextStyle(
                                            color: Theme
                                                .of(
                                                context)
                                                .textTheme
                                                .bodyText1!
                                                .color),
                                        controller: centsController,
                                        decoration: InputDecoration(
                                            hintStyle: TextStyle(
                                                color: Theme
                                                    .of(
                                                    context)
                                                    .textTheme
                                                    .bodyText2!
                                                    .color),
                                            filled: true,
                                            enabledBorder: InputBorder
                                                .none,
                                            errorBorder: InputBorder
                                                .none,
                                            disabledBorder: InputBorder
                                                .none,
                                            fillColor:
                                            Theme
                                                .of(
                                                context)
                                                .primaryColorLight,
                                            focusedBorder: OutlineInputBorder(
                                                borderSide: new BorderSide(
                                                    color:
                                                    Theme
                                                        .of(
                                                        context)
                                                        .accentColor)),
                                            hintText: 'Amount')),
                                  ),
                                  Spacer(),
                                ]),
                                Spacer(),
                                Row(children: [
                                  Spacer(),
                                  DropdownButton<
                                      String>(
                                      dropdownColor: Theme
                                          .of(context)
                                          .primaryColorDark,
                                      hint: new Text(
                                          "Select a payee",
                                          style: TextStyle(
                                              color: Theme
                                                  .of(
                                                  context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color)),
                                      value: payee,
                                      onChanged: (String? newValue) {
                                        setState(() {
                                          payee =
                                          newValue!;
                                        });
                                      },
                                      items: userNamesAndOther!
                                          .map((String user) {
                                        return new DropdownMenuItem<
                                            String>(
                                          value: user,
                                          child: new Text(
                                            user,
                                            style: TextStyle(
                                                color: Theme
                                                    .of(
                                                    context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color),
                                          ),
                                        );
                                      }).toList()),
                                  Spacer(),
                                ]),
                                SizedBox(
                                    height: MediaQuery
                                        .of(context)
                                        .size
                                        .height *
                                        0.01),
                                Container(
                                  width: 300,
                                  padding: EdgeInsets
                                      .symmetric(
                                      horizontal:
                                      MediaQuery
                                          .of(context)
                                          .size
                                          .width *
                                          0.02),
                                  child: TextField(
                                      enabled:
                                      payee != null &&
                                          payee!
                                              .compareTo(
                                              "Other") ==
                                              0,
                                      style: TextStyle(
                                          color: Theme
                                              .of(
                                              context)
                                              .textTheme
                                              .bodyText1!
                                              .color),
                                      controller: otherController,
                                      decoration: InputDecoration(
                                          hintStyle: TextStyle(
                                              color: Theme
                                                  .of(
                                                  context)
                                                  .textTheme
                                                  .bodyText2!
                                                  .color),
                                          filled: true,
                                          enabledBorder: InputBorder
                                              .none,
                                          errorBorder: InputBorder
                                              .none,
                                          disabledBorder: InputBorder
                                              .none,
                                          fillColor: Theme
                                              .of(
                                              context)
                                              .primaryColorLight,
                                          focusedBorder: OutlineInputBorder(
                                              borderSide: new BorderSide(
                                                  color: Theme
                                                      .of(
                                                      context)
                                                      .accentColor)),
                                          hintText: 'Insert name for Other')),
                                ),
                                Spacer(),
                                DropdownButton(
                                  dropdownColor: Theme
                                      .of(context)
                                      .primaryColorDark,
                                  hint: Text(
                                    "Select a category",
                                    style: TextStyle(
                                        color:
                                        Theme
                                            .of(
                                            context)
                                            .textTheme
                                            .bodyText1!
                                            .color),
                                  ),
                                  icon: Icon(Icons
                                      .arrow_drop_down,
                                      color:
                                      Theme
                                          .of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                  iconDisabledColor:
                                  Theme
                                      .of(context)
                                      .scaffoldBackgroundColor,
                                  iconEnabledColor: Theme
                                      .of(context)
                                      .accentColor,
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
                                            color: Theme
                                                .of(
                                                context)
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
                                            color: Theme
                                                .of(
                                                context)
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
                                            color: Theme
                                                .of(
                                                context)
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
                                            color: Theme
                                                .of(
                                                context)
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
                                            color: Theme
                                                .of(
                                                context)
                                                .textTheme
                                                .bodyText1!
                                                .color),
                                      ),
                                      value: 5,
                                    )
                                  ],
                                  onChanged: (int? value) {
                                    setState(() {
                                      selectedCategory =
                                          value;
                                    });
                                  },
                                ),
                                Spacer(),
                                Padding(
                                    padding: EdgeInsets
                                        .symmetric(
                                        horizontal:
                                        MediaQuery
                                            .of(
                                            context)
                                            .size
                                            .width *
                                            0.02),
                                    child: ElevatedButton(
                                        style: ElevatedButton
                                            .styleFrom(
                                            primary: Theme
                                                .of(
                                                context)
                                                .accentColor),
                                        child: Text(
                                            "Edit",
                                            style: TextStyle(
                                                color: Theme
                                                    .of(
                                                    context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color)),
                                        onPressed: () {
                                          if(payee!.compareTo('Other') == 0) {
                                            payee = otherController.text;
                                          }

                                          budgetEntryModel!.editBudgetEntry(
                                              currentBudget!,
                                              budgetE!,
                                              budgetE!.budgetEntryID,
                                              currentBudget!.id,
                                              UserApi.getInstance()
                                                  .getUserProfile()!
                                                  .userID,
                                              payer!,
                                              amountController.text + '.' + centsController.text ,
                                              titleController.text,
                                              descriptionController.text,
                                              payee!,
                                              categoryNames[selectedCategory! -
                                                  1],
                                              context);
                                          budgetEntryModel!
                                              .fetchAllReports(
                                              currentBudget!,
                                              UserApi
                                                  .getInstance()
                                                  .getUserProfile()!
                                                  .username);
                                          Navigator.pop(context);
                                        }
                                    )
                                )
                              ]
                          )
                      )
                    ]
                )
            )
        );
      }
  }
}


    class GetReport extends StatelessWidget {
  late final Budget? currentBudget;

  GetReport(Budget b) {
    this.currentBudget = b;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<BudgetEntryModel>(
        builder: (context, budgetReportModel, child) {
          if (budgetReportModel.reports == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme
                            .of(context)
                            .accentColor)));
          }
          if (budgetReportModel.reports!.length > 0) {
            return Column(
                mainAxisSize: MainAxisSize.max,
                children:[Expanded(
                flex: 2,
                child: ListView(children: [
                  ...List.generate(
                    budgetReportModel.reports!.length,
                        (index) =>
                        Card(
                            color: Theme
                                .of(context)
                                .primaryColorDark,
                            child: InkWell(
                                hoverColor:
                                Theme
                                    .of(context)
                                    .primaryColorLight,
                                child: Container(
                                  child: Row(
                                    children: <Widget>[
                                      Expanded(
                                        flex: 4,
                                        child: ListTile(
                                          title: Row(children: [
                                            Container(
                                              width: MediaQuery
                                                  .of(context)
                                                  .size
                                                  .width / 4,
                                              child: Text(
                                                  budgetReportModel.reports!
                                                      .elementAt(index).amount >
                                                      0
                                                      ? "You"
                                                      : budgetReportModel
                                                      .reports!
                                                      .elementAt(index)
                                                      .payeeName,
                                                  style: TextStyle(
                                                      fontSize: 12 *
                                                          MediaQuery
                                                              .of(
                                                              context)
                                                              .textScaleFactor,
                                                      fontWeight:
                                                      FontWeight.bold,
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color)),),
                                            Spacer(),
                                            Container(
                                              width: MediaQuery
                                                  .of(context)
                                                  .size
                                                  .width / 9,
                                              child:
                                              Text(
                                                  budgetReportModel.reports!
                                                      .elementAt(index).amount >
                                                      0
                                                      ? " owe "
                                                      : " owes ",
                                                  textAlign: TextAlign.center,
                                                  style: TextStyle(
                                                      fontSize: 10 *
                                                          MediaQuery
                                                              .of(
                                                              context)
                                                              .textScaleFactor,
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color)),),
                                            Spacer(),
                                            Container(
                                              width: MediaQuery
                                                  .of(context)
                                                  .size
                                                  .width / 7,
                                              child:
                                              Text(
                                                  budgetReportModel.reports!
                                                      .elementAt(index).amount >
                                                      0
                                                      ? "\$"+budgetReportModel
                                                      .reports!
                                                      .elementAt(index).amount
                                                      .toStringAsFixed(2)
                                                      : "\$"+(budgetReportModel
                                                      .reports!
                                                      .elementAt(index)
                                                      .amount * -1).toStringAsFixed(2),
                                                  textAlign: TextAlign.center,
                                                  style: TextStyle(
                                                      fontSize: 20 *
                                                          MediaQuery
                                                              .of(
                                                              context)
                                                              .textScaleFactor,
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color)),),
                                            Spacer(),
                                            Container(
                                              width: MediaQuery
                                                  .of(context)
                                                  .size
                                                  .width / 9,
                                              child: Text(
                                                  " to ",
                                                  textAlign: TextAlign.center,
                                                  style: TextStyle(
                                                      fontSize: 10 *
                                                          MediaQuery
                                                              .of(
                                                              context)
                                                              .textScaleFactor,
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color)),),
                                            Spacer(),
                                            Container(
                                              width: MediaQuery
                                                  .of(context)
                                                  .size
                                                  .width / 4,
                                              child: Text(
                                                  budgetReportModel.reports!
                                                      .elementAt(index).amount >
                                                      0
                                                      ? budgetReportModel
                                                      .reports!
                                                      .elementAt(index)
                                                      .payeeName
                                                      : "You",
                                                  textAlign: TextAlign.right,
                                                  style: TextStyle(
                                                      fontWeight:
                                                      FontWeight.bold,
                                                      fontSize: 12 *
                                                          MediaQuery
                                                              .of(
                                                              context)
                                                              .textScaleFactor,
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color)),)

                                          ]),
                                        ),
                                      ),
                                    ],
                                  ),
                                ))),

                  )
                ]))]);
          } else {
            return Center(
                child: Text("Well done! You owe no one money!",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery
                            .of(context)
                            .textScaleFactor,
                        color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color)));
          }
        });
  }
}
