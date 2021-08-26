import 'package:json_annotation/json_annotation.dart';

part 'document.g.dart';

@JsonSerializable(explicitToJson: true)
class Documents {
  final String id;
  final String type;
  final String name;
  final String owner;

  Documents({
    required this.id,
    required this.type,
    required this.name,
    required this.owner,
  });

  factory Documents.fromJson(Map<String, dynamic> json) =>
      _$DocumentsFromJson(json);

  Map<String, dynamic> toJson() => _$DocumentsToJson(this);
}
