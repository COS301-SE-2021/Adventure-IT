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

  FriendModel(String userID)
  {
    fetchAllFriends(userID).then((friends) => friends != null? _friends = friends:List.empty());
  }

  List<UserProfile>? get friends => _friends?.toList();

  Future fetchAllFriends(String value) async {
    _friends = await UserApi.getFriends(value);

    notifyListeners();
  }

  Future deleteFriend(String user, String friend) async {
    await UserApi.deleteFriend(user,friend);

    var index = _friends!.indexWhere((element) => element.userID == friend);
    _friends!.removeAt(index);

    notifyListeners();
  }


}

class FriendRequestModel extends ChangeNotifier {
  List<FriendRequest>? _friends = null;

  FriendRequestModel(String userID)
  {
    fetchAllFriends(userID).then((friends) => friends != null? _friends = friends:List.empty());
  }

  List<FriendRequest>? get friends => _friends?.toList();

  Future fetchAllFriends(String value) async {
    _friends = await UserApi.getFriendRequests(value);

    notifyListeners();
  }

  Future deleteFriendRequest(String id) async {
    await UserApi.deleteFriendRequest(id);

    var index = _friends!.indexWhere((element) => element.id == id);
    _friends!.removeAt(index);

    notifyListeners();
  }

  Future acceptFriendRequest(String id) async {
    await UserApi.acceptFriendRequest(id);

    var index = _friends!.indexWhere((element) => element.id == id);
    _friends!.removeAt(index);

    notifyListeners();
  }


}