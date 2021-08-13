import 'dart:core';

import 'package:adventure_it/api/userProfile.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';


part 'groupChatMessage.g.dart';

@JsonSerializable(explicitToJson: true)
class GroupChatMessage {
  final String id;
  final UserProfile sender;
  final String message;
  final LocalDateTime timestamp;
  final List<UserProfile> receivers;
  final Map<String, bool> read;


  GroupChatMessage(
      {  required this.id,
      required this.sender,
      required this.message,
        required this.timestamp,
      required this.receivers,
      required this.read,
      });

  factory GroupChatMessage.fromJson(Map<String, dynamic> json) =>
      _$GroupChatMessageFromJson(json);

  Map<String, dynamic> toJson() => _$GroupChatMessageToJson(this);
}