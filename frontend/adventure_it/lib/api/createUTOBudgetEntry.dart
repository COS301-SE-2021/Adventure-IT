import 'package:json_annotation/json_annotation.dart';

part 'createUTOBudgetEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateUTOBudgetEntry {
  final String entryContainerID;
  final String payer;
  final String amount;
  final String title;
  final String description;
  final String category;
  final String payee;

  CreateUTOBudgetEntry({
    required this.entryContainerID,
    required this.payer,
    required this.amount,
    required this.title,
    required this.description,
    required this.category,
    required this.payee,
  });

  factory CreateUTOBudgetEntry.fromJson(Map<String, dynamic> json) =>
      _$CreateUTOBudgetEntryFromJson(json);

  Map<String, dynamic> toJson() => _$CreateUTOBudgetEntryToJson(this);
}
