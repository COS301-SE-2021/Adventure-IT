import 'dart:convert';
import 'package:adventure_it/api/createItineraryEntry.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/constants.dart';
import '/api/itinerary.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'package:location/location.dart';

import 'itineraryEntry.dart';

import 'createItinerary.dart';
import 'locationAPI.dart';

class ItineraryApi {
  static Future<List<Itinerary>> getItineraries(Adventure? a) async {
    http.Response response = await _getItineraries(a!.adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of itineraries: ${response.body}');
    }

    List<Itinerary> itineraries = (jsonDecode(response.body) as List)
        .map((x) => Itinerary.fromJson(x))
        .toList();

    return itineraries;
  }

  static Future<List<ItineraryEntry>> getItineraryEntries(Itinerary i) async {
    http.Response response = await _getItineraryEntries(i.id);

    if (response.statusCode != 200) {
      throw Exception(
          'Failed to load list of entries for itinerary: ${response.body}');
    }

    List<ItineraryEntry> itineraries = (jsonDecode(response.body) as List)
        .map((x) => ItineraryEntry.fromJson(x))
        .toList();

    return itineraries;
  }

  static Future<http.Response> _getItineraries(adventureID) async {
    return http.get(Uri.http(
        mainApi, '/itinerary/viewItinerariesByAdventure/' + adventureID));
  }

  static Future<http.Response> _getItineraryEntries(itineraryID) async {
    return http
        .get(Uri.http(mainApi, '/itinerary/viewItinerary/' + itineraryID));
  }

  static Future<List<Itinerary>> getDeletedItinerary(adventureId) async {
    http.Response response = await _getDeletedItinerariesResponse(adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of itineraries: ${response.body}');
    }

    List<Itinerary> budgets = (jsonDecode(response.body) as List)
        .map((x) => Itinerary.fromJson(x))
        .toList();

    return budgets;
  }

  static Future restoreItinerry(itineraryID) async {
    http.Response response = await _restoreItineraryRequest(itineraryID);

    if (response.statusCode != 200) {
      throw Exception('Failed to restore itinerary: ${response.body}');
    }
  }

  static Future softDeleteItinerary(itineraryID) async {
    http.Response response = await _deleteItineraryRequest(itineraryID);

    if (response.statusCode != 200) {
      throw Exception('Failed to softDelete itinerary ${response.body}');
    }
  }

  static Future deleteItineraryEntry(ItineraryEntry i) async {
    http.Response response = await _deleteItineraryEntryRequest(i.id);

    if (response.statusCode != 200) {
      throw Exception('Failed to delete itinerary entry ${response.body}');
    }
  }

  static Future hardDeleteItinerary(itineraryID) async {
    http.Response response = await _hardDeleteItineraryRequest(itineraryID);

    if (response.statusCode != 200) {
      throw Exception('Failed to hardDelete checklist: ${response.body}');
    }
  }

  static Future<http.Response> _getDeletedItinerariesResponse(
      adventureId) async {
    return http.get(Uri.http(mainApi, '/itinerary/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteItineraryEntryRequest(
      itineraryEntryID) async {
    String userID = UserApi.getInstance().getUserProfile()!.userID;
    return http
        .get(Uri.http(mainApi, '/itinerary/removeEntry/' + itineraryEntryID + '/' + userID));
  }

  static Future<http.Response> _deleteItineraryRequest(itineraryID) async {
    return http.get(Uri.http(
        mainApi,
        '/itinerary/softDelete/' +
            itineraryID +
            '/' +
            UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<http.Response> _hardDeleteItineraryRequest(itineraryID) async {
    return http.get(Uri.http(
        mainApi,
        '/itinerary/hardDelete/' +
            itineraryID +
            '/' +
            UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<http.Response> _restoreItineraryRequest(itineraryID) async {
    return http.get(Uri.http(
        mainApi,
        '/itinerary/restoreItinerary/' +
            itineraryID +
            '/' +
            UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<CreateItinerary> createItinerary(String title,
      String description, String creatorID, String adventureID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/itinerary/create'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'title': title,
        'description': description,
        'advID': adventureID,
        'userID': creatorID
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateItinerary(
          title: title,
          description: description,
          adventureID: adventureID,
          creatorID: creatorID);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create an itinerary.');
    }
  }

  static Future<ItineraryEntry?> getNextEntry(Adventure a) async {
    http.Response response = await _getNextEntry(a);

    if (response.statusCode != 200) {
      return null;
    }

    ItineraryEntry entry = ItineraryEntry.fromJson(jsonDecode(response.body));

    return entry;
  }

  static Future<http.Response> _getNextEntry(Adventure a) async {
    return http
        .get(Uri.http(mainApi, '/itinerary/getNextEntry/' + a.adventureId+'/'+UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<CreateItineraryEntry> createItineraryEntry(
      String entryContainerID,
      String title,
      String description,
      String location,
      String timestamp,
      String userId) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/itinerary/addEntry'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'entryContainerID': entryContainerID,
        'userId':UserApi.getInstance().getUserProfile()!.userID,
        'title': title,
        'description': description,
        'location': location,
        'timestamp': timestamp,
        'userId': userId
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateItineraryEntry(
          entryContainerID: entryContainerID,
          title: title,
          description: description,
          location: location,
          timestamp: timestamp,
          userId: userId);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create an itinerary entry.');
    }
  }

  static Future<http.Response> itineraryEdit(
      String id,
      String userId,
      String entryContainerID,
      String title,
      String description,
      String location,
      String timestamp) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/itinerary/editEntry'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'id': id,
        'userId': userId,
        'entryContainerID': entryContainerID,
        'title': title,
        'description': description,
        'location': location,
        'timestamp': timestamp
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return response;
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to edit an itinerary entry.');
    }
  }

  static Future<List<String>?> getStartAndEndDate(Itinerary i) async {
    http.Response response = await _getStartAndEndDate(i);


    if (response.statusCode != 200) {
      return null;
    }

    if(response.body.length==0)
      {
        return null;
      }

    final body = json.decode(response.body);
    String start=body['startDate'].toString();
    String end=body['endDate'].toString();
    List<String> list=[start,end];
    return list;
  }

  static Future<http.Response> _getStartAndEndDate(Itinerary i) async {
    return http
        .get(Uri.parse("http://"+mainApi+'/itinerary/getStartDateEndDate/'+i.id));
  }

  static Future registerForItinerary(ItineraryEntry i) async {
    http.Response response = await _registerForItinerary(i);


    if (response.statusCode != 200) {
      throw Exception('Failed to register for itinerary item: ${response.body}');
    }

  }

  static Future<http.Response> _registerForItinerary(ItineraryEntry i) async {
    return http
        .get(Uri.parse("http://"+mainApi+'/itinerary/registerUser/' + UserApi.getInstance().getUserProfile()!.userID+"/"+i.id));
  }

  static Future deregisterForItinerary(ItineraryEntry i) async {
    http.Response response = await _deregisterForItinerary(i);


    if (response.statusCode != 200) {
      throw Exception('Failed to deregister for itinerary item: ${response.body}');
    }

  }

  static Future<http.Response> _deregisterForItinerary(ItineraryEntry i) async {
    return http
        .get(Uri.parse("http://"+mainApi+'/itinerary/deregisterUser/' + UserApi.getInstance().getUserProfile()!.userID+"/"+i.id));
  }

  static Future checkUserOff(ItineraryEntry i) async {

    bool serviceEnabled;
    PermissionStatus permissionGranted;
    Location location = Location();
    serviceEnabled = await location.serviceEnabled();
    if (!serviceEnabled) {
      serviceEnabled = await location.requestService();
    }
    permissionGranted = await location.hasPermission();
    if (permissionGranted == PermissionStatus.denied) {
      permissionGranted = await location.requestPermission();
    }
    LocationData? currentLocation;
    if (permissionGranted == PermissionStatus.granted &&
        serviceEnabled) {
      location.changeSettings(accuracy: LocationAccuracy.high);
      location.getLocation().then((value) {
        currentLocation = value;
        LocationApi.setCurrentLocation(currentLocation!);
      });
    }

    http.Response response = await _checkUserOff(i);


    if (response.statusCode != 200) {
      throw Exception('Failed to check itinerary item off for user: ${response.body}');
    }

  }

  static Future<http.Response> _checkUserOff(ItineraryEntry i) async {
    return http
        .get(Uri.parse("http://"+mainApi+'/itinerary/checkUserOff/' + UserApi.getInstance().getUserProfile()!.userID+"/"+i.id));
  }

  static Future<List<RegisteredUser>> getRegisteredUsers(ItineraryEntry i) async {
    http.Response response = await _getRegisteredUsers(i);


    if (response.statusCode != 200) {
      throw Exception('Failed to get the users for the itinerary: ${response.body}');
    }

    List<RegisteredUser> users = (jsonDecode(response.body) as List)
        .map((x) => RegisteredUser.fromJson(x))
        .toList();

    return users;

  }

  static Future<http.Response> _getRegisteredUsers(ItineraryEntry i) async {
    return http
        .get(Uri.parse("http://"+mainApi+'/itinerary/getRegisteredUsers/'+i.id));
  }

  static Future<bool> isRegisteredUser(ItineraryEntry i) async {
    http.Response response = await _isRegisteredUser(i);


    if (response.statusCode != 200) {
      throw Exception('Failed to see if user is registered for entry: ${response.body}');
    }

    bool x =(jsonDecode(response.body) as bool);

    return x;

  }

  static Future<http.Response> _isRegisteredUser(ItineraryEntry i) async {
    return http
        .get(Uri.parse("http://"+mainApi+'/itinerary/isRegisteredUser/'+i.id+"/"+UserApi.getInstance().getUserProfile()!.userID));
  }

}
