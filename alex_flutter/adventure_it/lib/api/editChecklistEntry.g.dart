// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'editChecklistEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

EditChecklistEntry _$EditChecklistEntryFromJson(Map<String, dynamic> json) {
  return EditChecklistEntry(
    id: json['id'] as String,
    title: json['title'] as String,
    entryContainerID: json['entryContainerID'] as String,
  );
}

Map<String, dynamic> _$EditChecklistEntryToJson(EditChecklistEntry instance) =>
    <String, dynamic>{
      'id': instance.id,
      'entryContainerID': instance.entryContainerID,
      'title': instance.title,
    };
