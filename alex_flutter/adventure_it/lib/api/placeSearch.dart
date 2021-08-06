import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/transactions.dart';

part 'placeSearch.g.dart';

@JsonSerializable(explicitToJson: true)
class PlaceSearch {
  final String description;
  final String placeId;

  PlaceSearch({
    required this.description,
    required this.placeId,
  });

  factory PlaceSearch.fromJson(Map<String, dynamic> json) => _$PlaceSearchFromJson(json);

  Map<String, dynamic> toJson() => _$PlaceSearchToJson(this);
}
