// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'flags.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Flags _$FlagsFromJson(Map<String, dynamic> json) {
  return Flags(
    placesVisited: (json['placesVisited'] as List<dynamic>)
        .map((e) => e as String)
        .toList(),
  );
}

Map<String, dynamic> _$FlagsToJson(Flags instance) => <String, dynamic>{
      'placesVisited': instance.placesVisited,
    };
