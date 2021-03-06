// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'itineraryEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ItineraryEntry _$ItineraryEntryFromJson(Map<String, dynamic> json) {
  return ItineraryEntry(
    title: json['title'] as String,
    description: json['description'] as String,
    id: json['id'] as String,
    entryContainerID: json['entryContainerID'] as String,
    completed: json['completed'] as bool,
    location: Location.fromJson(json['location'] as Map<String, dynamic>),
    timestamp: json['timestamp'] as String,
  );
}

Map<String, dynamic> _$ItineraryEntryToJson(ItineraryEntry instance) =>
    <String, dynamic>{
      'id': instance.id,
      'entryContainerID': instance.entryContainerID,
      'title': instance.title,
      'description': instance.description,
      'completed': instance.completed,
      'location': instance.location.toJson(),
      'timestamp': instance.timestamp,
    };
