import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/report.dart';

part 'budgetEntry.g.dart';
//
@JsonSerializable(explicitToJson: true)
class BudgetEntry {
  final String budgetEntryID;
  final String entryContainerID;
  final String payer;
  final double amount;
  final String title;
  final String description;
  final String category;
  final String payee;

  BudgetEntry({
    required this.budgetEntryID,
    required this.entryContainerID,
    required this.payer,
    required this.amount,
    required this.title,
    required this.description,
    required this.category,
    required this.payee
  });

  factory BudgetEntry.fromJson(Map<String, dynamic> json) => _$BudgetEntryFromJson(json);

  Map<String, dynamic> toJson() => _$BudgetEntryToJson(this);
}
