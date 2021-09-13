import 'dart:async';
import 'dart:convert';
import 'dart:core';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;
import 'package:location/location.dart';
import 'package:geocoder/geocoder.dart';
import 'package:intl/intl.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import 'adventure.dart';
import 'currentLocation.dart';

import 'flags.dart';

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
        Uri.parse("http://"+mainApi + "/location/getAllCurrentLocations/" + adventureID));
  }

  static Future<CurrentLocation> getCurrentLocation() async {
    http.Response response = await _getCurrentLocation();

    if (response.statusCode != 200) {
      throw Exception(
          'Failed to load the current location: ${response.body}');
    }

    CurrentLocation location = (CurrentLocation.fromJson(jsonDecode(response.body)));

    return location;
  }

  static Future<http.Response> _getCurrentLocation() async {
    return http.get(
        Uri.parse("http://"+mainApi + "/location/getCurrentLocation/" + UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future setCurrentLocation(LocationData location) async {
    http.Response response = await _setCurrentLocation(location.latitude.toString(), location.longitude.toString());

    if (response.statusCode != 200) {
      throw Exception('Failed to set location for user: ${response.body}');
    }
  }

  static Future<http.Response> _setCurrentLocation(
      String latitude, String longitude) async {
    return http.get(Uri.parse("http://"+mainApi +
        "/location/storeCurrentLocation/" +
        UserApi.getInstance().getUserProfile()!.userID +
        "/" +
        latitude +
        "/" +
        longitude));
  }


  static Future<Flags> getFlagList() async {
    http.Response response = await _getFlagList();

    if(response.statusCode != 200) {
      throw Exception('Failed to load flags: ${response.body}');
    }

    Flags flag = (jsonDecode(response.body));
    print(response.body);
    return flag;
  }

  static Future<http.Response> _getFlagList() async {
    return http.get(Uri.http(mainApi,
      'location/getFlagList/'+UserApi.getInstance().getUserProfile()!.userID));
  }
}
