import 'dart:async';
import 'dart:convert';
import 'dart:core';
import 'dart:core';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

import 'loginUser.dart';
import 'userProfile.dart';

class LocationApi {

  static Future<String> getPhotoURL(String reference) async {
    print(reference);
    http.Response response= await _getPhotoURL(reference);
    print(response.body);
    return 'https://picsum.photos/250?image=9';
  }

  static Future<http.Response> _getPhotoURL(String reference) async {
    String ref="https://maps.googleapis.com/maps/api/place/photo?photo_reference="+reference+"&maxWidth=400&key="+googleMapsKey;
    print(ref);
    return http.get(Uri.parse(ref));
  }



  static Future<List<PlaceSearch>> getSuggestions(String query) async {
    http.Response response =
    await _getSuggestions(query);
    print(response.body);

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
