// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'recommendedLocation.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

RecommendedLocation _$RecommendedLocationFromJson(Map<String, dynamic> json) {
  return RecommendedLocation(
    id: json['id'] as String,
    photoReference: json['photoReference'] as String,
    formattedAddress: json['formattedAddress'] as String,
    placeId: json['placeId'] as String,
    name: json['name'] as String,
    liked: json['liked'] as bool,
  );
}

Map<String, dynamic> _$RecommendedLocationToJson(RecommendedLocation instance) => <String, dynamic>{
      'id': instance.id,
      'photoReference': instance.photoReference,
      'formattedAddress': instance.formattedAddress,
      'placeId': instance.placeId,
      'name': instance.name,
    };
