import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/constants.dart';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;

import 'loginUser.dart';
import 'userProfile.dart';

// TODO: Convert to singleton
class UserApi {
  final String keycloakClientSecret = "e0ddc4e5-7d32-4340-843f-bd7d736d1100";
  late final String admin_jwt;
  late String username;
  late final String userUUID;

  UserApi() {
    this.adminLogIn().then((value) {
      this.admin_jwt = value;
    });
  }

  Future<RegisterUser> createUser(
      String firstName,
      String lastName,
      String username,
      String email,
      String phoneNumber,
      String password) async {
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
      return RegisterUser(
          firstName: firstName,
          lastName: lastName,
          username: username,
          email: email,
          phoneNumber: phoneNumber,
          password: password);
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

  static Future<UserProfile> getUserByUUID(String userID) async {
    print("in function");
    http.Response response = await http
        .get(Uri.parse('http://localhost:9002/api/GetUser/' + userID));

    if (response.statusCode != 200) {
      throw Exception('Failed to load user information: ${response.body}');
    }
    print(response.body);

    UserProfile users = (UserProfile.fromJson(jsonDecode(response.body)));
    print(users.username);
    return users;
  }

  Future<String?> attemptLogIn(String username, String password) async {
    var res = await http.post(Uri.parse(authApiGetToken), body: {
      "client_id": "adventure-it-maincontroller",
      "grant_type": "password",
      "client_secret": keycloakClientSecret,
      "scope": "openid",
      "username": username,
      "password": password
    });
    if (res.statusCode == 200) {
      this.username = username;
      return jsonDecode(res.body)["access_token"];
    }
    return null;
  }

  Future<String> adminLogIn() async {
    var res = await http.post(Uri.parse(authApiGetToken), body: {
      "client_id": "adventure-it-maincontroller",
      "grant_type": "password",
      "client_secret": keycloakClientSecret,
      "scope": "openid",
      "username": "admin",
      "password": "admin"
    });
    if (res.statusCode == 200) {
      return jsonDecode(res.body)["access_token"];
    } else {
      throw Exception("Admin Login Failed");
    }
  }

  Future<void> fetchUserUUID() async {
    // TODO: Figure out why it's not getting the UUID correctly
    // Response says "unauthorized"
    late final responseJson;
    var res = await http.get(
        Uri.parse(authApiAdmin + "users?username=" + this.username + "&max=1"),
        headers: {'Authorization': 'Bearer $this.admin_jwt'});
    debugPrint("Fetch User UUID JWT:");
    debugPrint(this.admin_jwt);
    if (res.statusCode == 200) {
      responseJson = jsonDecode(res.body);
      this.userUUID = responseJson[0]['id'];
      debugPrint("Fetched User UUID");
      debugPrint(this.userUUID);
    } else {
      debugPrint(res.body);
      throw Exception("Could Not Fetch User UUID");
    }
  }

  // Future<int> attemptSignUp(String username, String password) async {
  //   var res = await http.post(Uri.parse("$SERVER_IP/signup"),
  //       body: {"username": username, "password": password});
  //   return res.statusCode;
  // }

}
