import 'package:flutter/material.dart';
import 'package:flutter_testing/API/adventureAPI.dart';
import 'package:flutter_testing/API/adventures.dart';

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

class ViewAdventure extends StatefulWidget {
  const ViewAdventure({Key? key}) : super(key: key);

  @override
  State<ViewAdventure> createState() => _ViewAdventure();
}

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
              height: 155.0,
              child: Image.asset(
                "assets/adventure.PNG",
                fit: BoxFit.contain,
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

class _ViewAdventure extends State<ViewAdventure> {
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  Future<List<Adventure>>? adventuresFuture;

  @override
  void initState() {
    super.initState();

    adventuresFuture = AdventureApi.getAllAdventures();
  }


  @override
  Widget build(BuildContext context) {
    final backbutton = Material(
        elevation: 5.0,
        borderRadius: BorderRadius.circular(30.0),
        color: const Color(0xff01A0C7),
        child: MaterialButton(
            minWidth: MediaQuery.of(context).size.width,
            padding: const EdgeInsets.fromLTRB(20.0, 15.0, 20.0, 15.0),
            onPressed: () {
              Navigator.pop(
                context
              );
            },
            child: const Text("Logout",
              textAlign: TextAlign.center,)));
    return FutureBuilder(
        future: adventuresFuture,
        builder: (context, snapshot) {
          print(snapshot);
          if(snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          if(snapshot.hasData) {
            var adventures = snapshot.data as List<Adventure>;
            return Scaffold(
              appBar: AppBar(
                title: Text("View Adventures"),
              ),
              body: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    Expanded(
                      child: ListView(
                        children: [
                          ...List.generate(adventures.length, (index) => Text(adventures.elementAt(index).description))
                        ],
                      ),
                    ),            backbutton,

                  ]
              ),
                // This trailing comma makes auto-formatting nicer for build methods.
            )
            );
          } else
            return Center(child: Text("Something went wrong"));
        }
    );
  }
}