// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'createBudget.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CreateBudget _$CreateBudgetFromJson(Map<String, dynamic> json) {
  return CreateBudget(
    name: json['name'] as String,
    description: json['description'] as String,
    creatorID: json['creatorID'] as String,
    adventureID: json['adventureID'] as String,
  );
}

Map<String, dynamic> _$CreateBudgetToJson(CreateBudget instance) =>
    <String, dynamic>{
      'name': instance.name,
      'creatorID': instance.creatorID,
      'adventureID': instance.adventureID,
      'description': instance.description,
    };
