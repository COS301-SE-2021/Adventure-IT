import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/chatAPI.dart';
import 'package:adventure_it/api/directChat.dart';
import 'package:adventure_it/api/directChatMessage.dart';
import 'package:adventure_it/api/groupChat.dart';
import 'package:adventure_it/api/groupChatMessage.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';


class GroupChatModel extends ChangeNotifier {
  List<GroupChatMessage>? _messages = null;
  GroupChat? _chat=null;
  Adventure? currentAdventure;

  GroupChatModel(Adventure a) {
    this.currentAdventure=a;
    fetchAllMessages().then((messages) => messages != null? _messages = messages:List.empty());
  }

  List<GroupChatMessage>? get messages => _messages?.toList();
  GroupChat? get chat => _chat;

  Future fetchAllMessages() async {
    _chat=await ChatApi.getGroupChat(currentAdventure);
    _messages = await ChatApi.getGroupChatMessage(_chat!.id);
    notifyListeners();
  }


}

class DirectChatModel extends ChangeNotifier {
  List<DirectChatMessage>? _messages = null;
  DirectChat? _chat=null;

  DirectChatModel(String user1, String user2) {

    fetchAllMessages(user1, user2).then((messages) => messages != null? _messages = messages:List.empty());
  }

  List<DirectChatMessage>? get messages => _messages?.toList();
  DirectChat? get chat => _chat;

  Future fetchAllMessages(String user1, String user2) async {
    _chat=await ChatApi.getDirectChat(user1,user2);
    _messages = await ChatApi.getDirectChatMessage(_chat!.id);
    notifyListeners();
  }


}