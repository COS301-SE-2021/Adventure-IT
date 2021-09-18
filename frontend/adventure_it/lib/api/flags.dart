import 'package:json_annotation/json_annotation.dart';

part 'flags.g.dart';

@JsonSerializable(explicitToJson: true)
class Flags {
  final List<String> placesVisited;

  Flags({
    required this.placesVisited
  });

  factory Flags.fromJson(Map<String, dynamic> json) =>
      _$FlagsFromJson(json);

  Map<String, dynamic> toJson() => _$FlagsToJson(this);
}
