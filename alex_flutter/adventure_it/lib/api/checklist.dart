import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/transactions.dart';

part 'checklist.g.dart';

@JsonSerializable(explicitToJson: true)
class Checklist {
  final String title;
  final String description;
  final String id;
  final String creatorID;
  final String adventureID;
  final List<String> entries;
  final bool deleted;

  Checklist({
    required this.title;
    required this.description;
    required this.id;
    required this.creatorID;
    required this.adventureID;
    required this.entries;
    required this.deleted;
  });

  factory Checklist.fromJson(Map<String, dynamic> json) => _$ChecklistFromJson(json);

  Map<String, dynamic> toJson() => _$ChecklistToJson(this);
}
