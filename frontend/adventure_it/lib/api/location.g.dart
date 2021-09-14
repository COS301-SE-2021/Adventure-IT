// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'location.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Location _$LocationFromJson(Map<String, dynamic> json) {
  return Location(
    id: json['id'] as String,
    photoReference: json['photoReference'] as String,
    formattedAddress: json['formattedAddress'] as String,
    placeId: json['placeId'] as String,
    name: json['name'] as String,
  );
}

Map<String, dynamic> _$LocationToJson(Location instance) => <String, dynamic>{
      'id': instance.id,
      'photoReference': instance.photoReference,
      'formattedAddress': instance.formattedAddress,
      'placeId': instance.placeId,
      'name': instance.name,
    };
