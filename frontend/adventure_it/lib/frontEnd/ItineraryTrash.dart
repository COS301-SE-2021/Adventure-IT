import 'package:flutter/material.dart';
import 'package:adventure_it/Providers/itinerary_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:provider/provider.dart';
import 'ItinerariesList.dart';
import 'Navbar.dart';

class ItineraryTrash extends StatelessWidget {
  late final Adventure? adventure;

  ItineraryTrash(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Deleted Itineraries",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            iconTheme: IconThemeData(
                color: Theme.of(context).textTheme.bodyText1!.color),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(
            mainAxisSize:MainAxisSize.max,
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height / 60),
              Expanded(child: DeletedItineraryList(adventure)),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
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
                                        Itineraries(adventure)));
                          },
                          icon: const Icon(Icons.arrow_back_ios_new_rounded),
                          color: Theme.of(context).primaryColorDark)),
                ),
                Expanded(
                  flex: 1,
                  child: Container(),
                ),
                Expanded(
                  flex: 1,
                  child: Container(), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class DeletedItineraryList extends StatelessWidget {
  late final Adventure? a;
  BuildContext? c;

  DeletedItineraryList(Adventure? adventure) {
    this.a = adventure;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => DeletedItineraryModel(a!,context),
        child: Consumer<DeletedItineraryModel>(
            builder: (context, deletedItineraryModel, child) {
          this.c = context;
          if (deletedItineraryModel.deletedItineraries == null ||
              deletedItineraryModel.deletedItineraries!.length !=
                  deletedItineraryModel.creators!.length) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (deletedItineraryModel.deletedItineraries!.length > 0) {
            return Column(children: [
              Expanded(
                  flex: 2,
                  child: Container(
                      width: MediaQuery.of(context).size.width <= 500
                          ? MediaQuery.of(context).size.width
                          : MediaQuery.of(context).size.width * 0.9,
                      padding: EdgeInsets.symmetric(horizontal: MediaQuery.of(context).size.width <= 500
                          ? 0
                          : MediaQuery.of(context).size.width * 0.05),
                      child: ListView(children: [
                    ...List.generate(
                      deletedItineraryModel.deletedItineraries!.length,
                      (index) => Center(
                        child: Container(
                          width: MediaQuery.of(context).size.width * 0.8 < 500? 500: MediaQuery.of(context).size.width * 0.8,
                          child: Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                  hoverColor: Theme.of(context).primaryColorLight,
                                  child: Container(
                                    child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 4,
                                          child: ListTile(
                                            trailing: Text(
                                                "Created by: " +
                                                    deletedItineraryModel.creators!
                                                        .elementAt(index)!
                                                        .username,
                                                style: TextStyle(
                                                    fontSize: 10,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                            title: Text(
                                                deletedItineraryModel
                                                    .deletedItineraries!
                                                    .elementAt(index)
                                                    .title,
                                                style: TextStyle(
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                            subtitle: Text(
                                                deletedItineraryModel
                                                    .deletedItineraries!
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
                                          ),
                                        ),
                                        PopupMenuButton(
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color,
                                            onSelected: (value) {
                                              if (value == 1) {
                                                showDialog(
                                                    context: context,
                                                    builder:
                                                        (BuildContext context) {
                                                      return AlertDialog(
                                                          backgroundColor:
                                                              Theme.of(context)
                                                                  .primaryColorDark,
                                                          title: Text(
                                                            'Confirm Restoration',
                                                            style: TextStyle(
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText1!
                                                                    .color),
                                                          ),
                                                          content: Text(
                                                            'Are you sure you want to restore this itinerary to your adventure?',
                                                            style: TextStyle(
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText1!
                                                                    .color),
                                                          ),
                                                          actions: <Widget>[
                                                            TextButton(
                                                              child: Text('Restore',
                                                                  style: TextStyle(
                                                                      color: Theme.of(
                                                                              context)
                                                                          .textTheme
                                                                          .bodyText1!
                                                                          .color)),
                                                              onPressed: () {
                                                                Provider.of<DeletedItineraryModel>(
                                                                        c!,
                                                                        listen:
                                                                            false)
                                                                    .restoreItinerary(
                                                                        deletedItineraryModel
                                                                            .deletedItineraries!
                                                                            .elementAt(
                                                                                index));
                                                                Navigator.of(
                                                                        context)
                                                                    .pop();
                                                              },
                                                            ),
                                                            TextButton(
                                                              child: Text('Cancel',
                                                                  style: TextStyle(
                                                                      color: Theme.of(
                                                                              context)
                                                                          .textTheme
                                                                          .bodyText1!
                                                                          .color)),
                                                              onPressed: () {
                                                                Navigator.of(
                                                                        context)
                                                                    .pop();
                                                              },
                                                            ),
                                                          ]);
                                                    });
                                              }
                                              if (value == 2) {
                                                showDialog(
                                                    context: context,
                                                    builder:
                                                        (BuildContext context) {
                                                      return AlertDialog(
                                                          backgroundColor:
                                                              Theme.of(context)
                                                                  .primaryColorDark,
                                                          title: Text(
                                                            'Confirm Removal',
                                                            style: TextStyle(
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText1!
                                                                    .color),
                                                          ),
                                                          content: Text(
                                                            'Are you sure you want to remove this itinerary forever?',
                                                            style: TextStyle(
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText1!
                                                                    .color),
                                                          ),
                                                          actions: <Widget>[
                                                            TextButton(
                                                              child: Text('Remove',
                                                                  style: TextStyle(
                                                                      color: Theme.of(
                                                                              context)
                                                                          .textTheme
                                                                          .bodyText1!
                                                                          .color)),
                                                              onPressed: () {
                                                                Provider.of<DeletedItineraryModel>(
                                                                        c!,
                                                                        listen:
                                                                            false)
                                                                    .hardDeleteItinerary(
                                                                        deletedItineraryModel
                                                                            .deletedItineraries!
                                                                            .elementAt(
                                                                                index));
                                                                Navigator.of(
                                                                        context)
                                                                    .pop();
                                                              },
                                                            ),
                                                            TextButton(
                                                              child: Text('Cancel',
                                                                  style: TextStyle(
                                                                      color: Theme.of(
                                                                              context)
                                                                          .textTheme
                                                                          .bodyText1!
                                                                          .color)),
                                                              onPressed: () {
                                                                Navigator.of(
                                                                        context)
                                                                    .pop();
                                                              },
                                                            ),
                                                          ]);
                                                    });
                                              }
                                            },
                                            itemBuilder: (context) => [
                                                  PopupMenuItem(
                                                      value: 1,
                                                      child: Row(
                                                        children: <Widget>[
                                                          Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                    .all(5),
                                                            child: Icon(
                                                                Icons
                                                                    .restore_from_trash_rounded,
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText2!
                                                                    .color),
                                                          ),
                                                          Text("Restore",
                                                              style: TextStyle(
                                                                  color: Theme.of(
                                                                          context)
                                                                      .textTheme
                                                                      .bodyText2!
                                                                      .color))
                                                        ],
                                                      )),
                                                  PopupMenuItem(
                                                      value: 2,
                                                      child: Row(
                                                        children: <Widget>[
                                                          Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                    .all(5),
                                                            child: Icon(
                                                                Icons
                                                                    .delete_forever_rounded,
                                                                color: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyText2!
                                                                    .color),
                                                          ),
                                                          Text("Delete",
                                                              style: TextStyle(
                                                                  color: Theme.of(
                                                                          context)
                                                                      .textTheme
                                                                      .bodyText2!
                                                                      .color))
                                                        ],
                                                      ))
                                                ]),
                                      ],
                                    ),
                                  ))),
                        ),
                      ),
                    )
                  ])))
            ]);
          } else {
            return Center(
                child: Text("It seems you're not one for recycling...",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        }));
  }
}
