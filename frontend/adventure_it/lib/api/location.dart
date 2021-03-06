import 'package:json_annotation/json_annotation.dart';

part 'location.g.dart';

@JsonSerializable(explicitToJson: true)
class Location {
  final String id;
  final String photoReference;
  final String formattedAddress;
  final String placeId;
  final String name;

  Location({
    required this.id,
    required this.photoReference,
    required this.formattedAddress,
    required this.placeId,
    required this.name,
  });

  factory Location.fromJson(Map<String, dynamic> json) =>
      _$LocationFromJson(json);

  Map<String, dynamic> toJson() => _$LocationToJson(this);
}
