// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'document.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Documents _$DocumentsFromJson(Map<String, dynamic> json) {
  return Documents(
    id: json['id'] as String,
    type: json['type'] as String,
    name: json['name'] as String,
    owner: json['owner'] as String,
  );
}

Map<String, dynamic> _$DocumentsToJson(Documents instance) => <String, dynamic>{
      'id': instance.id,
      'type': instance.type,
      'name': instance.name,
      'owner': instance.owner,
    };
