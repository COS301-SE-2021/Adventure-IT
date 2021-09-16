import 'package:adventure_it/api/locationAPI.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:flutter/cupertino.dart';

class LocationModel extends ChangeNotifier {
  List<PlaceSearch>? _suggestions = List.empty();
  BuildContext? context;

  LocationModel(this.context);

  List<PlaceSearch>? get suggestions => _suggestions?.toList();

  Future fetchAllSuggestions(String value) async {
    _suggestions = await LocationApi.getSuggestions(value,context);
    notifyListeners();
  }
}
