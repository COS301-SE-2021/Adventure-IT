import 'package:adventure_it/api/loginUser.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'dart:developer';
import 'package:flutter/foundation.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Profile.dart';
import 'Register.dart';

class LoginCaller extends StatefulWidget {
  @override
  Login createState() => Login();
}

class Login extends State<LoginCaller> {
  Future<LoginUser>? _futureUser;
  final UserApi api = new UserApi();
  var storage = FlutterSecureStorage();
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Login",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: SingleChildScrollView(
            child: Center(
                child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            SizedBox(height: MediaQuery.of(context).size.height * 0.13),
            Container(
              width: MediaQuery.of(context).size.width / 2,
              height: MediaQuery.of(context).size.height / 4,
              child: CircleAvatar(
                radius: 90,
                backgroundImage: ExactAssetImage('assets/adventure.PNG'),
              ),
              decoration: new BoxDecoration(
                shape: BoxShape.circle,
                border: new Border.all(
                  color: Theme.of(context).accentColor,
                  width: 3.0,
                ),
              ),
            ),
            SizedBox(height: MediaQuery.of(context).size.height * 0.05),
            SizedBox(
              width: 350,
              child: TextField(
                  controller: usernameController,
                  style: TextStyle(
                      color: Theme.of(context).textTheme.bodyText1!.color),
                  decoration: InputDecoration(
                      hintStyle: TextStyle(
                          color: Theme.of(context).textTheme.bodyText2!.color),
                      filled: true,
                      fillColor: Theme.of(context).primaryColorLight,
                      focusedBorder: OutlineInputBorder(
                          borderSide: new BorderSide(
                              color: Theme.of(context).accentColor)),
                      hintText: 'Username')),
            ),
            SizedBox(height: MediaQuery.of(context).size.height * 0.02),
            SizedBox(
              width: 350,
              child: TextField(
                  controller: passwordController,
                  style: TextStyle(
                      color: Theme.of(context).textTheme.bodyText1!.color),
                  obscureText: true,
                  decoration: InputDecoration(
                      hintStyle: TextStyle(
                          color: Theme.of(context).textTheme.bodyText2!.color),
                      filled: true,
                      fillColor: Theme.of(context).primaryColorLight,
                      focusedBorder: OutlineInputBorder(
                          borderSide: new BorderSide(
                              color: Theme.of(context).accentColor)),
                      hintText: 'Password')),
            ),
            SizedBox(height: MediaQuery.of(context).size.height * 0.05),
            ElevatedButton(
                child: Text("Log In",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color)),
                style: ElevatedButton.styleFrom(
                  primary: Theme.of(context).accentColor,
                  padding: EdgeInsets.symmetric(
                      horizontal: MediaQuery.of(context).size.width * 0.05,
                      vertical: MediaQuery.of(context).size.height * 0.01),
                ),
                onPressed: () async {
                  // setState(() {
                  //   _futureUser = api.loginUser(
                  //       usernameController.text, passwordController.text);
                  // });
                  // Navigator.pushReplacement(
                  //   context,
                  //   MaterialPageRoute(
                  //       builder: (context) => HomepageStartupCaller()),
                  // );
                  var username = usernameController.text;
                  var password = passwordController.text;

                  var jwt = await api.attemptLogIn(username, password);
                  debugPrint("JWT Token: ");
                  if (jwt != null) {
                    debugPrint(jwt);
                    storage.write(key: "jwt", value: jwt);
                    setState(() {
                      _futureUser = api.loginUser(
                          usernameController.text, passwordController.text);
                    });
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  } else {
                    debugPrint("No token");
                    displayDialog(context, "An Error Occurred",
                        "No account was found matching that username and password");
                  }
                }),
            SizedBox(height: MediaQuery.of(context).size.height * 0.02),
            RichText(
                text: new TextSpan(children: [
              new TextSpan(
                text: 'Don\'t have an account?  ',
                style: new TextStyle(
                    color: Theme.of(context).textTheme.bodyText1!.color),
              ),
              new TextSpan(
                  text: 'Register here!',
                  style: new TextStyle(color: Theme.of(context).accentColor),
                  recognizer: new TapGestureRecognizer()
                    ..onTap = () {
                      Navigator.pushReplacement(
                        context,
                        MaterialPageRoute(
                            builder: (context) => RegisterCaller()),
                      );
                    })
            ])),
            SizedBox(height: MediaQuery.of(context).size.height * 0.02),
            RichText(
                text: new TextSpan(
                    text: 'Forgot Password?',
                    style: new TextStyle(color: Theme.of(context).accentColor),
                    recognizer: new TapGestureRecognizer()
                      ..onTap = () {
                        Navigator.pushReplacement(
                          context,
                          MaterialPageRoute(
                              builder: (context) => ProfileCaller()),
                        );
                      }))
          ],
        ))));
  }
}

void displayDialog(BuildContext context, String title, String text) =>
    showDialog(
      context: context,
      builder: (context) =>
          AlertDialog(title: Text(title), content: Text(text)),
    );
