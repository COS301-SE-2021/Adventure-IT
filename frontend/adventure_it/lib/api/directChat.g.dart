// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'directChat.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DirectChat _$DirectChatFromJson(Map<String, dynamic> json) {
  return DirectChat(
    id: json['id'] as String,
    participants: (json['participants'] as List<dynamic>)
        .map((e) => e as String)
        .toList(),
  );
}

Map<String, dynamic> _$DirectChatToJson(DirectChat instance) =>
    <String, dynamic>{
      'id': instance.id,
      'participants': instance.participants,
    };
