import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

import 'createItinerary.dart';

class ItineraryApi {
  Future<CreateItinerary> createItinerary(String title, String description, String creatorID, String adventureID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9002/api/itinerary/create'), //get uri
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
      throw Exception('Failed to create an itinerary.');
    }
}
}