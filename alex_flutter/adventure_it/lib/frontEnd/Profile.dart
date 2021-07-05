import 'dart:html';

import 'package:adventure_it/api/loginUser.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:flutter/gestures.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

class ProfileCaller extends StatefulWidget {
  @override
  Profile createState() => Profile();
}

class Profile extends State<ProfileCaller> {
  Future<LoginUser>? _futureUser;
  final UserApi api = new UserApi();

  @override
  Widget build(BuildContext context) {
    return Scaffold(

    );
  }
}