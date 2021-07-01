import 'dart:html';

import 'package:adventure_it/api/loginUser.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Register.dart';

class LoginCaller extends StatefulWidget {
  @override
  Login createState() => Login();
}

class Login extends State<LoginCaller> {
  Future<LoginUser>? _futureUser;
  final UserApi api = new UserApi();

  final usernameController = TextEditingController();
  final passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(title: Center(child: Text("Login",
          style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)
        )), backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Container(
                width: 500,
                height: 250,
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
              SizedBox(height: 10),
              SizedBox(
                width: 400.0,
                child: TextField(
                    controller: usernameController,
                    style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color),
                    decoration: InputDecoration(
                        hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color),
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        focusedBorder: OutlineInputBorder( borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Username')),
              ),
              SizedBox(height: 10),
              SizedBox(
                width: 400.0,
                child: TextField(
                    controller: passwordController,
                    style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color),
                    obscureText: true,
                    decoration: InputDecoration(
                        hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color),
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        focusedBorder: OutlineInputBorder(borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Password')),
              ),
              SizedBox(height: 10),
              ElevatedButton(
                  child: Text("Log In",
                    style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                  style: ElevatedButton.styleFrom(
                    primary: Theme.of(context).accentColor,
                    padding: EdgeInsets.symmetric(horizontal: 50, vertical: 20),
                  ),
                  onPressed: () {
                    setState(() {
                      _futureUser = api.loginUser(usernameController.text, passwordController.text);
                    });
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  }),
              SizedBox(height: 10),
              RichText(
                text: new TextSpan(
                  children: [
                  new TextSpan(
                    text: 'Don\'t have an account?  ',
                    style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color),
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
                  })])
              )]
        )));
  }
}
