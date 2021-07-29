import 'package:adventure_it/frontEnd/HomepageStartup.dart';
import 'package:flutter/material.dart';

import 'Login.dart';
import 'Profile.dart';

class NavDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Container(
        color: Theme.of(context).primaryColorDark,
        child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
      DrawerHeader(
      child: SizedBox(),
        decoration: BoxDecoration(
          color: Colors.green,
          image: DecorationImage(
              fit: BoxFit.fill,
              image: AssetImage('assets/adventure.PNG'))),
      ),
          ListTile(
            leading: Icon(Icons.location_city_outlined),
            title: Text('Adventure',
                style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
            onTap: () => {
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(
                  builder: (context) => HomepageStartupCaller()),
              )
            },
          ),
          ListTile(
            leading: Icon(Icons.person_outline_outlined),
            title: Text('Profile'),
            onTap: () => {
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(
                    builder: (context) => ProfileCaller()),
              )
            },
          ),
          ListTile(
            leading: Icon(Icons.people_outline_outlined),
            title: Text('Friend List'),
            onTap: () => {},
          ),
          ListTile(
            leading: Icon(Icons.doorbell_rounded),
            title: Text('Notifications'),
            onTap: () => {},
          ),
          ListTile(
            leading: Icon(Icons.settings),
            title: Text('Settings'),
            onTap: () => {},
          ),
          ListTile(
            leading: Icon(Icons.power_settings_new),
            title: Text('Logout'),
            onTap: () => {
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(
                    builder: (context) => LoginCaller()),
              )
            },
          )
        ],
      )),
    );
  }
}