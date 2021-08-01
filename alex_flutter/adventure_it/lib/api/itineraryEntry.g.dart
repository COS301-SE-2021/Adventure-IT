// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'itineraryEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

itineraryEntry _$itineraryEntryFromJson(Map<String, dynamic> json) {
  return itineraryEntry(
    title: json['title'] as String,
    description: json['description'] as String,
    id: json['id'] as String,
    entryContainerID: json['entryContainerID'] as String,
    completed: json['completed'] as bool,
    location: json['location'] as String,
    timestamp: json['timestamp'] as String,
  );
}

Map<String, dynamic> _$itineraryEntryToJson(itineraryEntry instance) =>
    <String, dynamic>{
      'id': instance.id,
      'entryContainerID': instance.entryContainerID,
      'title': instance.title,
      'description': instance.description,
      'completed': instance.completed,
      'location': instance.location,
      'timestamp': instance.timestamp,
    };
