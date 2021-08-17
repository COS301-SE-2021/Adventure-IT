import 'package:adventure_it/api/location.dart';
import 'package:http/http.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

part 'media.g.dart';

@JsonSerializable(explicitToJson: true)
class Media {
  final String id;
  final String type;
  final String name;
  final String adventureID;
  final String owner;
  final bool publicAccess;

  Media(
      {required this.id,
      required this.type,
      required this.name,
      required this.adventureID,
      required this.owner,
      required this.publicAccess});

  factory Media.fromJson(Map<String, dynamic> json) => _$MediaFromJson(json);

  Map<String, dynamic> toJson() => _$MediaToJson(this);
}
