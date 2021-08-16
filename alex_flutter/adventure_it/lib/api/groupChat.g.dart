// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'groupChat.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GroupChat _$GroupChatFromJson(Map<String, dynamic> json) {
  return GroupChat(
    id: json['id'] as String,
    adventureID: json['adventureID'] as String,
    participants: (json['participants'] as List<dynamic>)
        .map((e) => e as String)
        .toList(),
    messages:
        (json['messages'] as List<dynamic>).map((e) => e as String).toList(),
    name: json['name'] as String,
    colors: (json['colors'] as List<dynamic>)
        .map((e) => ColorPair.fromJson(e as Map<String, dynamic>))
        .toList(),
  );
}

Map<String, dynamic> _$GroupChatToJson(GroupChat instance) => <String, dynamic>{
      'id': instance.id,
      'adventureID': instance.adventureID,
      'participants': instance.participants,
      'messages': instance.messages,
      'name': instance.name,
      'colors': instance.colors.map((e) => e.toJson()).toList(),
    };
