import 'package:json_annotation/json_annotation.dart';

part 'userProfile.g.dart';
@JsonSerializable(explicitToJson: true)
class UserProfile {
  final String userID;
  final String username;
  final String firstname;
  final String lastname;
  final String email;

  UserProfile({
    required this.userID,
    required this.username,
    required this.firstname,
    required this.lastname,
    required this.email,//
  });

  factory UserProfile.fromJson(Map<String, dynamic> json) =>
      _$UserProfileFromJson(json);

  Map<String, dynamic> toJson() => _$UserProfileToJson(this);
}
