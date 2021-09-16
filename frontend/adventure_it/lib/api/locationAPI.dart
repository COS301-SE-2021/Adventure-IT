import 'dart:async';
import 'dart:convert';
import 'dart:core';
import 'package:adventure_it/api/location.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;
import 'package:location/location.dart' as loc;

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

  static Future setCurrentLocation(loc.LocationData location) async {
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

  static Future<List<Location>> getRecommendations(Adventure a) async {
    // http.Response response = await _getRecommendations(a);
    //
    // if (response.statusCode != 200) {
    //   throw Exception('Failed to get recommendations: ${response.body}');
    // }

    // List<loc.Location> locations = (jsonDecode(response.body) as List)
    //     .map((x) => loc.Location.fromJson(x))
    //     .toList();
    List<Location> locations=[Location(id:"2344d7ac-4689-468f-9531-a7180ccc239f",formattedAddress: "South Africa",photoReference: "Aap_uEAP73TFAeNg106T7IOabdtTS0J3AXcsNqRtUMrZXBkq0cKNRcrxILec8igl-zLRx3HNrTvxHOsykA2ak79vK-pfWDlA19yykx-3AKdSckUU6Ho9R1DCA2Ysmt3F0GTskkX93fRdDt_yNlUo8EEL6REitWoxWHjcWc3lwk3NP20pan8Y",placeId: "ChIJoaA1xZZIwx4Re3hnH9NOUJo",name:"Location 1"),Location(id:"68da551e-43d2-458e-812a-7f9e65fe0971",formattedAddress: "Rue de Rivoli, 75001 Paris, France",photoReference: "Aap_uEDzjpFaQEgjtZzLibwZcVQnjiFDaRtXuiLVmIg9vdNfm2Z-61YWBz5y-wNYvXdscV-jZW2-4TLDmO2UfKu65womOGN8WDmj0PFwcO7YirbyLGJUrY-TzRmlcn48hRSui_BA0f0l2-RRhjHtDMUmpuqimfuNSFIv1qGOElmUt9R5A61-",placeId: "ChIJD3uTd9hx5kcR1IQvGfr8dbk",name:"Location 2"),Location(id: "822f88de-d5d7-42e4-a178-997994051045", photoReference: "Aap_uEAZ8eBCVUw3Ed7GVsrB0EsgZeb9DtIDoXqPFklYeTm413hAHuucjKpVXyaWadhW2nWfL2CRDDs47D5BmrSGPj0-ZRYb15n9xz1END7URoy-B3NiwFdH0n3zH-TDMDLsqZ6zCwpdYXx4glCqELtIp-N3rRFJcxWwJ6toonD2mLEDTszq", formattedAddress: "Hawaii, USA", placeId: "ChIJBeB5Twbb_3sRKIbMdNKCd0s",name:"Location 3")];
    return locations;

  }

  static Future<List<Location>> getPopular(Adventure a) async {
    //http.Response response = await _getPopular(a);

    // if (response.statusCode != 200) {
    //   throw Exception('Failed to get recommendations: ${response.body}');
    // }

    // List<loc.Location> locations = (jsonDecode(response.body) as List)
    //     .map((x) => loc.Location.fromJson(x))
    //     .toList();
    List<Location> locations=[Location(id:"2344d7ac-4689-468f-9531-a7180ccc239f",formattedAddress: "South Africa",photoReference: "Aap_uEAP73TFAeNg106T7IOabdtTS0J3AXcsNqRtUMrZXBkq0cKNRcrxILec8igl-zLRx3HNrTvxHOsykA2ak79vK-pfWDlA19yykx-3AKdSckUU6Ho9R1DCA2Ysmt3F0GTskkX93fRdDt_yNlUo8EEL6REitWoxWHjcWc3lwk3NP20pan8Y",placeId: "ChIJoaA1xZZIwx4Re3hnH9NOUJo",name:"Pop 1"),Location(id:"68da551e-43d2-458e-812a-7f9e65fe0971",formattedAddress: "Rue de Rivoli, 75001 Paris, France",photoReference: "Aap_uEDzjpFaQEgjtZzLibwZcVQnjiFDaRtXuiLVmIg9vdNfm2Z-61YWBz5y-wNYvXdscV-jZW2-4TLDmO2UfKu65womOGN8WDmj0PFwcO7YirbyLGJUrY-TzRmlcn48hRSui_BA0f0l2-RRhjHtDMUmpuqimfuNSFIv1qGOElmUt9R5A61-",placeId: "ChIJD3uTd9hx5kcR1IQvGfr8dbk",name:"Pop 2"),Location(id: "822f88de-d5d7-42e4-a178-997994051045", photoReference: "Aap_uEAZ8eBCVUw3Ed7GVsrB0EsgZeb9DtIDoXqPFklYeTm413hAHuucjKpVXyaWadhW2nWfL2CRDDs47D5BmrSGPj0-ZRYb15n9xz1END7URoy-B3NiwFdH0n3zH-TDMDLsqZ6zCwpdYXx4glCqELtIp-N3rRFJcxWwJ6toonD2mLEDTszq", formattedAddress: "Hawaii, USA", placeId: "ChIJBeB5Twbb_3sRKIbMdNKCd0s",name:"Pop 3")];

    return locations;

  }

  // static Future<http.Response> _getPopular(Adventure a) async {
  //   return http.get(Uri.parse());
  // }


  static Future<List<dynamic>> getFlagList() async {
    http.Response response = await _getFlagList();
    if(response.statusCode != 200) {
      throw Exception('Failed to load flags: ${response.body}');
    }

    List<dynamic> flag = (jsonDecode(response.body) as List);
    return flag;
  }

  static Future<http.Response> _getFlagList() async {
    return http.get(Uri.http(mainApi,
      'location/getFlagList/'+UserApi.getInstance().getUserProfile()!.userID));
  }
}
