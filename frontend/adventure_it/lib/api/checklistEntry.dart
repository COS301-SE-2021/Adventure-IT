import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';

part 'checklistEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class ChecklistEntry {
      final String id;
      final String entryContainerID;
      final String title;
      late final bool completed;

  ChecklistEntry({
    required this.title,
    required this.id,
    required this.entryContainerID,
    required this.completed
  });

  factory ChecklistEntry.fromJson(Map<String, dynamic> json) => _$ChecklistEntryFromJson(json);

  Map<String, dynamic> toJson() => _$ChecklistEntryToJson(this);
}
