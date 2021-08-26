import 'package:json_annotation/json_annotation.dart';

part 'registerUser.g.dart';

@JsonSerializable(explicitToJson: true)
class RegisterUser {
  final String firstName;
  final String lastName;
  final String username;
  final String email;
  final String phoneNumber;
  final String password;

  RegisterUser({
    required this.firstName,
    required this.lastName,
    required this.username,
    required this.email,
    required this.phoneNumber,
    required this.password,
  });

  factory RegisterUser.fromJson(Map<String, dynamic> json) =>
      _$RegisterUserFromJson(json);

  Map<String, dynamic> toJson() => _$RegisterUserToJson(this);
}
