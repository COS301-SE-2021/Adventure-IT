import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/report.dart';

part 'payments.g.dart';
//
@JsonSerializable(explicitToJson: true)
class Payment {
  final String payeeName;
  final double amount;

  Payment({
    required this.payeeName,
    required this.amount,

  });

  factory Payment.fromJson(Map<String, dynamic> json) => _$PaymentFromJson(json);

  Map<String, dynamic> toJson() => _$PaymentToJson(this);
}
