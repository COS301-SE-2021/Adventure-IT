import 'dart:core';

import 'package:json_annotation/json_annotation.dart';


part 'directChat.g.dart';

@JsonSerializable(explicitToJson: true)
class DirectChat {
  final String id;
  final List<String> participants;
  final List<String> messages;

  DirectChat(
      {  required this.id,
      required this.participants,
      required this.messages});

  factory DirectChat.fromJson(Map<String, dynamic> json) =>
      _$DirectChatFromJson(json);

  Map<String, dynamic> toJson() => _$DirectChatToJson(this);
}