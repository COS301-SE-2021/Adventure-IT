
import 'package:adventure_it/api/location.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

part 'adventure.g.dart';

@JsonSerializable(explicitToJson: true)
class Adventure {
  final String adventureId;
  final List<String> attendees;
  final String name;
  final String ownerId;
  final String startDate;
  final String endDate;
  final String description;
  final Location location;

  Adventure(
      {required this.adventureId,
        required this.ownerId,
        required this.attendees,
        required this.name,
        required this.startDate,
        required this.endDate,
        required this.description,
      required this.location,});

  factory Adventure.fromJson(Map<String, dynamic> json) =>
      _$AdventureFromJson(json);

  Map<String, dynamic> toJson() => _$AdventureToJson(this);
}