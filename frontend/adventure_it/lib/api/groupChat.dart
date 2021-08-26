import 'dart:core';
import 'package:json_annotation/json_annotation.dart';
import 'colorPair.dart';

part 'groupChat.g.dart';

@JsonSerializable(explicitToJson: true)
class GroupChat {
  final String id;
  final String adventureID;
  final List<String> participants;
  final List<String> messages;
  final String name;
  final List<ColorPair> colors;

  GroupChat({
    required this.id,
    required this.adventureID,
    required this.participants,
    required this.messages,
    required this.name,
    required this.colors,
  });

  factory GroupChat.fromJson(Map<String, dynamic> json) =>
      _$GroupChatFromJson(json);

  Map<String, dynamic> toJson() => _$GroupChatToJson(this);
}
