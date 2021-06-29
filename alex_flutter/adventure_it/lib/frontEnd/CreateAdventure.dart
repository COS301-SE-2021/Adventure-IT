import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/gestures.dart';
import 'package:date_range_picker/date_range_picker.dart' as DateRangePicker;

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Register.dart';

class CreateAdventureCaller extends StatefulWidget {
  @override
  CreateAdventure createState() => CreateAdventure();
}

class CreateAdventure extends State<CreateAdventureCaller> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(title: Center(child: Text("Create Adventure",
          style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)
        )), backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Container(
                width: 500,
                height: 250,
                child: CircleAvatar(
                  radius: 90,
                  backgroundImage: ExactAssetImage('assets/adventure.PNG'),
                ),
                decoration: new BoxDecoration(
                  shape: BoxShape.circle,
                  border: new Border.all(
                    color: Theme.of(context).accentColor,
                    width: 3.0,
                  ),
                ),
              ),
              SizedBox(height: 10),
              SizedBox(
                width: 400.0,
                child: TextField(
                    style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color),
                    decoration: InputDecoration(
                        hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color),
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        focusedBorder: OutlineInputBorder( borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Adventure Name')),
              ),
              SizedBox(height: 10),
              SizedBox(
                width: 400.0,
                child: TextField(
                    style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color),
                    obscureText: true,
                    decoration: InputDecoration(
                        hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color),
                        filled: true,
                        fillColor: Theme.of(context).primaryColorLight,
                        focusedBorder: OutlineInputBorder(borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Adventure Description')),
              ),
              SizedBox(height: 10),
              ElevatedButton(
                  child: Text("Add",
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
              SizedBox(height: 10),
              MaterialButton(
                  color: Theme.of(context).accentColor,
                  onPressed: () async {
                    final List<DateTime> picked = await DateRangePicker.showDatePicker(
                        context: context,
                        initialFirstDate: new DateTime.now(),
                        initialLastDate: (new DateTime.now()).add(new Duration(days: 7)),
                        firstDate: new DateTime(DateTime.now().year - 5),
                        lastDate: new DateTime(DateTime.now().year + 5)
                    );
                    if (picked != null && picked.length == 2) {
                      print(picked);
                    }
                  },
                  child: new Text("Pick Date")
              )

              ])));

  }
}
