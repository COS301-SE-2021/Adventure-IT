import 'dart:convert';

import 'package:adventure_it/constants.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;

class UserApi {
  String? username;
  String? uuid;
  bool hasToken = false;

  // Secure Local Storage
  final storage = FlutterSecureStorage();

  // TODO: Use ENV for sensitive information
  final String keycloakClientSecret = "e0ddc4e5-7d32-4340-843f-bd7d736d1100";

  // Start: Singleton Design Pattern
  static UserApi _instance = new UserApi._();

  // Private constructor
  UserApi._() {
    // Check if there's an access token
    final jwtToken = storage.read(key: 'jwt');
    if (jwtToken != null) {
      this.hasToken = true;
    }
  }

  static UserApi getInstance() {
    return _instance;
  }
  // End: Singleton Design Pattern

  // Publically Exposed Login Method
  Future<bool> logIn(String username, String password) async {
    await _attemptLogIn(username, password);
    if (this.username != null) {
      await this._fetchUserUUID();
      return true;
    } else {
      return false;
    }
  }

  // Attempt Login to Keycloak (PRIVATE)
  Future _attemptLogIn(String username, String password) async {
    var res = await http.post(Uri.parse(authApiGetToken), body: {
      "client_id": "adventure-it-maincontroller",
      "grant_type": "password",
      "client_secret": keycloakClientSecret,
      "scope": "openid",
      "username": username,
      "password": password
    }, headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    });
    if (res.statusCode == 200) {
      this.username = username;
      storage.write(key: "jwt", value: jsonDecode(res.body)["access_token"]);
      this.hasToken = true;
    } else {
      debugPrint("Login Failed");
      debugPrint(res.body.toString());
    }
  }

  // Get Admin Access Token (PRIVATE)
  Future<String> _adminLogIn() async {
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

  // Get User's UUID from Keycloak's Admin API (PRIVATE)
  Future<void> _fetchUserUUID() async {
    if (this.username != null) {
      final adminJWT = await this._adminLogIn();
      late final responseJson;
      final Map<String, String> queryParameters = {
        'username': this.username!,
        'max': '1'
      };
      final uri = Uri.parse(authApiAdmin +
          'users?' +
          Uri(queryParameters: queryParameters).query);
      var res =
          await http.get(uri, headers: {'Authorization': 'Bearer $adminJWT'});
      if (res.statusCode == 200) {
        responseJson = jsonDecode(res.body);
        this.uuid = responseJson[0]['id'];
      } else {
        debugPrint(res.body);
        throw Exception("Could Not Fetch User UUID");
      }
    } else {
      throw Exception(
          "Attempted to get user's UUID without successful login first");
    }
  }
}
