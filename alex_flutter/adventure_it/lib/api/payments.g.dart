// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'payments.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Payment _$PaymentFromJson(Map<String, dynamic> json) {
  return Payment(
    payeeName: json['payeeName'] as String,
    amount: (json['amount'] as num).toDouble(),
  );
}

Map<String, dynamic> _$PaymentToJson(Payment instance) => <String, dynamic>{
      'payeeName': instance.payeeName,
      'amount': instance.amount,
    };
