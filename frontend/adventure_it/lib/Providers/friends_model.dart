import 'package:adventure_it/api/friendRequest.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';

class FriendModel extends ChangeNotifier {
  List<UserProfile>? _friends;
  UserApi _user = UserApi.getInstance();
  BuildContext? context;

  FriendModel(String userID,context) {
    this.context=context;
    fetchAllFriendProfiles(userID).then((friends) {
      friends != null ? _friends = friends : List.empty();
    });
  }

  List<UserProfile>? get friends => _friends?.toList();

  Future fetchAllFriendProfiles(String userID) async {
    _friends = await _user.getFriendProfiles(userID,context);

    notifyListeners();
  }

  Future deleteFriend(String user, String friend) async {
    await _user.deleteFriend(user, friend,context);

    var index = _friends!.indexWhere((element) => element.userID == friend);
    _friends!.removeAt(index);

    notifyListeners();
  }
}

class FriendRequestModel extends ChangeNotifier {
  List<FriendRequest>? _friends;
  BuildContext? context;
  UserApi _user = UserApi.getInstance();

  FriendRequestModel(String userID,context) {
    this.context=context;
    fetchAllFriends(userID).then((friends) {
      friends != null ? _friends = friends : List.empty();
    });
  }

  List<FriendRequest>? get friends => _friends?.toList();

  Future fetchAllFriends(String value) async {
    _friends = await _user.getFriendRequests(value,context);

    notifyListeners();
  }

  Future deleteFriendRequest(String id) async {
    await _user.deleteFriendRequest(id,context);

    var index = _friends!.indexWhere((element) => element.id == id);
    _friends!.removeAt(index);

    notifyListeners();
  }

  Future acceptFriendRequest(String id) async {
    await _user.acceptFriendRequest(id,context);

    var index = _friends!.indexWhere((element) => element.id == id);
    _friends!.removeAt(index);

    notifyListeners();
  }
}
