// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'itinerary.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Itinerary _$ItineraryFromJson(Map<String, dynamic> json) {
  return Itinerary(
    title: json['title'] as String,
    description: json['description'] as String,
    id: json['id'] as String,
    creatorID: json['creatorID'] as String,
    adventureID: json['adventureID'] as String,
    deleted: json['deleted'] as bool,
  );
}

Map<String, dynamic> _$ItineraryToJson(Itinerary instance) => <String, dynamic>{
      'title': instance.title,
      'description': instance.description,
      'id': instance.id,
      'creatorID': instance.creatorID,
      'adventureID': instance.adventureID,
      'deleted': instance.deleted,
    };
