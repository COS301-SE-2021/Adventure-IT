import 'package:adventure_it/frontEnd/Settings.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'HomepageStartup.dart';
import 'InitializeFireFlutter.dart';
import 'Login.dart';
import 'Profile.dart';
import 'FriendsPage.dart';

class NavDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Container(
          color: Theme.of(context).primaryColorDark,
          child: ListView(
            padding: EdgeInsets.zero,
            children: <Widget>[
              Container(
                  height: MediaQuery.of(context).size.height * 0.3,
                  child: DrawerHeader(
                    padding: EdgeInsets.symmetric(
                        vertical: MediaQuery.of(context).size.height * 0.01),
                    child: SizedBox(),
                    decoration: BoxDecoration(
                        color: Theme.of(context).scaffoldBackgroundColor,
                        image: DecorationImage(
                            fit: BoxFit.contain,
                            image: AssetImage("custom_images/logo.png"))),
                  )),
              ListTile(
                leading: Icon(Icons.location_city_outlined,
                    color: Theme.of(context).accentColor),
                title: Text('Adventures',
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color)),
                onTap: () {
                  FlutterMessagingChangeNotifier
                      .setGroupChatChangeNotifier(null);
                  FlutterMessagingChangeNotifier
                      .setDirectChatChangeNotifier(null);
                  Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(
                        builder: (context) => HomepageStartupCaller()),
                  );
                },
              ),
              ListTile(
                leading: Icon(Icons.person_outline_outlined,
                    color: Theme.of(context).accentColor),
                title: Text('Profile'),
                onTap: () {
                  FlutterMessagingChangeNotifier
                      .setGroupChatChangeNotifier(null);
                  FlutterMessagingChangeNotifier
                      .setDirectChatChangeNotifier(null);
                  Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(builder: (context) => ProfileCaller()),
                  );
                },
              ),
              ListTile(
                leading: Icon(Icons.people_outline_outlined,
                    color: Theme.of(context).accentColor),
                title: Text('Friend List'),
                onTap: () {
                  FlutterMessagingChangeNotifier
                      .setGroupChatChangeNotifier(null);
                  FlutterMessagingChangeNotifier
                      .setDirectChatChangeNotifier(null);
                  Navigator.pushReplacement(context,
                      MaterialPageRoute(builder: (context) => Friends()));
                },
              ),
              ListTile(
                leading:
                    Icon(Icons.settings, color: Theme.of(context).accentColor),
                title: Text('Settings'),
                onTap: () {
                  FlutterMessagingChangeNotifier
                      .setGroupChatChangeNotifier(null);
                  FlutterMessagingChangeNotifier
                      .setDirectChatChangeNotifier(null);
                  Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => SettingsCaller()));
                },
              ),
              ListTile(
                leading: Icon(Icons.power_settings_new,
                    color: Theme.of(context).accentColor),
                title: Text('Logout'),
                onTap: () {
                  FirebaseMessaging.instance.deleteToken();
                  FlutterMessagingChangeNotifier
                      .setGroupChatChangeNotifier(null);
                  FlutterMessagingChangeNotifier
                      .setDirectChatChangeNotifier(null);
                  Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(builder: (context) => LoginCaller()),
                  );
                },
              )
            ],
          )),
    );
  }
}
