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
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';

class Friends extends StatefulWidget {
  UserProfile? user;

  Friends() {


  }

  @override
  FriendsPage createState() => FriendsPage();
}

class FriendsPage extends State<Friends> {
  UserProfile? user;
  bool? friendList;
  FriendsPage() {
    friendList=true;
    UserApi.getUserByUUID("1660bd85-1c13-42c0-955c-63b1eda4e90b").then((value)
    {
      this.user=value;
      print("here");
      print(this.user);
    });
  }

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
              Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Spacer(flex: 2),
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
                              primary: !friendList!
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
                    Spacer(flex: 2),
                    Expanded(
                        flex: 3,
                        child: ElevatedButton(
                            child: Text("Friend Requests",
                                style: new TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color)),
                            style: ElevatedButton.styleFrom(
                              primary: friendList!
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
                    Spacer(flex: 2),
                  ]),
              SizedBox(height: MediaQuery.of(context).size.height / 50),
              Container(
                  height: MediaQuery.of(context).size.height * 0.75,
                  child: friendList!
                      ? getFriends(this.user)
                      : getFriendRequests(this.user)
              ),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class getFriends extends StatelessWidget {
  UserProfile? user;
  BuildContext? c;

  getFriends(user) {
    this.user = user;
  }

  @override
  Widget build(BuildContext context) {
    return Container();
    // return ChangeNotifierProvider(
    //     create: (context) => FriendModel(this.user!.userID),
    //     child: Consumer<FriendModel>(builder: (context, friendModel, child) {
    //       this.c = context;
    //       if (friendModel.friends == null) {
    //         return Center(
    //             child: CircularProgressIndicator(
    //                 valueColor: new AlwaysStoppedAnimation<Color>(
    //                     Theme.of(context).accentColor)));
    //       } else if (friendModel.friends!.length > 0) {
    //         return Expanded(
    //             flex: 2,
    //             child: ListView(children: [
    //               ...List.generate(
    //                   friendModel.friends!.length,
    //                   (index) => Dismissible(
    //                       background: Container(
    //                         // color: Theme.of(context).primaryColor,
    //                         //   margin: const EdgeInsets.all(5),
    //                         padding: EdgeInsets.all(
    //                             MediaQuery.of(context).size.height / 60),
    //                         child: Row(
    //                           children: [
    //                             new Spacer(),
    //                             Icon(Icons.delete,
    //                                 color: Theme.of(context).accentColor,
    //                                 size: 35 *
    //                                     MediaQuery.of(context).textScaleFactor),
    //                           ],
    //                         ),
    //                       ),
    //                       direction: DismissDirection.endToStart,
    //                       key:
    //                           Key(friendModel.friends!.elementAt(index).userID),
    //                       child: Card(
    //                           color: Theme.of(context).primaryColorDark,
    //                           child: InkWell(
    //                               hoverColor:
    //                                   Theme.of(context).primaryColorLight,
    //                               child: Container(
    //                                 child: Row(
    //                                   children: <Widget>[
    //                                     Expanded(
    //                                       flex: 4,
    //                                       child: ListTile(
    //                                         title: Text(
    //                                             friendModel.friends!
    //                                                 .elementAt(index)
    //                                                 .username,
    //                                             style: TextStyle(
    //                                                 fontSize: 25 *
    //                                                     MediaQuery.of(context)
    //                                                         .textScaleFactor,
    //                                                 fontWeight: FontWeight.bold,
    //                                                 color: Theme.of(context)
    //                                                     .textTheme
    //                                                     .bodyText1!
    //                                                     .color)),
    //                                       ),
    //                                     ),
    //                                   ],
    //                                 ),
    //                               ))),
    //                       confirmDismiss: (DismissDirection direction) async {
    //                         return await showDialog(
    //                           context: context,
    //                           builder: (BuildContext context) {
    //                             return AlertDialog(
    //                               backgroundColor:
    //                                   Theme.of(context).primaryColorDark,
    //                               title: Text(
    //                                 'Confirm Removal',
    //                                 style: TextStyle(
    //                                     color: Theme.of(context)
    //                                         .textTheme
    //                                         .bodyText1!
    //                                         .color),
    //                               ),
    //                               content: Text(
    //                                 'Are you sure you want to remove ' +
    //                                     friendModel.friends!
    //                                         .elementAt(index)
    //                                         .username +
    //                                     ' as your friend?',
    //                                 style: TextStyle(
    //                                     color: Theme.of(context)
    //                                         .textTheme
    //                                         .bodyText1!
    //                                         .color),
    //                               ),
    //                               actions: <Widget>[
    //                                 FlatButton(
    //                                     onPressed: () =>
    //                                         Navigator.of(context).pop(true),
    //                                     child: Text('Remove',
    //                                         style: TextStyle(
    //                                             color: Theme.of(context)
    //                                                 .textTheme
    //                                                 .bodyText1!
    //                                                 .color))),
    //                                 FlatButton(
    //                                   onPressed: () =>
    //                                       Navigator.of(context).pop(false),
    //                                   child: Text("Cancel",
    //                                       style: TextStyle(
    //                                           color: Theme.of(context)
    //                                               .textTheme
    //                                               .bodyText1!
    //                                               .color)),
    //                                 ),
    //                               ],
    //                             );
    //                           },
    //                         );
    //                       },
    //                       onDismissed: (direction) {
    //                         Provider.of<FriendModel>(context, listen: false)
    //                             .deleteFriend(
    //                                 this.user!.userID,
    //                                 friendModel.friends!
    //                                     .elementAt(index)
    //                                     .userID);
    //                       }))
    //             ]));
    //       } else {
    //         return Center(
    //             child: Text("You have no friends. That's so sad.",
    //                 textAlign: TextAlign.center,
    //                 style: TextStyle(
    //                     fontSize: 30 * MediaQuery.of(context).textScaleFactor,
    //                     color: Theme.of(context).textTheme.bodyText1!.color)));
    //       }
    //     }));
  }
}

class getFriendRequests extends StatelessWidget {
  UserProfile? user;
  BuildContext? c;

  getFriendRequests(user) {
    this.user = user;
  }

  @override
  Widget build(BuildContext context) {
    return Container();
    // return ChangeNotifierProvider(
    //     create: (context) => FriendRequestModel(this.user!.userID),
    //     child: Consumer<FriendRequestModel>(
    //         builder: (context, friendModel, child) {
    //       this.c = context;
    //       if (friendModel.friends == null ||
    //           friendModel.friendProfiles == null) {
    //         return Center(
    //             child: CircularProgressIndicator(
    //                 valueColor: new AlwaysStoppedAnimation<Color>(
    //                     Theme.of(context).accentColor)));
    //       } else if (friendModel.friends!.length > 0 &&
    //           friendModel.friendProfiles!.length > 0) {
    //         return Expanded(
    //             flex: 2,
    //             child: ListView(children: [
    //               ...List.generate(
    //                 friendModel.friends!.length,
    //                 (index) => Card(
    //                     color: Theme.of(context).primaryColorDark,
    //                     child: InkWell(
    //                         hoverColor: Theme.of(context).primaryColorLight,
    //                         child: Container(
    //                           child: Row(
    //                             children: <Widget>[
    //                               Expanded(
    //                                 flex: 4,
    //                                 child: ListTile(
    //                                   title: Text(
    //                                       friendModel.friendProfiles!
    //                                           .elementAt(index)
    //                                           .username,
    //                                       style: TextStyle(
    //                                           fontSize: 25 *
    //                                               MediaQuery.of(context)
    //                                                   .textScaleFactor,
    //                                           fontWeight: FontWeight.bold,
    //                                           color: Theme.of(context)
    //                                               .textTheme
    //                                               .bodyText1!
    //                                               .color)),
    //                                   trailing: Row(
    //                                     children: [
    //                                       Container(
    //                                           decoration: BoxDecoration(
    //                                               color: Theme.of(context)
    //                                                   .accentColor,
    //                                               shape: BoxShape.circle),
    //                                           child: IconButton(
    //                                               onPressed: () {
    //                                                 Provider.of<FriendRequestModel>(
    //                                                         context,
    //                                                         listen: false)
    //                                                     .acceptFriendRequest(
    //                                                         friendModel.friends!
    //                                                             .elementAt(
    //                                                                 index)
    //                                                             .id);
    //                                               },
    //                                               icon: const Icon(Icons.check),
    //                                               color: Theme.of(context)
    //                                                   .primaryColorDark)),
    //                                       Container(
    //                                           decoration: BoxDecoration(
    //                                               color: Theme.of(context)
    //                                                   .accentColor,
    //                                               shape: BoxShape.circle),
    //                                           child: IconButton(
    //                                               onPressed: () {
    //                                                 showDialog(
    //                                                     context: context,
    //                                                     builder: (BuildContext
    //                                                         context) {
    //                                                       return AlertDialog(
    //                                                           backgroundColor:
    //                                                               Theme.of(
    //                                                                       context)
    //                                                                   .primaryColorDark,
    //                                                           title: Text(
    //                                                             'Confirm Refusal',
    //                                                             style: TextStyle(
    //                                                                 color: Theme.of(
    //                                                                         context)
    //                                                                     .textTheme
    //                                                                     .bodyText1!
    //                                                                     .color),
    //                                                           ),
    //                                                           content: Text(
    //                                                             'Are you sure you want to refuse ' +
    //                                                                 friendModel
    //                                                                     .friendProfiles!
    //                                                                     .elementAt(
    //                                                                         index)
    //                                                                     .username +
    //                                                                 '\'s friend request?',
    //                                                             style: TextStyle(
    //                                                                 color: Theme.of(
    //                                                                         context)
    //                                                                     .textTheme
    //                                                                     .bodyText1!
    //                                                                     .color),
    //                                                           ),
    //                                                           actions: <Widget>[
    //                                                             FlatButton(
    //                                                                 onPressed: () =>
    //                                                                     Navigator.of(context).pop(
    //                                                                         true),
    //                                                                 child: Text(
    //                                                                     'Remove',
    //                                                                     style: TextStyle(
    //                                                                         color:
    //                                                                             Theme.of(context).textTheme.bodyText1!.color))),
    //                                                             FlatButton(
    //                                                               onPressed: () =>
    //                                                                   Navigator.of(
    //                                                                           context)
    //                                                                       .pop(
    //                                                                           false),
    //                                                               child: Text(
    //                                                                   "Cancel",
    //                                                                   style: TextStyle(
    //                                                                       color: Theme.of(context)
    //                                                                           .textTheme
    //                                                                           .bodyText1!
    //                                                                           .color)),
    //                                                             )
    //                                                           ]);
    //                                                     });
    //                                               },
    //                                               icon: const Icon(Icons.close),
    //                                               color: Theme.of(context)
    //                                                   .primaryColorDark)),
    //                                     ],
    //                                   ),
    //                                 ),
    //                               ),
    //                             ],
    //                           ),
    //                         ))),
    //               )
    //             ]));
    //       } else {
    //         return Center(
    //             child: Text("No one wants to be your friend. That's so sad.",
    //                 textAlign: TextAlign.center,
    //                 style: TextStyle(
    //                     fontSize: 30 * MediaQuery.of(context).textScaleFactor,
    //                     color: Theme.of(context).textTheme.bodyText1!.color)));
    //       }
    //     }));
  }
}
