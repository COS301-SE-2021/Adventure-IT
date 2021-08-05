import 'package:json_annotation/json_annotation.dart';
import '/api/adventure.dart';
import '/api/transactions.dart';

part 'friendRequest.g.dart';

@JsonSerializable(explicitToJson: true)
class FriendRequest {

  final String id;
  final String firstUser;
  final String secondUser;
  final String createdDate;
  final bool accepted;

  FriendRequest({
    required this.id,
    required this.firstUser,
    required this.secondUser,
    required this.createdDate,
    required this.accepted
  });

  factory FriendRequest.fromJson(Map<String, dynamic> json) => _$FriendRequestFromJson(json);

  Map<String, dynamic> toJson() => _$FriendRequestToJson(this);
}
