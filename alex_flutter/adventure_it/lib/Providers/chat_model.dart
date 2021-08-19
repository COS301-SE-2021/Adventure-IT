import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/chatAPI.dart';
import 'package:adventure_it/api/directChat.dart';
import 'package:adventure_it/api/directChatMessage.dart';
import 'package:adventure_it/api/groupChat.dart';
import 'package:adventure_it/api/groupChatMessage.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

class GroupChatModel extends ChangeNotifier {
  List<GroupChatMessage>? _messages = null;
  GroupChat? _chat = null;
  Adventure? currentAdventure;

  GroupChatModel(Adventure a) {
    this.currentAdventure = a;
    fetchAllMessages().then(
        (messages) => messages != null ? _messages = messages : List.empty());
  }

  List<GroupChatMessage>? get messages => _messages?.toList();

  GroupChat? get chat => _chat;

  Future fetchAllMessages() async {
    _chat = await ChatApi.getGroupChat(currentAdventure);
    if (_chat == null || _chat!.messages.isEmpty) {
      print(_chat!.messages.length);
      _messages = List.empty();
    } else {
      print(_chat!.messages.length);
      _messages = await ChatApi.getGroupChatMessage(_chat!.id);
    }
        notifyListeners();

  }

  Future sendMessage(String message) async
  {
      await ChatApi.sendGroupMessage(_chat!.id, UserApi.getInstance().getUserProfile()!.userID, message);

      fetchAllMessages();

      notifyListeners();
  }
}

class DirectChatModel extends ChangeNotifier {
  List<DirectChatMessage>? _messages = null;
  DirectChat? _chat = null;
  String? user1ID;
  String? user2ID;

  DirectChatModel(String user1, String user2) {
    user1ID=user1;
    user2ID=user2;
    fetchAllMessages(user1, user2).then(
        (messages) => messages != null ? _messages = messages : List.empty());
  }

  List<DirectChatMessage>? get messages => _messages?.toList();

  DirectChat? get chat => _chat;

  Future fetchAllMessages(String user1, String user2) async {
    _chat = await ChatApi.getDirectChat(user1, user2);
    if (_chat == null || _chat!.messages.isEmpty) {
      _messages = List.empty();
    } else
      _messages = await ChatApi.getDirectChatMessage(_chat!.id);
    notifyListeners();
  }

  Future sendMessage(String message) async
  {
    await ChatApi.sendGroupMessage(_chat!.id, UserApi.getInstance().getUserProfile()!.username, message);

    fetchAllMessages(user1ID!,user2ID!);

    notifyListeners();
  }
}
