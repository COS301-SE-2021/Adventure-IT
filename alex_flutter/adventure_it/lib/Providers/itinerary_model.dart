import 'package:adventure_it/api/itinerary.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';

class DeletedItineraryModel extends ChangeNotifier{
  List<Itinerary>? _deletedItineraries=null;

  DeletedItineraryModel(Adventure a) {
    fetchAllDeletedItineraries(a).then((deletedItineraries) => deletedItineraries != null? _deletedItineraries = deletedItineraries:List.empty());
  }

  List<Itinerary>? get deletedItineraries => _deletedItineraries?.toList();

  Future restoreItinerary(Itinerary it) async {
    await ItineraryApi.restoreItinerry(it.id);
    print('in here');

    var index = _deletedItineraries!.indexWhere((element) => element.id == it.id);
    _deletedItineraries!.removeAt(index);

    notifyListeners();
  }

  Future fetchAllDeletedItineraries(Adventure a) async {
    _deletedItineraries = await ItineraryApi.getDeletedItinerary(a);

    notifyListeners();
  }

  Future hardDeleteItinerary(Itinerary c) async {
    await ItineraryApi.hardDeleteItinerary(c.id);

    var index = _deletedItineraries!.indexWhere((element) => element.id == c.id);
    _deletedItineraries!.removeAt(index);

    notifyListeners();
  }

}
class ItineraryModel extends ChangeNotifier {
  List<Itinerary>? _itineraries = null;


  ItineraryModel(Adventure a) {
    fetchAllItineraries(a).then((itineraries) => itineraries != null? _itineraries = itineraries:List.empty());

  }


  List<Itinerary>? get itineraries => _itineraries?.toList();


  Future fetchAllItineraries(Adventure a) async {
    _itineraries = await ItineraryApi.getItineraries(a);

    notifyListeners();
  }



  // Future addAdventure(Adventure adventure) async {
  //   Adventure newAdventure = await AdventureApi.createAdventure(adventure);
  //   _adventures.add(newAdventure);
  //
  //   notifyListeners();
  // }


  Future softDeleteItinerary(Itinerary c) async {
    await ItineraryApi.softDeleteItinerary(c.id);

    var index = _itineraries!.indexWhere((element) => element.id == c.id);
    _itineraries!.removeAt(index);

    notifyListeners();
  }



}