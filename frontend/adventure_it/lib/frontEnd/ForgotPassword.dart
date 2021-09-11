import 'package:adventure_it/api/userAPI.dart';
import 'package:flutter/material.dart';
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
        body: Center(
            child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisSize: MainAxisSize.max,
                children: <Widget>[
              Spacer(),
              Container(
                child: Center(
                    child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                  SizedBox(
                    width: 250,
                    child: TextField(
                        controller: emailController,
                        decoration: InputDecoration(
                            filled: true,
                            fillColor: Theme.of(context).primaryColorLight,
                            border: OutlineInputBorder(),
                            hintText: 'Email')),
                  ),
                  SizedBox(width: 10),
                  Container(
                    decoration: BoxDecoration(
                        color: Theme.of(context).accentColor,
                        shape: BoxShape.circle),
                    child: IconButton(
                        onPressed: () async {
                          bool success = await this.api.registerKeycloakUser(
                              firstNameController.text,
                              lastNameController.text,
                              usernameController.text,
                              emailController.text,
                              passwordController.text,
                              passwordCheckController.text);
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
                                .displayDialog(
                                    context, "Error!", this.api.message)
                                .then((val) {
                              Navigator.pushReplacement(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => RegisterCaller()),
                              );
                            });
                          }
                        },
                        icon: const Icon(Icons.send_rounded),
                        iconSize: 30,
                        color: Theme.of(context).primaryColorDark),
                  )
                ])),
              ),
              Spacer(),
              Row(children: [
                Expanded(
                  child: Container(
                    decoration: BoxDecoration(
                        color: Theme.of(context).accentColor,
                        shape: BoxShape.circle),
                    child: IconButton(
                        onPressed: () {
                          Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(
                                builder: (context) => LoginCaller()),
                          );
                        },
                        icon: const Icon(Icons.arrow_back_ios_new_rounded),
                        iconSize: 30,
                        color: Theme.of(context).primaryColorDark),
                  ),
                ),
                Expanded(child: Container()),
                Expanded(flex: 1, child: Container()),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ])));
  }
}
