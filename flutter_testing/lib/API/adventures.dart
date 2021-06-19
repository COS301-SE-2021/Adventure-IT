
import 'package:json_annotation/json_annotation.dart';

part 'adventures.g.dart';

@JsonSerializable(explicitToJson: true)
class Adventure {
  final String id;
  final String name;
  final String description;

  Adventure({
    required this.id,
    this.name = "",
    this.description = ""
  });

  factory Adventure.fromJson(Map<String, dynamic> json) =>
      _$AdventureFromJson(json);

  Map<String, dynamic> toJson() => _$AdventureToJson(this);
}