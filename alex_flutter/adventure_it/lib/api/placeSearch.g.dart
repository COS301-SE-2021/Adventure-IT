// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'placeSearch.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PlaceSearch _$PlaceSearchFromJson(Map<String, dynamic> json) {
  return PlaceSearch(
    description: json['description'] as String,
    placeId: json['place_id'] as String,
  );
}

Map<String, dynamic> _$PlaceSearchToJson(PlaceSearch instance) =>
    <String, dynamic>{
      'description': instance.description,
      'place_id': instance.placeId,
    };
