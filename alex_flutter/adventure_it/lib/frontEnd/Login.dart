import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';

class Login extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(title: Center(child: Text("Login")), backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(
                child: Image.asset(
                  "assets/adventure.PNG",
                  scale: 2.0,
                ),
              ),
              SizedBox(height: 50),
              SizedBox(
                width: 400.0,
                child: TextField(
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(), hintText: 'Username')),
              ),
              SizedBox(height: 20),
              SizedBox(
                width: 400.0,
                child: TextField(
                    obscureText: true,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(), hintText: 'Password')),
              ),
              SizedBox(height: 20),
              ElevatedButton(
                  child: Text("Log In"),
                  style: ElevatedButton.styleFrom(
                    primary: Theme.of(context).primaryColor,
                    padding: EdgeInsets.symmetric(horizontal: 50, vertical: 20),
                  ),
                  onPressed: () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  }),
              SizedBox(height: 50),
              RichText(
                text: new TextSpan(
                  children: [
                  new TextSpan(
                    text: 'Don\'t have an account?  ',
                    style: new TextStyle(color: Colors.black),
                  ),
                new TextSpan(
                  text: 'Register here!',
                  style: new TextStyle(color: Colors.blue),
                  recognizer: new TapGestureRecognizer()
                  ..onTap = () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  })])
              )]
        )));
  }
}
