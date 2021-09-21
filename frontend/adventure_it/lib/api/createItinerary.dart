import 'package:json_annotation/json_annotation.dart';

part 'createItinerary.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateItinerary {
  final String title;
  final String description;
  final String creatorID;
  final String adventureID;

  CreateItinerary({
    required this.title,
    required this.description,
    required this.creatorID,
    required this.adventureID,
  });

  factory CreateItinerary.fromJson(Map<String, dynamic> json) =>
      _$CreateItineraryFromJson(json);

  Map<String, dynamic> toJson() => _$CreateItineraryToJson(this);
}
