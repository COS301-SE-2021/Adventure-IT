// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'budget.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Budget _$BudgetFromJson(Map<String, dynamic> json) {
  return Budget(
    adventureID: json['adventureID'] as String,
    id: json['id'] as String,
    transactions: (json['transactions'] as List<dynamic>)
        .map((e) => e as String)
        .toList(),
    deleted: json['deleted'] as String,
  );
}

Map<String, dynamic> _$BudgetToJson(Budget instance) => <String, dynamic>{
      'id': instance.id,
      'adventureID': instance.adventureID,
      'transactions': instance.transactions,
      'deleted': instance.deleted,
    };
