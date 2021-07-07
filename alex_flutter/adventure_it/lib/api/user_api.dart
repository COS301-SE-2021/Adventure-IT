import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

import 'loginUser.dart';
import 'userProfile.dart';

class UserApi {
  Future<RegisterUser> createUser(String firstName,String lastName,String username,String email,String phoneNumber,String password) async {
    final response = await http.post(
      Uri.parse('http://localhost:9002/api/RegisterUser'), //get uri
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
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return RegisterUser(firstName: firstName, lastName: lastName, username: username, email: email, phoneNumber: phoneNumber, password: password);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      throw Exception('Failed to register user.');
    }
  }

  Future<LoginUser> loginUser(String username, String password) async {
    final response = await http.post(
      Uri.parse('http://localhost:9002/api/LoginUser'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'username': username,
        'password': password,
      }),
    );

    if (response.statusCode == 201) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      return LoginUser(username: username, password: password);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      throw Exception('Failed to register user.');
    }
  }

  static Future<List<UserProfile>> getUserByUUID(String userID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9002/api/UserProfile'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'userID': userID,
      }),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to load user information: ${response.body}');
    }

    List<UserProfile> users = (jsonDecode(response.body) as List)
        .map((x) => UserProfile.fromJson(x)).toList();

    return users;
  }
}
