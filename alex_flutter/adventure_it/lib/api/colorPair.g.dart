// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'colorPair.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ColorPair _$ColorPairFromJson(Map<String, dynamic> json) {
  return ColorPair(
    userID: json['userID'] as String,
    adventureId: json['adventureId'] as String,
    colorPairId: json['colorPairId'] as String,
    color: json['color'] as int,
  );
}

Map<String, dynamic> _$ColorPairToJson(ColorPair instance) => <String, dynamic>{
      'userID': instance.userID,
      'adventureId': instance.adventureId,
      'color': instance.color,
  'colorPairId': instance.colorPairId,
    };
