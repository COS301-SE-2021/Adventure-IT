import 'dart:async';
import 'dart:convert';
import 'dart:core';
import 'package:adventure_it/api/location.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/recommendedLocation.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/constants.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:location/location.dart' as loc;

import 'adventure.dart';
import 'currentLocation.dart';

class LocationApi {
  static Future<List<PlaceSearch>> getSuggestions(String query, context) async {
    http.Response response = await _getSuggestions(query);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to load suggestions for places!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
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
        'http://app.adventure-it.co.za/googleMapsApi/autocomplete/json?input=$query&key=$googleMapsKey'));
  }

  static Future<List<CurrentLocation>> getAllCurrentLocations(
      Adventure a, context) async {
    http.Response response = await _getAllCurrentLocations(a.adventureId);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to get current locations of adventurers!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
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

  static Future<CurrentLocation> getCurrentLocation(context) async {
    http.Response response = await _getCurrentLocation();

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to get current location of adventurer!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to load the current location: ${response.body}');
    }

    CurrentLocation location =
        (CurrentLocation.fromJson(jsonDecode(response.body)));

    return location;
  }

  static Future<http.Response> _getCurrentLocation() async {
    return http.get(Uri.parse(mainApi +
        "/location/getCurrentLocation/" +
        UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future setCurrentLocation(loc.LocationData location, context) async {
    http.Response response = await _setCurrentLocation(
        location.latitude.toString(), location.longitude.toString());

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to set location for adventurer!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception(
          'Failed to set location for adventurer: ${response.body}');
    }
  }

  static Future<http.Response> _setCurrentLocation(
      String latitude, String longitude) async {
    return http.get(Uri.parse(mainApi +
        "/location/storeCurrentLocation/" +
        UserApi.getInstance().getUserProfile()!.userID +
        "/" +
        latitude +
        "/" +
        longitude));
  }

  static Future<List<RecommendedLocation>> getRecommendations(
      Adventure a, context) async {
    http.Response response = await _getRecommendations(a);

    if (response.body == "") {
      List<RecommendedLocation> locations = List.empty();
      return locations;
    }

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to get recommendations!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to get recommendations: ${response.body}');
    }

    List<RecommendedLocation> locations = (jsonDecode(response.body) as List)
        .map((x) => RecommendedLocation.fromJson(x))
        .toList();
    return locations;
  }

  static Future<http.Response> _getRecommendations(Adventure a) async {
    return http.get(Uri.parse(mainApi +
        "/recommendation/get/" +
        UserApi.getInstance().getUserProfile()!.userID +
        "/5/" +
        a.location.formattedAddress));
  }

  static Future<List<RecommendedLocation>> getPopular(
      Adventure a, context) async {
    http.Response response = await _getPopular(a);

    if (response.body == "") {
      List<RecommendedLocation> locations = List.empty();
      return locations;
    }

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to get recommendations!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception(
          'Failed to get popular recommendations: ${response.body}');
    }

    print(response.body.toString());

    List<RecommendedLocation> locations = (jsonDecode(response.body) as List)
        .map((x) => RecommendedLocation.fromJson(x))
        .toList();
    return locations;
  }

  static Future<http.Response> _getPopular(Adventure a) async {
    return http.get(Uri.parse(mainApi +
        "/recommendation/get/popular/" +
        UserApi.getInstance().getUserProfile()!.userID +
        "/5/" +
        a.location.formattedAddress));
  }

  static Future likeLocation(String locationId, context) async {
    http.Response response = await _likeLocation(locationId);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to like location!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to like the location: ${response.body}');
    }
  }

  static Future<http.Response> _likeLocation(String locationId) async {
    return http.get(Uri.parse(mainApi +
        "/recommendation/like/" +
        UserApi.getInstance().getUserProfile()!.userID +
        "/" +
        locationId));
  }

  static Future<List<dynamic>> getFlagList() async {
    http.Response response = await _getFlagList();
    if (response.statusCode != 200) {
      throw Exception('Failed to load flags: ${response.body}');
    }

    List<dynamic> flag = (jsonDecode(response.body) as List);
    return flag;
  }

  static Future<http.Response> _getFlagList() async {
    return http.get(Uri.parse(mainApi +
        'location/getFlagList/' +
        UserApi.getInstance().getUserProfile()!.userID));
  }
}
