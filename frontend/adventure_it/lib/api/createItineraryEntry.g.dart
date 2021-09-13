// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'createItineraryEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CreateItineraryEntry _$CreateItineraryEntryFromJson(Map<String, dynamic> json) {
  return CreateItineraryEntry(
    entryContainerID: json['entryContainerID'] as String,
    title: json['title'] as String,
    description: json['description'] as String,
    location: json['location'] as String,
    timestamp: json['timestamp'] as String,
    userId: json['userId'] as String,
  );
}

Map<String, dynamic> _$CreateItineraryEntryToJson(
        CreateItineraryEntry instance) =>
    <String, dynamic>{
      'entryContainerID': instance.entryContainerID,
      'title': instance.title,
      'description': instance.description,
      'location': instance.location,
      'timestamp': instance.timestamp,
      'userId': instance.userId,
    };
