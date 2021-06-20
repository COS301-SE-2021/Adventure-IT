import 'package:json_annotation/json_annotation.dart';

part 'transactions.g.dart';

@JsonSerializable(explicitToJson: true)
class Transaction {
  final String id;
  final double amount;
  final String description;
  final String title;


  Transaction(
      {required this.amount,
        required this.id,
        required this.description,
        required this.title});

  factory Transaction.fromJson(Map<String, dynamic> json) =>
      _$TransactionFromJson(json);

  Map<String, dynamic> toJson() => _$TransactionToJson(this);
}
