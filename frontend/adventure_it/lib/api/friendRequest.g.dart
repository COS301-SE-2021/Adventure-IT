// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'friendRequest.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

FriendRequest _$FriendRequestFromJson(Map<String, dynamic> json) {
  return FriendRequest(
    id: json['id'] as String,
    firstUser: json['firstUser'] as String,
    secondUser: json['secondUser'] as String,
    createdDate: json['createdDate'] as String,
    accepted: json['accepted'] as bool,
    requester: json['requester'] as String,
  );
}

Map<String, dynamic> _$FriendRequestToJson(FriendRequest instance) =>
    <String, dynamic>{
      'id': instance.id,
      'firstUser': instance.firstUser,
      'secondUser': instance.secondUser,
      'createdDate': instance.createdDate,
      'accepted': instance.accepted,
      'requester': instance.requester,
    };
