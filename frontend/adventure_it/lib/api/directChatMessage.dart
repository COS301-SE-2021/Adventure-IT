import 'dart:core';
import 'package:adventure_it/api/userProfile.dart';
import 'package:json_annotation/json_annotation.dart';

part 'directChatMessage.g.dart';

@JsonSerializable(explicitToJson: true)
class DirectChatMessage {
  final String id;
  final UserProfile sender;
  final String message;
  final String timestamp;

  DirectChatMessage({
    required this.id,
    required this.sender,
    required this.message,
    required this.timestamp,
  });

  factory DirectChatMessage.fromJson(Map<String, dynamic> json) =>
      _$DirectChatMessageFromJson(json);

  Map<String, dynamic> toJson() => _$DirectChatMessageToJson(this);
}
