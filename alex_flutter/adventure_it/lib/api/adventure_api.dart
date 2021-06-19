import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

class AdventureApi {
  static Future<List<Adventure>> getAdventuresByOwner() async {
    http.Response response =
        await _getAdventuresByOwner("1660bd85-1c13-42c0-955c-63b1eda4e90b");

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of adventures: ${response.body}');
    }

    List<Adventure> adventures = (jsonDecode(response.body) as List)
        .map((x) => Adventure.fromJson(x))
        .toList();

    return adventures;
  }

  static Future<List<Adventure>> getAdventuresByAttendee() async {
    http.Response response =
        await _getAdventuresByAttendee("7a984756-16a5-422e-a377-89e1772dd71e");

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of adventures: ${response.body}');
    }

    List<Adventure> adventures = (jsonDecode(response.body) as List)
        .map((x) => Adventure.fromJson(x))
        .toList();

    return adventures;
  }

  static Future<http.Response> _getAdventuresByOwner(ownerID) async {
    return http.get(Uri.http(kApi, '/adventure/owner/' + ownerID));
  }

  static Future<http.Response> _getAdventuresByAttendee(attendeeID) async {
    return http.get(Uri.http(kApi, '/adventure/attendee/' + attendeeID));
  }
}
