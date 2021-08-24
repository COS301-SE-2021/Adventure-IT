import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventureAPI.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:time_machine/time_machine.dart';

class AdventuresModel extends ChangeNotifier {
  List<Adventure>? _adventures = null;

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
}
