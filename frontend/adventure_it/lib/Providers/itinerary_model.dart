import 'package:adventure_it/api/itinerary.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:adventure_it/api/itineraryEntry.dart';
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


    var index = _deletedItineraries!.indexWhere((element) => element.id == it.id);
    _deletedItineraries!.removeAt(index);

    notifyListeners();
  }

  Future fetchAllDeletedItineraries(Adventure a) async {
    _deletedItineraries = await ItineraryApi.getDeletedItinerary(a.adventureId);

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
    print(_itineraries!.length.toString());

    notifyListeners();
  }



  Future softDeleteItinerary(Itinerary c) async {
    await ItineraryApi.softDeleteItinerary(c.id);

    var index = _itineraries!.indexWhere((element) => element.id == c.id);
    _itineraries!.removeAt(index);

    notifyListeners();
  }

  Future addItinerary(Adventure adv, String a, String b, String c, String d) async {
    await ItineraryApi.createItinerary(a, b, c, d);

    await fetchAllItineraries(adv);
  }

}

class ItineraryEntryModel extends ChangeNotifier {
  List<ItineraryEntry>? _entries = null;


  ItineraryEntryModel(Itinerary i) {
    fetchAllEntries(i).then((entries) => entries != null? _entries = entries:List.empty());

  }


  List<ItineraryEntry>? get entries => _entries?.toList();


  Future fetchAllEntries(Itinerary i) async {
    _entries = await ItineraryApi.getItineraryEntries(i);

    notifyListeners();
  }

  Future addItineraryEntry(Itinerary i, String a, String b, String c, String d, String e) async {
    await ItineraryApi.createItineraryEntry(a, b, c, d, e);

    await fetchAllEntries(i);
  }

  Future deleteItineraryEntry(ItineraryEntry c) async {
    await ItineraryApi.deleteItineraryEntry(c);

    var index = _entries!.indexWhere((element) => element.id == c.id);
    _entries!.removeAt(index);

    notifyListeners();
  }

  Future editItineraryEntry(ItineraryEntry entry, Itinerary i, String a, String b, String c, String d, String e, String f) async {
    print("============================correctly reaching model==================================");
    await ItineraryApi.itineraryEdit(a, b, c, d, e, f);
    print("============================correctly reaching function==================================");
    var index = _entries!.indexWhere((element) => element.id == entry.id);
    _entries!.removeAt(index);

    fetchAllEntries(i);
  }



}