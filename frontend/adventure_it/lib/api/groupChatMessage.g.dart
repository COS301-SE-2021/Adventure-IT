// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'groupChatMessage.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GroupChatMessage _$GroupChatMessageFromJson(Map<String, dynamic> json) {
  return GroupChatMessage(
    id: json['id'] as String,
    sender: UserProfile.fromJson(json['sender'] as Map<String, dynamic>),
    message: json['message'] as String,
    timestamp: json['timestamp'] as String,
  );
}

Map<String, dynamic> _$GroupChatMessageToJson(GroupChatMessage instance) =>
    <String, dynamic>{
      'id': instance.id,
      'sender': instance.sender.toJson(),
      'message': instance.message,
      'timestamp': instance.timestamp,
    };
