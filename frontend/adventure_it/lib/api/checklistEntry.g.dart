// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'checklistEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ChecklistEntry _$ChecklistEntryFromJson(Map<String, dynamic> json) {
  return ChecklistEntry(
    title: json['title'] as String,
    id: json['id'] as String,
    entryContainerID: json['entryContainerID'] as String,
    completed: json['completed'] as bool,
  );
}

Map<String, dynamic> _$ChecklistEntryToJson(ChecklistEntry instance) =>
    <String, dynamic>{
      'id': instance.id,
      'entryContainerID': instance.entryContainerID,
      'title': instance.title,
      'completed': instance.completed,
    };
