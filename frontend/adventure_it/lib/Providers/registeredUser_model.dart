import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:adventure_it/api/itineraryEntry.dart';
import 'package:adventure_it/api/participatingUser.dart';
import 'package:flutter/cupertino.dart';
import 'package:adventure_it/api/userProfile.dart';

class RegisteredUserModel extends ChangeNotifier {
  List<ParticipatingUser>? _users;
  ItineraryEntry? entry;
  BuildContext? context;

  RegisteredUserModel(ItineraryEntry i,context) {
    this.entry = i;
    this.context=context;
    fetchUsers(entry!).then(
        (users) => users != null ? _users = users : List.empty());
  }

  List<ParticipatingUser>? get users => _users?.toList();

  Future fetchUsers(ItineraryEntry i) async {
    _users = await ItineraryApi.getRegisteredUsers(i,context);
    notifyListeners();
  }

}
