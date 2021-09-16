import 'package:adventure_it/api/locationAPI.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:flutter/cupertino.dart';

class LocationModel extends ChangeNotifier {
  List<PlaceSearch>? _suggestions = List.empty();
  List<dynamic>? _flags = List.empty();

  LocationModel() {
    getFlags();
  }

  List<PlaceSearch>? get suggestions => _suggestions?.toList();
  List<dynamic>? get flags => _flags?.toList();

  Future fetchAllSuggestions(String value) async {
    _suggestions = await LocationApi.getSuggestions(value);
    notifyListeners();
  }

  Future getFlags() async {
    _flags = await LocationApi.getFlagList();
    notifyListeners();
  }
}
