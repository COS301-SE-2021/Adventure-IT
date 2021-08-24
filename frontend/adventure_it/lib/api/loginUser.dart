import 'package:json_annotation/json_annotation.dart';

part 'loginUser.g.dart';

@JsonSerializable(explicitToJson: true)
class LoginUser {
  final String username;
  final String password;

  LoginUser({
    required this.username,
    required this.password,
  });

  factory LoginUser.fromJson(Map<String, dynamic> json) =>
      _$LoginUserFromJson(json);

  Map<String, dynamic> toJson() => _$LoginUserToJson(this);
}
