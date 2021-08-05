import 'dart:async';
import 'dart:convert';
import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

import 'loginUser.dart';
import 'userProfile.dart';

class LocationApi {

  static Future<List<PlaceSearch>> getSuggestions(String query) async {
    http.Response response =
    await _getSuggestions(query);

    if (response.statusCode != 200) {
      throw Exception('Failed to load place suggestions: ${response.body}');
    }

    List<PlaceSearch> suggestions = (jsonDecode(response.body)['predictions'] as List)
        .map((x) => PlaceSearch.fromJson(x))
        .toList();


    return suggestions;
  }

  static Future<http.Response> _getSuggestions(query) async {

    return http.get(Uri.parse('https://maps.googleapis.com/maps/api/place/autocomplete/json?input=$query&key=$googleMapsKey'));
  }




}
