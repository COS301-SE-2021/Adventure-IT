// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'createItinerary.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CreateItinerary _$CreateItineraryFromJson(Map<String, dynamic> json) {
  return CreateItinerary(
    title: json['title'] as String,
    description: json['description'] as String,
    creatorID: json['creatorID'] as String,
    adventureID: json['adventureID'] as String,
  );
}

Map<String, dynamic> _$CreateItineraryToJson(CreateItinerary instance) =>
    <String, dynamic>{
      'title': instance.title,
      'description': instance.description,
      'creatorID': instance.creatorID,
      'adventureID': instance.adventureID,
    };
