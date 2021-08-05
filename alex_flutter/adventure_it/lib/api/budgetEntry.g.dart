// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'budgetEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BudgetEntry _$BudgetEntryFromJson(Map<String, dynamic> json) {
  return BudgetEntry(
    budgetEntryID: json['budgetEntryID'] as String,
    entryContainerID: json['entryContainerID'] as String,
    payers: (json['payers'] as List<dynamic>).map((e) => e as String).toList(),
    amount: (json['amount'] as num).toDouble(),
    title: json['title'] as String,
    description: json['description'] as String,
    category: json['category'] as String,
    payee: json['payee'] as String,
  );
}

Map<String, dynamic> _$BudgetEntryToJson(BudgetEntry instance) =>
    <String, dynamic>{
      'budgetEntryID': instance.budgetEntryID,
      'entryContainerID': instance.entryContainerID,
      'payers': instance.payers,
      'amount': instance.amount,
      'title': instance.title,
      'description': instance.description,
      'category': instance.category,
      'payee': instance.payee,
    };
