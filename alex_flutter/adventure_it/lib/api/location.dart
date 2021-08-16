import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/report.dart';

part 'location.g.dart';

@JsonSerializable(explicitToJson: true)
class Location {

  final String id;
  final String photo_reference;
  final String formattedAddress;
  final String place_id;

  Location({
    required this.id,
    required this.photo_reference,
    required this.formattedAddress,
    required this.place_id
  });

  factory Location.fromJson(Map<String, dynamic> json) => _$LocationFromJson(json);

  Map<String, dynamic> toJson() => _$LocationToJson(this);
}
