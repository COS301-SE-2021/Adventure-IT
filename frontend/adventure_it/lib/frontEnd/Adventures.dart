import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:adventure_it/constants.dart';
import 'package:provider/provider.dart';

import 'package:adventure_it/Providers/adventure_model.dart';
import 'package:adventure_it/api/adventure.dart';

import 'AdventurePage.dart';
import 'CreateAdventure.dart';

//Shows list of adventures
//TODO: upper camel case error
class HomePage_Pages extends StatelessWidget {
  HomePage_Pages();

  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return ChangeNotifierProvider(
        create: (context) => AdventuresModel(),
        child: PageView(
            scrollDirection: Axis.horizontal,
            controller: controller,
            children: <Widget>[
              HomePage_Pages_Adventures(),
            ]));
  }
}

class HomePage_Pages_Adventures extends StatelessWidget {
  HomePage_Pages_Adventures();

  @override
  Widget build(BuildContext context) {
    return Column(children: <Widget>[
      Container(
          alignment: Alignment.center,
          height: MediaQuery.of(context).size.height / 6,
          child: Text("Adventures",
              style: TextStyle(
                  fontSize: 35 * MediaQuery.of(context).textScaleFactor,
                  fontWeight: FontWeight.bold,
                  color: Theme.of(context).textTheme.bodyText1!.color))),
      Container(
        alignment: Alignment.centerLeft,
        padding: EdgeInsets.only(
          left: MediaQuery.of(context).size.width / 50,
        ),
      ),
      AdventureList(),
      SizedBox(height: MediaQuery.of(context).size.height / 60),
      Expanded(
          flex: 1,
          child: Align(
            alignment: FractionalOffset.bottomCenter,
            child: Container(
                decoration: BoxDecoration(
                    color: Theme.of(context).accentColor,
                    shape: BoxShape.circle),
                child: IconButton(
                    onPressed: () {
                      {
                        Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(
                                builder: (context) => CreateAdventureCaller()));
                      }
                    },
                    icon: const Icon(Icons.add),
                    color: Theme.of(context).primaryColorDark)),
          ) //Your widget here,
          ),
      SizedBox(height: MediaQuery.of(context).size.height / 60),
    ]);
  }
}

//dismiss to remove adventure (soft and hard delete)
class AdventureList extends StatelessWidget {
  String getDate(Adventure a) {
    if (DateTime.parse(a.startDate).difference(DateTime.now()).inDays > 0) {
      return "In " +
          DateTime.parse(a.startDate)
              .difference(DateTime.now())
              .inDays
              .toString() +
          " days";
    } else if (DateTime.parse(a.endDate).difference(DateTime.now()).inDays >
            0 &&
        DateTime.parse(a.startDate).difference(DateTime.now()).inDays <= 0) {
      return "Currently On Adventure!";
    } else {
      return "Adventure Completed!";
    }
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<AdventuresModel>(builder: (context, adventureModel, child) {
      if (adventureModel.adventures == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (adventureModel.adventures!.length > 0) {
        return Expanded(
            flex: 2,
            child: ListView(children: [
              ...List.generate(
                  adventureModel.adventures!.length,
                  (index) => Dismissible(
                      background: Container(
                        // color: Theme.of(context).primaryColor,
                        //   margin: const EdgeInsets.all(5),
                        padding: EdgeInsets.all(
                            MediaQuery.of(context).size.height / 60),
                        child: Row(
                          children: [
                            new Spacer(),
                            Icon(Icons.delete,
                                color: Theme.of(context).accentColor,
                                size: 35 *
                                    MediaQuery.of(context).textScaleFactor),
                          ],
                        ),
                      ),
                      direction: DismissDirection.endToStart,
                      key: Key(adventureModel.adventures!
                          .elementAt(index)
                          .adventureId),
                      child: Card(
                          color: Theme.of(context).primaryColorDark,
                          child: InkWell(
                              hoverColor: Theme.of(context).primaryColorLight,
                              onTap: () {
                                Navigator.pushReplacement(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => AdventurePage(
                                            adventureModel.adventures!
                                                .elementAt(index))));
                              },
                              child: Container(
                                decoration: new BoxDecoration(
                                    image: new DecorationImage(
                                        image: NetworkImage(
                                            "https://maps.googleapis.com/maps/api/place/photo?photo_reference=" +
                                                adventureModel.adventures!
                                                    .elementAt(index)
                                                    .location
                                                    .photo_reference +
                                                "&maxwidth=700&key=" +
                                                googleMapsKey),
                                        fit: BoxFit.cover,
                                        colorFilter: ColorFilter.mode(
                                            Theme.of(context)
                                                .backgroundColor
                                                .withOpacity(0.25),
                                            BlendMode.dstATop))),
                                child: Row(
                                  children: <Widget>[
                                    Expanded(
                                      flex: 4,
                                      child: ListTile(
                                        title: Text(
                                            adventureModel.adventures!
                                                .elementAt(index)
                                                .name,
                                            style: TextStyle(
                                                fontSize: 30 *
                                                    MediaQuery.of(context)
                                                        .textScaleFactor,
                                                fontWeight: FontWeight.bold,
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color)),
                                        // subtitle:Text(adventures.elementAt(index).description),
                                        subtitle: Column(
                                            crossAxisAlignment:
                                                CrossAxisAlignment.start,
                                            children: [
                                              Text(
                                                  adventureModel.adventures!
                                                      .elementAt(index)
                                                      .description,
                                                  style: TextStyle(
                                                      fontSize: 15 *
                                                          MediaQuery.of(context)
                                                              .textScaleFactor,
                                                      color: Theme.of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color)),
                                              SizedBox(
                                                  height: MediaQuery.of(context)
                                                          .size
                                                          .height *
                                                      0.005),
                                              RichText(
                                                text: TextSpan(children: [
                                                  WidgetSpan(
                                                      child: Icon(
                                                    Icons.location_on,
                                                    size: 12,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color,
                                                  )),
                                                  TextSpan(
                                                      text: " " +
                                                          adventureModel
                                                              .adventures!
                                                              .elementAt(index)
                                                              .location
                                                              .formattedAddress,
                                                      style: TextStyle(
                                                          fontSize: 12 *
                                                              MediaQuery.of(
                                                                      context)
                                                                  .textScaleFactor,
                                                          color:
                                                              Theme.of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color))
                                                ]),
                                              ),
                                              SizedBox(
                                                  height: MediaQuery.of(context)
                                                          .size
                                                          .height *
                                                      0.005),
                                            ]),
                                      ),
                                    ),
                                    Expanded(
                                      flex: 1,
                                      child: Text(
                                          getDate(adventureModel.adventures!
                                              .elementAt(index)),
                                          textAlign: TextAlign.center,
                                          style: TextStyle(
                                              fontSize: 12 *
                                                  MediaQuery.of(context)
                                                      .textScaleFactor,
                                              color: Theme.of(context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color)),
                                    ),
                                  ],
                                ),
                              ))),
                      confirmDismiss: (DismissDirection direction) async {
                        return await showDialog(
                          context: context,
                          builder: (BuildContext context) {
                            return AlertDialog(
                              backgroundColor:
                                  Theme.of(context).primaryColorDark,
                              title: Text("Confirmation",
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)),
                              content: Text(
                                  "Are you sure you want to remove this adventure?",
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)),
                              actions: <Widget>[
                                TextButton(
                                    onPressed: () =>
                                        Navigator.of(context).pop(true),
                                    child: Text("Remove",
                                        style: TextStyle(
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color))),
                                TextButton(
                                  onPressed: () =>
                                      Navigator.of(context).pop(false),
                                  child: Text("Cancel",
                                      style: TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                ),
                              ],
                            );
                          },
                        );
                      },
                      onDismissed: (direction) {
                        Provider.of<AdventuresModel>(context, listen: false)
                            .deleteAdventure(
                                adventureModel.adventures!.elementAt(index));
                      }))
            ]));
      } else {
        return Center(
            child: Text("It seems you're not very adventurous...",
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}
