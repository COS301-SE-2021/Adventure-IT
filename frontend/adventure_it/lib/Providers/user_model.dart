import 'package:adventure_it/api/mediaAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';

class UserModel extends ChangeNotifier {
  UserProfile? _profile;
  //String? _em = null;
  //bool? _t = null;

  UserApi api = UserApi.getInstance();
  BuildContext? context;

  UserModel(context) {
    getProfile();
  }

  Future getProfile() async {
    UserApi.getInstance().fetchBackendProfile(UserApi.getInstance().getUserProfile()!.userID);
    _profile = UserApi.getInstance().getUserProfile();
    //await getEM();
    //await getT();
    return;
  }

  UserProfile? get profile => _profile;
  UserApi? get userApi => api;
  //String? get em => _em;
  //bool? get t => _t;

  Future editProfile(String a, String b, String c, String d, String e) async {
    await UserApi.editProfile(a, b, c, d, e,context);
    await getProfile();
    notifyListeners();
  }

  Future getEM() async {
    /*await UserApi.getEmergencyContact();
    notifyListeners();*/
  }

  Future setEM(String a) async {
    await UserApi.setEmergencyContact(a);
    await getProfile();
    //_em = a;
    notifyListeners();
  }

  Future getT() async {
    /*UserApi.getInstance().getThemeSettings().then((value){
      _t = value;
      if(_t == null) {
        _t = false;
      }
      });
    return UserApi.getInstance().getThemeSettings();*/
  }

  Future setT(bool t) async {
    await UserApi.getInstance().setTheme(t);
    api = UserApi.getInstance();
    notifyListeners();
  }

  Future addProfilePicture(value) async {
    await ProfileApi.addProfilePicture(value,context);
    await getProfile();

    notifyListeners();
  }
}
