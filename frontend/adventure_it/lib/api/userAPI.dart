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
  bool? notify=false;
  bool? theme = false;
  String? em = "";

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
    // print("Attempting login for $username");
    // this._keycloakUser = await attemptLogIn(username, password);
    // if (this._keycloakUser != null) {
    //   final keycloakUser = this._keycloakUser!;
    //   if(keycloakUser.emailVerified&&keycloakUser.enabled) {
        this._userProfile = await this.fetchBackendProfile('80e1b64d-fd53-4f3a-84a9-14541caff723');
        await this.getNotificationSettings();
        await this.getThemeSettings();
        await this.getEmergencyContact();
    //     if (this._userProfile == null) {
    //       this._userProfile = await this.registerBackendProfile(keycloakUser);
    //     }
    //     return true;
    //   }
    //   else {
    //     this.message="Your email has not yet been verified.";
    //     return false;
    //   }
    // } else {
    //   return false;
    // }

    // this._userProfile = new UserProfile(
    //     userID: '80e1b64d-fd53-4f3a-84a9-14541caff723',
    //     username: 'sim',
    //     firstname: 'Sim',
    //     lastname: 'Siiiiiiiiiiim',
    //     email: 'u17015465@gmail.com',
    //     profileID: "");
    return true;


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
        profileID: ""
    );
  }

  UserProfile? getUserProfile() {
    return this._userProfile;
  }

  Future<List<String>> getFriends(String userID,context) async {
    http.Response response = await _getFriends(userID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to get list of friends!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
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

  Future<List<FriendRequest>> getFriendRequests(String userID,context) async {
    http.Response response = await _getFriendRequests(userID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to get list of friend requests!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
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

  Future<List<UserProfile>> getFriendProfiles(String userID,context) async {
    http.Response response = await _getFriendProfiles(userID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to get list of profiles of your friends!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
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

  Future deleteFriend(String userID, String friendID,context) async {
    http.Response response = await _deleteFriend(userID, friendID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to remove adventurer as a friend!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to delete friend: ${response.body}');
    }
  }

  Future<http.Response> _deleteFriend(String userID, String friendID) async {
    return http.get(
        Uri.parse(userApi + '/user/removeFriend/' + userID + "/" + friendID));
  }

  Future deleteFriendRequest(String requestID,context) async {
    http.Response response = await _deleteFriendRequest(requestID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to decline friend request!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to delete friendRequest: ${response.body}');
    }
  }

  Future<http.Response> _deleteFriendRequest(String requestID) async {
    return http
        .get(Uri.parse(userApi + '/user/deleteFriendRequest/' + requestID));
  }

  Future acceptFriendRequest(String requestID,context) async {
    http.Response response = await _acceptFriendRequest(requestID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to accept friend request!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to accept friendRequest: ${response.body}');
    }
  }

  Future<http.Response> _acceptFriendRequest(String requestID) async {
    return http
        .get(Uri.parse(userApi + '/user/acceptFriendRequest/' + requestID));
  }

  Future<String> searchUsername(String value,context) async {
    http.Response response = await _searchUsername(value);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to find an adventurer with the entered username!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to find user with username: ${response.body}');
    }

    String userID = (jsonDecode(response.body.toString()));

    return userID;
  }

  Future<http.Response> _searchUsername(String username) async {
    return http.get(Uri.parse(userApi + '/user/getByUserName/' + username));
  }

  Future createFriendRequest(String from, String to,context) async {
    http.Response response = await _createFriendRequest(from, to);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to create a friend request!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to create friend request: ${response.body}');
    }
  }

  Future<http.Response> _createFriendRequest(String from, String to) async {
    return http.get(
        Uri.parse(userApi + '/user/createFriendRequest/' + from + "/" + to));
  }

  Future<UserProfile> findUser(String userID,context) async {
    http.Response response = await _findUser(userID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to find adventurer!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to find user: ${response.body}');
    }

    Map<String, dynamic> parsed = json.decode(response.body);
    UserProfile user = UserProfile.fromJson(parsed);

    return user;
  }

  Future<http.Response> _findUser(String userID) async {
    return http.get(Uri.parse(userApi + "/user/getUser/" + userID));
  }

  Future updateUserProfile(context) async {
    http.Response response = await _updateUserProfile(
        _instance.getUserProfile()!.userID);
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to update adventurer\'s profile!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to find user: ${response.body}');
    }

    Map<String, dynamic> parsed = json.decode(response.body);
    UserProfile user = UserProfile.fromJson(parsed);

    _userProfile = user;
  }

  Future<http.Response> _updateUserProfile(String userID) async {
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
  Future<bool> registerKeycloakUser(firstname, lastname, username, email,
      password, passwordCheck) async {
    if (firstname == "" || lastname == "" || username == "" || email == "" ||
        password == "" || passwordCheck == "") {
      this.message = "Please fill in all necessary fields";
      return false;
    }
    if (password == passwordCheck) {
      RegExp passwordReg = RegExp(
        r'^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$',
        caseSensitive: false,
        multiLine: false,
      );
      RegExp usernameReg = RegExp(
        r'^[A-Za-z0-9]{5,}$',
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

  Future<void> displayDialog(BuildContext context, String title,
      String text) async =>
      await showDialog(
        context: context,
        builder: (context) =>
            AlertDialog(title: Text(title), content: Text(text)),
      );

  static Future<http.Response> editProfile(String userId,
      String username,
      String firstName,
      String lastName,
      String email,context) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/user/editUserProfile'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'userId': userId,
        'username': username,
        'firstName': firstName,
        'lastName': lastName,
        'email': email
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      final keycloakUser = UserApi
          .getInstance()
          ._keycloakUser;
      UserApi
          .getInstance()
          ._userProfile =
      await UserApi.getInstance().fetchBackendProfile(keycloakUser!.id);
      return response;
    } else {
      SnackBar snackBar=SnackBar(content: Text('Failed to edit adventurer\'s profile!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to edit the user\'s profile.');
    }
  }

  Future getEmergencyContact() async {
    String userID = UserApi.getInstance().getUserProfile()!.userID;
    final response = await http.get(
      Uri.http(mainApi, 'user/getEmergencyContact/' + userID)
    );
    if(response.statusCode != 200) {
      throw Exception('Failed to retrieve emergency contact: ${response.body}');
    }

    em = response.body.toString();
    return;
  }

  static Future<http.Response> setEmergencyContact(String email) async {
    String userID = UserApi.getInstance().getUserProfile()!.userID;
    /*RegExp emailReg = RegExp(
      r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$',
      caseSensitive: false,
      multiLine: false,
    );*/
    //if(emailReg.hasMatch(email)) {
    final response = await http.post(
      Uri.parse('http://localhost:9999/user/setEmergencyContact/'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'userId': userID,
        'email': email
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
      throw Exception('Failed to set the emergency contact.');
    }
  //}
    //this.message = "Email must have"
  }
  Future setNotificationSettings(context) async {
    http.Response response = await _setNotificationSettings();
    if (response.statusCode != 200) {
      SnackBar snackBar=SnackBar(content: Text('Failed to update notification settings!',style: TextStyle( color: Theme.of(context).textTheme.bodyText1!.color,fontWeight: FontWeight.bold)),backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to update settings: ${response.body}');
    }

  }

  Future<http.Response> _setNotificationSettings() async {
    return http.get(Uri.parse(userApi + "/user/setNotificationSettings/"+_userProfile!.userID));
  }

  Future getNotificationSettings() async {
    http.Response response = await _getNotificationSettings();
    if (response.statusCode != 200) {
      throw Exception('Failed to getSettings: ${response.body}');
    }

    bool x=(jsonDecode(response.body));

    this.notify=x;
    return;

  }

  Future<http.Response> _getNotificationSettings() async {
    return http.get(Uri.parse(userApi + "/user/getNotificationSettings/"+_userProfile!.userID));
  }

  Future getThemeSettings() async {
    http.Response response = await _getThemeSettings();
    if (response.statusCode != 200) {
      throw Exception('Failed to getSettings: ${response.body}');
    }

    bool x=(jsonDecode(response.body));
    print(x);
    this.theme=x;
    return;

  }

  static Future<http.Response> _getThemeSettings() async {
    return http.get(Uri.parse(userApi + "/user/getUserTheme/"+ UserApi.getInstance().getUserProfile()!.userID));
  }

  Future<http.Response> setTheme(bool theme) async {
    String userID = UserApi.getInstance().getUserProfile()!.userID;
    final response = await http.post(
      Uri.parse('http://localhost:9999/user/setUserTheme'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'userId': userID,
        'theme': theme.toString()
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      this.theme = theme;
      //fetchBackendProfile(UserApi.getInstance().getUserProfile()!.userID);
      return response;
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to set the theme.');
    }
  }
}
