import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

part 'createAdventure.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateAdventure {
  final String name;
  final String ownerId;
  final String startDate;
  final String endDate;
  final String description;
  final String location;

  CreateAdventure({
    required this.ownerId,
    required this.name,
    required this.startDate,
    required this.endDate,
    required this.description,
    required this.location,
  });

  factory CreateAdventure.fromJson(Map<String, dynamic> json) =>
      _$CreateAdventureFromJson(json);

  Map<String, dynamic> toJson() => _$CreateAdventureToJson(this);
}
