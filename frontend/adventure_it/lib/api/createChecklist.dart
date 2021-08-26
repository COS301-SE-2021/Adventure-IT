import 'package:json_annotation/json_annotation.dart';

part 'createChecklist.g.dart';

@JsonSerializable(explicitToJson: true)
class CreateChecklist {
  final String title;
  final String description;
  final String creatorID;
  final String adventureID;

  CreateChecklist({
    required this.title,
    required this.description,
    required this.creatorID,
    required this.adventureID,
  });

  factory CreateChecklist.fromJson(Map<String, dynamic> json) =>
      _$CreateChecklistFromJson(json);

  Map<String, dynamic> toJson() => _$CreateChecklistToJson(this);
}
