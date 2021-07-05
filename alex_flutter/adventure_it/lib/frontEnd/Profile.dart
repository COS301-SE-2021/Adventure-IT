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
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            SizedBox(
            width: 400.0,
            child: Text("Username",
                style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize: 30)),
          ),
        ])]
      )
    );
  }
}