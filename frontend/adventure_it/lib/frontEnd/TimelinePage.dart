import 'package:adventure_it/api/timeline.dart';
import 'package:flutter/material.dart';
import 'package:grouped_list/grouped_list.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/Providers/timeline_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'AdventurePage.dart';
import 'Navbar.dart';

class TimePage extends StatelessWidget {
  late final Adventure? currentAdventure;

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
  late final Adventure? a;

  TimeLine(Adventure? a) {
    this.a = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => TimelineModel(a!,context),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text("Timeline",
                        style: new TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color))),
                iconTheme: IconThemeData(color: Theme.of(context).textTheme.bodyText1!.color),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Expanded(
                      child: TimelineList(a!)),
                  SizedBox(height:MediaQuery.of(context).size.height/60),
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
                              icon:
                                  const Icon(Icons.arrow_back_ios_new_rounded),
                              color: Theme.of(context).primaryColorDark)),
                    ),
                    Expanded(
                      flex: 1,
                      child: Container(),
                    ),
                    Expanded(
                      flex: 1,
                      child: Container(),
                    ),
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ])));
  }
}

class TimelineList extends StatelessWidget {
  late final Adventure? a;

  TimelineList(Adventure? adv) {
    this.a = adv;
  }

  String getTime(Timeline i)
  {
    String dateTime="";

   DateTime time=DateTime.parse(i.timestamp);
   time.add(new Duration (hours:2));
    String hour=time.hour.toString();
    if(hour.length<2)
    {
      dateTime=dateTime+"0"+hour+":";
    }
    else
    {
      dateTime=dateTime+hour+":";
    }

    String minute=DateTime.parse(i.timestamp)
        .minute
        .toString();

    if(minute.length<2)
    {
      dateTime=dateTime+"0"+minute;
    }
    else
    {
      dateTime=dateTime+minute;
    }

    return dateTime;

  }

  Widget getIcon(String type, BuildContext context)
  {
    if(type=="ADVENTURE")
      {
          return Icon(
            Icons.person,
            size: 30,
            color: Theme.of(context)
                .accentColor,
          );
      }
    else if(type=="BUDGET")
      {
        return Icon(
          Icons.attach_money,
          size: 30,
          color: Theme.of(context)
              .accentColor,
        );
      }
    else if(type=="CHECKLIST")
      {
        return Icon(
          Icons.checklist,
          size: 30,
          color: Theme.of(context)
              .accentColor,
        );
      }
    else if(type=="ITINERARY")
      {
        return Icon(
          Icons.list_alt,
          size: 30,
          color: Theme.of(context)
              .accentColor,
        );
      }
    else
      return Container();
  }

  final List<String> months = [
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
                    Theme.of(context).accentColor)));
      } else if (timelineModel.timeline!.length > 0) {
        return Container(
            width: MediaQuery.of(context).size.width <= 500
            ? MediaQuery.of(context).size.width
            : MediaQuery.of(context).size.width * 0.9,
      padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width <= 500
      ? 0
          : MediaQuery.of(context).size.width * 0.05),
            child: GroupedListView<dynamic, String>(
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
                      color: Theme.of(context).textTheme.bodyText1!.color),
                )),
            indexedItemBuilder: (context, element, index) {
              return Card(
                  color: Theme.of(context).primaryColorDark,
                  child: InkWell(
                      hoverColor: Theme.of(context).primaryColorLight,
                      onTap: () {},
                      child: Container(
                          child: Row(children: <Widget>[
                        Expanded(
                          flex: 4,
                          child: ListTile(
                            leading: Container(
                                width: 50,
                                height: 50,
                                child:getIcon(timelineModel
                                .timeline!
                                .elementAt(index)
                                .type, context)),
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
                                getTime(timelineModel.timeline!
                                    .elementAt(index)),
                                style: TextStyle(
                                    fontSize: 15 *
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
            );}));
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
