import 'dart:convert';
import 'package:flutter_testing/constants.dart';
import 'package:flutter_testing/API/adventures.dart';
import 'package:http/http.dart' as http;

class AdventureApi {

  static Future<List<Adventure>?> getOwnerAdventures() async {
    http.Response response = await _getAdventureOwnerResponse();
    if (response.statusCode == 200) {
      List<Adventure>? ownerlist;
      ownerlist = (json.decode(response.body) as List)
          .map((i) => Adventure.fromJson(i))
          .toList();
      print("returned owners");
      return ownerlist;
    } else {
      throw Exception('Failed to load adventures');
    }
  }

  static Future<List<Adventure>?> getAttendeeAdventures() async {
    http.Response response = await _getAdventureAttendeesResponse();
    if (response.statusCode == 200) {
      List<Adventure>? attendeelist;
      attendeelist = (json.decode(response.body) as List)
          .map((i) => Adventure.fromJson(i))
          .toList();
      print("returned attendees");
      return attendeelist;
    } else {
      throw Exception('Failed to load adventures');
    }
  }

  static Future<http.Response> _getAdventureAttendeesResponse() async {

    return http.get(Uri.http(kApi, '/adventure/attendee/1660bd85-1c13-42c0-955c-63b1eda4e90b'));
  }
  static Future<http.Response> _getAdventureOwnerResponse() async {

    return http.get(Uri.http(kApi, '/adventure/owner/1660bd85-1c13-42c0-955c-63b1eda4e90b'));
  }
}