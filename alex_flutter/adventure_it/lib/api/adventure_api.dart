import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;
import 'package:time_machine/time_machine.dart';

import 'createAdventure.dart';

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

    List<UserProfile> attendees = (jsonDecode(response.body) as List)
        .map((x) => UserProfile.fromJson(x))
        .toList();
    print("here here "+attendees.toString());

    return attendees;//
  }

  static Future<http.Response> _getAttendeesOfAdventure(adventureID) async {
    return http.get(Uri.http(mainApi, '/adventure/getAttendees/' + adventureID));
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

  Future<CreateAdventure> createAdventure(String name, String ownerId, LocalDate startDate, LocalDate endDate, String description) async {

    final response = await http.post(
      Uri.parse('http://localhost:9001/adventure/create'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'name': name,
        'ownerId': ownerId,
        'startDate': startDate.toString(),
        'endDate': endDate.toString(),
        'description': description
      })
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateAdventure(name: name, ownerId: ownerId, startDate: startDate.toString(), endDate: endDate.toString(), description: description);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create an adventure.');
    }
  }

}
