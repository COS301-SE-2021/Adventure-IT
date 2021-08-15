import 'dart:convert';

import 'package:adventure_it/api/keycloakUser.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/constants.dart';
import 'package:flutter/cupertino.dart';
import 'package:adventure_it/api/friendRequest.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class UserApi {
  bool hasToken = false;
  KeycloakUser? _keycloakUser;
  UserProfile? _userProfile;

  // TODO: Use ENV for sensitive information
  final String keycloakClientSecret = "e0ddc4e5-7d32-4340-843f-bd7d736d1100";

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
    this._keycloakUser = await _attemptLogIn(username, password);
    if (this._keycloakUser != null) {
      final keycloakUser = this._keycloakUser!;
      this._userProfile = await this._fetchBackendProfile(keycloakUser.id);
      if (this._userProfile == null) {
        this._userProfile = await this._registerBackendProfile(keycloakUser);
      }
      return true;
    } else {
      return false;
    }
  }

  // Attempt Login to Keycloak (PRIVATE)
  Future<KeycloakUser?> _attemptLogIn(String username, String password) async {
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
      return this._fetchKeyCloakUser(username);
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

  // Get user from Keycloak's Admin API (PRIVATE)
  Future<KeycloakUser?> _fetchKeyCloakUser(username) async {
    final adminJWT = await this._adminLogIn();
    late final responseJson;
    final Map<String, String> queryParameters = {
      'username': username!,
      'max': '1'
    };
    final uri = Uri.parse(
        authApiAdmin + 'users?' + Uri(queryParameters: queryParameters).query);
    var res =
        await http.get(uri, headers: {'Authorization': 'Bearer $adminJWT'});
    if (res.statusCode == 200) {
      responseJson = jsonDecode(res.body)[0];
      return KeycloakUser.fromJson(responseJson);
    } else {
      debugPrint(res.body);
      return null;
    }
  }

  // Retrieve the backend user profile (PRIVATE)
  Future<UserProfile?> _fetchBackendProfile(String targetUuid) async {
    debugPrint("Getting backend profile for: " + targetUuid);
    final res = await http.get(Uri.parse(userApi + "/user/GetUser/" + targetUuid));
    final jsonRes = jsonDecode(res.body);
    if (res.statusCode == 500) {
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
  Future<UserProfile?> _registerBackendProfile(KeycloakUser userInfo) async {
    debugPrint("Registering backend profile for $userInfo.username");
    final res = await http.post(Uri.parse(userApi + "/user/RegisterUser/"),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          "userID": userInfo.id,
          "firstName": userInfo.firstName,
          "lastName": userInfo.lastName,
          "username": userInfo.username,
          "email": userInfo.email
        }));
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
    return http.get(Uri.http(userApi, '/user/GetFriends/' + userID));
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
    return http.get(Uri.parse(userApi+'/user/getFriendRequests/'+ userID));
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
        Uri.http(userApi, '/user/removeFriend/' + userID + "/" + friendID));
  }

  Future deleteFriendRequest(String requestID) async {
    print("success");
    http.Response response = await _deleteFriendRequest(requestID);
    if (response.statusCode != 200) {
      throw Exception('Failed to delete friendRequest: ${response.body}');
    }
  }

  Future<http.Response> _deleteFriendRequest(String requestID) async {
    return http
        .get(Uri.http(userApi, '/user/deleteFriendRequest/' + requestID));
  }

  Future acceptFriendRequest(String requestID) async {
    http.Response response = await _acceptFriendRequest(requestID);
    if (response.statusCode != 200) {
      throw Exception('Failed to accept friendRequest: ${response.body}');
    }
  }

  Future<http.Response> _acceptFriendRequest(String requestID) async {
    return http
        .get(Uri.http(userApi, '/user/acceptFriendRequest/' + requestID));
  }

  Future<String> searchUsername(String value) async {
    http.Response response = await _searchUsername(value);
    if (response.statusCode != 200) {
      throw Exception('Failed to find user with username: ${response.body}');
    }

    print(response.body.toString());

    String userID = (jsonDecode(response.body.toString()));

    return userID;
  }

  Future<http.Response> _searchUsername(String username) async {
    return http.get(Uri.http(userApi, '/user/getByUserName/' + username));
  }

  Future createFriendRequest(String from, String to) async {
    http.Response response = await _createFriendRequest(from, to);
    if (response.statusCode != 200) {
      throw Exception('Failed to create friend request: ${response.body}');
    }
  }

  Future<http.Response> _createFriendRequest(String from, String to) async {
    return http
        .get(Uri.http(userApi, '/user/createFriendRequest/' + from + "/" + to));
  }

  Future<String?> _retrieve(key) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString(key);
  }

  Future<void> _store(key, value) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString(key, value);
  }

  Future<bool> registerKeycloakUser(
      firstname, lastname, username, email, password) async {
    final adminJWT = await this._adminLogIn();
    final response =
        await http.post(Uri.parse(authApiAdmin + '/users/'), body: '''
      {
        "firstName" : "$firstname",
        "lastName" : "$lastname", 
        "username" : "$username", 
        "email" : "$email"
      },
      headers: {'Authorization': 'Bearer $adminJWT'}
    ''');
    // TODO figure out why this isn't working
    // Getting a 401 error here
    if (response.body == "success") {
      return true;
    } else {
      print(response.body);
      return false;
    }
  }
}
