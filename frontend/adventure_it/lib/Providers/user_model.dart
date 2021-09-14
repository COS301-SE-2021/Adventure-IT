import 'package:adventure_it/api/mediaAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';

class UserModel extends ChangeNotifier {
  UserProfile? _profile;
  String em = "";

  final UserApi api = UserApi.getInstance();

  UserModel() {
    getProfile().then((value) {
      if (value != null) {
        _profile = value;
      }
    });
  }

  Future getProfile() async {
    UserApi.getInstance().fetchBackendProfile(UserApi.getInstance().getUserProfile()!.userID);
    _profile = UserApi.getInstance().getUserProfile();
    return;
  }

  UserProfile? get profile => _profile;

  Future editProfile(String a, String b, String c, String d, String e) async {
    await UserApi.editProfile(a, b, c, d, e);
    await getProfile();
    notifyListeners();
  }

  Future getEM() async {
    em = await UserApi.getEmergencyContact();
    notifyListeners();
  }

  Future addProfilePicture(value) async {
    await ProfileApi.addProfilePicture(value);
    await getProfile();

    notifyListeners();
  }
}
