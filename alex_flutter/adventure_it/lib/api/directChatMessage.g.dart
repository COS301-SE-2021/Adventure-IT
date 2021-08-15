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
    receiver: UserProfile.fromJson(json['receiver'] as Map<String, dynamic>),
    read: json['read'] as bool,
  );
}

Map<String, dynamic> _$DirectChatMessageToJson(DirectChatMessage instance) =>
    <String, dynamic>{
      'id': instance.id,
      'sender': instance.sender.toJson(),
      'receiver': instance.receiver.toJson(),
      'message': instance.message,
      'read': instance.read,
      'timestamp': instance.timestamp,
    };
