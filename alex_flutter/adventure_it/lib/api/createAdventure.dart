import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

part 'createAdventure.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateAdventure {
  final String name;
  final String ownerId;
  final LocalDate startDate;
  final LocalDate endDate;
  final String description;

  CreateAdventure({
    required this.ownerId,
    required this.name,
    required this.startDate,
    required this.endDate,
    required this.description});

  factory CreateAdventure.fromJson(Map<String, dynamic> json) =>
      _$CreateAdventureFromJson(json);

  Map<String, dynamic> toJson() => _$CreateAdventureToJson(this);
}