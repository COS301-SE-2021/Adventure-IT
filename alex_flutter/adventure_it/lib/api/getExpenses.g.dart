// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'getExpenses.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

GetExpenses _$GetExpensesFromJson(Map<String, dynamic> json) {
  return GetExpenses(
    budgetID: json['budgetID'] as String,
    userName: json['userName'] as String,
  );
}

Map<String, dynamic> _$GetExpensesToJson(GetExpenses instance) =>
    <String, dynamic>{
      'budgetID': instance.budgetID,
      'userName': instance.userName,
    };
