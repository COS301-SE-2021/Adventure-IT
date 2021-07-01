import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

class UserApi {
  Future<RegisterUser> createUser(String firstName,String lastName,String username,String email,String phoneNumber,String password) async {
    final response = await http.post(
      Uri.parse('https://jsonplaceholder.typicode.com/albums'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'firstName': firstName,
        'lastName': lastName,
        'username': username,
        'email': email,
        'phoneNumber': phoneNumber,
        'password': password,
      }),
    );

    if (response.statusCode == 201) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      return RegisterUser(firstName: firstName, lastName: lastName, username: username, email: email, phoneNumber: phoneNumber, password: password);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      throw Exception('Failed to create album.');
    }
  }
}
