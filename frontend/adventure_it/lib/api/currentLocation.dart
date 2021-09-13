import 'package:adventure_it/api/location.dart';
import 'package:json_annotation/json_annotation.dart';

part 'currentLocation.g.dart';

@JsonSerializable(explicitToJson: true)
class CurrentLocation {
  final String id;
  final String userID;
  final String latitude;
  final String longitude;
  final String timestamp;

  CurrentLocation({
    required this.id,
    required this.userID,
    required this.latitude,
    required this.longitude,
    required this.timestamp,
  });

  factory CurrentLocation.fromJson(Map<String, dynamic> json) =>
      _$CurrentLocationFromJson(json);

  Map<String, dynamic> toJson() => _$CurrentLocationToJson(this);
}
