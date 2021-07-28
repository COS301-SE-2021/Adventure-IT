import 'dart:convert';
import 'package:adventure_it/constants.dart';
import '/api/itinerary.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

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

  static Future<http.Response> _getItineraries(adventureID) async {
    return http.get(Uri.http(itineraryApi, '/viewItinerariesByAdventure/' + adventureID));
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

  static Future hardDeleteItinerary(itineraryID) async {
    http.Response response = await _hardDeleteItineraryRequest(itineraryID);


    if (response.statusCode != 200) {
      throw Exception('Failed to hardDelete checklist: ${response.body}');
    }

  }



  static Future<http.Response> _getDeletedItinerariesResponse(adventureId) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteItineraryRequest(itineraryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/softDelete/' + itineraryID));
  }

  static Future<http.Response> _hardDeleteItineraryRequest(itineraryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/hardDelete/' + itineraryID));
  }


  static Future<http.Response> _restoreItineraryRequest(itineraryID) async {

    return http.get(Uri.http(itineraryApi, '/itinerary/restoreBudget/' + itineraryID));
  }
}