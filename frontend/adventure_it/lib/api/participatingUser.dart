import 'package:adventure_it/api/userProfile.dart';
import 'package:json_annotation/json_annotation.dart';

part 'participatingUser.g.dart';

@JsonSerializable(explicitToJson: true)
class ParticipatingUser {
  final UserProfile user;
 final bool checkIn;

  ParticipatingUser({
   required this.user,
    required this.checkIn
  });

  factory ParticipatingUser.fromJson(Map<String, dynamic> json) =>
      _$ParticipatingUserFromJson(json);

  Map<String, dynamic> toJson() => _$ParticipatingUserToJson(this);
}
