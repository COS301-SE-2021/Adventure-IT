// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'sendGroupMessage.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SendGroupMessage _$SendGroupMessageFromJson(Map<String, dynamic> json) {
  return SendGroupMessage(
    chatID: json['chatID'] as String,
    sender: json['sender'] as String,
    msg: json['msg'] as String,
  );
}

Map<String, dynamic> _$SendGroupMessageToJson(SendGroupMessage instance) =>
    <String, dynamic>{
      'chatID': instance.chatID,
      'sender': instance.sender,
      'msg': instance.msg,
    };
