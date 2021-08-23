import 'package:adventure_it/Providers/timeline_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:grouped_list/grouped_list.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';
class TimePage extends StatelessWidget {
  Adventure? currentAdventure;

  TimePage(Adventure? a) {
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          TimeLine(currentAdventure),
        ]);
  }
}

class TimeLine extends StatelessWidget {
  Adventure? a;

  TimeLine(Adventure? a) {
    this.a = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => TimelineModel(a!),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text("Timeline",
                        style: new TextStyle(
                            color: Theme.of(context).textTheme.bodyText1!.color))),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Container(
                      height: MediaQuery.of(context).size.height * 0.75,
                      child: TimelineList(a!)
                  ),
                  Spacer(),
                  Row(children: [
                    Expanded(
                      flex: 1,
                      child: Container(
                          decoration: BoxDecoration(
                              color: Theme.of(context).accentColor,
                              shape: BoxShape.circle),
                          child: IconButton(
                              onPressed: () {
                                Navigator.pushReplacement(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) =>
                                            AdventurePage(a)));
                              },
                              icon: const Icon(Icons.arrow_back_ios_new_rounded),
                              color: Theme.of(context).primaryColorDark)),
                    ),
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ]
                )
        )
    );
  }
}

class TimelineList extends StatelessWidget {
  Adventure? a;

  TimelineList(Adventure? adv) {
    this.a = adv;
  }

  List<String> months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
  ];

  @override
  Widget build(BuildContext context) {
    return Consumer<TimelineModel>(builder: (context, timelineModel, child) {
      if (timelineModel.timeline == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme
                        .of(context)
                        .accentColor)));
      } else if (timelineModel.timeline!.length > 0) {
        return GroupedListView<dynamic, String>(
            physics: const AlwaysScrollableScrollPhysics(),
            elements: timelineModel.timeline!,
            groupBy: (element) =>
            DateTime.parse(element.timestamp).day.toString() +
                " " +
                months[DateTime.parse(element.timestamp).month - 1] +
                " " +
                DateTime.parse(element.timestamp).year.toString(),
            useStickyGroupSeparators: false,
            groupSeparatorBuilder: (String value) => Container(
                padding: const EdgeInsets.all(8.0),
                child: Text(
                  value,
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color:
                      Theme.of(context).textTheme.bodyText1!.color),
                )),
            indexedItemBuilder: (context, element, index) {
              return Card(
                color: Theme.of(context).primaryColorDark,
                child: InkWell(
                  hoverColor:
                  Theme.of(context).primaryColorLight,
                  onTap: () {
                    },
                  child: Container(
                    child: Row(
                      children: <Widget>[
                        Expanded(
                          flex: 4,
                          child: ListTile(
                            title: Text(
                              timelineModel
                                  .timeline!
                                  .elementAt(index)
                                  .description,
                              style: TextStyle(
                                fontSize: 25 *
                                MediaQuery.of(context)
                                    .textScaleFactor,
                                fontWeight: FontWeight.bold,
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)
                            ),
                            trailing: Text(
                                DateTime.parse(timelineModel.timeline!
                                    .elementAt(index)
                                    .timestamp).hour.toString()+":"+DateTime.parse(timelineModel.timeline!
                                    .elementAt(index)
                                    .timestamp).minute.toString(),
                                style: TextStyle(
                                    fontSize: 25 *
                                        MediaQuery.of(context)
                                            .textScaleFactor,
                                    fontWeight: FontWeight.bold,
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color)
                            ),
                          )
                        )
                    ]
              )
            )
          )
            );});
      } else {
        return Center(
            child: Text("Nothing to see here...yet!",
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}