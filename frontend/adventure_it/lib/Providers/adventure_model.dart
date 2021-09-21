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
  BuildContext? context;

  AdventuresModel(context) {
    this.context=context;
    fetchAllAdventures().then((adventures) =>
        adventures != null ? _adventures = adventures : List.empty());
  }

  List<Adventure>? get adventures => _adventures?.toList();

  Future fetchAllAdventures() async {
    _adventures = await AdventureApi.getAdventuresByUUID(
        UserApi.getInstance().getUserProfile()!.userID,context);

    notifyListeners();
  }

  Future addAdventure(
      String a, String b, LocalDate c, LocalDate d, String e, String f) async {
    await AdventureApi.createAdventure(a, b, c, d, e, f,context);

    fetchAllAdventures();

    notifyListeners();
  }

  Future deleteAdventure(Adventure adventure) async {
    await AdventureApi.removeAdventure(adventure.adventureId,context);

    var index = _adventures!
        .indexWhere((element) => element.adventureId == adventure.adventureId);
    _adventures!.removeAt(index);

    notifyListeners();
  }

  Future editAdventure(
      String a, String b,String c, LocalDate d, LocalDate e, String f) async {
    await AdventureApi.editAdventure(a, b, c, d, e,f,context);

    notifyListeners();
  }
}

class AdventureAttendeesModel extends ChangeNotifier {
  List<UserProfile>? _attendees;
  Adventure? currentAdventure;
  List<CurrentLocation>? _locations;
  BuildContext? context;

  AdventureAttendeesModel(Adventure a,context) {
    this.currentAdventure = a;
    this.context=context;
    fetchAllAttendees().then((attendees) =>
        attendees != null ? _attendees = attendees : List.empty());
  }

  List<UserProfile>? get attendees => _attendees?.toList();
  List<CurrentLocation>? get locations => _locations?.toList();

  Future fetchAllAttendees() async {
    _attendees = await AdventureApi.getAttendeesOfAdventure(
        currentAdventure!.adventureId,context);

    _locations= await LocationApi.getAllCurrentLocations(this.currentAdventure!,context);

    notifyListeners();
  }

}
