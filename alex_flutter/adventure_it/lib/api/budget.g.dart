// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'budget.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Budget _$BudgetFromJson(Map<String, dynamic> json) {
  return Budget(
    deleted: json['deleted'] as bool,
    id: json['id'] as String,
    name: json['name'] as String,
    description: json['description'] as String,
    creatorID: json['creatorID'] as String,
    adventureID: json['adventureID'] as String,
  );
}

Map<String, dynamic> _$BudgetToJson(Budget instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'creatorID': instance.creatorID,
      'adventureID': instance.adventureID,
      'deleted': instance.deleted,
      'description': instance.description,
    };
