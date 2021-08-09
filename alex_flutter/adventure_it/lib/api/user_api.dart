import 'dart:async';
import 'dart:convert';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/constants.dart';
import 'package:http/http.dart' as http;

import 'friendRequest.dart';
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
        'username': username,//
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
    http.Response response = await http.get(Uri.parse('http://localhost:9002/api/GetUser/' + userID));

    if (response.statusCode != 200) {
      throw Exception('Failed to load user information: ${response.body}');
    }//
    print(response.body);

    UserProfile users = (UserProfile.fromJson(jsonDecode(response.body)) );
    print(users.username);
    return users;
  }

  static Future<List<String>> getFriends(String userID) async
  {
    http.Response response =await _getFriends(userID);
    if (response.statusCode != 200) {
      throw Exception('Failed to load list of friends: ${response.body}');
    }
    List<String> friends = (jsonDecode(response.body) as List).map((item) => item as String).toList();

    return friends;
  }

  static Future<http.Response> _getFriends(String userID) async {
    return http.get(Uri.http(userApi, 'api/GetFriends/' + userID));
  }

  static Future<List<FriendRequest>> getFriendRequests(String userID) async
  {
    http.Response response =await _getFriendRequests(userID);
    if (response.statusCode != 200) {
      throw Exception('Failed to load list of friend requests: ${response.body}');
    }

    List<FriendRequest> requests = (jsonDecode(response.body) as List)
        .map((x) => FriendRequest.fromJson(x))
        .toList();

    return requests;

  }

  static Future<http.Response> _getFriendRequests(String userID) async {
    return http.get(Uri.http(userApi, 'api/GetFriendRequests/' + userID));
  }


  static Future<List<UserProfile>> getFriendProfiles(String userID) async
  {
    http.Response response =await _getFriendProfiles(userID);
    if (response.statusCode != 200) {
      throw Exception('Failed to load list of profiles for friends: ${response.body}');
    }

    List<UserProfile> requests = (jsonDecode(response.body) as List)
        .map((x) => UserProfile.fromJson(x))
        .toList();



    return requests;

  }

  static Future<http.Response> _getFriendProfiles(String userID) async {
    return http.get(Uri.http(userApi, 'api/getFriendProfiles/' + userID));
  }

  static Future deleteFriend(String userID, String friendID) async
  {
    http.Response response =await _deleteFriend(userID,friendID);
    if (response.statusCode != 200) {
      throw Exception('Failed to delete friend: ${response.body}');
    }

  }

  static Future<http.Response> _deleteFriend(String userID, String friendID) async {
    return http.get(Uri.http(userApi, 'api/removeFriend/' + userID+"/"+friendID));
  }

  static Future deleteFriendRequest(String requestID) async
  {
    http.Response response =await _deleteFriendRequest(requestID);
    if (response.statusCode != 200) {
      throw Exception('Failed to delete friendRequest: ${response.body}');
    }

  }

  static Future<http.Response> _deleteFriendRequest(String requestID) async {
    return http.get(Uri.http(userApi, 'api/deleteFriendRequest/' + requestID));
  }

  static Future acceptFriendRequest(String requestID) async
  {
    http.Response response =await _acceptFriendRequest(requestID);
    if (response.statusCode != 200) {
      throw Exception('Failed to accept friendRequest: ${response.body}');
    }

  }

  static Future<http.Response> _acceptFriendRequest(String requestID) async {
    return http.get(Uri.http(userApi, 'api/acceptFriendRequest/' + requestID));
  }

  static Future<String> searchUsername (String value) async
  {
    http.Response response =await _searchUsername(value);
    if (response.statusCode != 200) {
      throw Exception('Failed to find user with username: ${response.body}');
    }

    print(response.body.toString());

   String userID= (jsonDecode(response.body.toString()));

    return userID;
  }

  static Future<http.Response> _searchUsername(String username) async {
    return http.get(Uri.http(userApi, 'api/getByUserName/' + username));
  }

  static Future createFriendRequest (String from, String to) async
  {
    http.Response response =await _createFriendRequest(from, to);
    if (response.statusCode != 200) {
      throw Exception('Failed to create friend request: ${response.body}');
    }

  }

  static Future<http.Response> _createFriendRequest(String from, String to) async {
    return http.get(Uri.http(userApi, 'api/createFriendRequest/' +from+"/"+to));
  }

}
