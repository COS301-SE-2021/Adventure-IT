import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';


class LocationModel extends ChangeNotifier {
  List<PlaceSearch>? _suggestions = List.empty();

  LocationModel();

  List<PlaceSearch>? get suggestions => _suggestions?.toList();

  Future fetchAllSuggestions(String value) async {
    _suggestions = await locationApi.getSuggestions(value);

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

    var index = _adventures!.indexWhere((element) => element.adventureId == adventure.adventureId);
    _adventures!.removeAt(index);

    notifyListeners();
  }

}