import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Login.dart';






class RegisterCaller extends StatefulWidget {
  @override
  Register createState() => Register();
}

class Register extends State<RegisterCaller> {

  Future<RegisterUser>? _futureUser;
  final UserApi api = new UserApi();

  final firstNameController = TextEditingController();
  final lastNameController = TextEditingController();
  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  final phoneNumberController = TextEditingController();
  final usernameController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(title: Center(child: Text("Create an account",
            style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)
        )), backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
            child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(
                    width: 400.0,
                    child: TextField(
                        controller: firstNameController,
                        decoration: InputDecoration(
                            filled: true,
                            fillColor: Theme.of(context).primaryColorLight,
                            border: OutlineInputBorder(), hintText: 'First name')),
                  ),
                  SizedBox(height: 10),
                  SizedBox(
                    width: 400.0,
                    child: TextField(
                        controller: lastNameController,
                        decoration: InputDecoration(
                            filled: true,
                            fillColor: Theme.of(context).primaryColorLight,
                            border: OutlineInputBorder(), hintText: 'Last name')),
                  ),
                  SizedBox(height: 10),
                  SizedBox(
                    width: 400.0,
                    child: TextField(
                        controller: usernameController,
                        decoration: InputDecoration(
                            filled: true,
                            fillColor: Theme.of(context).primaryColorLight,
                            border: OutlineInputBorder(), hintText: 'Username')),
                  ),
                  SizedBox(height: 10),
                  SizedBox(
                    width: 400.0,
                    child: TextField(
                        controller: emailController,
                        decoration: InputDecoration(
                            filled: true,
                            fillColor: Theme.of(context).primaryColorLight,
                            border: OutlineInputBorder(), hintText: 'Email')),
                  ),
                  SizedBox(height: 10),
                  SizedBox(
                    width: 400.0,
                    child: TextField(
                        controller: phoneNumberController,
                        decoration: InputDecoration(
                            filled: true,
                            fillColor: Theme.of(context).primaryColorLight,
                            border: OutlineInputBorder(), hintText: 'Phone number')),
                  ),
                  SizedBox(height: 10),
                  SizedBox(
                    width: 400.0,
                    child: TextField(
                        controller: passwordController,
                        obscureText: true,
                        decoration: InputDecoration(
                            filled: true,
                            fillColor: Theme.of(context).primaryColorLight,
                            border: OutlineInputBorder(), hintText: 'Password')),
                  ),
                  SizedBox(height: 10),
                  ElevatedButton(
                      child: Text("Register",
                          style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                      style: ElevatedButton.styleFrom(
                        primary: Theme.of(context).accentColor,
                        padding: EdgeInsets.symmetric(horizontal: 50, vertical: 20),
                      ),
                      onPressed: () {
                        setState(() {
                          _futureUser = api.createUser(firstNameController.text,lastNameController.text,usernameController.text,emailController.text,phoneNumberController.text,passwordController.text);
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
                              text: 'Already have an account?  ',
                              style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color),
                            ),
                            new TextSpan(
                                text: 'Login here!',
                                style: new TextStyle(color: Theme.of(context).accentColor),
                                recognizer: new TapGestureRecognizer()
                                  ..onTap = () {
                                    Navigator.pushReplacement(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) => LoginCaller()),
                                    );
                                  })])
                  )]
            )));
  }
}