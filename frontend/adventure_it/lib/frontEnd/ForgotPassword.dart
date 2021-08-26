import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/constants.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Login.dart';
import 'Register.dart';

class ForgotPasswordCaller extends StatefulWidget {
  @override
  ForgotPassword createState() => ForgotPassword();
}

class ForgotPassword extends State<ForgotPasswordCaller> {
  final UserApi api = UserApi.getInstance();

  final firstNameController = TextEditingController();
  final lastNameController = TextEditingController();
  final emailController = TextEditingController();
  final passwordController = TextEditingController();
  final phoneNumberController = TextEditingController();
  final usernameController = TextEditingController();
  final passwordCheckController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Forgot Password",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: SingleChildScrollView(
            child: Center(
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height * 0.25),
              SizedBox(
                width: 350,
                child: TextField(
                    controller: emailController,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(),
                        hintText: 'Email')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height * 0.05),
              ElevatedButton(
                  child: Text("Send Password Reset Link",
                      style: new TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color)),
                  style: ElevatedButton.styleFrom(
                    primary: Theme.of(context).accentColor,
                    padding: EdgeInsets.symmetric(
                        horizontal: MediaQuery.of(context).size.width * 0.05,
                        vertical: MediaQuery.of(context).size.width * 0.01),
                  ),
                  onPressed: () async {
                    bool success = await this.api.registerKeycloakUser(
                        firstNameController.text,
                        lastNameController.text,
                        usernameController.text,
                        emailController.text,
                        passwordController.text,passwordCheckController.text);
                    if (success) {
                      this
                          .api
                          .displayDialog(context, "Success!",
                              "Please check your email inbox for a verification link")
                          .then((val) {
                        Navigator.pushReplacement(
                          context,
                          MaterialPageRoute(
                              builder: (context) => LoginCaller()),
                        );
                      });
                    } else {
                      this
                          .api
                          .displayDialog(context, "Error!", this.api.message)
                          .then((val) {
                        Navigator.pushReplacement(
                          context,
                          MaterialPageRoute(
                              builder: (context) => RegisterCaller()),
                        );
                      });
                    }
                  }),
              SizedBox(height: MediaQuery.of(context).size.height * 0.01),
              ElevatedButton(
                  child: Text("Cancel",
                      style: new TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color)),
                  style: ElevatedButton.styleFrom(
                    primary: Theme.of(context).accentColor,
                    padding: EdgeInsets.symmetric(
                        horizontal: MediaQuery.of(context).size.width * 0.05,
                        vertical: MediaQuery.of(context).size.width * 0.01),
                  ),
                  onPressed: () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(builder: (context) => LoginCaller()),
                    );
                  }),
              SizedBox(height: MediaQuery.of(context).size.height * 0.01),
            ]))));
  }
}
