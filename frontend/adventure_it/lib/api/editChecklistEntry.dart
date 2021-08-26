import 'package:json_annotation/json_annotation.dart';

part 'editChecklistEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class EditChecklistEntry {
  final String id;
  final String entryContainerID;
  final String title;

  EditChecklistEntry({
    required this.id,
    required this.title,
    required this.entryContainerID,
  });

  factory EditChecklistEntry.fromJson(Map<String, dynamic> json) =>
      _$EditChecklistEntryFromJson(json);

  Map<String, dynamic> toJson() => _$EditChecklistEntryToJson(this);
}
