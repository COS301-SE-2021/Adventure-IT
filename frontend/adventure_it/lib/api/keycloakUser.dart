import 'package:json_annotation/json_annotation.dart';

part 'keycloakUser.g.dart';

@JsonSerializable()
class KeycloakUser {
  final String id;
  final String username;
  final bool enabled;
  final bool emailVerified;
  final String firstName;
  final String lastName;
  final String email;

  KeycloakUser(
      {
      required this.id,
      required this.username,
      required this.enabled,
      required this.emailVerified,
      required this.firstName,
      required this.lastName,
      required this.email});

  factory KeycloakUser.fromJson(Map<String, dynamic> json) =>
      _$KeycloakUserFromJson(json);

  Map<String, dynamic> toJson() => _$KeycloakUserToJson(this);
}
