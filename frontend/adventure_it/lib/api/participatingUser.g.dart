// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'participatingUser.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ParticipatingUser _$ParticipatingUserFromJson(Map<String, dynamic> json) {
  return ParticipatingUser(
    user: UserProfile.fromJson(json['user'] as Map<String, dynamic>),
    checkIn: json['checkIn'] as bool,
  );
}

Map<String, dynamic> _$ParticipatingUserToJson(ParticipatingUser instance) =>
    <String, dynamic>{
      'user': instance.user.toJson(),
      'checkIn': instance.checkIn,
    };
