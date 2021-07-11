import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';
import 'AdventurePage.dart';

import '../api/budget.dart';

class Checklists extends StatelessWidget {
  Adventure? currentAdventure;

  Checklists(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          Checklist_List(currentAdventure),
        ]);
  }
}

class Checklist_List extends StatelessWidget {
  Adventure? a;

  Checklist_List(Adventure? a) {
    this.a = a;
  }

 double getSize(context)
  {
    if(MediaQuery.of(context).size.height>MediaQuery.of(context).size.width)
      {
        return MediaQuery.of(context).size.height*0.49;
      }
    else
      {
        return MediaQuery.of(context).size.height*0.6;
      }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            leading: IconButton(
                onPressed: () {
                  {
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => (AdventurePage(a))),
                    );
                  }
                },
                icon: const Icon(Icons.arrow_back_ios),
                color: Theme.of(context).textTheme.bodyText1!.color),
            title: Center(
                child: Text("Checklists",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
            child: Column(children: <Widget>[
          SizedBox(height: MediaQuery.of(context).size.height / 60),
          Expanded(
              child: Align(
            alignment: FractionalOffset.bottomCenter,
            child: Container(
                decoration: BoxDecoration(
                    color: Theme.of(context).accentColor,
                    shape: BoxShape.circle),
                child: IconButton(
                    onPressed: () {
                      {
                        showDialog(
                            context: context,
                            builder: (BuildContext context) {
                              return AlertDialog(
                                backgroundColor: Theme.of(context).primaryColorDark,
                                content: Container(
                                  height: getSize(context),
                                  child: Stack(
                                  overflow: Overflow.visible,
                                  children: <Widget>[
                                    Positioned(
                                      right: -40.0,
                                      top: -40.0,
                                      child: InkResponse(
                                        onTap: () {
                                          Navigator.of(context).pop();
                                        },
                                        child: CircleAvatar(
                                          child: Icon(Icons.close, color: Theme.of(context).primaryColorDark),
                                          backgroundColor: Theme.of(context).accentColor,
                                        ),
                                      ),
                                    ),
                                Center(
                                child: Column(
                                       // mainAxisSize: MainAxisSize.min,
                                        children: <Widget>[
                                          Text("Create Checklist", textAlign: TextAlign.center, style: TextStyle(color: Theme.of(context).textTheme.bodyText1!.color,fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                                            fontWeight: FontWeight.bold,)),
                                          SizedBox(height: MediaQuery.of(context).size.height*0.07),
                                          Container(
                                            width: MediaQuery.of(context).size.width*0.5,
                                            padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width*0.02),
                                            child: TextField(
                                                style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color),
                                                decoration: InputDecoration(
                                                    hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color),
                                                    filled: true,
                                                    enabledBorder: InputBorder.none,
                                                    errorBorder: InputBorder.none,
                                                    disabledBorder: InputBorder.none,
                                                    fillColor: Theme.of(context).primaryColorLight,
                                                    focusedBorder: OutlineInputBorder( borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Title')),
                                          ),
                                          SizedBox(height: MediaQuery.of(context).size.height*0.02),
                                          Container(
                                            width: MediaQuery.of(context).size.width*0.5,
                                            padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width*0.02),
                              child: TextField(
                                  maxLength: 255,
                                  maxLengthEnforced: true,
                                  maxLines: 4,
                              style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color),
                              decoration: InputDecoration(
                              hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color),
                              filled: true,
                              fillColor: Theme.of(context).primaryColorLight,
                                  enabledBorder: InputBorder.none,
                                  errorBorder: InputBorder.none,
                                  disabledBorder: InputBorder.none,
                              focusedBorder: OutlineInputBorder( borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Description')),
                              ),
                                          SizedBox(height: MediaQuery.of(context).size.height*0.05),

                                            Padding(
                                              padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width*0.02),
                                            child: RaisedButton(
                                                  color: Theme.of(context).accentColor,
                                                  child:Text("Create", style: TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                                              onPressed: () {
                                                Navigator.of(context).pop();
                                              },
                                            ),
                                          )
                                            ],
                                      ),
                                )],
                                ),
                              ));
                            });
                      }
                    },
                    icon: const Icon(Icons.add),
                    color: Theme.of(context).primaryColorDark)),
          ) //Your widget here,
              ),
          SizedBox(height: MediaQuery.of(context).size.height / 60),
        ])));
  }
}
