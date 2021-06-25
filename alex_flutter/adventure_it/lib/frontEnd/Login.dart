import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';

class Login extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(title: Center(child: Text("Login")), backgroundColor: Theme.of(context).primaryColorDark),
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
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  })
            ]));
  }
}
