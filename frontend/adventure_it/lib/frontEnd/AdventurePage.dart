import 'dart:async';
import 'package:adventure_it/Providers/friends_model.dart';
import 'package:adventure_it/api/adventureAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:provider/provider.dart';

import 'ChecklistsList.dart';
import 'FileList.dart';
import 'GroupChat.dart';
import 'ItinerariesList.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'BudgetList.dart';
import 'package:adventure_it/frontEnd/HomepageStartup.dart';
import 'package:date_count_down/date_count_down.dart';

import 'package:flutter/material.dart';
import 'BudgetList.dart';
import 'CreateAdventure.dart';
import 'package:flutter/foundation.dart';

import '../api/budget.dart';
import 'MediaList.dart';
import 'Navbar.dart';
import 'TimelinePage.dart';

//Shows the page of an adventure and allows the user to look at budgets, itineraries etc
class AdventureTimer extends StatefulWidget {
  Adventure? a;

  AdventureTimer(Adventure? a) {
    this.a = a;
  }

  @override
  _AdventureTimer createState() => _AdventureTimer(a);
}

class _AdventureTimer extends State<AdventureTimer> {
  late Timer _timer;
  Adventure? currentAdventure;

  @override
  void initState() {
    super.initState();
    _timer = Timer.periodic(const Duration(seconds: 1), (_timer) {
      setState(() {});
    });
  }

  _AdventureTimer(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  void dispose() {
    super.dispose();
    _timer.cancel();
  }

  @override
  Widget build(BuildContext context) {
    if (DateTime.parse(currentAdventure!.startDate)
            .difference(DateTime.now())
            .inDays >
        0) {
      return Center(
          child: ClipRRect(
              borderRadius: BorderRadius.all(Radius.circular(20.0)),
              child: Container(
                  padding:
                      EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                  color: Theme.of(context)
                      .scaffoldBackgroundColor
                      .withOpacity(0.2),
                  child: Column(children: [
                    Text("Countdown Until Adventure Begins",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            color: Theme.of(context).textTheme.bodyText1!.color,
                            fontSize:
                                25 * MediaQuery.of(context).textScaleFactor)),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.03),
                    Text(
                        CountDown().timeLeft(
                            DateTime.parse(currentAdventure!.startDate),
                            "Currently on adventure!",
                            longDateName: true),
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            color: Theme.of(context).textTheme.bodyText1!.color,
                            fontWeight: FontWeight.bold,
                            fontSize: (45 / 1125) *
                                MediaQuery.of(context).size.width)),
                  ]))));
    } else if (DateTime.parse(currentAdventure!.startDate)
                .difference(DateTime.now())
                .inDays <=
            0 &&
        DateTime.parse(currentAdventure!.endDate)
                .difference(DateTime.now())
                .inDays >
            0) {
      return Center(
        child: ClipRRect(
            borderRadius: BorderRadius.all(Radius.circular(20.0)),
            child: Container(
                padding:
                    EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                color:
                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.2),
                child: Column(children: [
                  Text("Countdown Until Adventure Ends",
                      textAlign: TextAlign.center,
                      style: TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color,
                          fontSize:
                              25 * MediaQuery.of(context).textScaleFactor)),
                  SizedBox(height: MediaQuery.of(context).size.height * 0.03),
                  Text(
                    CountDown().timeLeft(
                        DateTime.parse(currentAdventure!.endDate),
                        "Adventure has ended!",
                        longDateName: true),
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color,
                        fontWeight: FontWeight.bold,
                        fontSize:
                            (45 / 1125) * MediaQuery.of(context).size.width),
                  ),
                ]))),
      );
    } else {
      return Center(
          child: ClipRRect(
              borderRadius: BorderRadius.all(Radius.circular(20.0)),
              child: Container(
                  padding:
                      EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                  color: Theme.of(context)
                      .scaffoldBackgroundColor
                      .withOpacity(0.2),
                  child: Column(children: [
                    Text("Completed",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            color: Theme.of(context).textTheme.bodyText1!.color,
                            fontSize:
                                25 * MediaQuery.of(context).textScaleFactor)),
                  ]))));
    }
  }
}

class AdventurePage extends StatelessWidget {
  Adventure? currentAdventure;

  AdventurePage(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text(currentAdventure!.name,
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            actions: [
              IconButton(
                  onPressed: () {
                    {}
                  },
                  icon: const Icon(Icons.edit),
                  color: Theme.of(context).textTheme.bodyText1!.color),
            ],
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
            child: Container(
                width: MediaQuery.of(context).size.width,
                height: MediaQuery.of(context).size.height,
                decoration: BoxDecoration(
                    image: DecorationImage(
                        image: NetworkImage("https://maps.googleapis.com/maps/api/place/photo?photo_reference="+currentAdventure!.location.photo_reference+"&maxwidth=700&key="+googleMapsKey),
                        fit: BoxFit.cover,
                        colorFilter: ColorFilter.mode(
                            Theme.of(context).backgroundColor.withOpacity(0.25),
                            BlendMode.dstATop))),
                child: Column(children: [
                  Spacer(),
                  Container(
                      // color: Theme.of(context).primaryColorDark,

                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.05),
                      child: Column(children: [
                        AdventureTimer(currentAdventure),
                        SizedBox(
                            height: MediaQuery.of(context).size.height * 0.05),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: MaterialButton(
                                  hoverColor: Theme.of(context)
                                      .primaryColorLight
                                      .withOpacity(0),
                                  padding: EdgeInsets.symmetric(
                                      vertical:
                                          MediaQuery.of(context).size.height *
                                              0.01),
                                  onPressed: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                Itineraries(currentAdventure)));
                                  },
                                  child: Column(
                                    children: <Widget>[
                                      Icon(
                                        Icons.list_alt,
                                        size: 50,
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color,
                                      ),
                                      Text(
                                        'Itineraries',
                                        textAlign: TextAlign.center,
                                        style: TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            ),
                            Spacer(),
                            Expanded(
                                flex: 8,
                                child: ClipRRect(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(20.0)),
                                  child: MaterialButton(
                                    hoverColor: Theme.of(context)
                                        .primaryColorLight
                                        .withOpacity(0),
                                    padding: EdgeInsets.symmetric(
                                        vertical:
                                            MediaQuery.of(context).size.height *
                                                0.01),
                                    onPressed: () {
                                      Navigator.pushReplacement(
                                          context,
                                          MaterialPageRoute(
                                              builder: (context) => Checklists(
                                                  currentAdventure)));
                                    },
                                    child: Column(
                                      children: <Widget>[
                                        Icon(
                                          Icons.checklist,
                                          size: 50,
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color,
                                        ),
                                        Text(
                                          'Checklists',
                                          textAlign: TextAlign.center,
                                          style: TextStyle(
                                              color: Theme.of(context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color),
                                        ),
                                      ],
                                    ),
                                  ),
                                )),
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: MaterialButton(
                                  hoverColor: Theme.of(context)
                                      .primaryColorLight
                                      .withOpacity(0),
                                  padding: EdgeInsets.symmetric(
                                      vertical:
                                          MediaQuery.of(context).size.height *
                                              0.01),
                                  onPressed: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                Budgets(currentAdventure)));
                                  },
                                  child: Column(
                                    children: <Widget>[
                                      Icon(
                                        Icons.attach_money,
                                        size: 50,
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color,
                                      ),
                                      Text(
                                        'Budgets',
                                        textAlign: TextAlign.center,
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
                        SizedBox(
                            height: MediaQuery.of(context).size.height / 40),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: MaterialButton(
                                  hoverColor: Theme.of(context)
                                      .primaryColorLight
                                      .withOpacity(0),
                                  padding: EdgeInsets.symmetric(
                                      vertical:
                                          MediaQuery.of(context).size.height *
                                              0.01),
                                  onPressed: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                GroupChat(currentAdventure)));
                                  },
                                  child: Column(
                                    children: <Widget>[
                                      Icon(
                                        Icons.chat_bubble,
                                        size: 50,
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color,
                                      ),
                                      Text(
                                        'Group Chat',
                                        textAlign: TextAlign.center,
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
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: MaterialButton(
                                  hoverColor: Theme.of(context)
                                      .primaryColorLight
                                      .withOpacity(0),
                                  padding: EdgeInsets.symmetric(
                                      vertical:
                                          MediaQuery.of(context).size.height *
                                              0.01),
                                  onPressed: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                Files(currentAdventure)));
                                  },
                                  child: Column(
                                    children: <Widget>[
                                      Icon(
                                        Icons.insert_drive_file,
                                        size: 50,
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color,
                                      ),
                                      Text(
                                        'Files',
                                        textAlign: TextAlign.center,
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
                            Spacer(),
                            Expanded(
                                flex: 8,
                                child: ClipRRect(
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(20.0)),
                                    child: MaterialButton(
                                      hoverColor: Theme.of(context)
                                          .primaryColorLight
                                          .withOpacity(0),
                                      padding: EdgeInsets.symmetric(
                                          vertical: MediaQuery.of(context)
                                                  .size
                                                  .height *
                                              0.01),
                                      onPressed: () {
                                        Navigator.pushReplacement(
                                            context,
                                            MaterialPageRoute(
                                                builder: (context) => MediaPage(
                                                    currentAdventure)));
                                      },
                                      child: Column(
                                        children: <Widget>[
                                          Icon(
                                            Icons.filter,
                                            size: 50,
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color,
                                          ),
                                          Text(
                                            'Media',
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color),
                                          ),
                                        ],
                                      ),
                                    ))),
                          ],
                        ),
                        SizedBox(
                            height: MediaQuery.of(context).size.height / 40),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: MaterialButton(
                                  hoverColor: Theme.of(context)
                                      .primaryColorLight
                                      .withOpacity(0),
                                  padding: EdgeInsets.symmetric(
                                      vertical:
                                          MediaQuery.of(context).size.height *
                                              0.01),
                                  onPressed: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) =>
                                                TimePage(currentAdventure)));
                                  },
                                  child: Column(
                                    children: <Widget>[
                                      Icon(
                                        Icons.access_time_filled,
                                        size: 50,
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color,
                                      ),
                                      Text(
                                        'Timeline',
                                        textAlign: TextAlign.center,
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
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: MaterialButton(
                                  hoverColor: Theme.of(context)
                                      .primaryColorLight
                                      .withOpacity(0),
                                  padding: EdgeInsets.symmetric(
                                      vertical:
                                          MediaQuery.of(context).size.height *
                                              0.01),
                                  onPressed: () {
                                    {}
                                  },
                                  child: Column(
                                    children: <Widget>[
                                      Icon(
                                        Icons.person,
                                        size: 50,
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color,
                                      ),
                                      Text(
                                        'Adventurers',
                                        textAlign: TextAlign.center,
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
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: MaterialButton(
                                  hoverColor: Theme.of(context)
                                      .primaryColorLight
                                      .withOpacity(0),
                                  padding: EdgeInsets.symmetric(
                                      vertical:
                                          MediaQuery.of(context).size.height *
                                              0.01),
                                  onPressed: () {
                                    {}
                                  },
                                  child: Column(
                                    children: <Widget>[
                                      Icon(
                                        Icons.play_arrow,
                                        size: 50,
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color,
                                      ),
                                      Text(
                                        'Play this song',
                                        textAlign: TextAlign.center,
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
                      ])),
                  Spacer(),
                  Row(
                    children: [
                      Expanded(
                        flex: 1,
                        child: Container(
                            decoration: BoxDecoration(
                                color: Theme.of(context).accentColor,
                                shape: BoxShape.circle),
                            child: IconButton(
                                onPressed: () {
                                  Navigator.pushReplacement(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              HomepageStartupCaller()));
                                },
                                icon: const Icon(
                                    Icons.arrow_back_ios_new_rounded),
                                color: Theme.of(context).primaryColorDark)),
                      ),
                      Expanded(flex: 1, child: Container()),
                      Expanded(
                          flex: 1,
                          child: Container(
                              decoration: BoxDecoration(
                                  color: Theme.of(context).accentColor,
                                  shape: BoxShape.circle),
                              child: IconButton(
                                  onPressed: () {
                                    showDialog(
                                        context: context,
                                        builder: (BuildContext context) {
                                          return AlertBox(currentAdventure!);
                                        });
                                  },
                                  icon: const Icon(Icons.share),
                                  color: Theme.of(context).primaryColorDark))),
                    ],
                  ),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ]))));
  }
}

class AlertBox extends StatelessWidget {
  Adventure? currentAdventure;

  AlertBox(Adventure a) {
    currentAdventure = a;
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  //controllers for the form fields
  String userID = UserApi.getInstance().getUserProfile()!.userID;

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme.of(context).primaryColorDark,
        title: Stack(overflow: Overflow.visible, children: <Widget>[
          Positioned(
            right: -40.0,
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
            ),
          ),
          Column(mainAxisSize: MainAxisSize.min, children: [
            Text("Add Friend To Adventure",
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                  fontWeight: FontWeight.bold,
                )),
            SizedBox(
                height: MediaQuery.of(context).size.height * 0.01, width: 300)
          ])
        ]),
        content: ChangeNotifierProvider(
            create: (context) =>
                FriendModel(UserApi.getInstance().getUserProfile()!.userID),
            child: Container(
                width: 300,
                child: Consumer<FriendModel>(
                    builder: (context, friendModel, child) {
                  return friendModel.friends!=null&&friendModel.friends!.length > 0
                      ? ListView.builder(
                          shrinkWrap: true,
                          itemCount: friendModel.friends!.length,
                          itemBuilder: (context, index) {
                            return InkWell(
                                hoverColor: Theme.of(context).primaryColorLight,
                                onTap: () {
                                  AdventureApi.addAttendee(
                                      currentAdventure!, friendModel.friends!.elementAt(index).userID);
                                  Navigator.of(context).pop();
                                },
                                child: Padding(
                                    padding: EdgeInsets.symmetric(
                                        vertical:
                                            MediaQuery.of(context).size.height *
                                                0.01,
                                        horizontal:
                                            MediaQuery.of(context).size.width *
                                                0.01),
                                    child: Expanded(
                                        child: Text(
                                      friendModel.friends!
                                          .elementAt(index)
                                          .username,
                                      style: TextStyle(
                                          fontSize: 20 *
                                              MediaQuery.of(context)
                                                  .textScaleFactor,
                                          fontWeight: FontWeight.bold,
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color),
                                    ))));
                          })
                      : Container(height: 10);
                }))));
  }
}
