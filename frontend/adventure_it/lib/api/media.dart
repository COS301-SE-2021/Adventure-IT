import 'package:json_annotation/json_annotation.dart';

part 'media.g.dart';

@JsonSerializable(explicitToJson: true)
class Media {
  final String id;
  final String type;
  final String name;
  final String adventureID;
  final String owner;

  Media({
    required this.id,
    required this.type,
    required this.name,
    required this.adventureID,
    required this.owner,
  });

  factory Media.fromJson(Map<String, dynamic> json) => _$MediaFromJson(json);

  Map<String, dynamic> toJson() => _$MediaToJson(this);
}
