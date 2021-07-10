import 'dart:html';
import 'dart:developer';
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
        children: [
          ProfileFutureBuilderCaller(),
          Container(
            padding: const EdgeInsets.only(left: 100.0, top: 0.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SizedBox(
                  width: 400.0,
                  child: Text("Friend list",
                    style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize:20)),
            )])),]
        ));}
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
    userFuture = UserApi.getUserByUUID("3f21ea6b-2288-42f3-9175-39adfafea9ab");
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
        print(userFuture);
        log(snapshot.data.toString());
        if (snapshot.hasData) {
          var user = snapshot.data as UserProfile;

          return Card(
            color: Theme.of(context).primaryColorDark,
              child: Row(
                children: [
                  Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      SizedBox(height: 50),
                      Container(
                        width: 200,
                        height: 100,
                        child: CircleAvatar(
                          radius: 70,
                          backgroundImage: ExactAssetImage('assets/adventure.PNG'),
                        ),
                        decoration: new BoxDecoration(
                          shape: BoxShape.circle,
                          border: new Border.all(
                            color: Theme.of(context).accentColor,
                            width: 3.0,
                          ),),),
                      Container(
                        padding: const EdgeInsets.only(left: 100.0),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            SizedBox(height: 50),
                            //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                            SizedBox(
                              width: 400.0,
                              child: Text("Name:  " + user.firstname + " " + user.lastname,
                                  style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize: 30)),
                            ),
                            SizedBox(
                              width: 400.0,
                              child: Text("Username:  " + user.username,
                                  style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize: 20)),
                            ),
                            SizedBox(height: 25),
                            SizedBox(
                              width: 400.0,
                              child: Text(user.email,
                                  style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize:20)),
                            ),
                            SizedBox(
                              width: 400.0,
                              child: Text(user.phoneNumber,
                                  style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color, fontSize:20)),
                            ),
                            SizedBox(height: 50),
                          ],)),
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
                          ))
                      ])
                  ])
          );
        }

        else
        {
          return Center(child: Text("Profile has not been created", style: TextStyle(fontSize: 30, color:Theme.of(context).textTheme.bodyText1!.color)));
        }
      });
    }
}