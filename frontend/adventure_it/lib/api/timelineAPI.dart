import 'dart:convert';
import 'package:adventure_it/api/timeline.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:async';

import '../constants.dart';
import 'adventure.dart';

class TimelineAPI {
  static Future<List<Timeline>> viewTimeline(Adventure? a, context) async {
    http.Response response = await http.get(Uri.parse(
        mainApi + '/timeline/getTimelineByAdventure/' + a!.adventureId));

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to get list of timeline entries for adventure!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to load the timeline list: ${response.body}');
    }

    List<Timeline> timeline = (jsonDecode(response.body) as List)
        .map((x) => Timeline.fromJson(x))
        .toList();

    return timeline;
  }
}
