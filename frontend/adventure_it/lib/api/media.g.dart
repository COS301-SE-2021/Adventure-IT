// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'media.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Media _$MediaFromJson(Map<String, dynamic> json) {
  return Media(
    id: json['id'] as String,
    type: json['type'] as String,
    name: json['name'] as String,
    adventureID: json['adventureID'] as String,
    owner: json['owner'] as String,
  );
}

Map<String, dynamic> _$MediaToJson(Media instance) => <String, dynamic>{
      'id': instance.id,
      'type': instance.type,
      'name': instance.name,
      'adventureID': instance.adventureID,
      'owner': instance.owner,
    };
