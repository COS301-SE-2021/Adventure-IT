import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/chatAPI.dart';
import 'package:adventure_it/api/directChat.dart';
import 'package:adventure_it/api/directChatMessage.dart';
import 'package:adventure_it/api/groupChat.dart';
import 'package:adventure_it/api/groupChatMessage.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class GroupChatModel extends ChangeNotifier {
  List<GroupChatMessage>? _messages;
  GroupChat? _chat;
  Adventure? currentAdventure;
  BuildContext? context;

  GroupChatModel(Adventure a, context) {
    this.currentAdventure = a;
    this.context = context;
    fetchAllMessages().then(
        (messages) => messages != null ? _messages = messages : List.empty());
  }

  List<GroupChatMessage>? get messages => _messages?.toList();

  GroupChat? get chat => _chat;

  Future fetchAllMessages() async {
    _chat = await ChatApi.getGroupChat(currentAdventure, context);
    if (_chat == null) {
      _messages = List.empty();
    } else {
      _messages = await ChatApi.getGroupChatMessage(_chat!.id, context);
    }
    notifyListeners();
  }

  Future sendMessage(String message) async {
    await ChatApi.sendGroupMessage(_chat!.id,
        UserApi.getInstance().getUserProfile()!.userID, message, context);

    fetchAllMessages();

    notifyListeners();
  }
}

class DirectChatModel extends ChangeNotifier {
  List<DirectChatMessage>? _messages;
  DirectChat? _chat;
  String? user1ID;
  String? user2ID;
  BuildContext? context;

  DirectChatModel(String user1, String user2, context) {
    user1ID = user1;
    user2ID = user2;
    this.context = context;
    fetchAllMessages().then(
        (messages) => messages != null ? _messages = messages : List.empty());
  }

  List<DirectChatMessage>? get messages => _messages?.toList();

  DirectChat? get chat => _chat;

  Future fetchAllMessages() async {
    _chat = await ChatApi.getDirectChat(user1ID!, user2ID!, context);
    if (_chat == null) {
      _messages = List.empty();
    } else
      _messages = await ChatApi.getDirectChatMessage(_chat!.id, context);
    notifyListeners();
  }

  Future sendMessage(String message) async {
    await ChatApi.sendDirectMessage(
        _chat!.id, user1ID!, user2ID!, message, context);

    fetchAllMessages();

    notifyListeners();
  }
}
