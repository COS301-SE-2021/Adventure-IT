// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'createChecklistEntry.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CreateChecklistEntry _$CreateChecklistEntryFromJson(Map<String, dynamic> json) {
  return CreateChecklistEntry(
    title: json['title'] as String,
    entryContainerID: json['entryContainerID'] as String,
  );
}

Map<String, dynamic> _$CreateChecklistEntryToJson(
        CreateChecklistEntry instance) =>
    <String, dynamic>{
      'entryContainerID': instance.entryContainerID,
      'title': instance.title,
    };
