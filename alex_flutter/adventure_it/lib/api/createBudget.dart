import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/report.dart';

part 'createBudget.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateBudget {
  final String name;
  final String creatorID;
  final String adventureID;
  final String description;

  CreateBudget({
    required this.name,
    required this.description,
    required this.creatorID,
    required this.adventureID
  });

  factory CreateBudget.fromJson(Map<String, dynamic> json) => _$CreateBudgetFromJson(json);

  Map<String, dynamic> toJson() => _$CreateBudgetToJson(this);
}
