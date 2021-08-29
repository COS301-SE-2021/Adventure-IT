import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';

class UserModel extends ChangeNotifier {
  UserProfile? _profile = null;
  final UserApi api = UserApi.getInstance();

  UserModel();

  Future getProfile() async {
    UserApi.getInstance().getUserProfile();
    return;
  }

  UserProfile? get profile => _profile;

  Future editProfile(
      String a, String b, String c, String d, String e) async {
    await UserApi.editProfile(a, b, c, d, e);
    await getProfile();
    notifyListeners();
  }
}