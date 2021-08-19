// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'keycloakUser.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

KeycloakUser _$KeycloakUserFromJson(Map<String, dynamic> json) {
  return KeycloakUser(
    id: json['id'] as String,
    username: json['username'] as String,
    enabled: json['enabled'] as bool,
    emailVerified: json['emailVerified'] as bool,
    firstName: json['firstName'] as String,
    lastName: json['lastName'] as String,
    email: json['email'] as String,
  );
}

Map<String, dynamic> _$KeycloakUserToJson(KeycloakUser instance) =>
    <String, dynamic>{
      'id': instance.id,
      'username': instance.username,
      'enabled': instance.enabled,
      'emailVerified': instance.emailVerified,
      'firstName': instance.firstName,
      'lastName': instance.lastName,
      'email': instance.email,
    };
