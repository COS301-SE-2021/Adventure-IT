import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/friendRequest.dart';
import 'package:adventure_it/api/locationAPI.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';

class FriendModel extends ChangeNotifier {
  List<UserProfile>? _friends = null;
  UserApi _user=UserApi.getInstance();

  FriendModel(String userID) {
    fetchAllFriendProfiles(userID).then((friends) {
      friends != null ? _friends = friends : List.empty();
    });

  }

  List<UserProfile>? get friends => _friends?.toList();


  Future fetchAllFriendProfiles(String userID) async {
    _friends = await _user.getFriendProfiles(userID);

    notifyListeners();
  }

  Future deleteFriend(String user, String friend) async {
    await _user.deleteFriend(user, friend);

    var index = _friends!.indexWhere((element) => element.userID == friend);
    _friends!.removeAt(index);

    notifyListeners();
  }
}

class FriendRequestModel extends ChangeNotifier {
  List<FriendRequest>? _friends = null;
  UserApi _user=UserApi.getInstance();

  FriendRequestModel(String userID) {
    fetchAllFriends(userID).then((friends) {
      friends != null ? _friends = friends : List.empty();
    });

  }

  List<FriendRequest>? get friends => _friends?.toList();


  Future fetchAllFriends(String value) async {
    _friends = await _user.getFriendRequests(value);

    notifyListeners();
  }


  Future deleteFriendRequest(String id) async {
    await _user.deleteFriendRequest(id);

    var index = _friends!.indexWhere((element) => element.id == id);
    _friends!.removeAt(index);

    notifyListeners();
  }

  Future acceptFriendRequest(String id) async {
    await _user.acceptFriendRequest(id);

    var index = _friends!.indexWhere((element) => element.id == id);
    _friends!.removeAt(index);

    notifyListeners();
  }
}
