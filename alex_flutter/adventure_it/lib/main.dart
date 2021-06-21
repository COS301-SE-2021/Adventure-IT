import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';

import 'api/budget.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: ThemeData(primaryColor: Colors.black), home: Login());
  }
}

class Login extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Center(child: Text("Login"))),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(
                child: Image.asset(
                  "assets/adventure.PNG",
                  scale: 1.0,
                ),
              ),
              SizedBox(
                width: 400.0,
                child: TextField(
                  decoration: InputDecoration(
                      border: OutlineInputBorder(), hintText: 'Email')),
              ),
              SizedBox(
                width: 400.0,
                child: TextField(
                  obscureText: true,
                  decoration: InputDecoration(
                      border: OutlineInputBorder(), hintText: 'Password')),
              ),
              ElevatedButton(
                  child: Text("Log In"),
                  onPressed: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  })
            ]));
  }
}

class HomepageStartupCaller extends StatefulWidget {
  @override
  HomePage createState() => HomePage();
}

class HomePage extends State<HomepageStartupCaller> {
  Future<List<Adventure>>? ownerAdventuresFuture;
  Future<List<Adventure>>? attendeeAdventuresFuture;

  void initState() {
    super.initState();
    ownerAdventuresFuture = AdventureApi.getAdventuresByOwner();
    attendeeAdventuresFuture = AdventureApi.getAdventuresByAttendee();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: Theme.of(context),
        home: Scaffold(
            appBar: AppBar(
                centerTitle: true,
                title: Text("Home Page"),
                leading: IconButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    icon: const Icon(Icons.logout))),
            body: HomePage_Pages(
                ownerAdventuresFuture: ownerAdventuresFuture,
                attendeeAdventuresFuture: attendeeAdventuresFuture)));
  }
}

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
          child: Text("Created Adventures",
              style: TextStyle(fontSize: 20))),
      AdventureFutureBuilder(adventuresFuture: ownerAdventuresFuture),
      SizedBox(height: 50),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Shared Adventures",
              style: TextStyle(fontSize: 20))),
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
                              Navigator.push(
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
                    title: Text('Budgets for ' + adventure.name),
                    leading: IconButton(
                        onPressed: () {
                          Navigator.pop(context);
                        },
                        icon: Icon(Icons.arrow_back))),
                body: Stack( children: <Widget> [ListView(children: [
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
                                                  adventure: this.adventure)));
                                },
                              ))))
                ]), Align(alignment: Alignment.bottomCenter,child:ElevatedButton(
                child: Text("Create Budget"),
                onPressed: () {
                  BudgetApi.createBudget("New Budget");
                }))]),
                floatingActionButton: FloatingActionButton(
                    onPressed: () {
                      Future<List<Budget>>? deletedBudgets =
                          BudgetApi.getDeletedBudgets();
                      Navigator.push(
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
                    title: Text('Deleted Budgets'),
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
