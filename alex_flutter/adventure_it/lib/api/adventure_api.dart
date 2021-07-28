import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

class AdventureApi {
  static Future<List<Adventure>> getAdventuresByUUID(String userId) async {
    http.Response response =
        await _getAdventuresByUUID(userId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of adventures: ${response.body}');
    }

    List<Adventure> adventures = (jsonDecode(response.body) as List)
        .map((x) => Adventure.fromJson(x))
        .toList();

    return adventures;
  }


  static Future<http.Response> _getAdventuresByUUID(userID) async {
    return http.get(Uri.http(adventureApi, '/adventure/all/' + userID));
  }

  static Future removeAdventure(String adventureId) async {
    http.Response response = await _removeAdventure(adventureId);


    if (response.statusCode != 200) {
      throw Exception('Failed to remove adventure: ${response.body}');
    }
  }


  static Future<http.Response> _removeAdventure(adventureID) async {
    return http.delete(Uri.http(adventureApi, '/adventure/remove/' + adventureID));
  }

}
