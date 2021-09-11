import 'package:adventure_it/api/itinerary.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:adventure_it/api/itineraryEntry.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';

class DeletedItineraryModel extends ChangeNotifier {
  List<Itinerary>? _deletedItineraries;
  List<UserProfile?>? _creators;

  DeletedItineraryModel(Adventure a) {
    fetchAllDeletedItineraries(a).then((deletedItineraries) =>
        deletedItineraries != null
            ? _deletedItineraries = deletedItineraries
            : List.empty());
  }

  List<Itinerary>? get deletedItineraries => _deletedItineraries?.toList();

  List<UserProfile?>? get creators => _creators?.toList();

  Future restoreItinerary(Itinerary it) async {
    await ItineraryApi.restoreItinerry(it.id);

    var index =
        _deletedItineraries!.indexWhere((element) => element.id == it.id);
    _deletedItineraries!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }

  Future fetchAllDeletedItineraries(Adventure a) async {
    _deletedItineraries = await ItineraryApi.getDeletedItinerary(a.adventureId);

    var total = List<UserProfile?>.filled(deletedItineraries!.length, null,
        growable: true);
    total.removeRange(0, deletedItineraries!.length);
    for (var b in deletedItineraries!) {
      await UserApi.getInstance().findUser(b.creatorID).then((value) {
        total.add(value);
      });
    }

    this._creators = total;

    notifyListeners();
  }

  Future hardDeleteItinerary(Itinerary c) async {
    await ItineraryApi.hardDeleteItinerary(c.id);

    var index =
        _deletedItineraries!.indexWhere((element) => element.id == c.id);
    _deletedItineraries!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }
}

class ItineraryModel extends ChangeNotifier {
  List<Itinerary>? _itineraries;
  List<String>? _startAndEndDates;
  List<String> months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
  ];

  ItineraryModel(Adventure a) {
    fetchAllItineraries(a).then((itineraries) =>
        itineraries != null ? _itineraries = itineraries : List.empty());

  }

  List<Itinerary>? get itineraries => _itineraries?.toList();

  List<String>? get dates => _startAndEndDates?.toList();

  Future fetchAllItineraries(Adventure a) async {
    _itineraries = await ItineraryApi.getItineraries(a);
    await fetchAllDates().then(
            (dates) => dates != null ? _startAndEndDates = dates : List.empty());

    notifyListeners();
  }

  Future fetchAllDates() async {
    var dateList =
        List<String>.filled(itineraries!.length, "0", growable: true);
    dateList.removeRange(0, itineraries!.length);
    for (var i in itineraries!) {
      await ItineraryApi.getStartAndEndDate(i).then((val) {
        if (val == null) {
          dateList.add("No dates yet!");
        }

        else {
          DateTime start = DateTime.parse(val[0]);
          DateTime end = DateTime.parse(val[1]);
          String x = start.day.toString() +
              " " +
              months[start.month - 1] +
              " " +
              start.year.toString() +
              "\n to \n" +
              end.day.toString() +
              " " +
              months[end.month - 1] +
              " " +
              end.year.toString();
          dateList.add(x);
        }
      });
    }

    this._startAndEndDates = dateList;


  }

  Future softDeleteItinerary(Itinerary c) async {
    await ItineraryApi.softDeleteItinerary(c.id);

    var index = _itineraries!.indexWhere((element) => element.id == c.id);
    _itineraries!.removeAt(index);
    _startAndEndDates!.removeAt(index);

    notifyListeners();
  }

  Future addItinerary(
      Adventure adv, String a, String b, String c, String d) async {
    await ItineraryApi.createItinerary(a, b, c, d);

    await fetchAllItineraries(adv);
  }
}

class ItineraryEntryModel extends ChangeNotifier {
  List<ItineraryEntry>? _entries;

  ItineraryEntryModel(Itinerary i) {
    fetchAllEntries(i)
        .then((entries) => entries != null ? _entries = entries : List.empty());
  }

  List<ItineraryEntry>? get entries => _entries?.toList();

  Future fetchAllEntries(Itinerary i) async {
    _entries = await ItineraryApi.getItineraryEntries(i);

    notifyListeners();
  }

  Future addItineraryEntry(
      Itinerary i, String a, String b, String c, String d, String e, String f) async {
    await ItineraryApi.createItineraryEntry(a, b, c, d, e, f);

    await fetchAllEntries(i);
  }

  Future deleteItineraryEntry(ItineraryEntry c) async {
    await ItineraryApi.deleteItineraryEntry(c);

    var index = _entries!.indexWhere((element) => element.id == c.id);
    _entries!.removeAt(index);

    notifyListeners();
  }

  Future editItineraryEntry(ItineraryEntry entry, Itinerary i, String a,
      String b, String c, String d, String e, String f, String g) async {
    await ItineraryApi.itineraryEdit(a, b, c, d, e, f, g);

    var index = _entries!.indexWhere((element) => element.id == entry.id);
    _entries!.removeAt(index);

    fetchAllEntries(i);
  }
}
