import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

part 'sendGroupMessage.g.dart';

@JsonSerializable(explicitToJson: true)
class SendGroupMessage {
  final String chatID;
  final String sender;
  final String msg;

  SendGroupMessage({
    required this.chatID,
    required this.sender,
    required this.msg,
  });

  factory SendGroupMessage.fromJson(Map<String, dynamic> json) =>
      _$SendGroupMessageFromJson(json);

  Map<String, dynamic> toJson() => _$SendGroupMessageToJson(this);
}
