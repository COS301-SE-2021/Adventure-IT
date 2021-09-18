import 'package:adventure_it/api/userAPI.dart';
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
        ]));
  }
}

class _SettingsBuilder extends StatefulWidget {
  @override
  SettingsBuilder createState() => SettingsBuilder();
}

class SettingsBuilder extends State<_SettingsBuilder> {
  bool? themeSwitch = UserApi.getInstance().theme;
  bool? locationSwitch = UserApi.getInstance().notify;

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Spacer(),
        Container(
          margin: EdgeInsets.symmetric(
          vertical: MediaQuery.of(context).size.height * 0.01),
          width: MediaQuery.of(context).size.width * 0.8,
          height: MediaQuery.of(context).size.height * 0.2,
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
                      Transform.scale(
                        scale: MediaQuery.of(context).size.height < MediaQuery.of(context).size.width ? 2 : 1,
                        child: Switch(
                          value: themeSwitch!,
                          activeTrackColor: Theme.of(context).scaffoldBackgroundColor,
                          activeColor: Theme.of(context).accentColor,
                          onChanged: (value) {
                            setState(() {
                              themeSwitch = value;
                              /*Navigator.pushReplacement(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) => Themes(isSwitched)));*/
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
        ),
        Container(
          margin: EdgeInsets.symmetric(
              vertical: MediaQuery.of(context).size.height * 0.01),
          width: MediaQuery.of(context).size.width * 0.8,
          height: MediaQuery.of(context).size.height * 0.4,
          child: Card(
              color: Theme.of(context).primaryColorDark,
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Center(
                      child: Text("Privacy",
                          style: new TextStyle(
                              color: Theme.of(context).textTheme.bodyText1!.color,
                              fontSize: MediaQuery.of(context).size.height * 0.04))
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.04),
                    Center(
                        child: Text("Emergency contact",
                            style: new TextStyle(
                                color: Theme.of(context).textTheme.bodyText1!.color,
                                fontSize: MediaQuery.of(context).size.height * 0.025))
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.01),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Center(
                            child: Text("user.emergencyContact",
                                style: new TextStyle(
                                    color: Theme.of(context).textTheme.bodyText1!.color,
                                    fontSize: MediaQuery.of(context).size.height * 0.02))
                          )
                        ),
                        SizedBox(width: MediaQuery.of(context).size.height * 0.02),
                        Divider(
                          height: 20,
                          thickness: 5,
                          indent: 20,
                          endIndent: 20,
                          color: Theme.of(context).textTheme.bodyText1!.color,
                        ),
                        Expanded(
                            child: Container(
                              decoration: BoxDecoration(
                                  color: Theme.of(context).accentColor,
                                  shape: BoxShape.circle),
                              child: IconButton(
                                icon: Icon(Icons.edit),
                                color: Theme.of(context).primaryColorDark,
                                onPressed: () {
                                  //alert dialog for edit
                                },
                                padding: EdgeInsets.symmetric(
                                    horizontal: MediaQuery.of(context).size.width * 0.05,
                                    vertical: MediaQuery.of(context).size.width * 0.01),
                              ),
                            )
                        )
                      ],
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.03),
                    Center(
                        child: Text("Disable location updates to emergency contact?",
                            style: new TextStyle(
                                color: Theme.of(context).textTheme.bodyText1!.color,
                                fontSize: MediaQuery.of(context).size.height * 0.025))
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
                                    Icons.thumb_up_outlined,
                                    size: 40,
                                    color: Theme.of(context).textTheme.bodyText1!.color,
                                  ),
                                  Text("Enable",
                                      style: new TextStyle(
                                          color: Theme.of(context).textTheme.bodyText1!.color,
                                          fontSize: MediaQuery.of(context).size.height < MediaQuery.of(context).size.width ? MediaQuery.of(context).size.height * 0.02 : MediaQuery.of(context).size.height * 0.018)),
                                ],
                              )
                          ),
                          Spacer(),
                          Transform.scale(
                            scale: MediaQuery.of(context).size.height < MediaQuery.of(context).size.width ? 2 : 1,
                            child: Switch(
                              value: locationSwitch!,
                              activeTrackColor: Theme.of(context).scaffoldBackgroundColor,
                              activeColor: Theme.of(context).accentColor,
                              onChanged: (value) async {
                                await UserApi.getInstance().setNotificationSettings(context);
                                setState(() {
                                  locationSwitch = value;
                                });
                              },
                            ),
                          ),
                          Spacer(),
                          Expanded(
                            child: Column(
                              children: [
                                Icon(
                                  Icons.thumb_down_outlined,
                                  size: 40,
                                  color: Theme.of(context).textTheme.bodyText1!.color,
                                ),
                                Text("Disable",
                                    style: new TextStyle(
                                        color: Theme.of(context).textTheme.bodyText1!.color,
                                        fontSize: MediaQuery.of(context).size.height < MediaQuery.of(context).size.width ? MediaQuery.of(context).size.height * 0.02 : MediaQuery.of(context).size.height * 0.018)),
                              ],
                            ),
                          ),
                          Spacer()
                        ],
                      ),
                    )
                  ]
              )
          ),
        )
      ],
    );
  }
}