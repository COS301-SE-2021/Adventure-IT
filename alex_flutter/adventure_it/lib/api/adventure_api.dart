import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/user_api.dart';
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

  static Future<List<UserProfile>> getAttendeesOfAdventure(String adventureID) async {
    http.Response response =
    await _getAttendeesOfAdventure(adventureID);

      print(response.body);
    if (response.statusCode != 200) {
      throw Exception('Failed to load list of attendees: ${response.body}');
    }

    List<String> userIDs = (jsonDecode(response.body) as List<dynamic>).cast<String>();
    List <UserProfile> attendees=List.empty();
    for(int i=0;i<userIDs.length;i++)
      {
        if(userIDs.elementAt(i)!="1660bd85-1c13-42c0-955c-63b1eda4e90b") {
          UserProfile p = await UserApi.getUserByUUID(userIDs.elementAt(i));
          attendees.add(p);
        }
      }
    return attendees;//
  }

  static Future<http.Response> _getAttendeesOfAdventure(adventureID) async {
    return http.get(Uri.http(adventureApi, '/adventure/getAttendees/' + adventureID));
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
