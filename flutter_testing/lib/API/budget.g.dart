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
    transactions: (json['transactions'] as List<dynamic>)
        .map((e) =>
            e == null ? null : Transaction.fromJson(e as Map<String, dynamic>))
        .toList(),
  );
}

Map<String, dynamic> _$BudgetToJson(Budget instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'transactions': instance.transactions.map((e) => e?.toJson()).toList(),
      'deleted': instance.deleted,
    };
