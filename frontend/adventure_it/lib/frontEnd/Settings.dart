import 'package:flutter/material.dart';

import 'Navbar.dart';

class SettingsCaller extends StatefulWidget {
  @override
  SettingsPage createState() => SettingsPage();
}

class SettingsPage extends State<SettingsCaller> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Settings",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            iconTheme: IconThemeData(
                color: Theme.of(context).textTheme.bodyText1!.color),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(children: [
          _SettingsBuilder(),
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
                            width: MediaQuery.of(context).size.height * 0.2,
                          ))
                    ])
                //_buildList()
              ])),
        ]));
  }
}

class _SettingsBuilder extends StatefulWidget {
  @override
  SettingsBuilder createState() => SettingsBuilder();
}

class SettingsBuilder extends State<_SettingsBuilder> {
  bool isSwitched = false;

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(
      vertical: MediaQuery.of(context).size.height * 0.01),
      width: MediaQuery.of(context).size.width * 0.7,
      height: MediaQuery.of(context).size.width * 0.15,
      child: Card(
        color: Theme.of(context).primaryColorDark,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Center(
                child: Text("Themes",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color,
                        fontSize: MediaQuery.of(context).size.height * 0.04))
            ),
            Center(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Spacer(),
                  Expanded(
                      child: Column(
                        children: [
                          Icon(
                            Icons.dark_mode_outlined,
                            size: 40,
                            color: Theme.of(context).textTheme.bodyText1!.color,
                          ),
                          Text("Dark Mode",
                              style: new TextStyle(
                                  color: Theme.of(context).textTheme.bodyText1!.color,
                                  fontSize: MediaQuery.of(context).size.height * 0.02)),
                        ],
                      )
                  ),
                  Spacer(),
                  Expanded(
                    child: SwitchListTile(
                      value: isSwitched,
                      activeTrackColor: Theme.of(context).scaffoldBackgroundColor,
                      activeColor: Theme.of(context).accentColor,
                      onChanged: (value) {
                        setState(() {
                          isSwitched = value;
                        });
                      },
                    ),
                  ),
                  Spacer(),
                  Expanded(
                    child: Column(
                      children: [
                        Icon(
                          Icons.light_mode_outlined,
                          size: 40,
                          color: Theme.of(context).textTheme.bodyText1!.color,
                        ),
                        Text("Light Mode",
                            style: new TextStyle(
                                color: Theme.of(context).textTheme.bodyText1!.color,
                                fontSize: MediaQuery.of(context).size.height * 0.02)),
                      ],
                    ),
                  ),
                  Spacer()
                ],
              ),
            )
          ],
        ),
      )
    );
  }
}