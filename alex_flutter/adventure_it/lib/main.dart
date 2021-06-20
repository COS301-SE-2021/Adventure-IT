import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';

import 'package:flutter/material.dart';

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
              TextField(
                  decoration: InputDecoration(
                      border: OutlineInputBorder(), hintText: 'Email')),
              TextField(
                  obscureText: true,
                  decoration: InputDecoration(
                      border: OutlineInputBorder(), hintText: 'Password')),
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
          child: Text("adventures I have created",
              style: TextStyle(fontSize: 20))),
      AdventureFutureBuilder(adventuresFuture: ownerAdventuresFuture),
      // Row(children: <Widget>[
      //   Expanded(
      //     child: Container(
      //         margin: EdgeInsets.all(10.0),
      //         padding: EdgeInsets.all(10.0),
      //         height: 50,
      //         color: Colors.blue,
      //         alignment: Alignment.centerLeft,
      //         child: Text("Test",
      //             style: TextStyle(
      //                 fontWeight: FontWeight.bold, color: Colors.white))),
      //   ),
      // ]),
      // Row(children: <Widget>[
      //   Expanded(
      //     child: Container(
      //         margin: EdgeInsets.only(bottom: 5.0, left: 10.0, right: 10.0),
      //         padding: EdgeInsets.all(10.0),
      //         height: 50,
      //         color: Colors.blue,
      //         alignment: Alignment.centerLeft,
      //         child: Text("Test",
      //             style: TextStyle(
      //                 fontWeight: FontWeight.bold, color: Colors.white))),
      //   ),
      // ]),
      SizedBox(height: 50),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left: 20.0),
          child: Text("adventures I am attending",
              style: TextStyle(fontSize: 20))),
      AdventureFutureBuilder(adventuresFuture: attendeeAdventuresFuture),
      // Row(children: <Widget>[
      //   Expanded(
      //     child: Container(
      //         margin: EdgeInsets.all(10.0),
      //         padding: EdgeInsets.all(10.0),
      //         height: 50,
      //         color: Colors.blue,
      //         alignment: Alignment.centerLeft,
      //         child: Text("Test",
      //             style: TextStyle(
      //                 fontWeight: FontWeight.bold, color: Colors.white))),
      //   ),
      // ]),
      // Row(children: <Widget>[
      //   Expanded(
      //     child: Container(
      //         margin: EdgeInsets.only(bottom: 5.0, left: 10.0, right: 10.0),
      //         padding: EdgeInsets.all(10.0),
      //         height: 50,
      //         color: Colors.blue,
      //         alignment: Alignment.centerLeft,
      //         child: Text("Test",
      //             style: TextStyle(
      //                 fontWeight: FontWeight.bold, color: Colors.white))),
      //   ),
      // ]),
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
                          title: Text(adventures.elementAt(index).name))))
            ]));
          } else {
            return Center(child: Text("Something went wrong"));
          }
        });
  }
}
