import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/timeline.dart';
import 'package:adventure_it/api/timelineAPI.dart';
import 'package:flutter/cupertino.dart';

class TimelineModel extends ChangeNotifier {
  List<Timeline>? _timeline;
  Adventure? a;
  BuildContext? context;

  TimelineModel(Adventure a,context) {
    this.a = a;
    this.context=context;
    fetchTimeline(a).then(
        (timeline) => timeline != null ? _timeline = timeline : List.empty());
  }

  List<Timeline>? get timeline => _timeline?.toList();

  Future fetchTimeline(Adventure a) async {
    _timeline = await TimelineAPI.viewTimeline(a,context);

    notifyListeners();
  }
}
