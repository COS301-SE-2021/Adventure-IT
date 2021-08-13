import 'dart:core';

import 'package:adventure_it/api/userProfile.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';


part 'directChatMessage.g.dart';

@JsonSerializable(explicitToJson: true)
class DirectChatMessage {
  final String id;
  final UserProfile sender;
  final UserProfile receiver;
  final String message;
  final bool read;
  final LocalDateTime timestamp;


  DirectChatMessage(
      {  required this.id,
      required this.sender,
      required this.message,
        required this.timestamp,
      required this.receiver,
      required this.read,
      });

  factory DirectChatMessage.fromJson(Map<String, dynamic> json) =>
      _$DirectChatMessageFromJson(json);

  Map<String, dynamic> toJson() => _$DirectChatMessageToJson(this);
}