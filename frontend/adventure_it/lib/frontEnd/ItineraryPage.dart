import 'dart:async';

import 'package:adventure_it/Providers/itinerary_model.dart';
import 'package:adventure_it/Providers/location_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/createItinerary.dart';
import 'package:adventure_it/api/createItineraryEntry.dart';
import 'package:adventure_it/api/itinerary.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:adventure_it/api/itineraryEntry.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:grouped_list/grouped_list.dart';
import 'package:provider/provider.dart';
import 'package:time_machine/time_machine.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'ItinerariesList.dart';
import 'Navbar.dart';

class ItineraryPage extends StatelessWidget {
  Itinerary? currentItinerary;
  Adventure? currentAdventure;

  ItineraryPage(Itinerary? i, Adventure? a) {
    this.currentItinerary = i;
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => ItineraryEntryModel(currentItinerary!),
        builder: (context, widget) => Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text(currentItinerary!.title,
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
                child: _ListItineraryItems(currentAdventure!, currentItinerary!),
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
                                        Itineraries(currentAdventure)));
                          },
                          icon: const Icon(Icons.arrow_back_ios_new_rounded),
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
                              var provider = Provider.of<ItineraryEntryModel>(context, listen: false);
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertBox(currentItinerary!, provider);
                                  });
                            }
                          },
                          icon: const Icon(Icons.add),
                          color: Theme.of(context).primaryColorDark)),
                ),
                Expanded(
                  flex: 1,
                  child: Container(), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ])));
  }
}

class AlertBox extends StatefulWidget {
  Itinerary? currentItinerary;
  final ItineraryEntryModel itineraryEntryModel;

  AlertBox(this.currentItinerary, this.itineraryEntryModel);

  @override
  _AlertBox createState() => _AlertBox(currentItinerary!);
}

class _AlertBox extends State<AlertBox> {
  Itinerary? currentItinerary;
  DateTime? date = null;
  TimeOfDay? time = null;
  String? location=null;
  final _debouncer = Debouncer(milliseconds: 500);

  Map<int, Color> color = {
    50: Color.fromRGBO(32, 34, 45, .1),
    100: Color.fromRGBO(32, 34, 45, .2),
    200: Color.fromRGBO(32, 34, 45, .3),
    300: Color.fromRGBO(32, 34, 45, .4),
    400: Color.fromRGBO(32, 34, 45, .5),
    500: Color.fromRGBO(32, 34, 45, .6),
    600: Color.fromRGBO(32, 34, 45, .7),
    700: Color.fromRGBO(32, 34, 45, .8),
    800: Color.fromRGBO(32, 34, 45, .9),
    900: Color.fromRGBO(32, 34, 45, 1),
  };

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

  _AlertBox(this.currentItinerary);

  Future<CreateItineraryEntry>? _futureItineraryEntry;
  final titleController = TextEditingController();
  final descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme.of(context).primaryColorDark,
        content: Container(
          height: getSize(context),
          child: Stack(
            overflow: Overflow.visible,
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
                    Text("Add Item To Itinerary",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color,
                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    Spacer(),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                          controller: titleController,
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
                    SizedBox(height: MediaQuery.of(context).size.height * 0.01),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLines: 3,
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
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme.of(context).primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Description')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    MaterialButton(
                        color: Theme.of(context).accentColor,
                        onPressed: () async {
                          DateTime? picked = await showDate();
                          if (picked != null) {
                            setState(() => date = picked);
                          }
                        },
                        child: Text(getTextDate(),
                            style: new TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color,
                                fontSize: 15 *
                                    MediaQuery.of(context).textScaleFactor))),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    MaterialButton(
                        color: Theme.of(context).accentColor,
                        onPressed: () async {
                          TimeOfDay? picked = await showTime();
                          if (picked != null) {
                            setState(() => time = picked);
                          }
                        },
                        child: Text(getTextTime(),
                            style: new TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color,
                                fontSize: 15 *
                                    MediaQuery.of(context).textScaleFactor))),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.02),
                    MaterialButton(
                        color: Theme
                            .of(context)
                            .accentColor,
                        onPressed: () async {
                          showDialog(
                              context: context,
                              builder: (BuildContext context) {
                                return AlertDialog(
                                    backgroundColor: Theme
                                        .of(context)
                                        .primaryColorDark,
                                    title: Stack(
                                        overflow: Overflow.visible,
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
                                                    color: Theme
                                                        .of(context)
                                                        .primaryColorDark),
                                                backgroundColor: Theme
                                                    .of(context)
                                                    .accentColor,
                                              ),
                                            ),
                                          ), Column(
                                              mainAxisSize: MainAxisSize.min,
                                              children: [
                                                Text("Find Location",
                                                    textAlign: TextAlign
                                                        .center,
                                                    style: TextStyle(
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color,
                                                      fontSize: 25 *
                                                          MediaQuery
                                                              .of(
                                                              context)
                                                              .textScaleFactor,
                                                      fontWeight: FontWeight
                                                          .bold,
                                                    )),
                                                SizedBox(height: MediaQuery
                                                    .of(context)
                                                    .size
                                                    .height * 0.01,width:300),TextField(
                                                  style: TextStyle(
                                                      color:
                                                      Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color),
                                                  decoration: InputDecoration(
                                                      hintStyle: TextStyle(
                                                          color: Theme
                                                              .of(
                                                              context)
                                                              .textTheme
                                                              .bodyText2!
                                                              .color),
                                                      filled: true,
                                                      enabledBorder: InputBorder
                                                          .none,
                                                      errorBorder: InputBorder
                                                          .none,
                                                      disabledBorder: InputBorder
                                                          .none,
                                                      fillColor: Theme
                                                          .of(context)
                                                          .primaryColorLight,
                                                      focusedBorder: OutlineInputBorder(
                                                          borderSide: new BorderSide(
                                                              color: Theme
                                                                  .of(
                                                                  context)
                                                                  .accentColor)),
                                                      hintText: 'Search for a place of interest'),
                                                  onChanged: (value) {
                                                    _debouncer.run(() {
                                                      Provider.of<
                                                          LocationModel>(
                                                          context,
                                                          listen: false)
                                                          .fetchAllSuggestions(
                                                          value);
                                                    });},
                                                ),
                                              ])
                                        ]),
                                    content:
                                    Container(
                                        width:300,
                                        child: Consumer<LocationModel>(
                                            builder: (context, locationModel, child) {
                                              return locationModel.suggestions!=null&&locationModel.suggestions!
                                                  .length > 0 ?
                                              ListView.builder(
                                                  shrinkWrap: true,
                                                  itemCount: locationModel.suggestions!.length,
                                                  itemBuilder: (context,
                                                      index) {
                                                    return
                                                      InkWell(
                                                          hoverColor:
                                                          Theme.of(context).primaryColorLight,
                                                          onTap: ()
                                                          {
                                                            setState(() {
                                                              this.location=locationModel.suggestions!
                                                                  .elementAt(index)
                                                                  .description;
                                                            });
                                                            Navigator.of(context).pop();
                                                          },
                                                          child: Padding(padding: EdgeInsets.symmetric(vertical: MediaQuery.of(context).size.height*0.01,horizontal: MediaQuery.of(context).size.width*0.01),child: Expanded(
                                                              child: Text(
                                                                locationModel.suggestions!
                                                                    .elementAt(index)
                                                                    .description,
                                                                style: TextStyle(
                                                                    fontSize: 16 *
                                                                        MediaQuery
                                                                            .of(
                                                                            context)
                                                                            .textScaleFactor,
                                                                    fontWeight: FontWeight
                                                                        .bold,
                                                                    color: Theme
                                                                        .of(
                                                                        context)
                                                                        .textTheme
                                                                        .bodyText1!
                                                                        .color
                                                                ),
                                                              )
                                                          )));
                                                  })
                                                  : Container(height: 10);

                                            })));
                              });
                        },

                        child: Text(
                            getTextLocation(), textAlign: TextAlign.center,style: new TextStyle(color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color, fontSize: 15 * MediaQuery
                            .of(context)
                            .textScaleFactor))
                    ),Spacer(),
                    Padding(
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: RaisedButton(
                        color: Theme.of(context).accentColor,
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () async {
                          await widget.itineraryEntryModel.addItineraryEntry(currentItinerary!, currentItinerary!.id, titleController.text, descriptionController.text, location!, (date!.toString()).substring(0, 10)+"T"+(time.toString()).substring(10,15));
                          Navigator.pop(context);
                        },
                      ),
                    ),
                    Spacer(),
                  ],
                ),
              )
            ],
          ),
        ));
  }

  Future<DateTime?> showDate() {
    return showDatePicker(
      context: context,
      builder: (BuildContext context, Widget? child) {
        return Theme(
          data: ThemeData(
            primarySwatch: Colors.grey,
            splashColor: Color(0xff20222D),
            textTheme: TextTheme(
              subtitle1: TextStyle(color: Color(0xffA7AAB9)),
              button: TextStyle(
                  color: Color(0xffA7AAB9), fontWeight: FontWeight.bold),
            ),
            textButtonTheme: TextButtonThemeData(
                style: TextButton.styleFrom(
              primary: Color(0xffA7AAB9), // button text color
            )),
            accentColor: Color(0xff6A7AC7),
            colorScheme: ColorScheme.light(
                primary: Color(0xff20222D),
                primaryVariant: Color(0xff20222D),
                secondaryVariant: Color(0xff20222D),
                onSecondary: Color(0xff20222D),
                onPrimary: Color(0xffA7AAB9),
                surface: Color(0xff20222D),
                onSurface: Color(0xffA7AAB9),
                secondary: Color(0xff6A7AC7)),
            dialogBackgroundColor: Color(0xff484D64),
          ),
          child: child!,
        );
      },
      initialDate: date ?? DateTime.now(),
      firstDate: new DateTime(DateTime.now().year - 5),
      lastDate: new DateTime(DateTime.now().year + 5),
    );
  }

  Future<TimeOfDay?> showTime() {
    return showTimePicker(
      context: context,
      builder: (BuildContext context, Widget? child) {
        return Theme(
          data: ThemeData(
            primarySwatch: MaterialColor(0xFF20222D, color),
            splashColor: Color(0xff20222D),
            timePickerTheme: TimePickerThemeData(
                helpTextStyle: TextStyle(
              color: Color(0xffA7AAB9),
            )),
            textTheme: TextTheme(
              subtitle1: TextStyle(color: Color(0xffA7AAB9)),
              bodyText2: TextStyle(color: Color(0xffA7AAB9)),
              bodyText1: TextStyle(color: Color(0xffA7AAB9)),
              subtitle2: TextStyle(color: Color(0xffA7AAB9)),
              button: TextStyle(
                  color: Color(0xffA7AAB9), fontWeight: FontWeight.bold),
            ),
            textButtonTheme: TextButtonThemeData(
                style: TextButton.styleFrom(
              primary: Color(0xffA7AAB9), // button text color
            )),
            accentColor: Color(0xff6A7AC7),
            colorScheme: ColorScheme.light(
                primary: Color(0xff6A7AC7),
                primaryVariant: Color(0xff484D64),
                secondaryVariant: Color(0xff484D64),
                onSecondary: Color(0xffA7AAB9),
                onPrimary: Color(0xffA7AAB9),
                surface: Color(0xff20222D),
                onSurface: Color(0xffA7AAB9),
                secondary: Color(0xff6A7AC7)),
            dialogBackgroundColor: Color(0xff484D64),
            backgroundColor: Color(0xff484D64),
          ),
          child: child!,
        );
      },
      initialTime: TimeOfDay.now(),
    );
  }

  String getTextDate() {
    if (date == null) {
      return "Select Date";
    } else {
      String x = date!.day.toString() +
          " " +
          months.elementAt(date!.month - 1) +
          " " +
          date!.year.toString();
      return x;
    }
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.60;
    } else {
      return MediaQuery.of(context).size.height * 0.65;
    }
  }

  String getTextTime() {
    if (time == null) {
      return 'Select Time';
    } else {
      final hours = time!.hour.toString().padLeft(2, '0');
      final minutes = time!.minute.toString().padLeft(2, '0');

      return '$hours:$minutes';
    }
  }

  String getTextLocation() {
    if (location == null) {
      return "Select Location";
    }
    else {
      return location!;
    }
  }
}

class _ListItineraryItems extends StatefulWidget
{
  Adventure? currentAdventure;
  Itinerary? currentItinerary;

  _ListItineraryItems(Adventure a, Itinerary i) {
    this.currentAdventure = a;
    this.currentItinerary = i;
  }

  @override
  ListItineraryItems createState() => ListItineraryItems(currentAdventure!, currentItinerary!);
}

  class ListItineraryItems extends State<_ListItineraryItems> {
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
  Adventure? currentAdventure;
  Itinerary? currentItinerary;
  DateTime? date = null;
  TimeOfDay? time = null;
  String? location=null;
  final _debouncer = Debouncer(milliseconds: 500);

  Map<int, Color> color = {
    50: Color.fromRGBO(32, 34, 45, .1),
    100: Color.fromRGBO(32, 34, 45, .2),
    200: Color.fromRGBO(32, 34, 45, .3),
    300: Color.fromRGBO(32, 34, 45, .4),
    400: Color.fromRGBO(32, 34, 45, .5),
    500: Color.fromRGBO(32, 34, 45, .6),
    600: Color.fromRGBO(32, 34, 45, .7),
    700: Color.fromRGBO(32, 34, 45, .8),
    800: Color.fromRGBO(32, 34, 45, .9),
    900: Color.fromRGBO(32, 34, 45, 1),
  };

  ListItineraryItems(Adventure a, Itinerary i) {
    this.currentAdventure = a;
    this.currentItinerary = i;
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.60;
    } else {
      return MediaQuery.of(context).size.height * 0.65;
    }
  }

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Consumer<ItineraryEntryModel>(
            builder: (context, entryModel, child) {
          if (entryModel.entries == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (entryModel.entries!.length > 0) {
            return Expanded(
                flex: 2,
                child: GroupedListView<dynamic, String>(
                    physics: const AlwaysScrollableScrollPhysics(),
                    elements: entryModel.entries!,
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
                      return Dismissible(
                          background: Container(
                            // color: Theme.of(context).primaryColor,
                            //   margin: const EdgeInsets.all(5),
                            padding: EdgeInsets.all(
                                MediaQuery.of(context).size.height / 60),
                            child: Row(
                              children: [
                                Icon(Icons.edit,
                                    color: Theme.of(context).accentColor,
                                    size: 35 *
                                        MediaQuery.of(context).textScaleFactor),
                                new Spacer(),
                                Icon(Icons.delete,
                                    color: Theme.of(context).accentColor,
                                    size: 35 *
                                        MediaQuery.of(context).textScaleFactor),
                              ],
                            ),
                          ),
                          key: Key(entryModel.entries!.elementAt(index).id),
                          child: Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                hoverColor: Theme.of(context).primaryColorLight,
                                child: Container(
                                  decoration: new BoxDecoration(
                                      image: new DecorationImage(
                                          image: entryModel.entries!.elementAt(index).location.photo_reference==""?NetworkImage("https://maps.googleapis.com/maps/api/place/photo?photo_reference="+currentAdventure!.location.photo_reference+"&maxwidth=700&key="+googleMapsKey):NetworkImage("https://maps.googleapis.com/maps/api/place/photo?photo_reference="+entryModel.entries!.elementAt(index).location.photo_reference+"&maxwidth=500&key="+googleMapsKey),
                                          fit: BoxFit.cover,
                                          colorFilter: ColorFilter.mode(
                                              Theme.of(context)
                                                  .backgroundColor
                                                  .withOpacity(0.25),
                                              BlendMode.dstATop))
                                  ),
                                  child: Row(children: <Widget>[
                                    Expanded(
                                        flex: 4,
                                        child: ListTile(
                                          isThreeLine: true,
                                            title: Text(
                                                entryModel.entries!
                                                    .elementAt(index)
                                                    .title,
                                                style: TextStyle(
                                                    decoration: entryModel.entries!
                                                        .elementAt(index)
                                                        .completed ? TextDecoration.lineThrough : null,
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                            // subtitle:Text(adventures.elementAt(index).description),
                                            subtitle: RichText(
                                              text: TextSpan(children: [
                                                TextSpan(
                                                    text: entryModel.entries!
                                                            .elementAt(index)
                                                            .description +
                                                        "\n",
                                                    style: TextStyle(
                                                       decoration: entryModel.entries!
                                                            .elementAt(index)
                                                            .completed ? TextDecoration.lineThrough : null,
                                                        fontSize: 15 *
                                                            MediaQuery.of(
                                                                    context)
                                                                .textScaleFactor,
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color)),
                                                WidgetSpan(
                                                    child: Icon(
                                                  Icons.location_on,
                                                  size:15,
                                                  color: Theme.of(context)
                                                      .textTheme
                                                      .bodyText1!
                                                      .color,
                                                )),
                                                TextSpan(
                                                    text: " " +
                                                        entryModel.entries!
                                                            .elementAt(index)
                                                            .location.formattedAddress,
                                                    style: TextStyle(
                                                        decoration: entryModel.entries!
                                                            .elementAt(index)
                                                            .completed ? TextDecoration.lineThrough : null,
                                                        fontSize: 15 *
                                                            MediaQuery.of(
                                                                    context)
                                                                .textScaleFactor,
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color))
                                              ]),
                                            ),trailing: Text(
                                        DateTime.parse(entryModel.entries!
                                            .elementAt(index)
                                            .timestamp).hour.toString()+":"+DateTime.parse(entryModel.entries!
                                            .elementAt(index)
                                            .timestamp).minute.toString(),
                                      style: TextStyle(
                                          decoration: entryModel.entries!
                                              .elementAt(index)
                                              .completed ? TextDecoration.lineThrough : null,
                                          fontSize: 25 *
                                              MediaQuery.of(context)
                                                  .textScaleFactor,
                                          fontWeight: FontWeight.bold,
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                        )),
                                  ]),
                                ),
                              )),
                          confirmDismiss: (DismissDirection direction) async {
                            if(direction == DismissDirection.endToStart) {
                            return await showDialog(
                              context: context,
                              builder: (BuildContext context) {
                                return AlertDialog(
                                  backgroundColor:
                                      Theme.of(context).primaryColorDark,
                                  title: Text("Confirm Removal",
                                      style: TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                  content: Text(
                                      "Are you sure you want to remove this itinerary for definite?",
                                      style: TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                  actions: <Widget>[
                                    FlatButton(
                                        onPressed: () =>
                                            Navigator.of(context).pop(true),
                                        child: Text("Remove",
                                            style: TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color))),
                                    FlatButton(
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
                          }
                          else if(direction == DismissDirection.startToEnd) {
                            return showDialog(
                            context: context,
                            builder: (BuildContext context) {
                              return AlertDialog(
                                backgroundColor: Theme.of(context).primaryColorDark,
                                content: Container(
                                  height: getSize(context),
                                  child: Stack(
                                    overflow: Overflow.visible,
                                    children: <Widget>[
                                    Positioned(
                                      right: -40.0,
                                      top: -40.0,
                                      child: InkResponse(
                                        onTap: () {
                                        Navigator.of(context).pop(false);
                                        },
                                        child: CircleAvatar(
                                          child: Icon(Icons.close,
                                          color: Theme.of(context).primaryColorDark),
                                          backgroundColor: Theme.of(context).accentColor,
                                    ),),),
                                    Column(
                                      children: <Widget>[
                                      Text("Edit: " + entryModel.entries!.elementAt(index).title,
                                        textAlign: TextAlign.center,
                                        style: TextStyle(
                                          color: Theme.of(context).textTheme.bodyText1!.color,
                                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                                          fontWeight: FontWeight.bold,
                                          )),
                                    Spacer(),
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.5,
                                      padding: EdgeInsets.symmetric(
                                        horizontal: MediaQuery.of(context).size.width * 0.02),
                                        child: TextField(
                                          style: TextStyle(
                                            color:
                                            Theme.of(context).textTheme.bodyText1!.color),
                                            controller: titleController,
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
                                    SizedBox(height: MediaQuery.of(context).size.height * 0.01),
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.5,
                                      padding: EdgeInsets.symmetric(
                                        horizontal: MediaQuery.of(context).size.width * 0.02),
                                        child: TextField(
                                          maxLength: 255,
                                          maxLines: 3,
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
                                          enabledBorder: InputBorder.none,
                                          errorBorder: InputBorder.none,
                                          disabledBorder: InputBorder.none,
                                          fillColor: Theme.of(context).primaryColorLight,
                                          focusedBorder: OutlineInputBorder(
                                            borderSide: new BorderSide(
                                            color: Theme.of(context).accentColor)),
                                            hintText: 'Description')),
                                    ),
                                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                                    MaterialButton(
                                      color: Theme.of(context).accentColor,
                                      onPressed: () async {
                                        DateTime? picked = await showDate();
                                        if (picked != null) {
                                        setState(() => date = picked);
                                      }},
                                      child: Text(getTextDate(),
                                      style: new TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color,
                                      fontSize: 15 *
                                      MediaQuery.of(context).textScaleFactor))),
                                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                                    MaterialButton(
                                      color: Theme.of(context).accentColor,
                                      onPressed: () async {
                                        TimeOfDay? picked = await showTime();
                                        if (picked != null) {
                                        setState(() => time = picked);
                                      }},
                                      child: Text(getTextTime(),
                                      style: new TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color,
                                      fontSize: 15 *
                                      MediaQuery.of(context).textScaleFactor))),
                                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                                    MaterialButton(
                                      color: Theme
                                          .of(context)
                                          .accentColor,
                                      onPressed: () async {
                                        showDialog(
                                        context: context,
                                        builder: (BuildContext context) {
                                        return AlertDialog(
                                          backgroundColor: Theme
                                              .of(context)
                                              .primaryColorDark,
                                          title: Stack(
                                            overflow: Overflow.visible,
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
                                              color: Theme
                                                  .of(context)
                                                  .primaryColorDark),
                                              backgroundColor: Theme
                                                  .of(context)
                                                  .accentColor,
                                              ),),),
                                          Column(
                                            mainAxisSize: MainAxisSize.min,
                                            children: [
                                              Text("Find Location",
                                              textAlign: TextAlign
                                                  .center,
                                              style: TextStyle(
                                                color: Theme
                                                    .of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color,
                                                fontSize: 25 *
                                                MediaQuery
                                                    .of(
                                                context)
                                                    .textScaleFactor,
                                                fontWeight: FontWeight
                                                    .bold,
                                              )),
                                          SizedBox(height: MediaQuery.of(context).size.height * 0.01,width:300),
                                          TextField(
                                            style: TextStyle(
                                              color:
                                              Theme
                                                  .of(context)
                                                  .textTheme
                                                  .bodyText1!
                                                  .color),
                                              decoration: InputDecoration(
                                                hintStyle: TextStyle(
                                                color: Theme
                                                    .of(
                                                context)
                                                    .textTheme
                                                    .bodyText2!
                                                    .color),
                                              filled: true,
                                              enabledBorder: InputBorder
                                                  .none,
                                              errorBorder: InputBorder
                                                  .none,
                                              disabledBorder: InputBorder
                                                  .none,
                                              fillColor: Theme
                                                  .of(context)
                                                  .primaryColorLight,
                                              focusedBorder: OutlineInputBorder(
                                                borderSide: new BorderSide(
                                                color: Theme
                                                    .of(
                                                context)
                                                    .accentColor)),
                                                hintText: 'Search for a place of interest'),
                                                  onChanged: (value) {
                                                    _debouncer.run(() {
                                                    Provider.of<
                                                    LocationModel>(
                                                    context,
                                                    listen: false)
                                                        .fetchAllSuggestions(
                                                    value);
                                                });},
                                              ),
                                            ])
                                          ]),
                                        content:
                                        Container(
                                          width:300,
                                          child: Consumer<LocationModel>(
                                            builder: (context, locationModel, child) {
                                            return  locationModel.suggestions!.length > 0 ?
                                            ListView.builder(
                                              shrinkWrap: true,
                                              itemCount: locationModel.suggestions!.length,
                                              itemBuilder: (context,
                                              index) {
                                              return InkWell(
                                                hoverColor:
                                                Theme.of(context).primaryColorLight,
                                                onTap: () {
                                                  setState(() {
                                                  this.location=locationModel.suggestions!
                                                      .elementAt(index)
                                                      .description;
                                                  });
                                                  Navigator.of(context).pop();
                                                  },
                                                child: Padding(padding: EdgeInsets.symmetric(vertical: MediaQuery.of(context).size.height*0.01,horizontal: MediaQuery.of(context).size.width*0.01),child: Expanded(
                                                  child: Text(
                                                    locationModel.suggestions!
                                                        .elementAt(index)
                                                        .description,
                                                    style: TextStyle(
                                                      fontSize: 16 *
                                                      MediaQuery
                                                          .of(
                                                      context)
                                                          .textScaleFactor,
                                                      fontWeight: FontWeight
                                                          .bold,
                                                      color: Theme
                                                          .of(
                                                      context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color
                                                    ),
                                                  )
                                            )));})
                                                : Container(height: 10);
                                        })));});}),
                                    Spacer(),
                                    Padding(
                                        padding: EdgeInsets.symmetric(
                                            horizontal: MediaQuery.of(context).size.width * 0.02),
                                        child: RaisedButton(
                                            color: Theme.of(context).accentColor,
                                            onPressed: () {
                                              Navigator.of(context).pop(true);
                                            },
                                            child: Text("Edit",
                                                style: TextStyle(
                                                    color: Theme.of(context).textTheme.bodyText1!.color)))
                                    )
                              ])])));});
                          }},
                          onDismissed: (direction) {
                            if(direction == DismissDirection.endToStart) {
                              Provider.of<ItineraryEntryModel>(context,
                                  listen: false)
                                  .deleteItineraryEntry(
                                  entryModel.entries!.elementAt(index));
                            }

                            else if(direction == DismissDirection.startToEnd) {
                              Provider.of<ItineraryEntryModel>(context, listen: false)
                                  .editItineraryEntry(entryModel.entries!.elementAt(index), currentItinerary!, entryModel.entries!.elementAt(index).id, currentItinerary!.id, titleController.text, descriptionController.text, location!, (date!.toString()).substring(0, 10)+"T"+(time.toString()).substring(10,15));
                            }
                          });
                    }));
          } else {
            return Center(
                child: Text("Seems like you've got nowhere to go!",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        });
  }

  Future<DateTime?> showDate() {
    return showDatePicker(
      context: context,
      builder: (BuildContext context, Widget? child) {
        return Theme(
          data: ThemeData(
            primarySwatch: Colors.grey,
            splashColor: Color(0xff20222D),
            textTheme: TextTheme(
              subtitle1: TextStyle(color: Color(0xffA7AAB9)),
              button: TextStyle(
                  color: Color(0xffA7AAB9), fontWeight: FontWeight.bold),
            ),
            textButtonTheme: TextButtonThemeData(
                style: TextButton.styleFrom(
                  primary: Color(0xffA7AAB9), // button text color
                )),
            accentColor: Color(0xff6A7AC7),
            colorScheme: ColorScheme.light(
                primary: Color(0xff20222D),
                primaryVariant: Color(0xff20222D),
                secondaryVariant: Color(0xff20222D),
                onSecondary: Color(0xff20222D),
                onPrimary: Color(0xffA7AAB9),
                surface: Color(0xff20222D),
                onSurface: Color(0xffA7AAB9),
                secondary: Color(0xff6A7AC7)),
            dialogBackgroundColor: Color(0xff484D64),
          ),
          child: child!,
        );
      },
      initialDate: date ?? DateTime.now(),
      firstDate: new DateTime(DateTime.now().year - 5),
      lastDate: new DateTime(DateTime.now().year + 5),
    );
  }

  Future<TimeOfDay?> showTime() {
    return showTimePicker(
      context: context,
      builder: (BuildContext context, Widget? child) {
        return Theme(
          data: ThemeData(
            primarySwatch: MaterialColor(0xFF20222D, color),
            splashColor: Color(0xff20222D),
            timePickerTheme: TimePickerThemeData(
                helpTextStyle: TextStyle(
                  color: Color(0xffA7AAB9),
                )),
            textTheme: TextTheme(
              subtitle1: TextStyle(color: Color(0xffA7AAB9)),
              bodyText2: TextStyle(color: Color(0xffA7AAB9)),
              bodyText1: TextStyle(color: Color(0xffA7AAB9)),
              subtitle2: TextStyle(color: Color(0xffA7AAB9)),
              button: TextStyle(
                  color: Color(0xffA7AAB9), fontWeight: FontWeight.bold),
            ),
            textButtonTheme: TextButtonThemeData(
                style: TextButton.styleFrom(
                  primary: Color(0xffA7AAB9), // button text color
                )),
            accentColor: Color(0xff6A7AC7),
            colorScheme: ColorScheme.light(
                primary: Color(0xff6A7AC7),
                primaryVariant: Color(0xff484D64),
                secondaryVariant: Color(0xff484D64),
                onSecondary: Color(0xffA7AAB9),
                onPrimary: Color(0xffA7AAB9),
                surface: Color(0xff20222D),
                onSurface: Color(0xffA7AAB9),
                secondary: Color(0xff6A7AC7)),
            dialogBackgroundColor: Color(0xff484D64),
            backgroundColor: Color(0xff484D64),
          ),
          child: child!,
        );
      },
      initialTime: TimeOfDay.now(),
    );
  }

  String getTextDate() {
    if (date == null) {
      return "Select Date";
    } else {
      String x = date!.day.toString() +
          " " +
          months.elementAt(date!.month - 1) +
          " " +
          date!.year.toString();
      return x;
    }
  }

  String getTextTime() {
    if (time == null) {
      return 'Select Time';
    } else {
      final hours = time!.hour.toString().padLeft(2, '0');
      final minutes = time!.minute.toString().padLeft(2, '0');

      return '$hours:$minutes';
    }
  }

  String getTextLocation() {
    if (location == null) {
      return "Select Location";
    }
    else {
      return location!;
    }
  }
}

class Debouncer {
  final int milliseconds;
  VoidCallback? action;
  Timer? _timer;

  Debouncer({required this.milliseconds});

  run(VoidCallback action) {
    if (null != _timer) {
      _timer!.cancel();
    }
    _timer = Timer(Duration(milliseconds: milliseconds), action);
  }
}
