// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'currentLocation.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CurrentLocation _$CurrentLocationFromJson(Map<String, dynamic> json) {
  return CurrentLocation(
    id: json['id'] as String,
   userID: json['userID'] as String,
  latitude: json['latitude'] as String,
  longitude: json['longitude'] as String,
  timestamp: json['timestamp'] as String

  );
}

Map<String, dynamic> _$CurrentLocationToJson(CurrentLocation instance) => <String, dynamic>{
      'id': instance.id,
      'userID': instance.userID,
      'latitude': instance.latitude,
      'longitude': instance.longitude,
      'timestamp': instance.timestamp,
    };
