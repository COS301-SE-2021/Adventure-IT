import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

part 'adventure.g.dart';

@JsonSerializable(explicitToJson: true)
class Adventure {
  final String adventureId;
  final List<String> attendees;
  final List<String> containers;
  final String name;
  final String ownerId;
  final String date;
  final String description;

  Adventure(
      {required this.adventureId,
      required this.ownerId,
      required this.attendees,
      required this.containers,
      required this.name,
      required this.date,
      required this.description});

  factory Adventure.fromJson(Map<String, dynamic> json) =>
      _$AdventureFromJson(json);

  Map<String, dynamic> toJson() => _$AdventureToJson(this);
}
