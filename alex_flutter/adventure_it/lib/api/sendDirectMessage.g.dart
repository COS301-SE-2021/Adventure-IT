// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'sendDirectMessage.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SendDirectMessage _$SendDirectMessageFromJson(Map<String, dynamic> json) {
  return SendDirectMessage(
    chatID: json['chatID'] as String,
    sender: json['sender'] as String,
    msg: json['msg'] as String,
    receiver: json['receiver'] as String,
  );
}

Map<String, dynamic> _$SendDirectMessageToJson(SendDirectMessage instance) =>
    <String, dynamic>{
      'chatID': instance.chatID,
      'sender': instance.sender,
      'receiver': instance.receiver,
      'msg': instance.msg,
    };
