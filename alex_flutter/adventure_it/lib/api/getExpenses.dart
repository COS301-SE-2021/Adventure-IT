import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/transactions.dart';

part 'getExpenses.g.dart';

@JsonSerializable(explicitToJson: true)
class GetExpenses {

  final String budgetID;
  final String userName;

  GetExpenses({
    required this.budgetID,
    required this.userName
  });

  factory GetExpenses.FromJson(Map<String, dynamic> json) => _$GetExpensesFromJson(json);

  Map<String, dynamic> toJson() => _$GetExpensesToJson(this);
}
