import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/report.dart';

part 'itinerary.g.dart';

@JsonSerializable(explicitToJson: true)
class Itinerary {
  final String title;
  final String description;
  final String id;
  final String creatorID;
  final String adventureID;
  final bool deleted;

  Itinerary({
    required this.title,
    required this.description,
    required this.id,
    required this.creatorID,
    required this.adventureID,
    required this.deleted
  });

  factory Itinerary.fromJson(Map<String, dynamic> json) => _$ItineraryFromJson(json);

  Map<String, dynamic> toJson() => _$ItineraryToJson(this);
}
