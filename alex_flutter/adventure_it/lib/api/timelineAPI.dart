import 'dart:convert';
import 'package:adventure_it/api/timeline.dart';
import 'package:http/http.dart' as http;
import 'dart:async';

import 'adventure.dart';

class TimelineAPI {
  static Future<List<Timeline>> viewTimeline(Adventure? a) async {
    http.Response response = await http.post(
        Uri.parse('http://localhost:9008/timeline/getTimelineByAdventure/' + a!.adventureId));

    if (response.statusCode != 200) {
      throw Exception('Failed to load the timeline list: ${response.body}');
    }

    List<Timeline> timeline = (jsonDecode(response.body) as List)
        .map((x) => Timeline.fromJson(x))
        .toList();

    return timeline;
  }
}