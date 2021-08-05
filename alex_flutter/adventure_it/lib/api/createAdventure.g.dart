// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'createAdventure.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CreateAdventure _$CreateAdventureFromJson(Map<String, dynamic> json) {
  return CreateAdventure(
    ownerId: json['ownerId'] as String,
    name: json['name'] as String,
    startDate: json['startDate'] as String,
    endDate: json['endDate'] as String,
    description: json['description'] as String,
  );
}

Map<String, dynamic> _$CreateAdventureToJson(CreateAdventure instance) =>
    <String, dynamic>{
      'name': instance.name,
      'ownerId': instance.ownerId,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'description': instance.description,
    };
