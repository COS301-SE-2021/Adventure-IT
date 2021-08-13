import 'dart:core';

import 'package:json_annotation/json_annotation.dart';


part 'colorPair.g.dart';

@JsonSerializable(explicitToJson: true)
class ColorPair {
  final String userID;
  final String adventureId;
  final int color;


  ColorPair(
      {required this.userID,
      required this.adventureId,
      required this.color,
      });

  factory ColorPair.fromJson(Map<String, dynamic> json) =>
      _$ColorPairFromJson(json);

  Map<String, dynamic> toJson() => _$ColorPairToJson(this);
}