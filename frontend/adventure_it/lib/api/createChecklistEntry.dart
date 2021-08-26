import 'package:json_annotation/json_annotation.dart';

part 'createChecklistEntry.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateChecklistEntry {
  final String entryContainerID;
  final String title;

  CreateChecklistEntry({
    required this.title,
    required this.entryContainerID,
  });

  factory CreateChecklistEntry.fromJson(Map<String, dynamic> json) =>
      _$CreateChecklistEntryFromJson(json);

  Map<String, dynamic> toJson() => _$CreateChecklistEntryToJson(this);
}
