import 'package:adventure_it/Providers/checklist_model.dart';
import 'package:adventure_it/Providers/friends_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/checklist.dart';
import 'package:adventure_it/api/checklistAPI.dart';
import 'package:adventure_it/api/checklistAPI.dart';
import 'package:adventure_it/api/createChecklist.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/frontEnd/ChecklistsList.dart';
import 'AdventurePage.dart';
import 'package:provider/provider.dart';

import 'package:flutter/material.dart';
import 'DirectChat.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';

class Friends extends StatefulWidget {
  Friends();

  @override
  FriendsPage createState() => FriendsPage();
}

class FriendsPage extends State<Friends> {
  UserProfile? user;
  bool friendList = true;
  UserApi _userApi=UserApi.getInstance();

  FriendsPage();

  final usernameController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Friends",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height / 60),
              Row(mainAxisSize: MainAxisSize.max, children: [
                Spacer(flex: 1),
                Expanded(
                    flex: 7,
                    child: TextField(
                        controller: usernameController,
                        style: TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color),
                        decoration: InputDecoration(
                            hintStyle: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText2!
                                    .color),
                            filled: true,
                            enabledBorder: InputBorder.none,
                            errorBorder: InputBorder.none,
                            disabledBorder: InputBorder.none,
                            fillColor: Theme.of(context).primaryColorLight,
                            focusedBorder: OutlineInputBorder(
                                borderSide: new BorderSide(
                                    color: Theme.of(context).accentColor)),
                            hintText: 'Username'))),
                SizedBox(width: MediaQuery.of(context).size.width * 0.01),
                Expanded(
                    flex: 1,
                    child: Container(
                        decoration: BoxDecoration(
                            color: Theme.of(context).accentColor,
                            shape: BoxShape.circle),
                        child: IconButton(
                            onPressed: () {

    UserApi.searchUsername(usernameController.text)
        .then((value) {
    print(value);
    if (value.compareTo("") != 0) {
    UserApi.createFriendRequest(
    "1660bd85-1c13-42c0-955c-63b1eda4e90b", value);

                            }});},
                            icon: const Icon(Icons.send_rounded),
                            color: Theme.of(context).primaryColorDark))
                ), //Your widget he
                Spacer(flex: 1),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 40),
              Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Spacer(flex: 1),
                    Expanded(
                        flex: 3,
                        child: ElevatedButton(
                            child: Text("Friend List",
                                style: new TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color)),
                            style: ElevatedButton.styleFrom(
                              primary: !friendList
                                  ? Theme.of(context).primaryColorDark
                                  : Theme.of(context).accentColor,
                              padding: EdgeInsets.symmetric(
                                  horizontal: 3, vertical: 20),
                            ),
                            onPressed: () {
                              setState(() {
                                this.friendList = true;
                                print(friendList);
                              });
                            })),
                    Spacer(flex: 1),
                    Expanded(
                        flex: 3,
                        child: ElevatedButton(
                            child: Text("Friend Requests",
                                style: new TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color), textAlign:TextAlign.center),
                            style: ElevatedButton.styleFrom(
                              primary: friendList
                                  ? Theme.of(context).primaryColorDark
                                  : Theme.of(context).accentColor,
                              padding: EdgeInsets.symmetric(
                                  horizontal: 3, vertical: 20),
                            ),
                            onPressed: () {
                              setState(() {
                                this.friendList = false;
                                print(friendList);
                              });
                            })),
                    Spacer(flex: 1),
                  ]),
              SizedBox(height: MediaQuery.of(context).size.height / 50),
              Container(
                  height: MediaQuery.of(context).size.height * 0.65,
                  child: friendList! ? getFriends() : getFriendRequests()),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class getFriends extends StatelessWidget {
  BuildContext? c;

  getFriends();

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) =>
            FriendModel("1660bd85-1c13-42c0-955c-63b1eda4e90b"),
        child: Consumer<FriendModel>(builder: (context, friendModel, child) {
          if (friendModel.friends == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (friendModel.friends!.length > 0) {
            return Expanded(
                flex: 2,
                child: ListView(children: [
                  ...List.generate(
                      friendModel.friends!.length,
                      (index) => Dismissible(
                          background: Container(
                            // color: Theme.of(context).primaryColor,
                            //   margin: const EdgeInsets.all(5),
                            padding: EdgeInsets.all(
                                MediaQuery.of(context).size.height / 60),
                            child: Row(
                              children: [
                                new Spacer(),
                                Icon(Icons.delete,
                                    color: Theme.of(context).accentColor,
                                    size: 35 *
                                        MediaQuery.of(context).textScaleFactor),
                              ],
                            ),
                          ),
                          direction: DismissDirection.endToStart,
                          key:
                              Key(friendModel.friends!.elementAt(index).userID),
                          child: Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                onTap:(){
                                  Navigator.pushReplacement(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              DirectChat(friendModel.friends!.elementAt(index))));
                                },
                                  hoverColor:
                                      Theme.of(context).primaryColorLight,
                                  child: Container(
                                    child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 4,
                                          child: ListTile(
                                            title: Text(
                                                friendModel.friends!
                                                    .elementAt(index)
                                                    .username,
                                                style: TextStyle(
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ))),
                          confirmDismiss: (DismissDirection direction) async {
                            return await showDialog(
                              context: context,
                              builder: (BuildContext context) {
                                return AlertDialog(
                                  backgroundColor:
                                      Theme.of(context).primaryColorDark,
                                  title: Text(
                                    'Confirm Removal',
                                    style: TextStyle(
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color),
                                  ),
                                  content: Text(
                                    'Are you sure you want to remove ' +
                                        friendModel.friends!
                                            .elementAt(index)
                                            .username +
                                        ' as your friend?',
                                    style: TextStyle(
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color),
                                  ),
                                  actions: <Widget>[
                                    FlatButton(
                                        onPressed: () =>
                                            Navigator.of(context).pop(true),
                                        child: Text('Remove',
                                            style: TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color))),
                                    FlatButton(
                                      onPressed: () =>
                                          Navigator.of(context).pop(false),
                                      child: Text("Cancel",
                                          style: TextStyle(
                                              color: Theme.of(context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color)),
                                    ),
                                  ],
                                );
                              },
                            );
                          },
                          onDismissed: (direction) {
                            Provider.of<FriendModel>(context, listen: false)
                                .deleteFriend(
                                    "1660bd85-1c13-42c0-955c-63b1eda4e90b",
                                    friendModel.friends!
                                        .elementAt(index)
                                        .userID);
                          }))
                ]));
          } else {
            return Center(
                child: Text("You have no friends. That's so sad.",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        }));
  }
}

class getFriendRequests extends StatelessWidget {
  BuildContext? c;

  getFriendRequests();

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context)=>

            FriendRequestModel("1660bd85-1c13-42c0-955c-63b1eda4e90b"),
        child: Consumer<FriendRequestModel>(
            builder: (context, friendModel, child) {
          this.c = context;
          if (friendModel.friends == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (friendModel.friends!.length > 0) {
            return Expanded(
                flex: 2,
                child: ListView(children: [
                  ...List.generate(
                    friendModel.friends!.length,
                    (index) => Card(
                        color: Theme.of(context).primaryColorDark,
                        child: InkWell(
                            hoverColor: Theme.of(context).primaryColorLight,
                            child: Container(
                              child: Row(
                                children: <Widget>[
                                  Expanded(
                                    flex: 4,
                                    child: ListTile(
                                      title: Text(
                                          friendModel.friends!
                                              .elementAt(index)
                                              .requester,
                                          style: TextStyle(
                                              fontSize: 25 *
                                                  MediaQuery.of(context)
                                                      .textScaleFactor,
                                              fontWeight: FontWeight.bold,
                                              color: Theme.of(context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color)),
                                      trailing: Row(
                                        mainAxisSize: MainAxisSize.min,
                                        children: [
                                          Container(
                                              width: MediaQuery.of(context)
                                                      .size
                                                      .width *
                                                  0.05,
                                              decoration: BoxDecoration(
                                                  color: Theme.of(context)
                                                      .accentColor,
                                                  shape: BoxShape.circle),
                                              child: IconButton(
                                                  onPressed: () {
                                                    Provider.of<FriendRequestModel>(
                                                            context,
                                                            listen: false)
                                                        .acceptFriendRequest(
                                                            friendModel.friends!
                                                                .elementAt(
                                                                    index)
                                                                .id);
                                                  },
                                                  icon: const Icon(Icons.check),
                                                  color: Theme.of(context)
                                                      .primaryColorDark)),
                                          Container(
                                              width: MediaQuery.of(context)
                                                      .size
                                                      .width *
                                                  0.05,
                                              decoration: BoxDecoration(
                                                  color: Theme.of(context)
                                                      .accentColor,
                                                  shape: BoxShape.circle),
                                              child: IconButton(
                                                  onPressed: () {
                                                    showDialog(
                                                        context: context,
                                                        builder: (BuildContext
                                                            context) {
                                                          return AlertDialog(
                                                              backgroundColor:
                                                                  Theme.of(
                                                                          context)
                                                                      .primaryColorDark,
                                                              title: Text(
                                                                'Decline',
                                                                style: TextStyle(
                                                                    color: Theme.of(
                                                                            context)
                                                                        .textTheme
                                                                        .bodyText1!
                                                                        .color),
                                                              ),
                                                              content: Text(
                                                                'Are you sure you want to decline ' +
                                                                    friendModel
                                                                        .friends!
                                                                        .elementAt(
                                                                            index)
                                                                        .requester +
                                                                    '\'s friend request?',
                                                                style: TextStyle(
                                                                    color: Theme.of(
                                                                            context)
                                                                        .textTheme
                                                                        .bodyText1!
                                                                        .color),
                                                              ),
                                                              actions: <Widget>[
                                                                FlatButton(
                                                                    onPressed:
                                                                        () {
                                                                      Provider.of<FriendRequestModel>(this.c!, listen: false).deleteFriendRequest(friendModel
                                                                          .friends!
                                                                          .elementAt(
                                                                              index)
                                                                          .id);
                                                                      Navigator.of(
                                                                              context)
                                                                          .pop();
                                                                    },
                                                                    child: Text(
                                                                        'Decline',
                                                                        style: TextStyle(
                                                                            color:
                                                                                Theme.of(context).textTheme.bodyText1!.color))),
                                                                FlatButton(
                                                                  onPressed: () =>
                                                                      Navigator.of(
                                                                              context)
                                                                          .pop(),
                                                                  child: Text(
                                                                      "Cancel",
                                                                      style: TextStyle(
                                                                          color: Theme.of(context)
                                                                              .textTheme
                                                                              .bodyText1!
                                                                              .color)),
                                                                )
                                                              ]);
                                                        });
                                                  },
                                                  icon: const Icon(Icons.close),
                                                  color: Theme.of(context)
                                                      .primaryColorDark)),
                                        ],
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ))),
                  )
                ]));
          } else {
            return Center(
                child: Text("No one wants to be your friend. That's so sad.",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        }));
  }
}
