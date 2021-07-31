import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/transactions.dart';

part 'budget.g.dart';

@JsonSerializable(explicitToJson: true)
class Budget {
  final String id;
  final String name;
  final String creatorID;
  final String adventureID;
  final double limit;
  final bool deleted;
  final String description;

  Budget({
    required this.deleted,
    required this.id,
    required this.name,
    required this.limit,
    required this.description,
    required this.creatorID,
    required this.adventureID
  });

  factory Budget.fromJson(Map<String, dynamic> json) => _$BudgetFromJson(json);

  Map<String, dynamic> toJson() => _$BudgetToJson(this);
}
