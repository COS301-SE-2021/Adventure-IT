import 'dart:convert';
import 'package:flutter_testing/constants.dart';
import 'package:flutter_testing/API/adventures.dart';
import 'package:http/http.dart' as http;

class AdventureApi {
  static Future<List<Adventure>> getOwnerAdventures() async {
    http.Response? response = await _getAdventuresOwnerResponse();

    if (response!.statusCode != 200) {
      throw Exception(
          'Failed to load list of adventures: ${response.body}');
    }
    print("returned");
    List<Adventure> adventures =
    (jsonDecode(response.body) as List)
        .map((fm) => Adventure.fromJson(fm))
        .toList();

    return adventures;
  }

  static Future<http.Response> _getAdventuresOwnerResponse() async {

    return http.get(Uri.http(kApi, '/viewAdventures'));
  }

  static Future<List<Adventure>> getAttendeeAdventures() async {
    http.Response? response = await _getAdventuresAttendeeResponse();

    if (response!.statusCode != 200) {
      throw Exception(
          'Failed to load list of adventures: ${response.body}');
    }
    print("returned");
    List<Adventure> adventures =
    (jsonDecode(response.body) as List)
        .map((fm) => Adventure.fromJson(fm))
        .toList();

    return adventures;
  }

  static Future<http.Response> _getAdventuresAttendeeResponse() async {

    return http.get(Uri.http(kApi, '/viewAdventures'));
  }
}