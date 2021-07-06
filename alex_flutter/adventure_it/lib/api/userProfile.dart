import 'package:json_annotation/json_annotation.dart';

part 'userProfile.g.dart';
@JsonSerializable(explicitToJson: true)
class UserProfile {
  final String userID;

  UserProfile({
    required this.userID
  });

  factory UserProfile.fromJson(Map<String, dynamic> json) =>
      _$UserProfileFromJson(json);

  Map<String, dynamic> toJson() => _$UserProfileToJson(this);
}
