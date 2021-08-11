// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'report.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Report _$ReportFromJson(Map<String, dynamic> json) {
  return Report(
    amount: (json['amount'] as num).toDouble(),
    payeeName: json['payeeName'] as String,
  );
}

Map<String, dynamic> _$ReportToJson(Report instance) =>
    <String, dynamic>{
      'payeeName': instance.payeeName,
      'amount': instance.amount,
    };
