// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'location.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Location _$LocationFromJson(Map<String, dynamic> json) {
  return Location(
    id: json['id'] as String,
    photo_reference: json['photo_reference'] as String,
    formattedAddress: json['formattedAddress'] as String,
    place_id: json['place_id'] as String,
  );
}

Map<String, dynamic> _$LocationToJson(Location instance) => <String, dynamic>{
      'id': instance.id,
      'photo_reference': instance.photo_reference,
      'formattedAddress': instance.formattedAddress,
      'place_id': instance.place_id,
    };