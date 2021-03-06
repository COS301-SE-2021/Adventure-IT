import 'package:json_annotation/json_annotation.dart';

part 'recommendedLocation.g.dart';

@JsonSerializable(explicitToJson: true)
class RecommendedLocation {
  final String id;
  final String photoReference;
  final String formattedAddress;
  final String placeId;
  final String name;
  bool liked;

  RecommendedLocation({
    required this.id,
    required this.photoReference,
    required this.formattedAddress,
    required this.placeId,
    required this.name,
    required this.liked,
  });

  void setLike()
  {
    if(this.liked!=true)
      {
        this.liked=true;
      }
  }

  factory RecommendedLocation.fromJson(Map<String, dynamic> json) =>
      _$RecommendedLocationFromJson(json);

  Map<String, dynamic> toJson() => _$RecommendedLocationToJson(this);
}
