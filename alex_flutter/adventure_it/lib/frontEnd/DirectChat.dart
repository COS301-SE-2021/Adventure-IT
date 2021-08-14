
import 'package:adventure_it/Providers/chat_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/frontEnd/FriendsPage.dart';
import 'package:grouped_list/grouped_list.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';
class DirectChat extends StatelessWidget {
  UserProfile? user2;

 DirectChat(UserProfile user2) {
    this.user2=user2;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => DirectChatModel("1660bd85-1c13-42c0-955c-63b1eda4e90b",user2!.userID),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text("Chat with "+user2!.username,
                        style: new TextStyle(
                            color: Theme.of(context).textTheme.bodyText1!.color))),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Container(
                      height: MediaQuery.of(context).size.height * 0.80,
                      child: MessageList()),
                  Spacer(),
                  Row(children: [
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
                                            Friends()));
                              },
                              icon: const Icon(Icons.arrow_back_ios_new_rounded),
                              color: Theme.of(context).primaryColorDark)),
                    ),
                    Expanded(
                      flex: 8,
                      child: TextField(
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
                              hintText: 'Start typing...')),
                    ),
                    Expanded(
                      flex: 1,
                      child: Container(
                          decoration: BoxDecoration(
                              color: Theme.of(context).accentColor,
                              shape: BoxShape.circle),
                          child: IconButton(
                              onPressed: () {

                              },
                              icon: const Icon(Icons.send_rounded),
                              color: Theme.of(context).primaryColorDark))
                          ), //Your widget here,
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ])));
  }
}

class MessageList extends StatefulWidget {

  MessageList();

  @override
  _MessageList createState() => _MessageList();
}

class _MessageList extends State<MessageList> {

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

  _MessageList();

  @override
  Widget build(BuildContext context) {
    return
      Consumer<DirectChatModel>(builder: (context, chatModel, child) {
        if (chatModel.messages == null) {
          return Center(
              child: CircularProgressIndicator(
                  valueColor: new AlwaysStoppedAnimation<Color>(
                      Theme.of(context).accentColor)));
        } else if (chatModel.messages!.length > 0) {
          return Expanded(
              child: GroupedListView<dynamic, String>(
              physics: const AlwaysScrollableScrollPhysics(),
    elements: chatModel.messages!,
    groupBy: (element) =>
    DateTime.parse(element.timestamp).day.toString() +
    " " +
    months[DateTime.parse(element.timestamp).month - 1] +
    " " +
    DateTime.parse(element.timestamp).year.toString(),
    useStickyGroupSeparators: false,
    groupSeparatorBuilder: (String value) => Container(
    padding: const EdgeInsets.all(8.0),
    child: Text(
    value,
    textAlign: TextAlign.center,
    style: TextStyle(
    fontSize: 12,
    fontWeight: FontWeight.bold,
    color:
    Theme.of(context).textTheme.bodyText1!.color),
    )),
    indexedItemBuilder: (context, element, index) {
    return Container(
    key: Key(chatModel.messages!.elementAt(index).id),
    child: Card(
    color: chatModel.messages!.elementAt(index).sender.userID=="1660bd85-1c13-42c0-955c-63b1eda4e90b"?Theme.of(context).accentColor:Theme.of(context).primaryColorDark,
    child: ListTile(
    title: Row(children:[Text(
    chatModel.messages!
        .elementAt(index)
        .sender.username,
    textAlign: TextAlign.left,
    style: TextStyle(
    fontSize: 20 *
    MediaQuery.of(context)
        .textScaleFactor,
    fontWeight: FontWeight.bold,
    color: chatModel.messages!.elementAt(index).sender.userID=="1660bd85-1c13-42c0-955c-63b1eda4e90b"?Theme.of(context)
        .textTheme
        .bodyText2!
        .color: Theme.of(context)
        .textTheme
        .bodyText1!
        .color,
    )), Text(
    DateTime.parse(chatModel.messages!
        .elementAt(index)
        .timestamp).hour.toString()+":"+DateTime.parse(chatModel.messages!
        .elementAt(index)
        .timestamp).minute.toString(),
    textAlign: TextAlign.right,
    style: TextStyle(
    fontSize: 10 *
    MediaQuery.of(context)
        .textScaleFactor,
    fontWeight: FontWeight.bold,
    color:chatModel.messages!.elementAt(index).sender.userID=="1660bd85-1c13-42c0-955c-63b1eda4e90b"?Theme.of(context)
        .textTheme
        .bodyText2!
        .color: Theme.of(context)
        .textTheme
        .bodyText1!
        .color,))]),
    subtitle: Text(chatModel.messages!.elementAt(index).message,
    style: TextStyle(
    fontSize: 15 *
    MediaQuery.of(
    context)
        .textScaleFactor,
    color: chatModel.messages!.elementAt(index).sender.userID=="1660bd85-1c13-42c0-955c-63b1eda4e90b"?Theme.of(context)
        .textTheme
        .bodyText2!
        .color: Theme.of(context)
        .textTheme
        .bodyText1!
        .color,)),
    )),);}));
        } else {
          return Center(
              child: Text(
                  "Let's get to chatting!",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                      color: Theme.of(context).textTheme.bodyText1!.color)));
        }
      });
  }
}
