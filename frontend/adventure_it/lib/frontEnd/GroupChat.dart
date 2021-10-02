import 'package:adventure_it/api/userAPI.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:grouped_list/grouped_list.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/Providers/chat_model.dart';
import 'package:adventure_it/api/adventure.dart';
import '../constants.dart';
import 'AdventurePage.dart';
import 'InitializeFireFlutter.dart';
import 'Navbar.dart';

class GroupChat extends StatelessWidget {
  late final Adventure? adventure;
  final messageController = TextEditingController();

  GroupChat(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    GroupChatModel x = new GroupChatModel(adventure!, context);
    FlutterMessagingChangeNotifier.setGroupChatChangeNotifier(x);
    return ChangeNotifierProvider(
        create: (context) => x,
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text(adventure!.name + "\'s Chat",
                        style: new TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color))),
                iconTheme: IconThemeData(
                    color: Theme.of(context).textTheme.bodyText1!.color),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Expanded(child: MessageList(adventure!)),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Row(mainAxisSize: MainAxisSize.min, children: [
                    Spacer(),
                    Expanded(
                      flex: 2,
                      child: Container(
                          decoration: BoxDecoration(
                              color: Theme.of(context).accentColor,
                              shape: BoxShape.circle),
                          child: IconButton(
                              onPressed: () {
                                FlutterMessagingChangeNotifier
                                    .setGroupChatChangeNotifier(null);
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
                                  Provider.of<GroupChatModel>(context,
                                          listen: false)
                                      .sendMessage(messageController.text);
                                  messageController.clear();
                                },
                                icon: const Icon(Icons.send_rounded),
                                color: Theme.of(context).primaryColorDark))),
                    Spacer(), //Your widget here,
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ])));
  }
}

class MessageList extends StatefulWidget {
  late final Adventure? currentAdventure;

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

  String getTime(DateTime x) {
    String toReturn = x.hour.toString() + ":";

    if (x.minute < 10) {
      toReturn = toReturn + "0" + x.minute.toString();
    } else {
      toReturn = toReturn + x.minute.toString();
    }
    return toReturn;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<GroupChatModel>(builder: (context, chatModel, child) {
      if (chatModel.messages == null && chatModel.chat == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (chatModel.messages!.length > 0) {
        WidgetsBinding.instance!.addPostFrameCallback((_) {
          _scrollController.animateTo(
              _scrollController.position.maxScrollExtent,
              duration: Duration(seconds: 3),
              curve: Curves.fastOutSlowIn);
        });
        return Container(
            width: MediaQuery.of(context).size.width <= 500
                ? MediaQuery.of(context).size.width
                : MediaQuery.of(context).size.width * 0.9,
            padding: EdgeInsets.symmetric(
                horizontal: MediaQuery.of(context).size.width <= 500
                    ? 0
                    : MediaQuery.of(context).size.width * 0.05),
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
                  return Row(children: [
                    chatModel.messages!.elementAt(index).sender.userID ==
                            UserApi.getInstance().getUserProfile()!.userID
                        ? Spacer()
                        : Container(),
                    Expanded(
                        flex: 2,
                        child: Card(
                            color: Theme.of(context).primaryColorDark,
                            child: ListTile(
                              leading: CachedNetworkImage(
                                  useOldImageOnUrlChange: true,
                                  imageUrl:mainApi +
                                      "/user/viewPicture/" +
                                      chatModel.messages!
                                          .elementAt(index)
                                          .sender
                                          .profileID,
                                  imageBuilder: (context, imageProvider) =>
                                      Container(
                                          width: 70,
                                          height: 70,
                                          decoration: new BoxDecoration(
                                              border: Border.all(
                                                color: Theme.of(context)
                                                    .accentColor,
                                                width: 3,
                                              ),
                                              shape: BoxShape.circle,
                                              image: DecorationImage(
                                                  fit: BoxFit.fill,
                                                  image: imageProvider))),
                                  placeholder: (context, url) => Container(
                                      width: 70,
                                      height: 70,
                                      decoration: new BoxDecoration(
                                          border: Border.all(
                                            color:
                                                Theme.of(context).accentColor,
                                            width: 3,
                                          ),
                                          shape: BoxShape.circle,
                                          image: DecorationImage(
                                              fit: BoxFit.fill,
                                              image: AssetImage(
                                                  "custom_images/pfp.png")))),
                                  errorWidget: (context, url, error) =>
                                      Container(
                                          width: 70,
                                          height: 70,
                                          decoration: new BoxDecoration(
                                              border: Border.all(
                                                color: Theme.of(context)
                                                    .accentColor,
                                                width: 3,
                                              ),
                                              shape: BoxShape.circle,
                                              image: DecorationImage(
                                                  fit: BoxFit.fill,
                                                  image: AssetImage(
                                                      "custom_images/pfp.png"))))),
                              title: Row(children: [
                                Expanded(
                                    child: Text(
                                        chatModel.messages!
                                            .elementAt(index)
                                            .sender
                                            .username,
                                        textAlign: TextAlign.left,
                                        style: TextStyle(
                                          fontSize: 15 *
                                              MediaQuery.of(context)
                                                  .textScaleFactor,
                                          fontWeight: FontWeight.bold,
                                          color: HSLColor.fromAHSL(
                                                  1,
                                                  chatModel.chat!.colors
                                                          .elementAt(chatModel
                                                              .chat!.colors
                                                              .indexWhere(
                                                                  (element) {
                                                            return element
                                                                    .userID ==
                                                                chatModel
                                                                    .messages!
                                                                    .elementAt(
                                                                        index)
                                                                    .sender
                                                                    .userID;
                                                          }))
                                                          .color *
                                                      1.0,
                                                  1,
                                                  0.7)
                                              .toColor(),
                                        ))),
                                Expanded(
                                    child: Text(
                                        getTime(DateTime.parse(chatModel
                                            .messages!
                                            .elementAt(index)
                                            .timestamp)),
                                        textAlign: TextAlign.right,
                                        style: TextStyle(
                                            fontSize: 15 *
                                                MediaQuery.of(context)
                                                    .textScaleFactor,
                                            fontWeight: FontWeight.bold,
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color)))
                              ]),
                              subtitle: Text(
                                  chatModel.messages!.elementAt(index).message,
                                  style: TextStyle(
                                      fontSize: 15 *
                                          MediaQuery.of(context)
                                              .textScaleFactor,
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)),
                            ))),
                    chatModel.messages!.elementAt(index).sender.userID !=
                            UserApi.getInstance().getUserProfile()!.userID
                        ? Spacer()
                        : Container(),
                  ]);
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
