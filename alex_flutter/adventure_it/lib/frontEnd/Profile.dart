import 'dart:html';

import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

class ProfileCaller extends StatefulWidget {
  @override
  Profile createState() => Profile();
}

class Profile extends State<ProfileCaller> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).scaffoldBackgroundColor,
      appBar: AppBar(title: Center(child: Text("Profile",
          style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)
      )), backgroundColor: Theme.of(context).primaryColorDark),
      body: Row(
        children: [ Column(
          //ProfileFutureBuilderCaller();
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
          ProfileFutureBuilderCaller(),
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
        /*Column(
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
                  ))*/]
        ),);
  }
}

class ProfileFutureBuilderCaller extends StatefulWidget {
  @override
  ProfileFutureBuilder createState() => ProfileFutureBuilder();
}

class ProfileFutureBuilder extends State<ProfileFutureBuilderCaller> {
    Future<UserProfile>? userFuture;
  final UserApi api = new UserApi();

  @override
  void initState() {
    super.initState();
    userFuture = UserApi.getUserByUUID("69e8eb21-eb63-4c83-9187-181a648bb759");
  }

    @override
    Widget build(BuildContext context) {
      return FutureBuilder(
          future: userFuture,
          builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator(
              valueColor: new AlwaysStoppedAnimation<Color>(Theme
                  .of(context)
                  .accentColor)));
        }
        print(snapshot.data);
        if (snapshot.hasData) {
          var user = snapshot.data as UserProfile;

          return Expanded(
            flex: 4,
            child: ListTile(
              title: Text(user.username, style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold,color: Theme.of(context).textTheme.bodyText1!.color)),
              // subtitle:Text(adventures.elementAt(index).description),
              subtitle: Text(user.firstname, style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color)),
            ),
          );
        }

        else
        {
          return Center(child: Text("Profile has not been created", style: TextStyle(fontSize: 30, color:Theme.of(context).textTheme.bodyText1!.color)));
        }
      });
    }
}