import 'dart:async';
import 'dart:convert';
import 'dart:core';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

import 'adventure.dart';
import 'currentLocation.dart';

class LocationApi {
  static Future<List<PlaceSearch>> getSuggestions(String query) async {
    http.Response response = await _getSuggestions(query);

    if (response.statusCode != 200) {
      throw Exception('Failed to load place suggestions: ${response.body}');
    }

    List<PlaceSearch> suggestions =
        (jsonDecode(response.body)['predictions'] as List)
            .map((x) => PlaceSearch.fromJson(x))
            .toList();

    return suggestions;
  }

  static Future<http.Response> _getSuggestions(query) async {
    return http.get(Uri.parse(
        'https://maps.googleapis.com/maps/api/place/autocomplete/json?input=$query&key=$googleMapsKey'));
  }

  static Future<List<CurrentLocation>> getAllCurrentLocations(
      Adventure a) async {
    http.Response response = await _getAllCurrentLocations(a.adventureId);

    if (response.statusCode != 200) {
      throw Exception(
          'Failed to load current locations for adventure: ${response.body}');
    }

    List<CurrentLocation> locations = (jsonDecode(response.body) as List)
        .map((x) => CurrentLocation.fromJson(x))
        .toList();

    return locations;
  }

  static Future<http.Response> _getAllCurrentLocations(
      String adventureID) async {
    return http.get(
        Uri.parse(mainApi + "/location/getAllCurrentLocations/" + adventureID));
  }

  static Future setCurrentLocation(String latitude, String longitude) async {
    http.Response response = await _setCurrentLocation(latitude, longitude);

    if (response.statusCode != 200) {
      throw Exception('Failed to set location for user: ${response.body}');
    }
  }

  static Future<http.Response> _setCurrentLocation(
      String latitude, String longitude) async {
    return http.get(Uri.parse(mainApi +
        "/storeCurrentLocation/{userID}/{latitude}/{longitude}" +
        UserApi.getInstance().getUserProfile()!.userID +
        "/" +
        latitude +
        "/" +
        longitude));
  }
}
