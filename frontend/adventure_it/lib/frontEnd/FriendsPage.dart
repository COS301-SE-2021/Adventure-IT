import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/Providers/friends_model.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import '../constants.dart';
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
                Spacer(flex: 2),
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
                                  .searchUsername(
                                      usernameController.text, context)
                                  .then((value) {
                                if (value.compareTo("") != 0) {
                                  _userApi.createFriendRequest(
                                      UserApi.getInstance()
                                          .getUserProfile()!
                                          .userID,
                                      value,
                                      context);
                                }
                              });
                            },
                            icon: const Icon(Icons.person_search),
                            iconSize: 30,
                            color: Theme.of(context)
                                .primaryColorDark))), //Your widget he
                Spacer(flex: 2),
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
        create: (context) => FriendModel(
            UserApi.getInstance().getUserProfile()!.userID, context),
        child: Consumer<FriendModel>(builder: (context, friendModel, child) {
          BuildContext c = context;
          if (friendModel.friends == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (friendModel.friends!.length > 0) {
            return Column(children: [
              Expanded(
                  flex: 2,
                  child: Container(
                      width: MediaQuery.of(context).size.width <= 500
                          ? MediaQuery.of(context).size.width
                          : MediaQuery.of(context).size.width * 0.9,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width <= 500
                              ? 0
                              : MediaQuery.of(context).size.width * 0.05),
                      child: ListView(children: [
                        ...List.generate(
                            friendModel.friends!.length,
                            (index) => Card(
                                color: Theme.of(context).primaryColorDark,
                                child: InkWell(
                                  hoverColor:
                                      Theme.of(context).primaryColorLight,
                                  child: Container(
                                    child: Row(children: <Widget>[
                                      Expanded(
                                        flex: 4,
                                        child: ListTile(
                                          leading: CachedNetworkImage(
                                              useOldImageOnUrlChange: true,
                                              imageUrl: "http://" +
                                                  mainApi +
                                                  "user/viewPicture/" +
                                                  friendModel.friends!
                                                      .elementAt(index)
                                                      .profileID,
                                              imageBuilder: (context,
                                                      imageProvider) =>
                                                  Container(
                                                      width: 70,
                                                      height: 70,
                                                      decoration:
                                                          new BoxDecoration(
                                                              border:
                                                                  Border.all(
                                                                color: Theme.of(
                                                                        context)
                                                                    .accentColor,
                                                                width: 3,
                                                              ),
                                                              shape: BoxShape
                                                                  .circle,
                                                              image: DecorationImage(
                                                                  fit: BoxFit
                                                                      .fill,
                                                                  image:
                                                                      imageProvider))),
                                              placeholder:
                                                  (context, url) => Container(
                                                      width: 70,
                                                      height: 70,
                                                      decoration:
                                                          new BoxDecoration(
                                                              border:
                                                                  Border.all(
                                                                color: Theme.of(
                                                                        context)
                                                                    .accentColor,
                                                                width: 3,
                                                              ),
                                                              shape: BoxShape
                                                                  .circle,
                                                              image: DecorationImage(
                                                                  fit: BoxFit
                                                                      .fitWidth,
                                                                  image: AssetImage(
                                                                      "pfp.png")))),
                                              errorWidget: (context, url, error) =>
                                                  Container(
                                                      width: 70,
                                                      height: 70,
                                                      decoration:
                                                          new BoxDecoration(
                                                              border:
                                                                  Border.all(
                                                                color: Theme.of(
                                                                        context)
                                                                    .accentColor,
                                                                width: 3,
                                                              ),
                                                              shape: BoxShape
                                                                  .circle,
                                                              image: DecorationImage(
                                                                  fit: BoxFit
                                                                      .fitWidth,
                                                                  image: AssetImage("pfp.png"))))),
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
                                      PopupMenuButton(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color,
                                          onSelected: (value) {
                                            if (value == 1) {
                                              showDialog(
                                                context: context,
                                                builder:
                                                    (BuildContext context) {
                                                  return AlertDialog(
                                                    backgroundColor:
                                                        Theme.of(context)
                                                            .primaryColorDark,
                                                    title: Text(
                                                      'Confirm Removal',
                                                      style: TextStyle(
                                                          color:
                                                              Theme.of(context)
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
                                                          color:
                                                              Theme.of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color),
                                                    ),
                                                    actions: <Widget>[
                                                      TextButton(
                                                          onPressed: () {
                                                            Provider.of<FriendModel>(
                                                                    c,
                                                                    listen:
                                                                        false)
                                                                .deleteFriend(
                                                                    UserApi.getInstance()
                                                                        .getUserProfile()!
                                                                        .userID,
                                                                    friendModel
                                                                        .friends!
                                                                        .elementAt(
                                                                            index)
                                                                        .userID);
                                                            Navigator.of(
                                                                    context)
                                                                .pop();
                                                          },
                                                          child: Text('Remove',
                                                              style: TextStyle(
                                                                  color: Theme.of(
                                                                          context)
                                                                      .textTheme
                                                                      .bodyText1!
                                                                      .color))),
                                                      TextButton(
                                                        onPressed: () {
                                                          Navigator.of(context)
                                                              .pop();
                                                        },
                                                        child: Text("Cancel",
                                                            style: TextStyle(
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText1!
                                                                    .color)),
                                                      ),
                                                    ],
                                                  );
                                                },
                                              );
                                            }

                                            if (value == 2) {
                                              Navigator.pushReplacement(
                                                  context,
                                                  MaterialPageRoute(
                                                      builder: (context) =>
                                                          DirectChat(friendModel
                                                              .friends!
                                                              .elementAt(
                                                                  index))));
                                            }
                                          },
                                          itemBuilder: (context) => [
                                                PopupMenuItem(
                                                    value: 2,
                                                    child: Row(
                                                      children: <Widget>[
                                                        Padding(
                                                          padding:
                                                              const EdgeInsets
                                                                  .all(5),
                                                          child: Icon(
                                                              Icons
                                                                  .chat_rounded,
                                                              color: Theme.of(
                                                                      context)
                                                                  .textTheme
                                                                  .bodyText2!
                                                                  .color),
                                                        ),
                                                        Text("Chat",
                                                            style: TextStyle(
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText2!
                                                                    .color))
                                                      ],
                                                    )),
                                                PopupMenuItem(
                                                    value: 1,
                                                    child: Row(
                                                      children: <Widget>[
                                                        Padding(
                                                          padding:
                                                              const EdgeInsets
                                                                  .all(5),
                                                          child: Icon(
                                                              Icons
                                                                  .person_remove_rounded,
                                                              color: Theme.of(
                                                                      context)
                                                                  .textTheme
                                                                  .bodyText2!
                                                                  .color),
                                                        ),
                                                        Text("Unfriend",
                                                            style: TextStyle(
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText2!
                                                                    .color))
                                                      ],
                                                    ))
                                              ]),
                                    ]),
                                  ),
                                ))),
                      ])))
            ]);
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

  String getUserID(String requester, context) {
    UserApi.getInstance().searchUsername(requester, context).then((value) {
      UserApi.getInstance().findUser(value, context).then((user) {
        return "http://" + mainApi + "user/viewPicture/" + user.profileID;
      });
    });
    return "";
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => FriendRequestModel(
            UserApi.getInstance().getUserProfile()!.userID, context),
        child: Consumer<FriendRequestModel>(
            builder: (context, friendModel, child) {
          this.c = context;
          if (friendModel.friends == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (friendModel.friends!.length > 0) {
            return Column(children: [
              Expanded(
                  flex: 2,
                  child: Container(
                      width: MediaQuery.of(context).size.width <= 500
                          ? MediaQuery.of(context).size.width
                          : MediaQuery.of(context).size.width * 0.9,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width <= 500
                              ? 0
                              : MediaQuery.of(context).size.width * 0.05),
                      child: ListView(children: [
                        ...List.generate(
                          friendModel.friends!.length,
                          (index) => Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                  hoverColor:
                                      Theme.of(context).primaryColorLight,
                                  child: Container(
                                    child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 4,
                                          child: ListTile(
                                            leading: CachedNetworkImage(
                                                imageUrl: getUserID(
                                                    friendModel.friends!
                                                        .elementAt(index)
                                                        .requester,
                                                    context),
                                                imageBuilder: (context,
                                                        imageProvider) =>
                                                    Container(
                                                        width: 70,
                                                        height: 70,
                                                        decoration:
                                                            new BoxDecoration(
                                                                border:
                                                                    Border.all(
                                                                  color: Theme.of(
                                                                          context)
                                                                      .accentColor,
                                                                  width: 3,
                                                                ),
                                                                shape: BoxShape
                                                                    .circle,
                                                                image: DecorationImage(
                                                                    fit: BoxFit
                                                                        .fill,
                                                                    image:
                                                                        imageProvider))),
                                                placeholder: (context, url) =>
                                                    Container(
                                                        width: 70,
                                                        height: 70,
                                                        decoration:
                                                            new BoxDecoration(
                                                                border:
                                                                    Border.all(
                                                                  color: Theme.of(
                                                                          context)
                                                                      .accentColor,
                                                                  width: 3,
                                                                ),
                                                                shape: BoxShape
                                                                    .circle,
                                                                image: DecorationImage(
                                                                    fit: BoxFit
                                                                        .fill,
                                                                    image: AssetImage(
                                                                        "pfp.png")))),
                                                errorWidget: (context, url,
                                                        error) =>
                                                    Container(
                                                        width: 70,
                                                        height: 70,
                                                        decoration: new BoxDecoration(
                                                            border: Border.all(
                                                              color: Theme.of(
                                                                      context)
                                                                  .accentColor,
                                                              width: 3,
                                                            ),
                                                            shape: BoxShape.circle,
                                                            image: DecorationImage(fit: BoxFit.fill, image: AssetImage("pfp.png"))))),
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
                                                              friendModel
                                                                  .friends!
                                                                  .elementAt(
                                                                      index)
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
                                                                actions: <
                                                                    Widget>[
                                                                  TextButton(
                                                                      onPressed:
                                                                          () {
                                                                        Provider.of<FriendRequestModel>(this.c!, listen: false).deleteFriendRequest(friendModel
                                                                            .friends!
                                                                            .elementAt(index)
                                                                            .id);
                                                                        Navigator.of(context)
                                                                            .pop();
                                                                      },
                                                                      child: Text(
                                                                          'Decline',
                                                                          style:
                                                                              TextStyle(color: Theme.of(context).textTheme.bodyText1!.color))),
                                                                  TextButton(
                                                                    onPressed: () =>
                                                                        Navigator.of(context)
                                                                            .pop(),
                                                                    child: Text(
                                                                        "Cancel",
                                                                        style: TextStyle(
                                                                            color:
                                                                                Theme.of(context).textTheme.bodyText1!.color)),
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
                      ])))
            ]);
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
