import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';


class AdventuresModel extends ChangeNotifier {
  List<Adventure> _adventures = List.empty();

  AdventuresModel() {
    fetchAllAdventures().then((adventures) => adventures != null? _adventures = adventures:_adventures);
  }

  List<Adventure> get adventures => _adventures.toList();

  Future fetchAllAdventures() async {
    _adventures = await AdventureApi.getAdventuresByUUID("1660bd85-1c13-42c0-955c-63b1eda4e90b");

    notifyListeners();
  }

  // Future addAdventure(Adventure adventure) async {
  //   Adventure newAdventure = await AdventureApi.createAdventure(adventure);
  //   _adventures.add(newAdventure);
  //
  //   notifyListeners();
  // }


  Future deleteAdventure(Adventure adventure) async {
    await AdventureApi.removeAdventure(adventure.adventureId);

    var index = _adventures.indexWhere((element) => element.adventureId == adventure.adventureId);
    _adventures.removeAt(index);

    notifyListeners();
  }

}