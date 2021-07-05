import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/frontEnd/HomepageStartup.dart';

import 'package:flutter/material.dart';
import 'Budgets.dart';
import 'CreateAdventure.dart';
import 'package:flutter/foundation.dart';

import '../api/budget.dart';

class AdventurePage extends StatefulWidget {
  Adventure? a;

  AdventurePage(Adventure? ad) {
    this.a = ad;
  }

  @override
  _AdventurePage createState() => _AdventurePage(a);
}

class _AdventurePage extends State<AdventurePage> {
  Adventure? currentAdventure;

  _AdventurePage(Adventure? a) {
    this.currentAdventure = a;
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
                          builder: (context) => HomepageStartupCaller()),
                    );
                  }
                },
                icon: const Icon(Icons.arrow_back_ios),
                color: Theme.of(context).textTheme.bodyText1!.color),
            title: Center(
                child: Text(currentAdventure!.name,
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
            child: Container(
                color: Theme.of(context).primaryColorDark,
                width: MediaQuery.of(context).size.width * 0.75,
                height: MediaQuery.of(context).size.height * 0.75,
                padding: EdgeInsets.symmetric(horizontal: 20, vertical: 20),
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                        child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                            children: <Widget>[
                              Icon(
                                Icons.list_alt,
                                size: 40,
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color,
                              ),
                              Text(
                                'Itineraries',
                                style: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color),
                              ),
                            ],
                          ),
                        ),
                        ),
                        ),
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                        child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                              children: <Widget>[
                                Icon(
                                  Icons.checklist,
                                  size: 40,
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color,
                                ),
                                Text(
                                  'Checklists',
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              ],
                            ),
                          ),
                        ),
                        ),
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                        child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                              children: <Widget>[
                                Icon(
                                  Icons.attach_money ,
                                  size: 40,
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color,
                                ),
                                Text(
                                  'Budgets',
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              ],
                            ),
                          ),
                        ),
                        ),
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                        child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                              children: <Widget>[
                                Icon(
                                  Icons.chat_bubble ,
                                  size: 40,
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color,
                                ),
                                Text(
                                  'Group Chat',
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              ],
                            ),
                          ),
                        ),
                        ),
                      ],
                    ),
                    SizedBox(height: 10),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                        child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                              children: <Widget>[
                                Icon(
                                  Icons.insert_drive_file,
                                  size: 40,
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color,
                                ),
                                Text(
                                  'Documents',
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              ],
                            ),
                          ),
                        ),
                        ),
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                          child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                              children: <Widget>[
                                Icon(
                                  Icons.filter,
                                  size: 40,
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color,
                                ),
                                Text(
                                  'Media',
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              ],
                            ),
                          ),
                        ),
                        ),
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                          child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                              children: <Widget>[
                                Icon(
                                  Icons.play_arrow,
                                  size: 40,
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color,
                                ),
                                Text(
                                  'Play this song',
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              ],
                            ),
                          ),
                        ),
                        ),
                        ConstrainedBox(
                          constraints: BoxConstraints.tightFor(width: MediaQuery.of(context).size.width * 0.16),
                          child: ElevatedButton(
                          onPressed: null,
                          child:
                          Container(
                            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                            child: Column(
                              children: <Widget>[
                                Icon(
                                  Icons.play_arrow,
                                  size: 40,
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color,
                                ),
                                Text(
                                  'Play this song',
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color),
                                ),
                              ],
                            ),
                          ),
                        ),
                        ),
                      ],
                    ),
                  ],
                ))));
  }
}
