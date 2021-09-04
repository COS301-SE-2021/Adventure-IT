import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventureAPI.dart';
import 'package:adventure_it/api/currentLocation.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';
import 'package:time_machine/time_machine.dart';
import 'package:adventure_it/api/locationAPI.dart';

class AdventuresModel extends ChangeNotifier {
  List<Adventure>? _adventures;

  AdventuresModel() {
    fetchAllAdventures().then((adventures) =>
        adventures != null ? _adventures = adventures : List.empty());
  }

  List<Adventure>? get adventures => _adventures?.toList();

  Future fetchAllAdventures() async {
    _adventures = await AdventureApi.getAdventuresByUUID(
        UserApi.getInstance().getUserProfile()!.userID);

    notifyListeners();
  }

  Future addAdventure(
      String a, String b, LocalDate c, LocalDate d, String e, String f) async {
    await AdventureApi.createAdventure(a, b, c, d, e, f);

    fetchAllAdventures();

    notifyListeners();
  }

  Future deleteAdventure(Adventure adventure) async {
    await AdventureApi.removeAdventure(adventure.adventureId);

    var index = _adventures!
        .indexWhere((element) => element.adventureId == adventure.adventureId);
    _adventures!.removeAt(index);

    notifyListeners();
  }

  Future editAdventure(
      String a, String b, LocalDate c, LocalDate d, String e) async {
    await AdventureApi.editAdventure(a, b, c, d, e);

    notifyListeners();
  }
}

class AdventureAttendeesModel extends ChangeNotifier {
  List<UserProfile>? _attendees;
  Adventure? currentAdventure;
  List<CurrentLocation>? _locations;

  AdventureAttendeesModel(Adventure a) {
    this.currentAdventure = a;
    fetchAllAttendees().then((attendees) =>
        attendees != null ? _attendees = attendees : List.empty());
  }

  List<UserProfile>? get attendees => _attendees?.toList();
  List<CurrentLocation>? get locations => _locations?.toList();

  Future fetchAllAttendees() async {
    _attendees = await AdventureApi.getAttendeesOfAdventure(
        currentAdventure!.adventureId);

    _locations= await LocationApi.getAllCurrentLocations(this.currentAdventure!);

    notifyListeners();
  }

}
