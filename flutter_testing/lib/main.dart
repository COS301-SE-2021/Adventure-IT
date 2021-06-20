

import 'package:flutter/material.dart';
import '/API/adventureAPI.dart';
import '/API/adventures.dart';
import '/API/budgetAPI.dart';
import '/API/budget.dart';

void main() => runApp(const MyApp());

/// This is the main application widget.
class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  static const String _login = 'Login';



  @override
  Widget build(BuildContext context) {


    return MaterialApp(
      title: _login,
      home: Scaffold(
        appBar: AppBar(title: const Text(_login)),
        body: const Login(),
      ),
    );
  }
}

/// This is the stateful widget that the main application instantiates.
class Login extends StatefulWidget {
  const Login({Key? key}) : super(key: key);

  @override
  State<Login> createState() => _Login();
}

////////////////////////////////////////////////////////////////////////////////////////////////////Login
/// This is the private State class that goes with MyStatefulWidget.
class _Login extends State<Login> {
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    String? _username;
    final emailField = TextField(
        obscureText: false,
        onSubmitted: (String value) {_username=value; },
        //style: style,
        decoration: InputDecoration(
            contentPadding: const EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
            hintText: "Username",
            border:
            OutlineInputBorder(borderRadius: BorderRadius.circular(32.0)))
    );

    final passwordField = TextField(
      obscureText: true,
      //style: style,
      decoration: InputDecoration(
          contentPadding: const EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
          hintText: "Password",
          border:
          OutlineInputBorder(borderRadius: BorderRadius.circular(32.0))),
    );

    final loginButton = Material(
      elevation: 5.0,
      borderRadius: BorderRadius.circular(30.0),
      color: const Color(0xff01A0C7),
      child: MaterialButton(
        minWidth: MediaQuery.of(context).size.width,
        padding: const EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => ViewAdventure()),
          );
        },
        child: const Text("Login",
          textAlign: TextAlign.center,
          //style: style.copyWith(
          //  color: Colors.white, fontWeight: FontWeight.bold)),
        ),
      ),
    );

    return Scaffold(
      body: SingleChildScrollView(
        child: Center(
          child: Container(
            color: Colors.white,
            child: Padding(
              padding: const EdgeInsets.all(36.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  SizedBox(
                    child: Image.asset(
                      "assets/adventure.PNG",
                        scale: 1.0,
                    ),
                  ),
                  const SizedBox(height: 45.0),
                  emailField,
                  const SizedBox(height: 25.0),
                  passwordField,
                  const SizedBox(
                    height: 35.0,
                  ),
                  loginButton,
                  const SizedBox(
                    height: 15.0,
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////viewAdventure
class ViewAdventure extends StatefulWidget {
  const ViewAdventure({Key? key}) : super(key: key);
  @override
  State<ViewAdventure> createState() => _ViewAdventure();
}

class _ViewAdventure extends State<ViewAdventure> {
  Future<List<Adventure>>? ownerAdventures;
  Future<List<Adventure>>? attendeeAdventures;

  void initState() {
    super.initState();
    ownerAdventures = AdventureApi.getOwnerAdventures();
    attendeeAdventures = AdventureApi.getAttendeeAdventures();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: Theme.of(context),
        home: Scaffold(
            appBar: AppBar(
                title: Text("Adventures")),
            body: _AdventureList(
                ownerAdventures: ownerAdventures,
                attendeeAdventures: attendeeAdventures)));
  }
}

class _AdventureList extends StatelessWidget {
  Future<List<Adventure>>? ownerAdventures;
  Future<List<Adventure>>? attendeeAdventures;
  _AdventureList({@required this.ownerAdventures,@required this.attendeeAdventures});
  @override
  Widget build(BuildContext context) {
    final backbutton = Material(
        elevation: 5.0,
        borderRadius: BorderRadius.circular(30.0),
        color: const Color(0xff01A0C7),
        child: MaterialButton(
            minWidth: MediaQuery
                .of(context)
                .size
                .width,
            padding: const EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => MyApp()),
              );
            },
            child: const Text("Logout",
              textAlign: TextAlign.center,)));
    final checkTrash=MaterialButton(
        color: Colors.blue,
        shape: CircleBorder(),
        onPressed: () {},
        child: Padding(
            padding: const EdgeInsets.all(100),
            child: Text(
              'Trash',
              style: TextStyle(color: Colors.white, fontSize: 24),
            )
        )
    );
    return Column(children: <Widget>[
      Container(
          alignment: Alignment.center,
          height: 100,
          child: Text("Your Adventures",
              style: TextStyle(fontSize: 35, fontWeight: FontWeight.bold))),
      Container(
          alignment: Alignment.center,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Created Adventures",
              style: TextStyle(fontSize: 20))),
      AdventureFutureBuilder(adventuresFuture: ownerAdventures),
      SizedBox(height: 50),
      Container(
          alignment: Alignment.center,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Shared Adventures",
              style: TextStyle(fontSize: 20))),
      AdventureFutureBuilder(adventuresFuture: attendeeAdventures),
    SizedBox(
    height: 20,
    ),checkTrash, backbutton]);
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
                          child: InkWell(
                            onTap:  (){},
                          child: ListTile(
                              trailing: IconButton(
                                  icon: Icon(Icons.more_vert),
                                  onPressed: () => Navigator.push(
                                    context,
                                    MaterialPageRoute(builder: (context) => ViewContainers(key,adventures.elementAt(index))),
                                  )),
                              dense: true,
                              title: Text(adventures.elementAt(index).name)),
                              hoverColor: Colors.blue)))



          ]));
          } else {
            return Center(child: Text("It seems you're not very adventurous..."));
          }
        });
  }
}
////////////////////////////////////////////////////////////////////////////////Containers
class ViewContainers extends StatefulWidget {
  Adventure? a;
  ViewContainers(Key? key, Adventure? a) : super(key: key)
  {
    this.a=a;
  }
  @override
  State<ViewContainers> createState() => _ViewContainers(a);
}

class _ViewContainers extends State<ViewContainers> {
  Adventure? a;
  Future<List<Budget>>? BudgetsForAdventure;

  _ViewContainers(Adventure? a)
  {
    this.a=a;
  }


  void initState() {
    super.initState();
    BudgetsForAdventure = BudgetApi.getBudgets(a);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: Theme.of(context),
        home: Scaffold(
            appBar: AppBar(
                title: Text("Items")),
            body: _ContainerList(
                BudgetsForAdventure: BudgetsForAdventure)));
  }
}

class _ContainerList extends StatelessWidget {
  Future<List<Budget>>? BudgetsForAdventure;
  _ContainerList({@required this.BudgetsForAdventure});
  final makeBudget=MaterialButton(
      color: Colors.blue,
      shape: CircleBorder(),
      onPressed: () {},
      child: Padding(
          padding: const EdgeInsets.all(100),
          child: Text(
            'Create Budget',
            style: TextStyle(color: Colors.white, fontSize: 24),
          )
      )
  );
  @override
  Widget build(BuildContext context) {
    final backbutton = Material(
        elevation: 5.0,
        borderRadius: BorderRadius.circular(30.0),
        color: const Color(0xff01A0C7),
        child: MaterialButton(
            minWidth: MediaQuery
                .of(context)
                .size
                .width,
            padding: const EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => ViewAdventure()),
              );
            },
            child: const Text("Back To Adventures",
              textAlign: TextAlign.center,)));
    return Column(children: <Widget>[
      Container(
          alignment: Alignment.center,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Budgets",
              style: TextStyle(fontSize: 20))),
      ContainerFutureBuilder(budgetFuture: BudgetsForAdventure),
      SizedBox(height: 50),
      Container(
          alignment: Alignment.center,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Itineraries",
              style: TextStyle(fontSize: 20))),
      ContainerFutureBuilder(budgetFuture: null),
      SizedBox(
        height: 20,
      ),
      Container(
          alignment: Alignment.center,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("Checklists",
              style: TextStyle(fontSize: 20))),
      ContainerFutureBuilder(budgetFuture: null),makeBudget,backbutton]);
  }
}

class ContainerFutureBuilder extends StatelessWidget {
  Future<List<Budget>>? budgetFuture;
  ContainerFutureBuilder({@required this.budgetFuture});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: budgetFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasData) {
            var budgets = snapshot.data as List<Budget>;
            print(budgets);
            return Expanded(
                child: ListView(children: [
                  ...List.generate(
                      budgets.length,
                          (index) => Card(
                          child: InkWell(
                              onTap:  (){},
                              child: ListTile(
                                  trailing: Icon(Icons.more_vert),
                                  dense: true,
                                  title: Text(budgets.elementAt(index).name)),
                              hoverColor: Colors.blue)))

                ]));
          } else {
            return Center(child: Text("There's nothing here"));
          }
        });
  }
}





