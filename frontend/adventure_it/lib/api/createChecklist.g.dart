// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'createChecklist.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CreateChecklist _$CreateChecklistFromJson(Map<String, dynamic> json) {
  return CreateChecklist(
    title: json['title'] as String,
    description: json['description'] as String,
    creatorID: json['creatorID'] as String,
    adventureID: json['adventureID'] as String,
  );
}

Map<String, dynamic> _$CreateChecklistToJson(CreateChecklist instance) =>
    <String, dynamic>{
      'title': instance.title,
      'description': instance.description,
      'creatorID': instance.creatorID,
      'adventureID': instance.adventureID,
    };
