import 'package:adventure_it/api/mediaAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';

class UserModel extends ChangeNotifier {
  UserProfile? _profile;
  String? _em = null;
  bool? _t = null;

  final UserApi api = UserApi.getInstance();
  BuildContext? context;

  UserModel(context) {
    getProfile().then((value) {
      if (value != null) {
        _profile = value;
      }
    });
  }

  Future getProfile() async {
    UserApi.getInstance().fetchBackendProfile(UserApi.getInstance().getUserProfile()!.userID);
    _profile = UserApi.getInstance().getUserProfile();
    await getEM();
    await getT();
    return;
  }

  UserProfile? get profile => _profile;
  String? get em => _em;
  bool? get t => _t;

  Future editProfile(String a, String b, String c, String d, String e) async {
    await UserApi.editProfile(a, b, c, d, e,context);
    await getProfile();
    notifyListeners();
  }

  Future getEM() async {
    _em = await UserApi.getEmergencyContact();
    if(_em == null) {
      _em = "";
    }
    notifyListeners();
  }

  Future setEM(String a) async {
    await UserApi.setEmergencyContact(a);
    _em = a;
    notifyListeners();
  }

  Future getT() async {
    /*_t = await UserApi.getTheme();
    if(_t == null) {
      _t = false;
    }
    notifyListeners();*/
  }

  Future setT(bool t) async {
    await UserApi.setTheme(t);
    _t = t;
    notifyListeners();
  }

  Future addProfilePicture(value) async {
    await ProfileApi.addProfilePicture(value,context);
    await getProfile();

    notifyListeners();
  }
}
