import 'dart:convert';
import 'package:adventure_it/api/createItineraryEntry.dart';
import 'package:adventure_it/constants.dart';
import '/api/itinerary.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

import 'itineraryEntry.dart';

import 'createItinerary.dart';

class ItineraryApi {
  static Future<List<Itinerary>> getItineraries(Adventure? a) async {
    http.Response response =
    await _getItineraries(a!.adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of itineraries: ${response.body}');
    }

    List<Itinerary> itineraries = (jsonDecode(response.body) as List)
        .map((x) => Itinerary.fromJson(x))
        .toList();

    return itineraries;
  }

  static Future<List<ItineraryEntry>> getItineraryEntries(Itinerary i) async {
    http.Response response =
    await _getItineraryEntries(i!.id);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of entries for itinerary: ${response.body}');
    }


    List<ItineraryEntry> itineraries = (jsonDecode(response.body) as List)
        .map((x) => ItineraryEntry.fromJson(x))
        .toList();

    return itineraries;
  }



  static Future<http.Response> _getItineraries(adventureID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/viewItinerariesByAdventure/' + adventureID));
  }

  static Future<http.Response> _getItineraryEntries(itineraryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/viewItinerary/' + itineraryID));
  }

  static Future<List<Itinerary>> getDeletedItinerary(adventureId) async {
    http.Response response =
    await _getDeletedItinerariesResponse(adventureId);

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



  static Future<http.Response> _getDeletedItinerariesResponse(adventureId) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteItineraryEntryRequest(itineraryEntryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/removeEntry/' + itineraryEntryID));
  }

  static Future<http.Response> _deleteItineraryRequest(itineraryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/softDelete/' + itineraryID));
  }

  static Future<http.Response> _hardDeleteItineraryRequest(itineraryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/hardDelete/' + itineraryID));
  }


  static Future<http.Response> _restoreItineraryRequest(itineraryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/restoreItinerary/' + itineraryID));
  }

  static Future<CreateItinerary> createItinerary(String title, String description, String creatorID, String adventureID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9009/itinerary/create'), //get uri
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

    if (response.statusCode == 201) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateItinerary(title: title, description: description, adventureID: adventureID, creatorID: creatorID);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create an itinerary.');
    }
  }

  static Future<CreateItineraryEntry> createItineraryEntry(String entryContainerID, String title, String description, String location, String timestamp) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/itinerary/addEntry'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'entryContainerID': entryContainerID,
        'title': title,
        'description': description,
        'location': location,
        'timestamp': timestamp
      }),


    );

    if (response.statusCode == 201) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateItineraryEntry(entryContainerID: entryContainerID, title: title, description: description, location: location, timestamp: timestamp);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create an itinerary entry.');
    }
  }
}