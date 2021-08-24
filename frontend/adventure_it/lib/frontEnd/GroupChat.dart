import 'dart:async';

import 'package:adventure_it/Providers/chat_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:grouped_list/grouped_list.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';

class GroupChat extends StatelessWidget {
  Adventure? adventure;
  final messageController = TextEditingController();

  GroupChat(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => GroupChatModel(adventure!),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text(adventure!.name + "\'s Chat",
                        style: new TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color))),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Expanded(
                      child: MessageList(adventure!)),
                SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Row(
                    mainAxisSize: MainAxisSize.min,
                      children: [
                    Spacer(),
                    Expanded(
                      flex: 2,
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
                                            AdventurePage(adventure)));
                              },
                              icon:
                                  const Icon(Icons.arrow_back_ios_new_rounded),
                              color: Theme.of(context).primaryColorDark)),
                    ),
                    Spacer(),
                    Expanded(
                      flex: 12,
                      child: TextField(
                          controller: messageController,
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
                    Spacer(),
                    Expanded(
                        flex: 2,
                        child: Container(
                            decoration: BoxDecoration(
                                color: Theme.of(context).accentColor,
                                shape: BoxShape.circle),
                            child: IconButton(
                                onPressed: () {
                                  Provider.of<GroupChatModel>(context, listen: false).sendMessage(messageController.text);
                                },
                                icon: const Icon(Icons.send_rounded),
                                color: Theme.of(context)
                                    .primaryColorDark))),
                    Spacer(),//Your widget here,
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ])));
  }
}

class MessageList extends StatefulWidget {
  Adventure? currentAdventure;

  MessageList(Adventure a) {
    currentAdventure = a;
  }

  @override
  _MessageList createState() => _MessageList(currentAdventure);
}

class _MessageList extends State<MessageList> {
  Adventure? a;
  final _scrollController = ScrollController();

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


  _MessageList(Adventure? adventure) {
    this.a = adventure;
  }

  String getTime(DateTime x)
  {
    String toReturn=x.hour.toString()+":";

    if(x.minute<10)
      {
        toReturn=toReturn+"0"+x.minute.toString();
      }
    else
      {
        toReturn=toReturn+x.minute.toString();
      }
    return toReturn;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<GroupChatModel>(builder: (context, chatModel, child) {
      if (chatModel.messages == null&&chatModel.chat==null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (chatModel.messages!.length > 0) {
        WidgetsBinding.instance!.addPostFrameCallback((_){_scrollController.animateTo(
            _scrollController.position.maxScrollExtent,
            duration: Duration(seconds: 3),
            curve: Curves.fastOutSlowIn);});
        return Container(
            height: double.infinity,
            child: GroupedListView<dynamic, String>(
                controller: _scrollController,
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
                          color: Theme.of(context).textTheme.bodyText1!.color),
                    )),
                indexedItemBuilder: (context, element, index) {
                  print("here"+index.toString());
                    return Card(
                        color: Theme.of(context).primaryColorDark,
                        child: ListTile(
                          title: Row(children: [
                            Expanded(child: Text(
                                chatModel.messages!
                                    .elementAt(index)
                                    .sender
                                    .username,
                                textAlign: TextAlign.left,
                                style: TextStyle(
                                  fontSize: 15 *
                                      MediaQuery.of(context).textScaleFactor,
                                  fontWeight: FontWeight.bold,
                                  color: HSLColor.fromAHSL(
                                              1,
                                              chatModel.chat!.colors.elementAt(chatModel.chat!.colors.indexWhere((element){return element.userID==chatModel.messages!.elementAt(index).sender.userID;})).color* 1.0,
                                              1,
                                              0.7)
                                          .toColor(),
                                ))),
                            Expanded( child:Text(
                                getTime(DateTime.parse(chatModel.messages!
                                    .elementAt(index)
                                    .timestamp)),
                                textAlign: TextAlign.right,
                                style: TextStyle(
                                    fontSize: 15 *
                                        MediaQuery.of(context).textScaleFactor,
                                    fontWeight: FontWeight.bold,
                                    color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color))
                            )]),
                          subtitle: Text(
                              chatModel.messages!.elementAt(index).message,
                              style: TextStyle(
                                  fontSize: 15 *
                                      MediaQuery.of(context).textScaleFactor,
                                  color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)),

                  ));
                }));
      } else {
        return Center(
            child: Text("Let's get to chatting!",
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}
