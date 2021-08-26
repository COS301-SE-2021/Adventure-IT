import 'dart:core';
import 'package:json_annotation/json_annotation.dart';

part 'sendDirectMessage.g.dart';

@JsonSerializable(explicitToJson: true)
class SendDirectMessage {
  final String chatID;
  final String sender;
  final String receiver;
  final String msg;

  SendDirectMessage({
    required this.chatID,
    required this.sender,
    required this.msg,
    required this.receiver,
  });

  factory SendDirectMessage.fromJson(Map<String, dynamic> json) =>
      _$SendDirectMessageFromJson(json);

  Map<String, dynamic> toJson() => _$SendDirectMessageToJson(this);
}
