import 'dart:convert';
import 'dart:core';

import 'package:adventure_it/api/keycloakUser.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/constants.dart';
import 'package:flutter/cupertino.dart';
import 'package:adventure_it/api/friendRequest.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class UserApi {
  bool hasToken = false;
  String message = "";
  KeycloakUser? _keycloakUser;
  UserProfile? _userProfile;

  // TODO: Use ENV for sensitive information
  final String keycloakClientSecret = "f0e75041-7324-4949-bb90-bcd3ddda5bc6";

  // Start: Singleton Design Pattern
  static UserApi _instance = new UserApi._();

  // Private constructor
  UserApi._() {
    // Check if there's an access token
    final jwtToken = this._retrieve('jwt');
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
    print("Attempting login for $username");
    this._keycloakUser = await attemptLogIn(username, password);
    if (this._keycloakUser != null) {
      final keycloakUser = this._keycloakUser!;
      if(keycloakUser.emailVerified&&keycloakUser.enabled) {
        this._userProfile = await this.fetchBackendProfile(keycloakUser.id);
        if (this._userProfile == null) {
          this._userProfile = await this.registerBackendProfile(keycloakUser);
        }
        return true;
      }
      else {
        this.message="Your email has not yet been verified.";
        return false;
      }
    } else {
      return false;
    }
  }

  // Attempt Login to Keycloak (PRIVATE)
  Future<KeycloakUser?> attemptLogIn(String username, String password) async {
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
      this._store("jwt", jsonDecode(res.body)["access_token"]);
      this._store("refresh_token", jsonDecode(res.body)["refresh_token"]);
      return this.fetchKeyCloakUser(username);
    } else {
      String errorMessage = jsonDecode(res.body)['error_description'];
        this.message = errorMessage;
    }
  }

  // Get Admin Access Token (PRIVATE)
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

  // Get user from Keycloak's Admin API (PRIVATE)
  Future<KeycloakUser?> fetchKeyCloakUser(username) async {
    final adminJWT = await this.adminLogIn();
    late final responseJson;
    final Map<String, dynamic> queryParameters = {
      'username': username!,
      'exact': "true"
    };
    final uri = Uri.parse(
        authApiAdmin + 'users?' + Uri(queryParameters: queryParameters).query);
    var res =
        await http.get(uri, headers: {'Authorization': 'Bearer $adminJWT'});
    if (res.statusCode == 200) {
      responseJson = jsonDecode(res.body)[0];
      return KeycloakUser.fromJson(responseJson);
    } else {
      debugPrint("Error fetching keycloak user");
      debugPrint(res.body);
      return null;
    }
  }

  // Retrieve the backend user profile (PRIVATE)
  Future<UserProfile?> fetchBackendProfile(String targetUuid) async {
    debugPrint("Getting backend profile for: " + targetUuid);
    final res =
        await http.get(Uri.parse(userApi + "/user/getUser/" + targetUuid));
    final jsonRes = jsonDecode(res.body);
    print(jsonRes);
    print(res.statusCode);
    if (res.statusCode == 404) {
      if (jsonRes['message'] ==
          "User does not exist - user is not registered as an Adventure-IT member") {
        debugPrint("Backend profile does not exist");
        return null;
      }
    } else if (res.statusCode == 200) {
      debugPrint("Backend profile exists");
      debugPrint(jsonRes.toString());
      return new UserProfile.fromJson(jsonRes!);
    }
  }

  // Register a user in the backend (PRIVATE)
  Future<UserProfile?> registerBackendProfile(KeycloakUser userInfo) async {
    String username = userInfo.username;
    debugPrint("Registering backend profile for $username");
    final res = await http.post(Uri.parse(userApi + "/user/registerUser/"),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(<String, String>{
          "userID": userInfo.id,
          "firstName": userInfo.firstName,
          "lastName": userInfo.lastName,
          "username": userInfo.username,
          "email": userInfo.email
        }));
    print(res.body);
    return new UserProfile(
      userID: userInfo.id,
      username: userInfo.username,
      firstname: userInfo.firstName,
      lastname: userInfo.lastName,
      email: userInfo.email,
    );
  }

  UserProfile? getUserProfile() {
    return this._userProfile;
  }

  Future<List<String>> getFriends(String userID) async {
    http.Response response = await _getFriends(userID);
    if (response.statusCode != 200) {
      throw Exception('Failed to load list of friends: ${response.body}');
    }
    List<String> friends = (jsonDecode(response.body) as List)
        .map((item) => item as String)
        .toList();

    return friends;
  }

  Future<http.Response> _getFriends(String userID) async {
    return http
        .get(Uri.parse("http://" + mainApi + '/user/getFriends/' + userID));
  }

  Future<List<FriendRequest>> getFriendRequests(String userID) async {
    http.Response response = await _getFriendRequests(userID);
    if (response.statusCode != 200) {
      throw Exception(
          'Failed to load list of friend requests: ${response.body}');
    }

    List<FriendRequest> requests = (jsonDecode(response.body) as List)
        .map((x) => FriendRequest.fromJson(x))
        .toList();

    return requests;
  }

  Future<http.Response> _getFriendRequests(String userID) async {
    return http.get(
        Uri.parse("http://" + mainApi + '/user/getFriendRequests/' + userID));
  }

  Future<List<UserProfile>> getFriendProfiles(String userID) async {
    http.Response response = await _getFriendProfiles(userID);
    if (response.statusCode != 200) {
      throw Exception(
          'Failed to load list of profiles for friends: ${response.body}');
    }

    List<UserProfile> requests = (jsonDecode(response.body) as List)
        .map((x) => UserProfile.fromJson(x))
        .toList();

    return requests;
  }

  Future<http.Response> _getFriendProfiles(String userID) async {
    return http.get(Uri.parse(userApi + '/user/getFriendProfiles/' + userID));
  }

  Future deleteFriend(String userID, String friendID) async {
    http.Response response = await _deleteFriend(userID, friendID);
    if (response.statusCode != 200) {
      throw Exception('Failed to delete friend: ${response.body}');
    }
  }

  Future<http.Response> _deleteFriend(String userID, String friendID) async {
    return http.get(
        Uri.parse(userApi + '/user/removeFriend/' + userID + "/" + friendID));
  }

  Future deleteFriendRequest(String requestID) async {
    http.Response response = await _deleteFriendRequest(requestID);
    if (response.statusCode != 200) {
      throw Exception('Failed to delete friendRequest: ${response.body}');
    }
  }

  Future<http.Response> _deleteFriendRequest(String requestID) async {
    return http
        .get(Uri.parse(userApi + '/user/deleteFriendRequest/' + requestID));
  }

  Future acceptFriendRequest(String requestID) async {
    http.Response response = await _acceptFriendRequest(requestID);
    if (response.statusCode != 200) {
      throw Exception('Failed to accept friendRequest: ${response.body}');
    }
  }

  Future<http.Response> _acceptFriendRequest(String requestID) async {
    return http
        .get(Uri.parse(userApi + '/user/acceptFriendRequest/' + requestID));
  }

  Future<String> searchUsername(String value) async {
    http.Response response = await _searchUsername(value);
    if (response.statusCode != 200) {
      throw Exception('Failed to find user with username: ${response.body}');
    }

    String userID = (jsonDecode(response.body.toString()));

    return userID;
  }

  Future<http.Response> _searchUsername(String username) async {
    return http.get(Uri.parse(userApi + '/user/getByUserName/' + username));
  }

  Future createFriendRequest(String from, String to) async {
    http.Response response = await _createFriendRequest(from, to);
    if (response.statusCode != 200) {
      throw Exception('Failed to create friend request: ${response.body}');
    }
  }

  Future<http.Response> _createFriendRequest(String from, String to) async {
    return http.get(
        Uri.parse(userApi + '/user/createFriendRequest/' + from + "/" + to));
  }

  Future<UserProfile> findUser(String userID) async {
    http.Response response = await _findUser(userID);
    if (response.statusCode != 200) {
      throw Exception('Failed to find user: ${response.body}');
    }

    Map<String, dynamic> parsed = json.decode(response.body);
    UserProfile user = UserProfile.fromJson(parsed);

    return user;
  }

  Future<http.Response> _findUser(String userID) async {
    return http.get(Uri.parse(userApi + "/user/getUser/" + userID));
  }

  Future<String?> _retrieve(key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString(key);
  }

  Future<void> _store(key, value) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString(key, value);
  }

  // Register a user in Keycloak
  Future<bool> registerKeycloakUser(
      firstname, lastname, username, email, password, passwordCheck) async {
    if(firstname==""||lastname==""||username==""||email==""||password==""||passwordCheck=="")
      {
        this.message="Please fill in necessary fields";
        return false;
      }
    if (password == passwordCheck) {
      RegExp passwordReg = RegExp(
        r'^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$',
        caseSensitive: false,
        multiLine: false,
      );
      RegExp usernameReg = RegExp(
        r'^(?=.*?[a-z])(?=.*?[0-9]).{5,}$',
        caseSensitive: false,
        multiLine: false,
      );
      if (usernameReg.hasMatch(username)) {
        if (passwordReg.hasMatch(password)) {
          final adminJWT = await this.adminLogIn();
          final res = await http.post(Uri.parse(authApiAdmin + 'users/'),
              body: jsonEncode(<String, dynamic>{
                "firstName": "$firstname",
                "lastName": "$lastname",
                "username": "$username",
                "email": "$email",
                "credentials": [
                  {"type": "password", "value": "$password", "temporary": false}
                ],
                "enabled": "true"
              }),
              headers: {
                'Authorization': 'Bearer $adminJWT',
                'Content-Type': 'application/json; charset=UTF-8'
              });
          if (res.statusCode == 201) {
            String newUser = (await this.fetchKeyCloakUser(username))!.id;
            final res = await http.put(
                Uri.parse(authApiAdmin + 'users/$newUser/send-verify-email'),
                headers: {
                  'Authorization': 'Bearer $adminJWT',
                  'Content-Type': 'application/json; charset=UTF-8'
                });
            debugPrint(res.body);
            return true;
          } else {
            this.message = res.body;
            debugPrint(res.body);
            return false;
          }
        }
        this.message =
            "Password must contain one uppercase letter, one lowercase letter, one special character, one digit and be at least 8 characters long";
        return false;
      }
      this.message =
          "Username may only be comprised of lowercase letters and digits and must be at least 5 characters long";
      return false;
    }
    this.message = "Passwords do not match";
    return false;
  }

  Future<void> displayDialog(
          BuildContext context, String title, String text) async =>
      await showDialog(
        context: context,
        builder: (context) =>
            AlertDialog(title: Text(title), content: Text(text)),
      );
}
