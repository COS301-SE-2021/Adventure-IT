import 'package:adventure_it/api/userProfile.dart';
import 'package:json_annotation/json_annotation.dart';

part 'registeredUser.g.dart';

@JsonSerializable(explicitToJson: true)
class RegisteredUser {
  final UserProfile user;
 final bool checkIn;

  RegisteredUser({
   required this.user,
    required this.checkIn
  });

  factory RegisteredUser.fromJson(Map<String, dynamic> json) =>
      _$RegisteredUserFromJson(json);

  Map<String, dynamic> toJson() => _$RegisteredUserToJson(this);
}
