import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';


class AdventuresModel extends ChangeNotifier {
  List<Adventure>? _adventures = null;

  AdventuresModel() {
    fetchAllAdventures().then((adventures) => adventures != null? _adventures = adventures:List.empty());
  }

  List<Adventure>? get adventures => _adventures?.toList();

  Future fetchAllAdventures() async {
    _adventures = await AdventureApi.getAdventuresByUUID("1660bd85-1c13-42c0-955c-63b1eda4e90b");

    notifyListeners();
  }

   /*Future addAdventure(String a, String b, String c, String d, String e, String f) async {
     Adventure newAdventure = (await AdventureApi.createAdventure(a, b, c, d, e, f)) as Adventure;

     _adventures!.add(newAdventure);

     notifyListeners();
   }*/


  Future deleteAdventure(Adventure adventure) async {
    await AdventureApi.removeAdventure(adventure.adventureId);

    var index = _adventures!.indexWhere((element) => element.adventureId == adventure.adventureId);
    _adventures!.removeAt(index);

    notifyListeners();
  }

}