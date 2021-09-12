import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventureAPI.dart';
import 'package:adventure_it/api/currentLocation.dart';
import 'package:adventure_it/api/location.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';
import 'package:time_machine/time_machine.dart';
import 'package:adventure_it/api/locationAPI.dart';

class RecommendationModel extends ChangeNotifier {
  List<Location>? _recommendations;
  List<Location>? _popular;
  Adventure? currentAdventure;

  RecommendationModel(Adventure a) {
    this.currentAdventure=a;
    fetchAllRecommendations().then((recs) =>
        recs != null ? _recommendations = recs : List.empty());
    fetchAllPopular().then((recs) =>
    recs != null ? _popular = recs : List.empty());
  }

  List<Location>? get recommendations => _recommendations?.toList();
  List<Location>? get popular => _popular?.toList();

  Future fetchAllRecommendations() async {
    _recommendations = await LocationApi.getRecommendations(
        this.currentAdventure!);

    notifyListeners();
  }

  Future fetchAllPopular() async {
    _recommendations = await LocationApi.getPopular(
        this.currentAdventure!);

    notifyListeners();
  }


}
