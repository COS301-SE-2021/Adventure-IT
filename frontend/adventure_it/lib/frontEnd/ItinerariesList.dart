import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:maps_launcher/maps_launcher.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/Providers/itinerary_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:adventure_it/api/itineraryEntry.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'AdventurePage.dart';
import 'ItineraryPage.dart';
import 'ItineraryTrash.dart';
import 'Navbar.dart';

class Itineraries extends StatelessWidget {
  late final Adventure? adventure;

  Itineraries(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => ItineraryModel(adventure!, context),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text("Itineraries",
                        style: new TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color))),
                iconTheme: IconThemeData(
                    color: Theme.of(context).textTheme.bodyText1!.color),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Expanded(child: ItinerariesList(adventure!, context)),
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
                                            AdventurePage(adventure)));
                              },
                              icon:
                                  const Icon(Icons.arrow_back_ios_new_rounded),
                              color: Theme.of(context).primaryColorDark)),
                    ),
                    Expanded(
                      flex: 1,
                      child: Container(
                          decoration: BoxDecoration(
                              color: Theme.of(context).accentColor,
                              shape: BoxShape.circle),
                          child: IconButton(
                              onPressed: () {
                                {
                                  var provider = Provider.of<ItineraryModel>(
                                      context,
                                      listen: false);
                                  showDialog(
                                      context: context,
                                      builder: (BuildContext context) {
                                        return AlertBox(adventure!, provider);
                                      });
                                }
                              },
                              icon: const Icon(Icons.add),
                              color: Theme.of(context).primaryColorDark)),
                    ),
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
                                            ItineraryTrash(adventure)));
                              },
                              icon: const Icon(Icons.delete_outline_rounded),
                              color: Theme.of(context)
                                  .primaryColorDark)), //Your widget here,
                    ),
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ])));
  }
}

class ItinerariesList extends StatefulWidget {
  late final Adventure? currentAdventure;
  late final BuildContext? context;

  ItinerariesList(Adventure a, context) {
    currentAdventure = a;
    this.context = context;
  }

  @override
  _ItinerariesList createState() => _ItinerariesList(currentAdventure, context);
}

class _ItinerariesList extends State<ItinerariesList> {
  Adventure? a;
  ItineraryEntry? next;
  bool check = false;

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

  _ItinerariesList(Adventure? adventure, context) {
    this.a = adventure;
    ItineraryApi.getNextEntry(a!, context).then((value) {
      setState(() {
        check = true;

        next = value;
      });
    });
  }

  String getTime(ItineraryEntry next) {
    String dateTime = DateTime.parse(next.timestamp).day.toString() +
        " " +
        months[DateTime.parse(next.timestamp).month - 1] +
        " " +
        DateTime.parse(next.timestamp).year.toString() +
        " ";

    String hour = DateTime.parse(next.timestamp).hour.toString();

    if (hour.length < 2) {
      dateTime = dateTime + "0" + hour + ":";
    } else {
      dateTime = dateTime + hour + ":";
    }

    String minute = DateTime.parse(next.timestamp).minute.toString();

    if (minute.length < 2) {
      dateTime = dateTime + "0" + minute;
    } else {
      dateTime = dateTime + minute;
    }

    return dateTime;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<ItineraryModel>(builder: (context, itineraryModel, child) {
      if (itineraryModel.itineraries == null ||
          check == false ||
          itineraryModel.dates == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (itineraryModel.itineraries!.length > 0 &&
          itineraryModel.dates!.length > 0 &&
          itineraryModel.itineraries!.length == itineraryModel.dates!.length) {
        return Column(children: [
          Expanded(
              flex: 3,
              child: InkWell(
                  onTap: () {
                    MapsLauncher.launchQuery(next!.location.formattedAddress);
                  },
                  child: Container(
                      width: MediaQuery.of(context).size.width <= 500
                          ? MediaQuery.of(context).size.width
                          : MediaQuery.of(context).size.width * 0.8,
                      decoration: new BoxDecoration(
                          image: new DecorationImage(
                              image: next != null
                                  ? next!.location.photoReference != ""
                                      ? NetworkImage(
                                          "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                              next!.location.photoReference +
                                              "&maxwidth=500&key=" +
                                              googleMapsKey)
                                      : NetworkImage(
                                          "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                              a!.location.photoReference +
                                              "&maxwidth=500&key=" +
                                              googleMapsKey)
                                  : NetworkImage(
                                      "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                          a!.location.photoReference +
                                          "&maxwidth=500&key=" +
                                          googleMapsKey),
                              fit: BoxFit.cover,
                              colorFilter: ColorFilter.mode(
                                  Theme.of(context)
                                      .backgroundColor
                                      .withOpacity(0.25),
                                  BlendMode.dstATop))),
                      child: next != null
                          ? Column(children: [
                              Spacer(),
                              Text("Next Stop!",
                                  style: TextStyle(
                                      fontSize: 20 *
                                          MediaQuery.of(context)
                                              .textScaleFactor,
                                      fontWeight: FontWeight.bold,
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)),
                              Text(next!.title,
                                  style: TextStyle(
                                      fontSize: 35 *
                                          MediaQuery.of(context)
                                              .textScaleFactor,
                                      fontWeight: FontWeight.bold,
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)),
                              Row(children: [
                                Expanded(
                                    child: RichText(
                                  textAlign: TextAlign.center,
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
                                        text: " " + next!.location.name,
                                        style: TextStyle(
                                            fontSize: 12 *
                                                MediaQuery.of(context)
                                                    .textScaleFactor,
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color))
                                  ]),
                                )),
                                Spacer(),
                                Expanded(
                                  child: Text(getTime(next!),
                                      textAlign: TextAlign.center,
                                      style: TextStyle(
                                          fontSize: 12 *
                                              MediaQuery.of(context)
                                                  .textScaleFactor,
                                          fontWeight: FontWeight.bold,
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                ),
                              ]),
                              Spacer(),
                            ])
                          : Center(
                              child: Text(
                                  "There's nothing coming up next. Is this the end?",
                                  textAlign: TextAlign.center,
                                  style: TextStyle(
                                      fontSize: 30 *
                                          MediaQuery.of(context)
                                              .textScaleFactor,
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)))))),
          SizedBox(height: MediaQuery.of(context).size.height / 60),
          Expanded(
              flex: 8,
              child: Container(
                  width: MediaQuery.of(context).size.width <= 500
                      ? MediaQuery.of(context).size.width
                      : MediaQuery.of(context).size.width * 0.9,
                  padding: EdgeInsets.symmetric(
                      horizontal: MediaQuery.of(context).size.width <= 500
                          ? 0
                          : MediaQuery.of(context).size.width * 0.05),
                  child: ListView.builder(
                    itemCount: itineraryModel.itineraries!.length,
                    itemBuilder: (context, index) => Card(
                        color: Theme.of(context).primaryColorDark,
                        child: InkWell(
                            hoverColor: Theme.of(context).primaryColorLight,
                            onTap: () {
                              UserApi.getInstance()
                                  .findUser(
                                      itineraryModel.itineraries!
                                          .elementAt(index)
                                          .creatorID,
                                      context)
                                  .then((c) {
                                Navigator.pushReplacement(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => ItineraryPage(
                                            itineraryModel.itineraries!
                                                .elementAt(index),
                                            a,
                                            c)));
                              });
                            },
                            child: Container(
                              width:
                                  MediaQuery.of(context).size.width * 0.8 < 500
                                      ? 500
                                      : MediaQuery.of(context).size.width * 0.8,
                              child: Row(
                                children: <Widget>[
                                  Expanded(
                                    flex: 4,
                                    child: ListTile(
                                        title:
                                            Text(itineraryModel.itineraries!.elementAt(index).title,
                                                style: TextStyle(
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                        // subtitle:Text(adventures.elementAt(index).description),
                                        subtitle: Text(itineraryModel.itineraries!.elementAt(index).description,
                                            style: TextStyle(
                                                fontSize: 15 *
                                                    MediaQuery.of(context)
                                                        .textScaleFactor,
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color)),
                                        trailing: Text(itineraryModel.dates!.elementAt(index),
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                                fontSize: 11 *
                                                    MediaQuery.of(context).textScaleFactor,
                                                color: Theme.of(context).textTheme.bodyText1!.color))),
                                  ),
                                  PopupMenuButton(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color,
                                      onSelected: (value) {
                                        if (value == 1) {
                                          Provider.of<ItineraryModel>(context,
                                                  listen: false)
                                              .softDeleteItinerary(
                                                  itineraryModel.itineraries!
                                                      .elementAt(index));
                                        }
                                      },
                                      itemBuilder: (context) => [
                                            PopupMenuItem(
                                                value: 1,
                                                child: Row(
                                                  children: <Widget>[
                                                    Padding(
                                                      padding:
                                                          const EdgeInsets.all(
                                                              5),
                                                      child: Icon(Icons.delete,
                                                          color:
                                                              Theme.of(context)
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
                  )))
        ]);
      } else {
        return Center(
            child: Text(
                "Some like to be spontaneous while others prefer to plan!",
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}

class AlertBox extends StatefulWidget {
  late final Adventure? adventure;
  final ItineraryModel itineraryModel;

  AlertBox(this.adventure, this.itineraryModel);

  @override
  _AlertBox createState() => _AlertBox(adventure!);
}

class _AlertBox extends State<AlertBox> {
  Adventure? adventure;

  _AlertBox(this.adventure);

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  //controllers for the form fields
  String userID = UserApi.getInstance().getUserProfile()!.userID;

  final nameController = TextEditingController();
  final descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme.of(context).primaryColorDark,
        content: Container(
          height: getSize(context),
          child: Stack(
            clipBehavior: Clip.none,
            children: <Widget>[
              Positioned(
                right: -40.0,
                top: -40.0,
                child: InkResponse(
                  onTap: () {
                    Navigator.of(context).pop();
                  },
                  child: CircleAvatar(
                    child: Icon(Icons.close,
                        color: Theme.of(context).primaryColorDark),
                    backgroundColor: Theme.of(context).accentColor,
                  ),
                ),
              ),
              Center(
                child: Column(
                  // mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Text("Create Itinerary",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color,
                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.07),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                          controller: nameController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme.of(context).primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Title')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLengthEnforcement: MaxLengthEnforcement.enforced,
                          maxLines: 4,
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                          controller: descriptionController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              fillColor: Theme.of(context).primaryColorLight,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Description')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.05),
                    Padding(
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            primary: Theme.of(context).accentColor),
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () async {
                          await widget.itineraryModel.addItinerary(
                              adventure!,
                              nameController.text,
                              descriptionController.text,
                              userID,
                              adventure!.adventureId);
                          Navigator.pop(context);
                        },
                      ),
                    )
                  ],
                ),
              )
            ],
          ),
        ));
  }
}
