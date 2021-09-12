import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/Providers/friends_model.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'DirectChat.dart';
import 'Navbar.dart';

//User can see his/ her friends
class Friends extends StatefulWidget {
  Friends();

  @override
  FriendsPage createState() => FriendsPage();
}

class FriendsPage extends State<Friends> with SingleTickerProviderStateMixin {
  UserProfile? user;
  bool friendList = true;
  UserApi _userApi = UserApi.getInstance();
  TabController? tabs;

  FriendsPage();

  @override
  void initState() {
    super.initState();
    tabs = new TabController(vsync: this, length: 2);
  }

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
            iconTheme: IconThemeData(
                color: Theme.of(context).textTheme.bodyText1!.color),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Container(
            child: Column(
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
                    flex: 2,
                    child: Container(
                        decoration: BoxDecoration(
                            color: Theme.of(context).accentColor,
                            shape: BoxShape.circle),
                        child: IconButton(
                            onPressed: () {
                              _userApi
                                  .searchUsername(usernameController.text)
                                  .then((value) {
                                if (value.compareTo("") != 0) {
                                  _userApi.createFriendRequest(
                                      UserApi.getInstance()
                                          .getUserProfile()!
                                          .userID,
                                      value);
                                }
                              });
                            },
                            icon: const Icon(Icons.person_search),
                            iconSize: 30,
                            color: Theme.of(context)
                                .primaryColorDark))), //Your widget he
                Spacer(flex: 1),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 40),
              Container(
                  child: TabBar(
                controller: tabs,
                labelColor: Theme.of(context).accentColor,
                unselectedLabelColor:
                    Theme.of(context).textTheme.bodyText1!.color,
                indicatorSize: TabBarIndicatorSize.tab,
                tabs: [
                  Tab(
                      icon: Icon(Icons.people_outline_rounded),
                      text: "Friends"),
                  Tab(
                      icon: Icon(Icons.person_add_alt_1_rounded),
                      text: "Friend Requests"),
                ],
              )),
              SizedBox(height: MediaQuery.of(context).size.height / 40),
              Expanded(
                  child: TabBarView(controller: tabs, children: <Widget>[
                Container(
                  child: GetFriends(context),
                ),
                Container(
                  child: GetFriendRequests(context),
                )
              ]))
            ])));
  }
}

class GetFriends extends StatelessWidget {
  BuildContext? c;

  GetFriends(context) {
    this.c = context;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) =>
            FriendModel(UserApi.getInstance().getUserProfile()!.userID),
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
                                  onTap: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) => DirectChat(
                                                friendModel.friends!
                                                    .elementAt(index))));
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
                                    TextButton(
                                        onPressed: () =>
                                            Navigator.of(context).pop(true),
                                        child: Text('Remove',
                                            style: TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color))),
                                    TextButton(
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
                                    UserApi.getInstance()
                                        .getUserProfile()!
                                        .userID,
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

class GetFriendRequests extends StatelessWidget {
  BuildContext? c;

  GetFriendRequests(context) {
    this.c = context;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) =>
            FriendRequestModel(UserApi.getInstance().getUserProfile()!.userID),
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
                                      trailing: ButtonBar(
                                        mainAxisSize: MainAxisSize.min,
                                        children: [
                                          IconButton(
                                              onPressed: () {
                                                Provider.of<FriendRequestModel>(
                                                        context,
                                                        listen: false)
                                                    .acceptFriendRequest(
                                                        friendModel.friends!
                                                            .elementAt(index)
                                                            .id);
                                              },
                                              icon: Icon(Icons.check),
                                              iconSize: 20,
                                              color: Theme.of(context)
                                                  .accentColor),
                                          IconButton(
                                              onPressed: () {
                                                showDialog(
                                                    context: context,
                                                    builder:
                                                        (BuildContext context) {
                                                      return AlertDialog(
                                                          backgroundColor: Theme
                                                                  .of(context)
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
                                                            TextButton(
                                                                onPressed: () {
                                                                  Provider.of<FriendRequestModel>(
                                                                          this
                                                                              .c!,
                                                                          listen:
                                                                              false)
                                                                      .deleteFriendRequest(friendModel
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
                                                                        color: Theme.of(context)
                                                                            .textTheme
                                                                            .bodyText1!
                                                                            .color))),
                                                            TextButton(
                                                              onPressed: () =>
                                                                  Navigator.of(
                                                                          context)
                                                                      .pop(),
                                                              child: Text(
                                                                  "Cancel",
                                                                  style: TextStyle(
                                                                      color: Theme.of(
                                                                              context)
                                                                          .textTheme
                                                                          .bodyText1!
                                                                          .color)),
                                                            )
                                                          ]);
                                                    });
                                              },
                                              icon: Icon(Icons.close),
                                              iconSize: 20,
                                              color: Theme.of(context)
                                                  .accentColor),
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
