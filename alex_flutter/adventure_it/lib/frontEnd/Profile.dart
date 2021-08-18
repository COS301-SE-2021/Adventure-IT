import 'dart:developer';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';
import 'Navbar.dart';

class ProfileCaller extends StatefulWidget {
  @override
  Profile createState() => Profile();
}

class Profile extends State<ProfileCaller> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme
            .of(context)
            .scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Profile",
                    style: new TextStyle(
                        color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color))),
            backgroundColor: Theme
                .of(context)
                .primaryColorDark),
        body: Column(children: [
          ProfileFutureBuilderCaller(),
          Container(
              padding: const EdgeInsets.only(left: 100.0, top: 0.0),
              child:
              Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Center(
                          child: SizedBox(
                            width: MediaQuery
                                .of(context)
                                .size
                                .height * 0.2,
                          )
                      )
                    ])
                //_buildList()
              ])),
        ]));
  }
}

class ProfileFutureBuilderCaller extends StatefulWidget {
  @override
  ProfileFutureBuilder createState() => ProfileFutureBuilder();
}

class ProfileFutureBuilder extends State<ProfileFutureBuilderCaller> {
  UserProfile? user;
  // final UserApi api = new UserApi();

  @override
  void initState() {
    super.initState();
    user=UserApi.getInstance().getUserProfile();
  }

  @override
  Widget build(BuildContext context) {
          if (user==null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          }
          else
            {
            return Container(
                margin: EdgeInsets.symmetric(
                    vertical: MediaQuery.of(context).size.height * 0.01),
                width: MediaQuery.of(context).size.width * 0.9,
                height: MediaQuery.of(context).size.height * 0.55,
                child: Card(
                    color: Theme.of(context).primaryColorDark,
                    child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Center(
                              child: Column(children: <Widget>[
                            Container(
                              width: MediaQuery.of(context).size.width * 0.2,
                              height: MediaQuery.of(context).size.height * 0.2,
                              child: CircleAvatar(
                                radius: MediaQuery.of(context).size.height * 0.5,
                                backgroundImage:
                                    ExactAssetImage('assets/adventure.PNG'),
                              ),
                              decoration: new BoxDecoration(
                                shape: BoxShape.circle,
                                border: new Border.all(
                                  color: Theme.of(context).accentColor,
                                  width: MediaQuery.of(context).size.height * 0.005,
                                ),
                              ),
                            ),
                            Container(
                                child: Column(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                  SizedBox(height: MediaQuery.of(context).size.height * 0.01),
                                  //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  Text(user!.firstname + " " + user!.lastname,
                                      textAlign: TextAlign.center,
                                      style: new TextStyle(
                                          color: Theme.of(context).textTheme.bodyText1!.color,
                                          fontSize: MediaQuery.of(context).size.height * 0.04)),
                                      SizedBox(height: MediaQuery.of(context).size.height * 0.005),
                                  Text(user!.username,
                                      textAlign: TextAlign.center,
                                      style: new TextStyle(
                                          color: Theme.of(context).textTheme.bodyText1!.color,
                                          fontSize: MediaQuery.of(context).size.height * 0.04)),
                                  SizedBox(height: MediaQuery.of(context).size.height * 0.01),
                                  Row(children: [
                                    Expanded(
                                        child: Text(user!.email,
                                            textAlign: TextAlign.center,
                                            style: new TextStyle(
                                                color: Theme.of(context).textTheme.bodyText1!.color,
                                                fontSize: MediaQuery.of(context).size.height * 0.03))),
                                    Expanded(
                                        child: Container(
                                            margin: EdgeInsets.symmetric(
                                                horizontal: MediaQuery.of(context).size.width * 0.1),
                                            child: MaterialButton(
                                                child: Text("My Documents",
                                                    style: new TextStyle(
                                                        color: Theme.of(context).textTheme.bodyText1!.color)),
                                                color: Theme.of(context)
                                                    .accentColor,
                                                padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width * 0.05, vertical: MediaQuery.of(context).size.width * 0.01),
                                                onPressed: () {
                                                  Navigator.pushReplacement(
                                                    context,
                                                    MaterialPageRoute(
                                                        builder: (context) =>
                                                            HomepageStartupCaller()),
                                                  );
                                                }))),
                                  ]),
                                  SizedBox(height: MediaQuery.of(context).size.height * 0.03),
                                  Row(children: [
                                    // Expanded(
                                    //     child: Text(user.phoneNumber,
                                    //         textAlign: TextAlign.center,
                                    //         style: new TextStyle(
                                    //             color: Theme.of(context).textTheme.bodyText1!.color,
                                    //             fontSize: MediaQuery.of(context).size.height * 0.03))),
                                    Expanded(
                                      child: Container(
                                          margin: EdgeInsets.symmetric(
                                              horizontal: MediaQuery.of(context).size.width * 0.1),
                                          child: MaterialButton(
                                              onPressed: () => {
                                                    Navigator.pushReplacement(
                                                      context,
                                                      MaterialPageRoute(
                                                          builder: (context) =>
                                                              HomepageStartupCaller()),
                                                    )
                                                  },
                                              color:
                                                  Theme.of(context).accentColor,
                                              padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width * 0.05, vertical: MediaQuery.of(context).size.width * 0.01),
                                              child:
                                                  Text("Edit Profile",
                                                      textAlign: TextAlign.center,
                                                      style: new TextStyle(
                                                          color:
                                                              Theme.of(context).textTheme.bodyText1!.color))
                                              ))),
                                    ])
                                ]))
                          ]))
                        ])));
          }
        }
  }
