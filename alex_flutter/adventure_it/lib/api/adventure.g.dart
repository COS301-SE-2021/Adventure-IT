// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'adventure.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Adventure _$AdventureFromJson(Map<String, dynamic> json) {
  return Adventure(
    adventureId: json['adventureId'] as String,
    ownerId: json['ownerId'] as String,
    attendees:
        (json['attendees'] as List<dynamic>).map((e) => e as String).toList(),
    containers:
        (json['containers'] as List<dynamic>).map((e) => e as String).toList(),
    name: json['name'] as String,
    startDate: json['startDate'] as String,
    endDate: json['endDate'] as String,
    description: json['description'] as String,
  );
}

Map<String, dynamic> _$AdventureToJson(Adventure instance) => <String, dynamic>{
      'adventureId': instance.adventureId,
      'attendees': instance.attendees,
      'containers': instance.containers,
      'name': instance.name,
      'ownerId': instance.ownerId,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'description': instance.description,
    };
