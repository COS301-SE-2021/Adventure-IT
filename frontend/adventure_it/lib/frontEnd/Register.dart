import 'package:adventure_it/api/userAPI.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'Login.dart';

class RegisterCaller extends StatefulWidget {
  @override
  Register createState() => Register();
}

class Register extends State<RegisterCaller> {
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
                child: Text("Create an account",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: SingleChildScrollView(
            child: Center(
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height * 0.15),
              SizedBox(
                width: 350,
                child: TextField(
                  style: TextStyle(color: Theme
                      .of(context)
                      .textTheme
                      .bodyText1!
                      .color),
                    controller: firstNameController,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(),
                        hintText: 'First name')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height * 0.01),
              SizedBox(
                width: 350,
                child: TextField(
                    style: TextStyle(color: Theme
                        .of(context)
                        .textTheme
                        .bodyText1!
                        .color),
                    controller: lastNameController,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(),
                        hintText: 'Last name')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height * 0.01),
              SizedBox(
                width: 350,
                child: TextField(
                    style: TextStyle(color: Theme
                        .of(context)
                        .textTheme
                        .bodyText1!
                        .color),
                    controller: usernameController,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(),
                        hintText: 'Username')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height * 0.01),
              SizedBox(
                width: 350,
                child: TextField(
                    style: TextStyle(color: Theme
                        .of(context)
                        .textTheme
                        .bodyText1!
                        .color),
                    controller: emailController,
                    decoration: InputDecoration(
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(),
                        hintText: 'Email')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height * 0.01),
              SizedBox(
                width: 350,
                child: TextField(
                    style: TextStyle(color: Theme
                        .of(context)
                        .textTheme
                        .bodyText1!
                        .color),
                    controller: passwordController,
                    obscureText: true,
                    decoration: InputDecoration(
                        filled: true,
                        enabledBorder: InputBorder.none,
                        errorBorder: InputBorder.none,
                        disabledBorder: InputBorder.none,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(),
                        hintText: 'Password')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height * 0.01),
              SizedBox(
                width: 350,
                child: TextField(
                    style: TextStyle(color: Theme
                        .of(context)
                        .textTheme
                        .bodyText1!
                        .color),
                    controller: passwordCheckController,
                    obscureText: true,
                    decoration: InputDecoration(
                        filled: true,
                        enabledBorder: InputBorder.none,
                        errorBorder: InputBorder.none,
                        disabledBorder: InputBorder.none,
                        fillColor: Theme.of(context).primaryColorLight,
                        border: OutlineInputBorder(),
                        hintText: 'Confirm Password')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height * 0.03),
              ElevatedButton(
                  child: Text("Register",
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
                        passwordController.text, passwordCheckController.text);
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
              SizedBox(height: MediaQuery.of(context).size.height * 0.02),
              RichText(
                  text: new TextSpan(children: [
                new TextSpan(
                  text: 'Already have an account?  ',
                  style: new TextStyle(
                      color: Theme.of(context).textTheme.bodyText1!.color),
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
                      })
              ])),
                SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                /*Row(
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(left: 50, right: 0, top: 0, bottom: 0),
                      child: Align(
                          alignment: Alignment.bottomLeft,
                          child: Container(
                          decoration: BoxDecoration(
                              color: Theme.of(context).accentColor,
                              shape: BoxShape.circle),
                          child: IconButton(
                              onPressed: () {
                                Navigator.pushReplacement(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) =>
                                            LoginCaller()));
                              },
                              icon:
                              const Icon(Icons.arrow_back_ios_new_rounded),
                              color: Theme.of(context).primaryColorDark))),
                    ),
                  ],
                )*/
            ]))));
  }
}
