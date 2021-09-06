import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:adventure_it/api/itineraryEntry.dart';
import 'package:flutter/cupertino.dart';
import 'package:adventure_it/api/userProfile.dart';

class RegisteredUserModel extends ChangeNotifier {
  List<UserProfile>? _users;
  ItineraryEntry? entry;

  RegisteredUserModel(ItineraryEntry i) {
    this.entry = i;
    fetchUsers(entry!).then(
        (users) => users != null ? _users = users : List.empty());
  }

  List<UserProfile>? get users => _users?.toList();

  Future fetchUsers(ItineraryEntry i) async {
    _users = await ItineraryApi.getRegisteredUsers(i);

    notifyListeners();
  }

  Future registeredUser() async {
    await ItineraryApi.registerForItinerary(this.entry!);
    fetchUsers(this.entry!);

    notifyListeners();
  }

  Future deregisterUser() async {
    await ItineraryApi.deregisterForItinerary(this.entry!);
    fetchUsers(this.entry!);

    notifyListeners();
  }

}
