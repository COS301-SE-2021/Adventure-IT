import 'dart:core';

import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';


part 'timeline.g.dart';

@JsonSerializable(explicitToJson: true)
class Timeline {
  final String timelineID;
  final String adventureID;
  final String userID;
  final String descrpition;
  final String timestamp;
  final String type;

  Timeline(
      {  required this.timelineID,
      required this.adventureID,
      required this.userID,
      required this.descrpition,
      required this.timestamp,
      required this.type});

  factory Timeline.fromJson(Map<String, dynamic> json) =>
      _$TimelineFromJson(json);

  Map<String, dynamic> toJson() => _$TimelineToJson(this);
}