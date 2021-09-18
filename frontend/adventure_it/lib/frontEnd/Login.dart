import 'package:adventure_it/api/locationAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/frontEnd/ForgotPassword.dart';
import 'package:flutter/material.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:theme_provider/theme_provider.dart';
import 'ForgotPassword.dart';
import 'HomepageStartup.dart';
import 'Register.dart';
import 'package:location/location.dart';

class LoginCaller extends StatefulWidget {
  @override
  Login createState() => Login();
}

class Login extends State<LoginCaller> {
  final UserApi api = UserApi.getInstance();

  // TODO: Check if an auth token is present, if not display login screen, else just go to homepage
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
            SizedBox(height: MediaQuery.of(context).size.height * 0.10),
            Container(
              width: 250,
              height: MediaQuery.of(context).size.height / 3,
              decoration: new BoxDecoration(
                shape: BoxShape.circle,
                image: new DecorationImage(
                    fit: BoxFit.contain,
                    image: new AssetImage("assets/logo.png")),
                // border: new Border.all(
                //   color: Theme.of(context).accentColor,
                //   width: 3.0,
                // ),
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
                  final username = usernameController.text;
                  final password = passwordController.text;

                  final success = await api.logIn(username, password);

                  print(UserApi.getInstance().theme!);
                  if(UserApi.getInstance().theme!) {
                    ThemeProvider.controllerOf(context).setTheme('light_theme');
                  }
                  else {
                    ThemeProvider.controllerOf(context).setTheme('dark_theme');
                  }

                  if (success == true) {
                    bool serviceEnabled;
                    PermissionStatus permissionGranted;
                    Location location = Location();
                    serviceEnabled = await location.serviceEnabled();
                    if (!serviceEnabled) {
                      serviceEnabled = await location.requestService();
                    }
                    permissionGranted = await location.hasPermission();
                    if (permissionGranted == PermissionStatus.denied) {
                      permissionGranted = await location.requestPermission();
                    }
                    LocationData? currentLocation;
                    if (permissionGranted == PermissionStatus.granted &&
                        serviceEnabled) {
                      location.changeSettings(accuracy: LocationAccuracy.high);
                      location.getLocation().then((value) {
                        currentLocation = value;
                        LocationApi.setCurrentLocation(currentLocation!,context);
                      });
                    }

                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  } else {
                    api.displayDialog(context, "Oops!", api.message);
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
                              builder: (context) => ForgotPasswordCaller()),
                        );
                      }))
          ],
        ))));
  }
}
