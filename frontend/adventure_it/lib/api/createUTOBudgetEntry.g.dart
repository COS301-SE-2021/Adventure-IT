// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'createUTOBudgetEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CreateUTOBudgetEntry _$CreateUTOBudgetEntryFromJson(Map<String, dynamic> json) {
  return CreateUTOBudgetEntry(
    entryContainerID: json['entryContainerID'] as String,
    payer: json['payer'] as String,
    amount: json['amount'] as String,
    title: json['title'] as String,
    description: json['description'] as String,
    category: json['category'] as String,
    payee: json['payee'] as String,
  );
}

Map<String, dynamic> _$CreateUTOBudgetEntryToJson(
        CreateUTOBudgetEntry instance) =>
    <String, dynamic>{
      'entryContainerID': instance.entryContainerID,
      'payer': instance.payer,
      'amount': instance.amount,
      'title': instance.title,
      'description': instance.description,
      'category': instance.category,
      'payee': instance.payee,
    };
