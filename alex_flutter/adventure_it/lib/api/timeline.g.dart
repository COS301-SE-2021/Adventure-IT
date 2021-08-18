// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'timeline.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Timeline _$TimelineFromJson(Map<String, dynamic> json) {
  return Timeline(
    timelineID: json['timelineID'] as String,
    adventureID: json['adventureID'] as String,
    userID: json['userID'] as String,
    descrpition: json['descrpition'] as String,
    timestamp: json['timestamp'] as String,
    type: json['type'] as String,
  );
}

Map<String, dynamic> _$TimelineToJson(Timeline instance) => <String, dynamic>{
      'timelineID': instance.timelineID,
      'adventureID': instance.adventureID,
      'userID': instance.userID,
      'descrpition': instance.descrpition,
      'timestamp': instance.timestamp,
      'type': instance.type,
    };
