// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'directChatMessage.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DirectChatMessage _$DirectChatMessageFromJson(Map<String, dynamic> json) {
  return DirectChatMessage(
    id: json['id'] as String,
    sender: UserProfile.fromJson(json['sender'] as Map<String, dynamic>),
    message: json['message'] as String,
    timestamp: json['timestamp'] as String,
  );
}

Map<String, dynamic> _$DirectChatMessageToJson(DirectChatMessage instance) =>
    <String, dynamic>{
      'id': instance.id,
      'sender': instance.sender.toJson(),
      'message': instance.message,
      'timestamp': instance.timestamp,
    };
