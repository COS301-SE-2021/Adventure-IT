import 'package:json_annotation/json_annotation.dart';

part 'createItineraryEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateItineraryEntry {
  final String entryContainerID;
  final String title;
  final String description;
  final String location;
  final String timestamp;

  CreateItineraryEntry({
    required this.entryContainerID,
    required this.title,
    required this.description,
    required this.location,
    required this.timestamp,
  });

  factory CreateItineraryEntry.fromJson(Map<String, dynamic> json) =>
      _$CreateItineraryEntryFromJson(json);

  Map<String, dynamic> toJson() => _$CreateItineraryEntryToJson(this);
}
