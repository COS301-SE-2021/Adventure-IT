import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';
import '/api/adventure.dart';
import '/api/report.dart';

part 'itineraryEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class ItineraryEntry {

  final String id;
  final String entryContainerID;
  final String title;
  final String description;
  final bool completed;
  final String location;
  final String timestamp;

  ItineraryEntry({
    required this.title,
    required this.description,
    required this.id,
    required this.entryContainerID,
    required this.completed,
    required this.location,
  required this.timestamp
  });

  factory ItineraryEntry.fromJson(Map<String, dynamic> json) => _$ItineraryEntryFromJson(json);

  Map<String, dynamic> toJson() => _$ItineraryEntryToJson(this);
}
