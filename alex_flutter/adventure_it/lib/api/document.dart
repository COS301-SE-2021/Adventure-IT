import 'package:adventure_it/api/location.dart';
import 'package:http/http.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

part 'document.g.dart';

@JsonSerializable(explicitToJson: true)
class Document {
  final String id;
  final String type;
  final String name;
  final String owner;

  Document(
      {required this.id,
      required this.type,
      required this.name,
      required this.owner,});

  factory Document.fromJson(Map<String, dynamic> json) => _$DocumentFromJson(json);

  Map<String, dynamic> toJson() => _$DocumentToJson(this);
}
