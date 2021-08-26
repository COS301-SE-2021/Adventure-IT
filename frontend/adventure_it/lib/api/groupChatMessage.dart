import 'dart:core';
import 'package:adventure_it/api/userProfile.dart';
import 'package:json_annotation/json_annotation.dart';

part 'groupChatMessage.g.dart';

@JsonSerializable(explicitToJson: true)
class GroupChatMessage {
  final String id;
  final UserProfile sender;
  final String message;
  final String timestamp;

  GroupChatMessage({
    required this.id,
    required this.sender,
    required this.message,
    required this.timestamp,
  });

  factory GroupChatMessage.fromJson(Map<String, dynamic> json) =>
      _$GroupChatMessageFromJson(json);

  Map<String, dynamic> toJson() => _$GroupChatMessageToJson(this);
}
