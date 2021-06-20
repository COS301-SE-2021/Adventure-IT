import 'package:json_annotation/json_annotation.dart';

part 'budget.g.dart';

@JsonSerializable(explicitToJson: true)
class Budget {
  final String id;
  String name;
  final List<String> transactions;
  final String deleted;


  Budget(
      {required this.name,
        required this.id,
        required this.transactions,
        required this.deleted});

  factory Budget.fromJson(Map<String, dynamic> json) =>
      _$BudgetFromJson(json);

  Map<String, dynamic> toJson() => _$BudgetToJson(this);
}
