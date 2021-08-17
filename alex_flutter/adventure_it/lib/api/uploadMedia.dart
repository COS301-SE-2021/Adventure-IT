import 'package:adventure_it/api/location.dart';
import 'package:http/http.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';

part 'uploadMedia.g.dart';

@JsonSerializable(explicitToJson: true)
class UploadMedia {
  final String file;
  final String userId;
  final String adventureId;

  UploadMedia(
      {required this.file,
      required this.userId,
      required this.adventureId});

  factory UploadMedia.fromJson(Map<String, dynamic> json) =>
      _$UploadMediaFromJson(json);

  Map<String, dynamic> toJson() => _$UploadMediaToJson(this);
}