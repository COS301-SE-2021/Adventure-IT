import 'package:adventure_it/api/location.dart';
import 'package:json_annotation/json_annotation.dart';

part 'itineraryEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class ItineraryEntry {
  final String id;
  final String entryContainerID;
  final String title;
  final String description;
  final bool completed;
  final Location location;
  final String timestamp;

  ItineraryEntry({
    required this.title,
    required this.description,
    required this.id,
    required this.entryContainerID,
    required this.completed,
    required this.location,
    required this.timestamp,
  });

  factory ItineraryEntry.fromJson(Map<String, dynamic> json) =>
      _$ItineraryEntryFromJson(json);

  Map<String, dynamic> toJson() => _$ItineraryEntryToJson(this);
}
