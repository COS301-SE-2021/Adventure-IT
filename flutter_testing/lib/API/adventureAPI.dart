import 'dart:convert';
import '/constants.dart';
import '/API/adventures.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class AdventureApi {

  static Future<List<Adventure>> getOwnerAdventures() async {
    http.Response response = await _getAdventureOwnerResponse("7a984756-16a5-422e-a377-89e1772dd71e");
    if (response.statusCode == 200) {
      List<Adventure> ownerlist;
      ownerlist = (json.decode(response.body) as List)
          .map((i) => Adventure.fromJson(i))
          .toList();
      print("returned owners");
      return ownerlist;
    } else {
      throw Exception('Failed to load adventures');
    }
  }

  static Future<List<Adventure>> getAttendeeAdventures() async {
    http.Response response = await _getAdventureAttendeesResponse("7a984756-16a5-422e-a377-89e1772dd71e");
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

  static Future<http.Response> _getAdventureAttendeesResponse(attendeeID) async {

    print("here1");
    return http.get(Uri.http(adventureApi, '/adventure/attendee/'+attendeeID));
  }
  static Future<http.Response> _getAdventureOwnerResponse(ownerID) async {
    print("here2");
    return http.get(Uri.http(adventureApi, '/adventure/owner/'+ownerID));
  }
}