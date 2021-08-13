import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';


class ChatModel extends ChangeNotifier {
  List<GroupMessage>? _messages = null;
  Adventure? currentAdventure;

  ChatModel(Adventure a) {
    this.currentAdventure=a;
    fetchAllMessages().then((messages) => messages != null? _messages = messages:List.empty());
  }

  List<GroupMessage>? get messages => _messages?.toList();

  Future fetchAllMessages() async {
    GroupChat chat=await ChatApi.getGroupChatByAdventure(currentAdventure);
    _messages = await ChatApi.getMessagesForGroupChat(chat.id);
    notifyListeners();
  }

  Future addMessage(String a, String b, LocalDate c, LocalDate d, String e, String f) async {
    await ChatApi.addMessageToGroupChat(a, b, c, d, e, f);

    fetchAllMessages();

    notifyListeners();
  }


}