import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/transactions.dart';

part 'budget.g.dart';

@JsonSerializable(explicitToJson: true)
class Budget {
  final String id;
  final String name;
  final List<Transaction?> transactions;
  final bool deleted;

  Budget({
    required this.deleted,
    required this.id,
    required this.name,
    required this.transactions,
  });

  factory Budget.fromJson(Map<String, dynamic> json) => _$BudgetFromJson(json);

  Map<String, dynamic> toJson() => _$BudgetToJson(this);
}
