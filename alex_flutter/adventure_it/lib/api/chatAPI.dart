import 'dart:convert';
import 'package:adventure_it/api/createUTOBudgetEntry.dart';
import 'package:adventure_it/api/groupChatMessage.dart';

import 'package:adventure_it/api/report.dart';

import '/constants.dart';
import '/api/budget.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

import 'colorPair.dart';
import 'createBudget.dart';

import 'budgetEntry.dart';
import 'createUTUBudgetEntry.dart';
import 'directChat.dart';
import 'directChatMessage.dart';
import 'groupChat.dart';

class ChatApi {
  static Future<GroupChat> getGroupChat(Adventure? a) async {
    http.Response response =
    await _getGroupChat(a!.adventureId);
    print(response.body);

    if (response.statusCode != 200) {
      throw Exception('Failed to find group chat: ${response.body}');
    }

    GroupChat gc = (GroupChat.fromJson(jsonDecode(response.body)));

    return gc;
  }

  static Future<List<GroupChatMessage>> getGroupChatMessage(chatID) async {
    http.Response response =
    await _getGroupChatMessages(chatID);

    print(response.body);

    if (response.statusCode != 200) {
      throw Exception(
          'Failed to load list of messages for group chat: ${response.body}');
    }

    List<GroupChatMessage> messages = (jsonDecode(response.body) as List)
        .map((x) => GroupChatMessage.fromJson(x))
        .toList();

    return messages;
  }

  static Future<http.Response> _getGroupChatMessages(chatID) async {
    return http.get(Uri.parse(mainApi+'/chat/getGroupMessages/' + chatID));
  }

  static Future<http.Response> _getGroupChat(adventureID) async {
    return http.get(
        Uri.http(mainApi,'/chat/getGroupChatByAdventureID/' + adventureID));
  }

  static Future<DirectChat> getDirectChat(String user1, String user2) async {
    http.Response response =
    await _getDirectChat(user1,user2);

    if (response.statusCode != 200) {
      throw Exception('Failed to find direct chat: ${response.body}');
    }

    DirectChat dc = (DirectChat.fromJson(jsonDecode(response.body)));

    return dc;
  }

  static Future<http.Response> _getDirectChat(String user1, String user2) async {
    return http.get(
        Uri.http(mainApi,'/chat/getDirectChat/'+user1+'/'+user2));
  }

  static Future<List<DirectChatMessage>> getDirectChatMessage(chatID) async {
    http.Response response =
    await _getDirectChatMessages(chatID);

    print(response.body);

    if (response.statusCode != 200) {
      throw Exception(
          'Failed to load list of messages for direct chat: ${response.body}');
    }

    List<DirectChatMessage> messages = (jsonDecode(response.body) as List)
        .map((x) => DirectChatMessage.fromJson(x))
        .toList();

    return messages;
  }

  static Future<http.Response> _getDirectChatMessages(chatID) async {
    return http.get(Uri.http(mainApi, '/chat/getDirectMessages/' + chatID));
  }


}
