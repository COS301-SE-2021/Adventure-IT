import 'package:adventure_it/Providers/user_model.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:theme_provider/theme_provider.dart';

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
        body: Column(
            mainAxisSize:MainAxisSize.max,
            children: [
          Expanded(
          child:_SettingsBuilder()
          ),
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
    return ChangeNotifierProvider(
      create: (context) => UserModel(context),
      child: Consumer<UserModel>(builder: (context, userModel, child) {
        if(userModel.profile == null || userModel.em == null) {
          return Center(
              child: CircularProgressIndicator(
                  valueColor: new AlwaysStoppedAnimation<Color>(
                      Theme.of(context).accentColor)));
        }
        else {
      return Column(
        mainAxisSize:MainAxisSize.max,
        children: [
          Spacer(),
          Center(child: Container(
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
                              UserApi.getInstance().setTheme(value);
                              setState(() {
                                themeSwitch = value;
                                if(value == true) {
                                  ThemeProvider.controllerOf(context).setTheme('light_theme');
                                }
                                else {
                                  ThemeProvider.controllerOf(context).setTheme('dark_theme');
                                }

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
          )),
          Center(child: Container(
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
                            flex: MediaQuery.of(context).size.height > MediaQuery.of(context).size.width ? 2 : 1,
                            child: Center(
                              child: Text(
                              UserApi.getInstance().em! == "" ? "No emergency contact set" : UserApi.getInstance().em!,
                                  textAlign: TextAlign.center,
                                  style: new TextStyle(
                                      color: Theme.of(context).textTheme.bodyText1!.color,
                                      fontSize: MediaQuery.of(context).size.height * 0.02)),
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
                                    var provider = Provider
                                        .of<UserModel>(
                                        context,
                                        listen:
                                        false);
                                    showDialog(
                                        context: context,
                                        builder:
                                            (BuildContext
                                        context) {
                                          return AlertBox(
                                              provider);
                                        });
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
                              textAlign: TextAlign.center,
                              style: new TextStyle(
                                  color: Theme.of(context).textTheme.bodyText1!.color,
                                  fontSize: MediaQuery.of(context).size.height < MediaQuery.of(context).size.width ? MediaQuery.of(context).size.height * 0.025 : MediaQuery.of(context).size.height * 0.02)),
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
          )),
          Spacer()
        ],
      );}}),
    );
  }
}

class AlertBox extends StatefulWidget {
  final UserModel userModel;

  AlertBox(this.userModel);

  @override
  _AlertBox createState() => _AlertBox();
}

class _AlertBox extends State<AlertBox> {
  final editController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      backgroundColor: Theme.of(context).primaryColorDark,
      content: Container(
        height: MediaQuery.of(context).size.height * 0.3,
        child: Stack(
          clipBehavior: Clip.none, children: <Widget>[
          Positioned(right: -40.0,
             top: -40.0,
             child: InkResponse(
               onTap: () {
                 Navigator.of(context).pop();
               },
               child: CircleAvatar(
                 child: Icon(Icons.close,
                    color: Theme.of(context).primaryColorDark),
                    backgroundColor: Theme.of(context).accentColor,
               ),
             )
            ),
          Column(
            children: <Widget>[
              Text(
                "Change emergency contact",
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Spacer(),
              Container(
                width: 300,
                padding: EdgeInsets.symmetric(
                  horizontal: MediaQuery.of(context).size.width * 0.02
                ),
                child: TextField(
                  style: TextStyle(
                    color: Theme.of(context).textTheme.bodyText1!.color),
                      controller: editController,
                      decoration: InputDecoration(
                          hintStyle: TextStyle(
                              color: Theme.of(context)
                                  .textTheme
                                  .bodyText2!
                                  .color),
                          filled: true,
                          enabledBorder:
                          InputBorder.none,
                          errorBorder:
                          InputBorder.none,
                          disabledBorder:
                          InputBorder.none,
                          fillColor:
                          Theme.of(context)
                              .primaryColorLight,
                          focusedBorder: OutlineInputBorder(
                              borderSide: new BorderSide(
                                  color: Theme.of(context)
                                      .accentColor)),
                          hintText:
                          'Emergency contact email address')),
                  ),
                  Spacer(),
                  Padding(
                    padding: EdgeInsets.symmetric(
                        horizontal:
                        MediaQuery.of(context)
                            .size
                            .width *
                            0.02),
                    child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            primary: Theme.of(context).accentColor),
                        onPressed: () async {
                          UserApi.getInstance().setEmergencyContact(editController.text);
                          Navigator.of(
                              context)
                              .pop();
                        },
                        child: Text("Set",
                            style: TextStyle(
                                color: Theme.of(
                                    context)
                                    .textTheme
                                    .bodyText1!
                                    .color))))
            ],
          )
        ]),
      ),
    );
  }
}