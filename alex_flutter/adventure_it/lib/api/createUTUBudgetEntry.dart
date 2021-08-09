import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/transactions.dart';

part 'createUTUBudgetEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateUTUBudgetEntry {
  final String entryContainerID;
  final String payer;
  final String amount;
  final String title;
  final String description;
  final String category;
  final String payee;

  CreateUTUBudgetEntry({
    required this.entryContainerID,
    required this.payer,
    required this.amount,
    required this.title,
    required this.description,
    required this.category,
    required this.payee
  });

  factory CreateUTUBudgetEntry.fromJson(Map<String, dynamic> json) => _$CreateUTUBudgetEntryFromJson(json);

  Map<String, dynamic> toJson() => _$CreateUTUBudgetEntryToJson(this);
}
