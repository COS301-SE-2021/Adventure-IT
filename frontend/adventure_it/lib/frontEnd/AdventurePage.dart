import 'dart:async';
import 'package:adventure_it/Providers/adventure_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'package:date_count_down/date_count_down.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/Providers/friends_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventureAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:time_machine/time_machine.dart';
import 'ChecklistsList.dart';
import 'FileList.dart';
import 'GroupChat.dart';
import 'ItinerariesList.dart';
import 'BudgetList.dart';
import 'HomepageStartup.dart';
import 'MediaList.dart';
import 'Navbar.dart';
import 'TimelinePage.dart';

//TODO: import countdown in non-null-safe file

//Shows adventure countdown/ completion
class AdventureTimer extends StatefulWidget {
  late final Adventure? a;

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
    if (DateTime
        .parse(currentAdventure!.startDate)
        .difference(DateTime.now())
        .inDays >
        0) {
      return Center(
          child: ClipRRect(
              borderRadius: BorderRadius.all(Radius.circular(20.0)),
              child: Container(
                  padding:
                  EdgeInsets.all(MediaQuery
                      .of(context)
                      .size
                      .width * 0.02),
                  color: Theme
                      .of(context)
                      .scaffoldBackgroundColor
                      .withOpacity(0.2),
                  child: Column(children: [
                    Text("Countdown Until Adventure Begins",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            color: Theme
                                .of(context)
                                .textTheme
                                .bodyText1!
                                .color,
                            fontSize:
                            25 * MediaQuery
                                .of(context)
                                .textScaleFactor)),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.03),
                    Text(
                        CountDown().timeLeft(
                            DateTime.parse(currentAdventure!.startDate),
                            "Currently on adventure!",
                            longDateName: true),
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            color: Theme
                                .of(context)
                                .textTheme
                                .bodyText1!
                                .color,
                            fontWeight: FontWeight.bold,
                            fontSize: (45 / 1125) *
                                MediaQuery
                                    .of(context)
                                    .size
                                    .width)),
                  ]))));
    } else if (DateTime
        .parse(currentAdventure!.startDate)
        .difference(DateTime.now())
        .inDays <=
        0 &&
        DateTime
            .parse(currentAdventure!.endDate)
            .difference(DateTime.now())
            .inDays >
            0) {
      return Center(
        child: ClipRRect(
            borderRadius: BorderRadius.all(Radius.circular(20.0)),
            child: Container(
                padding:
                EdgeInsets.all(MediaQuery
                    .of(context)
                    .size
                    .width * 0.02),
                color:
                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
                child: Column(children: [
                  Text("Countdown Until Adventure Ends",
                      textAlign: TextAlign.center,
                      style: TextStyle(
                          color: Theme
                              .of(context)
                              .textTheme
                              .bodyText1!
                              .color,
                          fontSize:
                          25 * MediaQuery
                              .of(context)
                              .textScaleFactor)),
                  SizedBox(height: MediaQuery
                      .of(context)
                      .size
                      .height * 0.03),
                  Text(
                    CountDown().timeLeft(
                        DateTime.parse(currentAdventure!.endDate),
                        "Adventure has ended!",
                        longDateName: true),
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color,
                        fontWeight: FontWeight.bold,
                        fontSize:
                        (45 / 1125) * MediaQuery
                            .of(context)
                            .size
                            .width),
                  ),
                ]))),
      );
    } else {
      return Center(
          child: ClipRRect(
              borderRadius: BorderRadius.all(Radius.circular(20.0)),
              child: Container(
                  padding:
                  EdgeInsets.all(MediaQuery
                      .of(context)
                      .size
                      .width * 0.02),
                  color: Theme
                      .of(context)
                      .scaffoldBackgroundColor
                      .withOpacity(0.2),
                  child: Column(children: [
                    Text("Completed",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                            color: Theme
                                .of(context)
                                .textTheme
                                .bodyText1!
                                .color,
                            fontSize:
                            25 * MediaQuery
                                .of(context)
                                .textScaleFactor)),
                  ]))));
    }
  }
}

//Shows adventure page and allows user to see different items within an adventure
class AdventurePage extends StatelessWidget {
  late final Adventure? currentAdventure;

  AdventurePage(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => AdventuresModel(),
        builder: (context, widget) => Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text(currentAdventure!.name,
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            iconTheme: IconThemeData(color: Theme.of(context).textTheme.bodyText1!.color),
            actions: [
              IconButton(
                  onPressed: () {
                    {
                      var provider = Provider.of<AdventuresModel>(context, listen: false);
                      showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return _EditAlert(currentAdventure!, provider);
                          });
                    }
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
                        image: NetworkImage(
                            "https://maps.googleapis.com/maps/api/place/photo?photo_reference=" +
                                currentAdventure!.location.photoReference +
                                "&maxwidth=700&key=" +
                                googleMapsKey),
                        fit: BoxFit.cover,
                        colorFilter: ColorFilter.mode(
                            Theme.of(context).backgroundColor.withOpacity(0.35),
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
                                  child: Container(
                                      padding:
                                      EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                      color:
                                      Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                              ),
                            Spacer(),
                            Expanded(
                                flex: 8,
                                child: ClipRRect(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(20.0)),
                                  child: Container(
                                      padding:
                                      EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                      color:
                                      Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                                  ),
                                )),
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: Container(
                                    padding:
                                    EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                    color:
                                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                                child: Container(
                                    padding:
                                    EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                    color:
                                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                            ),
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: Container(
                                    padding:
                                    EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                    color:
                                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                                SizedBox(
                                    height: MediaQuery
                                        .of(context)
                                        .size
                                        .height / 40),
                                Row(
                                  mainAxisAlignment: MainAxisAlignment
                                      .spaceEvenly,
                                  children: [
                                    Expanded(
                                      flex: 8,
                                      child: ClipRRect(
                                        borderRadius:
                                        BorderRadius.all(Radius.circular(20.0)),
                                    child: Container(
                                        padding:
                                        EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                        color:
                                        Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                                child: Container(
                                    padding:
                                    EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                    color:
                                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                            ),
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: Container(
                                    padding:
                                    EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                    color:
                                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                            ),
                            Spacer(),
                            Expanded(
                              flex: 8,
                              child: ClipRRect(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20.0)),
                                child: Container(
                                    padding:
                                    EdgeInsets.all(MediaQuery.of(context).size.width * 0.02),
                                    color:
                                    Theme.of(context).scaffoldBackgroundColor.withOpacity(0.3),
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
                              ])),
                          Spacer(),
                          Row(
                            children: [
                              Expanded(
                                flex: 1,
                                child: Container(
                                    decoration: BoxDecoration(
                                        color: Theme
                                            .of(context)
                                            .accentColor,
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
                                        color: Theme
                                            .of(context)
                                            .primaryColorDark)),
                              ),
                              Expanded(flex: 1, child: Container()),
                              Expanded(
                                  flex: 1,
                                  child: Container(
                                      decoration: BoxDecoration(
                                          color: Theme
                                              .of(context)
                                              .accentColor,
                                          shape: BoxShape.circle),
                                      child: IconButton(
                                          onPressed: () {
                                            showDialog(
                                                context: context,
                                                builder: (
                                                    BuildContext context) {
                                                  return AlertBox(
                                                      currentAdventure!);
                                                });
                                          },
                                          icon: const Icon(Icons.share),
                                          color: Theme
                                              .of(context)
                                              .primaryColorDark))),
                            ],
                          ),
                          SizedBox(height: MediaQuery
                              .of(context)
                              .size
                              .height / 60),
                        ])))));
  }
}

//Sharing button: adds friend to adventure
class AlertBox extends StatelessWidget {
  late final Adventure? currentAdventure;

  AlertBox(Adventure a) {
    currentAdventure = a;
  }

  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.49;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.6;
    }
  }

  //controllers for the form fields
  late final String userID = UserApi.getInstance().getUserProfile()!.userID;

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme
            .of(context)
            .primaryColorDark,
        title: Stack(clipBehavior: Clip.none, children: <Widget>[
          Positioned(
            right: -40.0,
            top: -40.0,
            child: InkResponse(
              onTap: () {
                Navigator.of(context).pop();
              },
              child: CircleAvatar(
                child: Icon(Icons.close,
                    color: Theme
                        .of(context)
                        .primaryColorDark),
                backgroundColor: Theme
                    .of(context)
                    .accentColor,
              ),
            ),
          ),
          Column(mainAxisSize: MainAxisSize.min, children: [
            Text("Add Friend To Adventure",
                textAlign: TextAlign.center,
                style: TextStyle(
                  color: Theme
                      .of(context)
                      .textTheme
                      .bodyText1!
                      .color,
                  fontSize: 25 * MediaQuery
                      .of(context)
                      .textScaleFactor,
                  fontWeight: FontWeight.bold,
                )),
            SizedBox(
                height: MediaQuery
                    .of(context)
                    .size
                    .height * 0.01, width: 300)
          ])
        ]),
        content: ChangeNotifierProvider(
            create: (context) =>
                FriendModel(UserApi.getInstance().getUserProfile()!.userID),
            child: Container(
                width: 300,
                child: Consumer<FriendModel>(
                    builder: (context, friendModel, child) {
                      return friendModel.friends != null &&
                          friendModel.friends!.length > 0
                          ? ListView.builder(
                          shrinkWrap: true,
                          itemCount: friendModel.friends!.length,
                          itemBuilder: (context, index) {
                            return InkWell(
                                hoverColor: Theme
                                    .of(context)
                                    .primaryColorLight,
                                onTap: () {
                                  AdventureApi.addAttendee(
                                      currentAdventure!,
                                      friendModel.friends!
                                          .elementAt(index)
                                          .userID);
                                  Navigator.of(context).pop();
                                },
                                child: Padding(
                                    padding: EdgeInsets.symmetric(
                                        vertical:
                                        MediaQuery
                                            .of(context)
                                            .size
                                            .height *
                                            0.01,
                                        horizontal:
                                        MediaQuery
                                            .of(context)
                                            .size
                                            .width *
                                            0.01),
                                    child: Expanded(
                                        child: Text(
                                          friendModel.friends!
                                              .elementAt(index)
                                              .username,
                                          style: TextStyle(
                                              fontSize: 20 *
                                                  MediaQuery
                                                      .of(context)
                                                      .textScaleFactor,
                                              fontWeight: FontWeight.bold,
                                              color: Theme
                                                  .of(context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color),
                                        ))));
                          })
                          : Container(height: 10);
                    }))));
  }
}

//edit button
class _EditAlert extends StatefulWidget {
  late final Adventure? currentAdventure;
  final AdventuresModel adventuresModel;

  _EditAlert(this.currentAdventure, this.adventuresModel);

  @override
  EditAlert createState() => EditAlert(currentAdventure!);
}

class EditAlert extends State<_EditAlert> {
  late final Adventure? adventure;

  EditAlert(this.adventure) {
    nameController.text = adventure!.name;
    descriptionController.text = adventure!.description;
    dateController.text = adventure!.startDate.toString() + " - " + adventure!.endDate.toString();
  }

  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.49;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.6;
    }
  }

  double getHeight(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.80;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.80;
    }
  }

  double getWidth(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .width * 0.8;
    } else {
      return MediaQuery
          .of(context)
          .size
          .width * 0.5;
    }
  }

  DateTimeRange? dates;
  String? location;
  final initialDateRange = DateTimeRange(
    start: DateTime.now(),
    end: DateTime.now().add(Duration(hours: 24 * 7)),
  );

  List<String> months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
  ];

  String getText(DateTimeRange? dateRange) {
    if (dateRange!.start == dateRange.end) {
      String x = dateRange.start.day.toString() + " " +
          months.elementAt(dateRange.start.month - 1) + " " +
          dateRange.start.year.toString();
      return x;
    }
    else {
      String x = dateRange.start.day.toString() + " " +
          months.elementAt(dateRange.start.month - 1) + " " +
          dateRange.start.year.toString() + " to " +
          dateRange.end.day.toString() +
          " " + months.elementAt(dateRange.end.month - 1) + " " +
          dateRange.end.year.toString();
      return x;
    }
  }


  Map<int, Color> color =
  {
    50: Color.fromRGBO(32, 34, 45, .1),
    100: Color.fromRGBO(32, 34, 45, .2),
    200: Color.fromRGBO(32, 34, 45, .3),
    300: Color.fromRGBO(32, 34, 45, .4),
    400: Color.fromRGBO(32, 34, 45, .5),
    500: Color.fromRGBO(32, 34, 45, .6),
    600: Color.fromRGBO(32, 34, 45, .7),
    700: Color.fromRGBO(32, 34, 45, .8),
    800: Color.fromRGBO(32, 34, 45, .9),
    900: Color.fromRGBO(32, 34, 45, 1),
  };

  //controllers for the form fields
  String ownerID = UserApi.getInstance().getUserProfile()!.userID;

  final nameController = TextEditingController();
  final descriptionController = TextEditingController();
  final dateController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme
            .of(context)
            .primaryColorDark,
        content: Container(
            height: getSize(context),
            child: Stack(
                clipBehavior: Clip.none, children: <Widget>[
              Positioned(
                right: -40.0,
                top: -40.0,
                child: InkResponse(
                  onTap: () {
                    Navigator.of(context).pop();
                  },
                  child: CircleAvatar(
                    child: Icon(Icons.close,
                        color: Theme
                            .of(context)
                            .primaryColorDark),
                    backgroundColor: Theme
                        .of(context)
                        .accentColor,
                  ),
                ),
              ),
              Center(
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: <Widget>[
                        Text(
                            "Edit: " +
                                adventure!.name,
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color,
                              fontSize: 25 *
                                  MediaQuery
                                      .of(context)
                                      .textScaleFactor,
                              fontWeight: FontWeight.bold,
                            )),
                        SizedBox(height: MediaQuery
                            .of(context)
                            .size
                            .height * 0.03),
                        SizedBox(
                          width: 350,
                          child: TextField(
                              style: TextStyle(color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color, fontSize: 15 * MediaQuery
                                  .of(context)
                                  .textScaleFactor),
                              controller: nameController,
                              decoration: InputDecoration(
                                  hintStyle: TextStyle(color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color, fontSize: 15 * MediaQuery
                                      .of(context)
                                      .textScaleFactor),
                                  filled: true,
                                  enabledBorder: InputBorder.none,
                                  errorBorder: InputBorder.none,
                                  disabledBorder: InputBorder.none,
                                  fillColor: Theme
                                      .of(context)
                                      .primaryColorLight,
                                  focusedBorder: OutlineInputBorder(
                                      borderSide: new BorderSide(color: Theme
                                          .of(context)
                                          .accentColor)),
                                  hintText: 'Adventure Name')),
                        ),
                        SizedBox(height: MediaQuery
                            .of(context)
                            .size
                            .height * 0.01),
                        SizedBox(
                          width: 350,
                          child: TextField(
                              maxLength: 255,
                              maxLengthEnforcement: MaxLengthEnforcement
                                  .enforced,
                              maxLines: 4,
                              style: TextStyle(color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color, fontSize: 15 * MediaQuery
                                  .of(context)
                                  .textScaleFactor),
                              controller: descriptionController,
                              decoration: InputDecoration(
                                  hintStyle: TextStyle(color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color, fontSize: 15 * MediaQuery
                                      .of(context)
                                      .textScaleFactor),
                                  filled: true,
                                  enabledBorder: InputBorder.none,
                                  errorBorder: InputBorder.none,
                                  disabledBorder: InputBorder.none,
                                  fillColor: Theme
                                      .of(context)
                                      .primaryColorLight,
                                  focusedBorder: OutlineInputBorder(
                                      borderSide: new BorderSide(color: Theme
                                          .of(context)
                                          .accentColor)),
                                  hintText: 'Adventure Description')),
                        ),
                        Container(
                            width: 350,
                            child: TextField(
                              style: TextStyle(color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color, fontSize: 15 * MediaQuery
                                  .of(context)
                                  .textScaleFactor),
                              controller: dateController,
                              decoration: InputDecoration(
                                  prefixIcon: Icon(
                                      Icons.calendar_today_rounded),
                                  hintStyle: TextStyle(color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color, fontSize: 15 * MediaQuery
                                      .of(context)
                                      .textScaleFactor),
                                  filled: true,
                                  enabledBorder: InputBorder.none,
                                  errorBorder: InputBorder.none,
                                  disabledBorder: InputBorder.none,
                                  fillColor: Theme
                                      .of(context)
                                      .primaryColorLight,
                                  focusedBorder: OutlineInputBorder(
                                      borderSide: new BorderSide(color: Theme
                                          .of(context)
                                          .accentColor)),
                                  hintText: 'Select Dates'),
                              onTap: () async {
                                DateTimeRange? picked = await showDateRangePicker(
                                    context: context,
                                    builder: (BuildContext context,
                                        Widget ?child) {
                                      return Column(
                                          mainAxisAlignment: MainAxisAlignment
                                              .center,
                                          children:[
                                          Container(
                                          width: getWidth(context),
                                      height: getHeight(context),
                                      child: Theme(
                                      data: ThemeData(
                                      primarySwatch: MaterialColor(
                                      0xFF20222D, color),
                                      splashColor: Color(0xff20222D),
                                      scaffoldBackgroundColor: Color(0xff484D64),
                                      canvasColor: Color(0xff484D64),
                                      textTheme: TextTheme(
                                      subtitle1: TextStyle(
                                      color: Color(0xffA7AAB9)),
                                      bodyText2: TextStyle(
                                      color: Color(0xffA7AAB9)),
                                      bodyText1: TextStyle(
                                      color: Color(0xffA7AAB9))
                                      subtitle2: TextStyle(color:Color(
                                      0xffA7AAB9)),
                                      button: TextStyle(color: Color(0xffA7AAB9),
                                      fontWeight: FontWeight.bold),
                                      ),
                                      accentColor: Color(0xff6A7AC7),
                                      colorScheme: ColorScheme.light(
                                      primary: Color(0xff20222D),
                                      primaryVariant: Color(0xff20222D),
                                      secondaryVariant: Color(0xff20222D),
                                      onSecondary: Color(0xff20222D),
                                      onPrimary: Color(0xffA7AAB9),
                                      surface: Color(0xff20222D)
                                      onSurface: Color(0xffA7AAB9),
                                      secondary: Color(0xff6A7AC7)),
                                      dialogBackgroundColor: Color(0xff484D64),
                                      backgroundColor:Color(0xff484D64),
                                      highlightColor: Color(0xff484D64)


                                      ) child
                                          :
                                      child
                                      !
                                      ,
                                      )
                                      )
                                      ]
                                      );
                                    },
                                    initialDateRange: dates ?? initialDateRange
                                    firstDate: new DateTime(DateTime.now()
                                    .year - 5),
                                lastDate: new DateTime(DateTime.now().year + 5)
                                );
                                setState((){dates=picked;
                                dateController.text=getText(picked);
                                });
                              },
                            )
                        ),
                        SizedBox(height: MediaQuery
                            .of(context)
                            .size
                            .height * 0.03),
                          Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              mainAxisSize: MainAxisSize.max,
                              children: <Widget>[
                                Spacer(flex: 2),
                                Expanded(
                                    flex: 3,
                                    child: ElevatedButton(
                                        child: Text("Edit",
                                            style: new TextStyle(color: Theme
                                                .of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color)),
                                        style: ElevatedButton.styleFrom(
                                          primary: Theme
                                              .of(context)
                                              .accentColor,
                                          padding: EdgeInsets.symmetric(
                                              horizontal: 3, vertical: 20),
                                        ),
                                        onPressed: () async {
                                          await widget.adventuresModel.editAdventure(
                                              adventure!.adventureId,
                                              nameController.text,
                                              dates == null ?
                                              LocalDate.dateTime(DateTime.parse(adventure!.startDate))
                                                  : LocalDate.dateTime(dates!.start),
                                              dates == null ?
                                              LocalDate.dateTime(DateTime.parse(adventure!.endDate))
                                                  : LocalDate.dateTime(dates!.end),
                                              descriptionController.text);
                                          Navigator.of(context)
                                              .pop(true);
                                          Navigator.pushReplacement(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      HomepageStartupCaller()));
                                        })),
                                Spacer(flex: 2),
                      ]
                    )]
                  ))
              ]
            )
        )
    );
  }
}
