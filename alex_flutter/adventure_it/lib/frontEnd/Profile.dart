import 'dart:html';

import 'package:adventure_it/api/loginUser.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

class ProfileCaller extends StatefulWidget {
  @override
  Profile createState() => Profile();
}

class Profile extends State<ProfileCaller> {
  Future<LoginUser>? _futureUser;
  final UserApi api = new UserApi();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).scaffoldBackgroundColor,
      appBar: AppBar(title: Center(child: Text("Profile",
          style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)
      )), backgroundColor: Theme.of(context).primaryColorDark),
      body: Row(
        children: [ Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            Container(
              width: 200,
              height: 100,
              child: CircleAvatar(
                radius: 50,
                backgroundImage: ExactAssetImage('assets/adventure.PNG'),
              ),
              decoration: new BoxDecoration(
              shape: BoxShape.circle,
              border: new Border.all(
              color: Theme.of(context).accentColor,
              width: 3.0,
            ),),),
          ]),
        Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            SizedBox(
            width: 400.0,
            child: Text("Full name",
                style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize: 30)),
            ),
            SizedBox(
              width: 400.0,
              child: Text("Username",
                style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize: 20)),
            ),
            SizedBox(height: 25),
            SizedBox(
              width: 400.0,
              child: Text("Email address",
                style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize:20)),
            ),
            SizedBox(
              width: 400.0,
              child: Text("Phone number",
                  style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize:20)),
            ),
            SizedBox(height: 20),
        ]),
        Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              ElevatedButton(
                  child: Text("My Documents",
                      style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                  style: ElevatedButton.styleFrom(
                    primary: Theme.of(context).accentColor,
                    padding: EdgeInsets.symmetric(horizontal: 50, vertical: 20),
                  ),
                  onPressed: () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
              }),
              SizedBox(height: 20),
              ElevatedButton(
                  child: Text("Upload Document",
                      style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                  style: ElevatedButton.styleFrom(
                    primary: Theme.of(context).accentColor,
                    padding: EdgeInsets.symmetric(horizontal: 50, vertical: 20),
                  ),
                  onPressed: () {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  }),
              FlatButton(
                  onPressed: () => {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                      builder: (context) => HomepageStartupCaller()),
                  )},
                  color: Theme.of(context).accentColor,
                  padding: EdgeInsets.symmetric(horizontal: 50, vertical: 20),
                  child: Row(
                    children: <Widget>[
                      Icon(Icons.settings, color: Theme.of(context).textTheme.bodyText1!.color),
                      Text("Edit Profile",
                        style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)) ],
                  ))]
        ),])
    );
  }
}