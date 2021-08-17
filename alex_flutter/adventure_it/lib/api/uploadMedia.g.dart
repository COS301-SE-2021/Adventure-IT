// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'uploadMedia.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UploadMedia _$UploadMediaFromJson(Map<String, dynamic> json) {
  return UploadMedia(
    file: json['file'] as String,
    userId: json['userId'] as String,
    adventureId: json['adventureId'] as String,
  );
}

Map<String, dynamic> _$UploadMediaToJson(UploadMedia instance) =>
    <String, dynamic>{
      'file': instance.file,
      'userId': instance.userId,
      'adventureId': instance.adventureId,
    };
