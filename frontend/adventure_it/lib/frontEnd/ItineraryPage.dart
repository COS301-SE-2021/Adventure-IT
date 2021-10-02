import 'dart:async';
import 'package:adventure_it/Providers/registeredUser_model.dart';
import 'package:adventure_it/api/itineraryAPI.dart';
import 'package:adventure_it/api/itineraryEntry.dart';
import 'package:adventure_it/api/locationAPI.dart';
import 'package:adventure_it/api/recommendedLocation.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:grouped_list/grouped_list.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/Providers/itinerary_model.dart';
import 'package:adventure_it/Providers/location_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/itinerary.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'ItinerariesList.dart';
import 'package:maps_launcher/maps_launcher.dart';
import 'Navbar.dart';

class ItineraryPage extends StatelessWidget {
  late final Itinerary? currentItinerary;
  late final Adventure? currentAdventure;
  late final UserProfile? creator;
  BuildContext? c;

  ItineraryPage(Itinerary? i, Adventure? a, UserProfile c) {
    this.currentItinerary = i;
    this.currentAdventure = a;
    this.creator = c;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) =>
            ItineraryEntryModel(currentItinerary!, currentAdventure!, context),
        builder: (context, widget) {
          this.c = context;
          return Scaffold(
              drawer: NavDrawer(),
              backgroundColor: Theme
                  .of(context)
                  .scaffoldBackgroundColor,
              appBar: AppBar(
                  iconTheme: IconThemeData(
                      color: Theme
                          .of(context)
                          .textTheme
                          .bodyText1!
                          .color),
                  actions: [
                    Center(
                        child: creator != null
                            ? Text("Created by: " + this.creator!.username,
                            style: new TextStyle(
                                color: Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color,
                                fontSize: 10))
                            : Text("Created by: unknown",
                            style: new TextStyle(
                                color: Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color,
                                fontSize: 10))),
                    SizedBox(width: MediaQuery
                        .of(context)
                        .size
                        .width * 0.02)
                  ],
                  title: Center(
                      child: Text(currentItinerary!.title,
                          style: new TextStyle(
                              color: Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color))),
                  backgroundColor: Theme
                      .of(context)
                      .primaryColorDark),
              body: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height / 60),
                    Expanded(
                      child: _ListItineraryItems(
                          currentAdventure!, currentItinerary!, c!),
                    ),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height / 60),
                    Row(children: [
                      Expanded(
                        flex: 1,
                        child: Container(
                            decoration: BoxDecoration(
                                color: Theme
                                    .of(context)
                                    .accentColor,
                                shape: BoxShape.circle),
                            child: IconButton(
                                onPressed: () {
                                  Navigator.pushReplacement(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              Itineraries(currentAdventure)));
                                },
                                icon: const Icon(
                                    Icons.arrow_back_ios_new_rounded),
                                color: Theme
                                    .of(context)
                                    .primaryColorDark)),
                      ),
                      Expanded(
                        flex: 1,
                        child: Container(
                            decoration: BoxDecoration(
                                color: Theme
                                    .of(context)
                                    .accentColor,
                                shape: BoxShape.circle),
                            child: IconButton(
                                onPressed: () {
                                  {
                                    var provider =
                                    Provider.of<ItineraryEntryModel>(
                                        context,
                                        listen: false);
                                    showDialog(
                                        context: context,
                                        builder: (BuildContext context) {
                                          return AlertBox(
                                              currentItinerary!, provider);
                                        });
                                  }
                                },
                                icon: const Icon(Icons.add),
                                color: Theme
                                    .of(context)
                                    .primaryColorDark)),
                      ),
                      Expanded(
                        flex: 1,
                        child: Container(), //Your widget here,
                      ),
                    ]),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height / 60),
                  ]));
        });
  }
}

class AlertBox extends StatefulWidget {
  late final Itinerary? currentItinerary;
  final ItineraryEntryModel itineraryEntryModel;
  late final RecommendedLocation? recommendedLocation;

  AlertBox(this.currentItinerary, this.itineraryEntryModel);

  @override
  _AlertBox createState() => _AlertBox(currentItinerary!);
}

class _AlertBox extends State<AlertBox> {
  Itinerary? currentItinerary;
  DateTime? date;
  TimeOfDay? time;
  String? location;
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

  String userID = UserApi.getInstance().getUserProfile()!.userID;

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final locationController = TextEditingController();
  final dateController = TextEditingController();
  final timeController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme
            .of(context)
            .primaryColorDark,
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
                        color: Theme
                            .of(context)
                            .primaryColorDark),
                    backgroundColor: Theme
                        .of(context)
                        .accentColor,
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
                          color: Theme
                              .of(context)
                              .textTheme
                              .bodyText1!
                              .color,
                          fontSize: 25 * MediaQuery
                              .of(context)
                              .textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    Spacer(),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: titleController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Title')),
                    ),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLines: 3,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: descriptionController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Description')),
                    ),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.02),
                    Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                            MediaQuery
                                .of(context)
                                .size
                                .width * 0.02),
                        child: TextField(
                          maxLines: 1,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: dateController,
                          decoration: InputDecoration(
                              prefixIcon: Icon(Icons.calendar_today_rounded),
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Pick a date'),
                          onTap: () async {
                            DateTime? picked = await showDate();
                            if (picked != null) {
                              setState(() {
                                date = picked;
                                dateController.text = getTextDate(picked);
                              });
                            }
                          },
                        )),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.02),
                    Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                            MediaQuery
                                .of(context)
                                .size
                                .width * 0.02),
                        child: TextField(
                          maxLines: 1,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: timeController,
                          decoration: InputDecoration(
                              prefixIcon: Icon(Icons.access_time_rounded),
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Pick a time'),
                          onTap: () async {
                            TimeOfDay? picked = await showTime();
                            if (picked != null) {
                              setState(() {
                                time = picked;
                                timeController.text = getTextTime(picked);
                              });
                            }
                          },
                        )),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.02),
                    Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                            MediaQuery
                                .of(context)
                                .size
                                .width * 0.02),
                        child: TextField(
                          maxLines: 1,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: locationController,
                          decoration: InputDecoration(
                              prefixIcon: Icon(Icons.location_on_rounded),
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Find a location'),
                          onTap: () async {
                            showDialog(
                                context: context,
                                builder: (BuildContext context) {
                                  return AlertDialog(
                                      backgroundColor:
                                      Theme
                                          .of(context)
                                          .primaryColorDark,
                                      title: Stack(
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
                                                      color: Theme
                                                          .of(context)
                                                          .primaryColorDark),
                                                  backgroundColor:
                                                  Theme
                                                      .of(context)
                                                      .accentColor,
                                                ),
                                              ),
                                            ),
                                            Column(
                                                mainAxisSize: MainAxisSize.min,
                                                children: [
                                                  Text("Find Location",
                                                      textAlign:
                                                      TextAlign.center,
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
                                                        fontWeight:
                                                        FontWeight.bold,
                                                      )),
                                                  SizedBox(
                                                      height:
                                                      MediaQuery
                                                          .of(context)
                                                          .size
                                                          .height *
                                                          0.01,
                                                      width: 300),
                                                  TextField(
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color),
                                                    decoration: InputDecoration(
                                                        hintStyle: TextStyle(
                                                            color: Theme
                                                                .of(context)
                                                                .textTheme
                                                                .bodyText2!
                                                                .color),
                                                        filled: true,
                                                        enabledBorder:
                                                        InputBorder.none,
                                                        errorBorder:
                                                        InputBorder.none,
                                                        disabledBorder:
                                                        InputBorder.none,
                                                        fillColor:
                                                        Theme
                                                            .of(context)
                                                            .primaryColorLight,
                                                        focusedBorder: OutlineInputBorder(
                                                            borderSide: new BorderSide(
                                                                color: Theme
                                                                    .of(
                                                                    context)
                                                                    .accentColor)),
                                                        hintText:
                                                        'Search for a place of interest'),
                                                    onChanged: (value) {
                                                      _debouncer.run(() {
                                                        Provider.of<
                                                            LocationModel>(
                                                            context,
                                                            listen: false)
                                                            .fetchAllSuggestions(
                                                            value);
                                                      });
                                                    },
                                                  ),
                                                ])
                                          ]),
                                      content: Container(
                                          width: 300,
                                          child: Consumer<LocationModel>(
                                              builder: (context, locationModel,
                                                  child) {
                                                return locationModel
                                                    .suggestions !=
                                                    null &&
                                                    locationModel.suggestions!
                                                        .length >
                                                        0
                                                    ? ListView.builder(
                                                    shrinkWrap: true,
                                                    itemCount: locationModel
                                                        .suggestions!.length,
                                                    itemBuilder:
                                                        (context, index) {
                                                      return InkWell(
                                                          hoverColor: Theme
                                                              .of(
                                                              context)
                                                              .primaryColorLight,
                                                          onTap: () {
                                                            setState(() {
                                                              this.location =
                                                                  locationModel
                                                                      .suggestions!
                                                                      .elementAt(
                                                                      index)
                                                                      .description;
                                                              locationController
                                                                  .text =
                                                              this.location!;
                                                            });
                                                            Navigator.of(
                                                                context)
                                                                .pop();
                                                          },
                                                          child: Padding(
                                                              padding: EdgeInsets
                                                                  .symmetric(
                                                                  vertical: MediaQuery
                                                                      .of(
                                                                      context)
                                                                      .size
                                                                      .height *
                                                                      0.01,
                                                                  horizontal: MediaQuery
                                                                      .of(
                                                                      context)
                                                                      .size
                                                                      .width *
                                                                      0.01),
                                                              child: ListTile(
                                                                  title: Text(
                                                                    locationModel
                                                                        .suggestions!
                                                                        .elementAt(
                                                                        index)
                                                                        .description,
                                                                    style: TextStyle(
                                                                        fontSize: 16 *
                                                                            MediaQuery
                                                                                .of(
                                                                                context)
                                                                                .textScaleFactor,
                                                                        fontWeight:
                                                                        FontWeight
                                                                            .bold,
                                                                        color: Theme
                                                                            .of(
                                                                            context)
                                                                            .textTheme
                                                                            .bodyText1!
                                                                            .color),
                                                                  ))));
                                                    })
                                                    : Container(height: 10);
                                              })));
                                });
                          },
                        )),
                    Spacer(),
                    Padding(
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            primary: Theme
                                .of(context)
                                .accentColor),
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () async {
                          await widget.itineraryEntryModel.addItineraryEntry(
                              currentItinerary!,
                              currentItinerary!.id,
                              titleController.text,
                              descriptionController.text,
                              location!,
                              date!,
                              time!,
                              userID);
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
      firstDate: new DateTime(DateTime
          .now()
          .year - 5),
      lastDate: new DateTime(DateTime
          .now()
          .year + 5),
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

  String getTextDate(DateTime theDate) {
    String x = theDate.day.toString() +
        " " +
        months.elementAt(theDate.month - 1) +
        " " +
        theDate.year.toString();
    return x;
  }

  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.65;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.70;
    }
  }

  String getTextTime(TimeOfDay theTime) {
    final hours = time!.hour.toString().padLeft(2, '0');
    final minutes = time!.minute.toString().padLeft(2, '0');
    return '$hours:$minutes';
  }
}

class _ListItineraryItems extends StatefulWidget {
  late final Adventure? currentAdventure;
  late final Itinerary? currentItinerary;
  late final BuildContext? c;

  _ListItineraryItems(Adventure a, Itinerary i, BuildContext c) {
    this.currentAdventure = a;
    this.currentItinerary = i;
    this.c = c;
  }

  @override
  ListItineraryItems createState() =>
      ListItineraryItems(currentAdventure!, currentItinerary!, c!);
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
  DateTime? date;
  TimeOfDay? time;
  String? location;
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

  BuildContext? c;

  ListItineraryItems(Adventure a, Itinerary i, BuildContext context) {
    this.currentAdventure = a;
    this.currentItinerary = i;
    this.c = context;
  }

  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.65;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.70;
    }
  }

  String getTime(ItineraryEntry i) {
    String dateTime = "";

    String hour = DateTime
        .parse(i.timestamp)
        .hour
        .toString();

    if (hour.length < 2) {
      dateTime = dateTime + "0" + hour + ":";
    } else {
      dateTime = dateTime + hour + ":";
    }

    String minute = DateTime
        .parse(i.timestamp)
        .minute
        .toString();

    if (minute.length < 2) {
      dateTime = dateTime + "0" + minute;
    } else {
      dateTime = dateTime + minute;
    }

    return dateTime;
  }

  String userID = UserApi.getInstance().getUserProfile()!.userID;

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final locationController = TextEditingController();
  final dateController = TextEditingController();
  final timeController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Consumer<ItineraryEntryModel>(builder: (context, entryModel, child) {
      if (entryModel.entries == null &&
          entryModel.popular == null &&
          entryModel.recommendations == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme
                        .of(context)
                        .accentColor)));
      } else if (entryModel.entries!.length > 0) {
        return Column(children: [
          Expanded(
              flex: 4,
              child: Container(
                width: MediaQuery
                    .of(context)
                    .size
                    .width <= 500
                    ? MediaQuery
                    .of(context)
                    .size
                    .width
                    : MediaQuery
                    .of(context)
                    .size
                    .width * 0.9,
                padding: EdgeInsets.symmetric(
                    horizontal: MediaQuery
                        .of(context)
                        .size
                        .width <= 500
                        ? 0
                        : MediaQuery
                        .of(context)
                        .size
                        .width * 0.05),
                child: GroupedListView<dynamic, String>(
                    physics: const AlwaysScrollableScrollPhysics(),
                    elements: entryModel.entries!,
                    groupBy: (element) =>
                    DateTime
                        .parse(element.timestamp)
                        .day
                        .toString() +
                        " " +
                        months[DateTime
                            .parse(element.timestamp)
                            .month - 1] +
                        " " +
                        DateTime
                            .parse(element.timestamp)
                            .year
                            .toString(),
                    useStickyGroupSeparators: false,
                    groupSeparatorBuilder: (String value) =>
                        Container(
                            padding: const EdgeInsets.all(8.0),
                            child: Text(
                              value,
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                  fontSize: 20,
                                  fontWeight: FontWeight.bold,
                                  color:
                                  Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color),
                            )),
                    indexedItemBuilder: (context, element, index) {
                      return Card(
                          color: Theme
                              .of(context)
                              .primaryColorDark,
                          child: InkWell(
                              hoverColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              child: Container(
                                decoration: new BoxDecoration(
                                    image: new DecorationImage(
                                      //TODO: operand can't be null (always false)
                                        image: entryModel.entries!
                                            .elementAt(index)
                                            .location
                                            .photoReference ==
                                            null
                                            ? NetworkImage(
                                            "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                                currentAdventure!.location
                                                    .photoReference +
                                                "&maxwidth=700&key=" +
                                                googleMapsKey)
                                            : NetworkImage(
                                            "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                                entryModel.entries!
                                                    .elementAt(index)
                                                    .location
                                                    .photoReference +
                                                "&maxwidth=500&key=" +
                                                googleMapsKey),
                                        fit: BoxFit.cover,
                                        colorFilter: ColorFilter.mode(
                                            Theme
                                                .of(context)
                                                .backgroundColor
                                                .withOpacity(0.25),
                                            BlendMode.dstATop))),
                                child: Row(children: <Widget>[
                                  Expanded(
                                      flex: 4,
                                      child: ExpansionTile(
                                          title: Text(
                                              entryModel.entries!
                                                  .elementAt(index)
                                                  .title,
                                              style: TextStyle(
                                                  fontSize: 25 *
                                                      MediaQuery
                                                          .of(context)
                                                          .textScaleFactor,
                                                  fontWeight: FontWeight.bold,
                                                  color: Theme
                                                      .of(context)
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
                                                      fontSize: 15 *
                                                          MediaQuery
                                                              .of(context)
                                                              .textScaleFactor,
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color)),
                                              WidgetSpan(
                                                  child: Icon(
                                                    Icons.location_on,
                                                    size: 15,
                                                    color: Theme
                                                        .of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color,
                                                  )),
                                              TextSpan(
                                                  text: " " +
                                                      entryModel.entries!
                                                          .elementAt(index)
                                                          .location
                                                          .name,
                                                  style: TextStyle(
                                                      fontSize: 15 *
                                                          MediaQuery
                                                              .of(context)
                                                              .textScaleFactor,
                                                      color: Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText1!
                                                          .color))
                                            ]),
                                          ),
                                          trailing: Text(
                                              getTime(entryModel.entries!
                                                  .elementAt(index)),
                                              style: TextStyle(
                                                  fontSize: 25 *
                                                      MediaQuery
                                                          .of(context)
                                                          .textScaleFactor,
                                                  fontWeight: FontWeight.bold,
                                                  color: Theme
                                                      .of(context)
                                                      .textTheme
                                                      .bodyText1!
                                                      .color)),
                                          children: [
                                            RegisteredUsers(entryModel.entries!
                                                .elementAt(index))
                                          ])),
                                  PopupMenuButton(
                                      color: Theme
                                          .of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color,
                                      onSelected: (value) {
                                        if (value == 1) {
                                          titleController.text = entryModel
                                              .entries!
                                              .elementAt(index)
                                              .title;
                                          descriptionController.text =
                                              entryModel.entries!
                                                  .elementAt(index)
                                                  .description;
                                          locationController.text = entryModel
                                              .entries!
                                              .elementAt(index)
                                              .location
                                              .name;
                                          location = entryModel.entries!
                                              .elementAt(index)
                                              .location
                                              .name;
                                          dateController.text = entryModel
                                              .entries!
                                              .elementAt(index)
                                              .timestamp
                                              .substring(0, 10);
                                          date = DateTime(
                                              int.parse(entryModel.entries!
                                                  .elementAt(index)
                                                  .timestamp
                                                  .substring(0, 4)),
                                              int.parse(entryModel.entries!
                                                  .elementAt(index)
                                                  .timestamp
                                                  .substring(5, 7)),
                                              int.parse(entryModel.entries!
                                                  .elementAt(index)
                                                  .timestamp
                                                  .substring(8, 10)),
                                              0,
                                              0);
                                          timeController.text = entryModel
                                              .entries!
                                              .elementAt(index)
                                              .timestamp
                                              .substring(11, 16);
                                          time = TimeOfDay(
                                              hour: int.parse(entryModel
                                                  .entries!
                                                  .elementAt(index)
                                                  .timestamp
                                                  .substring(11, 13)),
                                              minute: int.parse(entryModel
                                                  .entries!
                                                  .elementAt(index)
                                                  .timestamp
                                                  .substring(14, 16)));
                                          showDialog(
                                              context: context,
                                              builder: (BuildContext context) {
                                                return AlertDialog(
                                                    backgroundColor:
                                                    Theme
                                                        .of(context)
                                                        .primaryColorDark,
                                                    content: Container(
                                                        height:
                                                        getSize(context),
                                                        child: Stack(
                                                            clipBehavior:
                                                            Clip.none,
                                                            children: <Widget>[
                                                              Positioned(
                                                                right: -40.0,
                                                                top: -40.0,
                                                                child:
                                                                InkResponse(
                                                                  onTap: () {
                                                                    Navigator
                                                                        .of(
                                                                        context)
                                                                        .pop(
                                                                        false);
                                                                  },
                                                                  child:
                                                                  CircleAvatar(
                                                                    child: Icon(
                                                                        Icons
                                                                            .close,
                                                                        color: Theme
                                                                            .of(
                                                                            context)
                                                                            .primaryColorDark),
                                                                    backgroundColor:
                                                                    Theme
                                                                        .of(
                                                                        context)
                                                                        .accentColor,
                                                                  ),
                                                                ),
                                                              ),
                                                              Column(
                                                                  children: <
                                                                      Widget>[
                                                                    Text(
                                                                        "Edit: " +
                                                                            entryModel
                                                                                .entries!
                                                                                .elementAt(
                                                                                index)
                                                                                .title,
                                                                        textAlign:
                                                                        TextAlign
                                                                            .center,
                                                                        style:
                                                                        TextStyle(
                                                                          color: Theme
                                                                              .of(
                                                                              context)
                                                                              .textTheme
                                                                              .bodyText1!
                                                                              .color,
                                                                          fontSize:
                                                                          25 *
                                                                              MediaQuery
                                                                                  .of(
                                                                                  context)
                                                                                  .textScaleFactor,
                                                                          fontWeight:
                                                                          FontWeight
                                                                              .bold,
                                                                        )),
                                                                    Spacer(),
                                                                    Container(
                                                                      width:
                                                                      300,
                                                                      padding: EdgeInsets
                                                                          .symmetric(
                                                                          horizontal:
                                                                          MediaQuery
                                                                              .of(
                                                                              context)
                                                                              .size
                                                                              .width *
                                                                              0.02),
                                                                      child: TextField(
                                                                          style: TextStyle(
                                                                              color: Theme
                                                                                  .of(
                                                                                  context)
                                                                                  .textTheme
                                                                                  .bodyText1!
                                                                                  .color),
                                                                          controller:
                                                                          titleController,
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
                                                                                  .of(
                                                                                  context)
                                                                                  .primaryColorLight,
                                                                              focusedBorder: OutlineInputBorder(
                                                                                  borderSide: new BorderSide(
                                                                                      color: Theme
                                                                                          .of(
                                                                                          context)
                                                                                          .accentColor)),
                                                                              hintText: 'Title')),
                                                                    ),
                                                                    SizedBox(
                                                                        height: MediaQuery
                                                                            .of(
                                                                            context)
                                                                            .size
                                                                            .height *
                                                                            0.01),
                                                                    Container(
                                                                      width:
                                                                      300,
                                                                      padding: EdgeInsets
                                                                          .symmetric(
                                                                          horizontal:
                                                                          MediaQuery
                                                                              .of(
                                                                              context)
                                                                              .size
                                                                              .width *
                                                                              0.02),
                                                                      child: TextField(
                                                                          maxLength:
                                                                          255,
                                                                          maxLines:
                                                                          3,
                                                                          style: TextStyle(
                                                                              color: Theme
                                                                                  .of(
                                                                                  context)
                                                                                  .textTheme
                                                                                  .bodyText1!
                                                                                  .color),
                                                                          controller:
                                                                          descriptionController,
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
                                                                                  .of(
                                                                                  context)
                                                                                  .primaryColorLight,
                                                                              focusedBorder: OutlineInputBorder(
                                                                                  borderSide: new BorderSide(
                                                                                      color: Theme
                                                                                          .of(
                                                                                          context)
                                                                                          .accentColor)),
                                                                              hintText: 'Description')),
                                                                    ),
                                                                    SizedBox(
                                                                        height: MediaQuery
                                                                            .of(
                                                                            context)
                                                                            .size
                                                                            .height *
                                                                            0.02),
                                                                    Container(
                                                                        width:
                                                                        300,
                                                                        padding:
                                                                        EdgeInsets
                                                                            .symmetric(
                                                                            horizontal: MediaQuery
                                                                                .of(
                                                                                context)
                                                                                .size
                                                                                .width *
                                                                                0.02),
                                                                        child: TextField(
                                                                            style: TextStyle(
                                                                                color: Theme
                                                                                    .of(
                                                                                    context)
                                                                                    .textTheme
                                                                                    .bodyText1!
                                                                                    .color),
                                                                            controller: dateController,
                                                                            decoration: InputDecoration(
                                                                                prefixIcon: Icon(
                                                                                    Icons
                                                                                        .calendar_today_rounded),
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
                                                                                    .of(
                                                                                    context)
                                                                                    .primaryColorLight,
                                                                                focusedBorder: OutlineInputBorder(
                                                                                    borderSide: new BorderSide(
                                                                                        color: Theme
                                                                                            .of(
                                                                                            context)
                                                                                            .accentColor)),
                                                                                hintText: 'Pick a date'),
                                                                            onTap: () async {
                                                                              DateTime? picked = await showDate();
                                                                              date =
                                                                                  picked;
                                                                              dateController
                                                                                  .text =
                                                                                  getTextDate(
                                                                                      picked!);
                                                                            })),
                                                                    SizedBox(
                                                                        height: MediaQuery
                                                                            .of(
                                                                            context)
                                                                            .size
                                                                            .height *
                                                                            0.02),
                                                                    Container(
                                                                        width:
                                                                        300,
                                                                        padding: EdgeInsets
                                                                            .symmetric(
                                                                            horizontal: MediaQuery
                                                                                .of(
                                                                                context)
                                                                                .size
                                                                                .width *
                                                                                0.02),
                                                                        child:
                                                                        TextField(
                                                                          maxLines:
                                                                          1,
                                                                          style:
                                                                          TextStyle(
                                                                              color: Theme
                                                                                  .of(
                                                                                  context)
                                                                                  .textTheme
                                                                                  .bodyText1!
                                                                                  .color),
                                                                          controller:
                                                                          timeController,
                                                                          decoration: InputDecoration(
                                                                              prefixIcon: Icon(
                                                                                  Icons
                                                                                      .access_time_rounded),
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
                                                                                  .of(
                                                                                  context)
                                                                                  .primaryColorLight,
                                                                              focusedBorder: OutlineInputBorder(
                                                                                  borderSide: new BorderSide(
                                                                                      color: Theme
                                                                                          .of(
                                                                                          context)
                                                                                          .accentColor)),
                                                                              hintText: 'Pick a time'),
                                                                          onTap:
                                                                              () async {
                                                                            TimeOfDay?
                                                                            picked =
                                                                            await showTime();
                                                                            if (picked !=
                                                                                null) {
                                                                              setState(() {
                                                                                time =
                                                                                    picked;
                                                                                timeController
                                                                                    .text =
                                                                                    getTextTime(
                                                                                        picked);
                                                                              });
                                                                            }
                                                                          },
                                                                        )),
                                                                    SizedBox(
                                                                        height: MediaQuery
                                                                            .of(
                                                                            context)
                                                                            .size
                                                                            .height *
                                                                            0.02),
                                                                    Container(
                                                                        width:
                                                                        300,
                                                                        padding:
                                                                        EdgeInsets
                                                                            .symmetric(
                                                                            horizontal: MediaQuery
                                                                                .of(
                                                                                context)
                                                                                .size
                                                                                .width *
                                                                                0.02),
                                                                        child: TextField(
                                                                            maxLines: 1,
                                                                            style: TextStyle(
                                                                                color:
                                                                                Theme
                                                                                    .of(
                                                                                    context)
                                                                                    .textTheme
                                                                                    .bodyText1!
                                                                                    .color),
                                                                            controller: locationController,
                                                                            decoration: InputDecoration(
                                                                                prefixIcon: Icon(
                                                                                    Icons
                                                                                        .location_on_rounded),
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
                                                                                    .of(
                                                                                    context)
                                                                                    .primaryColorLight,
                                                                                focusedBorder: OutlineInputBorder(
                                                                                    borderSide: new BorderSide(
                                                                                        color: Theme
                                                                                            .of(
                                                                                            context)
                                                                                            .accentColor)),
                                                                                hintText: 'Find a location'),
                                                                            onTap: () async {
                                                                              showDialog(
                                                                                  context: context,
                                                                                  builder: (
                                                                                      BuildContext context) {
                                                                                    return AlertDialog(
                                                                                        backgroundColor:
                                                                                        Theme
                                                                                            .of(
                                                                                            context)
                                                                                            .primaryColorDark,
                                                                                        title: Stack(
                                                                                            clipBehavior: Clip
                                                                                                .none,
                                                                                            children: <
                                                                                                Widget>[
                                                                                              Positioned(
                                                                                                right: -40.0,
                                                                                                top: -40.0,
                                                                                                child: InkResponse(
                                                                                                  onTap: () {
                                                                                                    Navigator
                                                                                                        .of(
                                                                                                        context)
                                                                                                        .pop();
                                                                                                  },
                                                                                                  child: CircleAvatar(
                                                                                                    child: Icon(
                                                                                                        Icons
                                                                                                            .close,
                                                                                                        color: Theme
                                                                                                            .of(
                                                                                                            context)
                                                                                                            .primaryColorDark),
                                                                                                    backgroundColor:
                                                                                                    Theme
                                                                                                        .of(
                                                                                                        context)
                                                                                                        .accentColor,
                                                                                                  ),
                                                                                                ),
                                                                                              ),
                                                                                              Column(
                                                                                                  mainAxisSize: MainAxisSize
                                                                                                      .min,
                                                                                                  children: [
                                                                                                    Text(
                                                                                                        "Find Location",
                                                                                                        textAlign:
                                                                                                        TextAlign
                                                                                                            .center,
                                                                                                        style: TextStyle(
                                                                                                          color: Theme
                                                                                                              .of(
                                                                                                              context)
                                                                                                              .textTheme
                                                                                                              .bodyText1!
                                                                                                              .color,
                                                                                                          fontSize: 25 *
                                                                                                              MediaQuery
                                                                                                                  .of(
                                                                                                                  context)
                                                                                                                  .textScaleFactor,
                                                                                                          fontWeight:
                                                                                                          FontWeight
                                                                                                              .bold,
                                                                                                        )),
                                                                                                    SizedBox(
                                                                                                        height:
                                                                                                        MediaQuery
                                                                                                            .of(
                                                                                                            context)
                                                                                                            .size
                                                                                                            .height *
                                                                                                            0.01,
                                                                                                        width: 300),
                                                                                                    TextField(
                                                                                                      style: TextStyle(
                                                                                                          color: Theme
                                                                                                              .of(
                                                                                                              context)
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
                                                                                                          enabledBorder:
                                                                                                          InputBorder
                                                                                                              .none,
                                                                                                          errorBorder:
                                                                                                          InputBorder
                                                                                                              .none,
                                                                                                          disabledBorder:
                                                                                                          InputBorder
                                                                                                              .none,
                                                                                                          fillColor:
                                                                                                          Theme
                                                                                                              .of(
                                                                                                              context)
                                                                                                              .primaryColorLight,
                                                                                                          focusedBorder: OutlineInputBorder(
                                                                                                              borderSide: new BorderSide(
                                                                                                                  color: Theme
                                                                                                                      .of(
                                                                                                                      context)
                                                                                                                      .accentColor)),
                                                                                                          hintText:
                                                                                                          'Search for a place of interest'),
                                                                                                      onChanged: (
                                                                                                          value) {
                                                                                                        _debouncer
                                                                                                            .run(() {
                                                                                                          Provider
                                                                                                              .of<
                                                                                                              LocationModel>(
                                                                                                              context,
                                                                                                              listen: false)
                                                                                                              .fetchAllSuggestions(
                                                                                                              value);
                                                                                                        });
                                                                                                      },
                                                                                                    ),
                                                                                                  ])
                                                                                            ]),
                                                                                        content: Container(
                                                                                            width: 300,
                                                                                            child: Consumer<
                                                                                                LocationModel>(
                                                                                                builder: (
                                                                                                    context,
                                                                                                    locationModel,
                                                                                                    child) {
                                                                                                  return locationModel
                                                                                                      .suggestions !=
                                                                                                      null &&
                                                                                                      locationModel
                                                                                                          .suggestions!
                                                                                                          .length >
                                                                                                          0
                                                                                                      ? ListView
                                                                                                      .builder(
                                                                                                      shrinkWrap: true,
                                                                                                      itemCount: locationModel
                                                                                                          .suggestions!
                                                                                                          .length,
                                                                                                      itemBuilder:
                                                                                                          (
                                                                                                          context,
                                                                                                          index) {
                                                                                                        return InkWell(
                                                                                                            hoverColor: Theme
                                                                                                                .of(
                                                                                                                context)
                                                                                                                .primaryColorLight,
                                                                                                            onTap: () {
                                                                                                              setState(() {
                                                                                                                this
                                                                                                                    .location =
                                                                                                                    locationModel
                                                                                                                        .suggestions!
                                                                                                                        .elementAt(
                                                                                                                        index)
                                                                                                                        .description;
                                                                                                                locationController
                                                                                                                    .text =
                                                                                                                this
                                                                                                                    .location!;
                                                                                                              });
                                                                                                              Navigator
                                                                                                                  .of(
                                                                                                                  context)
                                                                                                                  .pop();
                                                                                                            },
                                                                                                            child: Padding(
                                                                                                                padding: EdgeInsets
                                                                                                                    .symmetric(
                                                                                                                    vertical: MediaQuery
                                                                                                                        .of(
                                                                                                                        context)
                                                                                                                        .size
                                                                                                                        .height *
                                                                                                                        0.01,
                                                                                                                    horizontal: MediaQuery
                                                                                                                        .of(
                                                                                                                        context)
                                                                                                                        .size
                                                                                                                        .width *
                                                                                                                        0.01),
                                                                                                                child: ListTile(
                                                                                                                    title: Text(
                                                                                                                      locationModel
                                                                                                                          .suggestions!
                                                                                                                          .elementAt(
                                                                                                                          index)
                                                                                                                          .description,
                                                                                                                      style: TextStyle(
                                                                                                                          fontSize: 16 *
                                                                                                                              MediaQuery
                                                                                                                                  .of(
                                                                                                                                  context)
                                                                                                                                  .textScaleFactor,
                                                                                                                          fontWeight:
                                                                                                                          FontWeight
                                                                                                                              .bold,
                                                                                                                          color: Theme
                                                                                                                              .of(
                                                                                                                              context)
                                                                                                                              .textTheme
                                                                                                                              .bodyText1!
                                                                                                                              .color),
                                                                                                                    ))));
                                                                                                      })
                                                                                                      : Container(
                                                                                                      height: 10);
                                                                                                })));
                                                                                  });
                                                                            })),
                                                                    Spacer(),
                                                                    Padding(
                                                                        padding:
                                                                        EdgeInsets
                                                                            .symmetric(
                                                                            horizontal: MediaQuery
                                                                                .of(
                                                                                context)
                                                                                .size
                                                                                .width *
                                                                                0.02),
                                                                        child: ElevatedButton(
                                                                            style: ElevatedButton
                                                                                .styleFrom(
                                                                                primary: Theme
                                                                                    .of(
                                                                                    context)
                                                                                    .accentColor),
                                                                            onPressed: () {
                                                                              Provider
                                                                                  .of<
                                                                                  ItineraryEntryModel>(
                                                                                  c!,
                                                                                  listen: false)
                                                                                  .editItineraryEntry(
                                                                                  entryModel
                                                                                      .entries!
                                                                                      .elementAt(
                                                                                      index),
                                                                                  currentItinerary!,
                                                                                  entryModel
                                                                                      .entries!
                                                                                      .elementAt(
                                                                                      index)
                                                                                      .id,
                                                                                  userID,
                                                                                  currentItinerary!
                                                                                      .id,
                                                                                  titleController
                                                                                      .text,
                                                                                  descriptionController
                                                                                      .text,
                                                                                  location!,
                                                                                  date!,
                                                                                  time!);
                                                                              Navigator
                                                                                  .of(
                                                                                  context)
                                                                                  .pop();
                                                                            },
                                                                            child: Text(
                                                                                "Edit",
                                                                                style: TextStyle(
                                                                                    color: Theme
                                                                                        .of(
                                                                                        context)
                                                                                        .textTheme
                                                                                        .bodyText1!
                                                                                        .color))))
                                                                  ])
                                                            ])));
                                              });
                                        }
                                        if (value == 2) {
                                          showDialog(
                                            context: context,
                                            builder: (BuildContext context) {
                                              return AlertDialog(
                                                backgroundColor:
                                                Theme
                                                    .of(context)
                                                    .primaryColorDark,
                                                title: Text("Confirm Removal",
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color)),
                                                content: Text(
                                                    "Are you sure you want to remove this itinerary for definite?",
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color)),
                                                actions: <Widget>[
                                                  TextButton(
                                                      onPressed: () {
                                                        Provider.of<
                                                            ItineraryEntryModel>(
                                                            c!,
                                                            listen: false)
                                                            .deleteItineraryEntry(
                                                            entryModel
                                                                .entries!
                                                                .elementAt(
                                                                index));

                                                        Navigator.of(context)
                                                            .pop();
                                                      },
                                                      child: Text("Remove",
                                                          style: TextStyle(
                                                              color: Theme
                                                                  .of(
                                                                  context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color))),
                                                  TextButton(
                                                    onPressed: () =>
                                                        Navigator.of(context)
                                                            .pop(false),
                                                    child: Text("Cancel",
                                                        style: TextStyle(
                                                            color: Theme
                                                                .of(
                                                                context)
                                                                .textTheme
                                                                .bodyText1!
                                                                .color)),
                                                  ),
                                                ],
                                              );
                                            },
                                          );
                                        }
                                        if (value == 5) {
                                          MapsLauncher.launchQuery(entryModel
                                              .entries!
                                              .elementAt(index)
                                              .location
                                              .formattedAddress);
                                        }
                                        if (value == 3) {
                                          ItineraryApi.isRegisteredUser(
                                              entryModel.entries!
                                                  .elementAt(index),
                                              context)
                                              .then((value) {
                                            if (value) {
                                              ItineraryApi
                                                  .deregisterForItinerary(
                                                  entryModel.entries!
                                                      .elementAt(index),
                                                  context);
                                            } else {
                                              ItineraryApi.registerForItinerary(
                                                  entryModel.entries!
                                                      .elementAt(index),
                                                  context);
                                            }
                                          });
                                        }
                                        if (value == 4) {
                                          ItineraryApi.checkUserOff(
                                              entryModel.entries!
                                                  .elementAt(index),
                                              context)
                                              .then((value) =>
                                          {
                                            showDialog(
                                                context: context,
                                                builder: (BuildContext
                                                context) {
                                                  return AlertDialog(
                                                      backgroundColor:
                                                      Theme
                                                          .of(
                                                          context)
                                                          .primaryColorDark,
                                                      title: Text(
                                                        'Did you like this location?',
                                                        style: TextStyle(
                                                            color: Theme
                                                                .of(
                                                                context)
                                                                .textTheme
                                                                .bodyText1!
                                                                .color),
                                                      ),
                                                      actions: <Widget>[
                                                        TextButton(
                                                          child: Text(
                                                              'Yes',
                                                              style: TextStyle(
                                                                  color: Theme
                                                                      .of(
                                                                      context)
                                                                      .textTheme
                                                                      .bodyText1!
                                                                      .color)),
                                                          onPressed:
                                                              () {
                                                            LocationApi
                                                                .likeLocation(
                                                                entryModel
                                                                    .entries!
                                                                    .elementAt(
                                                                    index)
                                                                    .id,
                                                                context);
                                                            Navigator.of(
                                                                context)
                                                                .pop();
                                                          },
                                                        ),
                                                        TextButton(
                                                          child: Text(
                                                              'No',
                                                              style: TextStyle(
                                                                  color: Theme
                                                                      .of(
                                                                      context)
                                                                      .textTheme
                                                                      .bodyText1!
                                                                      .color)),
                                                          onPressed:
                                                              () {
                                                            Navigator.of(
                                                                context)
                                                                .pop();
                                                          },
                                                        ),
                                                      ]);
                                                })
                                          });
                                        }
                                      },
                                      itemBuilder: (context) =>
                                      [
                                        PopupMenuItem(
                                            value: 1,
                                            child: Row(
                                              children: <Widget>[
                                                Padding(
                                                  padding:
                                                  const EdgeInsets.all(
                                                      5),
                                                  child: Icon(
                                                      Icons.edit_rounded,
                                                      color:
                                                      Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText2!
                                                          .color),
                                                ),
                                                Text("Edit",
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(
                                                            context)
                                                            .textTheme
                                                            .bodyText2!
                                                            .color))
                                              ],
                                            )),
                                        PopupMenuItem(
                                            value: 3,
                                            child: Row(
                                              children: <Widget>[
                                                Padding(
                                                  padding:
                                                  const EdgeInsets.all(
                                                      5),
                                                  child: Icon(Icons.person,
                                                      color:
                                                      Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText2!
                                                          .color),
                                                ),
                                                Text("Sign up/Opt out",
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(
                                                            context)
                                                            .textTheme
                                                            .bodyText2!
                                                            .color))
                                              ],
                                            )),
                                        PopupMenuItem(
                                            value: 5,
                                            child: Row(
                                              children: <Widget>[
                                                Padding(
                                                  padding:
                                                  const EdgeInsets.all(
                                                      5),
                                                  child: Icon(
                                                      Icons.map_outlined,
                                                      color:
                                                      Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText2!
                                                          .color),
                                                ),
                                                Text("Open Map",
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(
                                                            context)
                                                            .textTheme
                                                            .bodyText2!
                                                            .color))
                                              ],
                                            )),
                                        PopupMenuItem(
                                            value: 4,
                                            child: Row(
                                              children: <Widget>[
                                                Padding(
                                                  padding:
                                                  const EdgeInsets.all(
                                                      5),
                                                  child: Icon(
                                                      Icons
                                                          .location_history,
                                                      color:
                                                      Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText2!
                                                          .color),
                                                ),
                                                Text("Check in",
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(
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
                                                  const EdgeInsets.all(
                                                      5),
                                                  child: Icon(Icons.delete,
                                                      color:
                                                      Theme
                                                          .of(context)
                                                          .textTheme
                                                          .bodyText2!
                                                          .color),
                                                ),
                                                Text("Delete",
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(
                                                            context)
                                                            .textTheme
                                                            .bodyText2!
                                                            .color))
                                              ],
                                            ))
                                      ]),
                                ]),
                              )));
                    }),
              )),
          SizedBox(height: 10),
          SizedBox(
              child: Text("Recommendations",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 20 * MediaQuery
                          .of(context)
                          .textScaleFactor,
                      fontWeight: FontWeight.bold,
                      color: Theme
                          .of(context)
                          .textTheme
                          .bodyText1!
                          .color))),
          SizedBox(height: 10),
          Expanded(
            flex: 3,
            child: _RecommendedItems(
                currentAdventure!, currentItinerary!, c!, entryModel),
          ),
        ]);
      } else {
        return Column(children: [
          Expanded(
              flex: 4,
              child: Center(
                  child: Text("Seems like you've got nowhere to go!",
                      textAlign: TextAlign.center,
                      style: TextStyle(
                          fontSize: 30 * MediaQuery
                              .of(context)
                              .textScaleFactor,
                          color:
                          Theme
                              .of(context)
                              .textTheme
                              .bodyText1!
                              .color)))),
          SizedBox(height: 10),
          SizedBox(
              child: Text("Recommendations",
                  textAlign: TextAlign.center,
                  style: TextStyle(
                      fontSize: 20 * MediaQuery
                          .of(context)
                          .textScaleFactor,
                      fontWeight: FontWeight.bold,
                      color: Theme
                          .of(context)
                          .textTheme
                          .bodyText1!
                          .color))),
          SizedBox(height: 10),
          Expanded(
            flex: 2,
            child: _RecommendedItems(
                currentAdventure!, currentItinerary!, c!, entryModel),
          )
        ]);
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
      firstDate: new DateTime(DateTime
          .now()
          .year - 5),
      lastDate: new DateTime(DateTime
          .now()
          .year + 5),
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

  String getTextDate(DateTime theDate) {
    String x = theDate.day.toString() +
        " " +
        months.elementAt(theDate.month - 1) +
        " " +
        theDate.year.toString();
    return x;
  }

  String getTextTime(TimeOfDay theTime) {
    final hours = time!.hour.toString().padLeft(2, '0');
    final minutes = time!.minute.toString().padLeft(2, '0');
    return '$hours:$minutes';
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

class RegisteredUsers extends StatelessWidget {
  ItineraryEntry? currentEntry;

  RegisteredUsers(ItineraryEntry i) {
    this.currentEntry = i;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => RegisteredUserModel(this.currentEntry!, context),
        builder: (context, widget) {
          return Consumer<RegisteredUserModel>(
              builder: (context, registeredModel, child) {
                if (registeredModel.users == null) {
                  return Column(children: [
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                    Center(
                        child: CircularProgressIndicator(
                            valueColor: new AlwaysStoppedAnimation<Color>(
                                Theme
                                    .of(context)
                                    .accentColor))),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01)
                  ]);
                } else if (registeredModel.users!.length > 0) {
                  return Column(mainAxisSize: MainAxisSize.min, children: [
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                    Center(
                        child: Text("Participating Adventurers",
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                fontSize:
                                20 * MediaQuery
                                    .of(context)
                                    .textScaleFactor,
                                color:
                                Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color))),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                    ListView.builder(
                        shrinkWrap: true,
                        itemCount: registeredModel.users!.length,
                        itemBuilder: (context, index) {
                          return ListTile(
                              leading: CachedNetworkImage(
                                  useOldImageOnUrlChange: true,
                                  imageUrl: mainApi +
                                      "/user/viewPicture/" +
                                      registeredModel.users!
                                          .elementAt(index)
                                          .user
                                          .profileID,
                                  imageBuilder: (context, imageProvider) =>
                                      Container(
                                          width: 70,
                                          height: 70,
                                          decoration: new BoxDecoration(
                                              border: Border.all(
                                                color:
                                                Theme
                                                    .of(context)
                                                    .accentColor,
                                                width: 3,
                                              ),
                                              shape: BoxShape.circle,
                                              image: DecorationImage(
                                                  fit: BoxFit.cover,
                                                  image: imageProvider))),
                                  placeholder: (context, url) =>
                                      Container(
                                          width: 70,
                                          height: 70,
                                          decoration: new BoxDecoration(
                                              border: Border.all(
                                                color: Theme
                                                    .of(context)
                                                    .accentColor,
                                                width: 3,
                                              ),
                                              shape: BoxShape.circle,
                                              image: DecorationImage(
                                                  fit: BoxFit.cover,
                                                  image: AssetImage(
                                                      "custom_images/pfp.png")))),
                                  errorWidget: (context, url, error) =>
                                      Container(
                                          width: 70,
                                          height: 70,
                                          decoration: new BoxDecoration(
                                              border: Border.all(
                                                color: Theme
                                                    .of(context)
                                                    .accentColor,
                                                width: 3,
                                              ),
                                              shape: BoxShape.circle,
                                              image: DecorationImage(
                                                  fit: BoxFit.cover,
                                                  image: AssetImage(
                                                      "custom_images/pfp.png"))))),
                              title: Text(
                                  registeredModel.users!
                                      .elementAt(index)
                                      .user
                                      .username,
                                  style: TextStyle(
                                      decoration: registeredModel.users!
                                          .elementAt(index)
                                          .checkIn
                                          ? TextDecoration.lineThrough
                                          : null,
                                      fontSize: 15,
                                      color: Theme
                                          .of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)));
                        }),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                  ]);
                } else {
                  return Column(children: [
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                    Center(
                        child: Text("Be the first to join in on this activity!",
                            textAlign: TextAlign.center,
                            style: TextStyle(
                                fontSize:
                                20 * MediaQuery
                                    .of(context)
                                    .textScaleFactor,
                                color:
                                Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color))),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01)
                  ]);
                }
              });
        });
  }
}

class _RecommendedItems extends StatelessWidget {
  Adventure? currentAdventure;
  Itinerary? currentItinerary;
  BuildContext? context;
  ItineraryEntryModel? entryModel;
  final ScrollController _controller = ScrollController();

  _RecommendedItems(Adventure a, Itinerary i, BuildContext c,
      ItineraryEntryModel iEM) {
    this.currentAdventure = a;
    this.currentItinerary = i;
    this.context = c;
    this.entryModel = iEM;
  }

  double getSize(BuildContext context) {
    if (MediaQuery
        .of(context)
        .size
        .width >
        MediaQuery
            .of(context)
            .size
            .height) {
      return (MediaQuery
          .of(context)
          .size
          .width / 3);
    } else {
      return (MediaQuery
          .of(context)
          .size
          .width / 2);
    }
  }

  @override
  Widget build(context) {
    if (entryModel!.popular != null &&
        entryModel!.recommendations != null &&
        entryModel!.popular!.length > 0) {
      return Container(
          width: MediaQuery
              .of(context)
              .size
              .width,
          child: Stack(children: [
            ListView.builder(
                scrollDirection: Axis.horizontal,
                controller: _controller,
                shrinkWrap: true,
                itemCount: entryModel!.popular!.length +
                    entryModel!.recommendations!.length,
                itemBuilder: (context, index) {
                  if (index < entryModel!.popular!.length) {
                    return Container(
                        width: getSize(context),
                        child: ClipRect(
                            child: Card(
                                color: Theme
                                    .of(context)
                                    .primaryColorDark,
                                child: Banner(
                                    location: BannerLocation.topEnd,
                                    message: "Popular",
                                    color: Theme
                                        .of(context)
                                        .accentColor,
                                    textStyle: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        fontSize: 10 *
                                            MediaQuery
                                                .of(context)
                                                .textScaleFactor,
                                        color: Theme
                                            .of(context)
                                            .scaffoldBackgroundColor),
                                    child: InkWell(
                                        onTap: () {
                                          var provider =
                                          Provider.of<ItineraryEntryModel>(
                                              context,
                                              listen: false);
                                          showDialog(
                                              context: context,
                                              builder: (BuildContext context) {
                                                return AlertBoxRecommendation(
                                                    currentItinerary!,
                                                    provider,
                                                    entryModel!.popular!
                                                        .elementAt(index));
                                              });
                                        },
                                        hoverColor:
                                        Theme
                                            .of(context)
                                            .primaryColorLight,
                                        child: Column(
                                            mainAxisSize: MainAxisSize.min,
                                            children: [
                                              Expanded(
                                                  flex: 5,
                                                  child: Container(
                                                      decoration:
                                                      new BoxDecoration(
                                                          image:
                                                          new DecorationImage(
                                                            //TODO: operand can't be null (always false)
                                                            image: entryModel!
                                                                .popular!
                                                                .elementAt(
                                                                index)
                                                                .photoReference ==
                                                                ""
                                                                ? NetworkImage(
                                                                "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                                                    currentAdventure!
                                                                        .location
                                                                        .photoReference +
                                                                    "&maxwidth=700&key=" +
                                                                    googleMapsKey)
                                                                : NetworkImage(
                                                                "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                                                    entryModel!
                                                                        .popular!
                                                                        .elementAt(
                                                                        index)
                                                                        .photoReference +
                                                                    "&maxwidth=500&key=" +
                                                                    googleMapsKey),
                                                            fit: BoxFit.cover,
                                                          )))),
                                              Expanded(
                                                  flex: 3,
                                                  child: ListTile(
                                                    title: Text(
                                                        entryModel!.popular!
                                                            .elementAt(index)
                                                            .name,
                                                        textAlign: TextAlign
                                                            .center,
                                                        style: TextStyle(
                                                            fontSize: 20 *
                                                                MediaQuery
                                                                    .of(
                                                                    context)
                                                                    .textScaleFactor,
                                                            fontWeight:
                                                            FontWeight.bold,
                                                            color: Theme
                                                                .of(
                                                                context)
                                                                .textTheme
                                                                .bodyText1!
                                                                .color)),
                                                    // subtitle:Text(adventures.elementAt(index).description),
                                                    subtitle: RichText(
                                                      textAlign:
                                                      TextAlign.center,
                                                      text: TextSpan(children: [
                                                        WidgetSpan(
                                                            child: Icon(
                                                              Icons.location_on,
                                                              size: 15,
                                                              color:
                                                              Theme
                                                                  .of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color,
                                                            )),
                                                        TextSpan(
                                                            text: " " +
                                                                entryModel!
                                                                    .popular!
                                                                    .elementAt(
                                                                    index)
                                                                    .name,
                                                            style: TextStyle(
                                                                fontSize: 12 *
                                                                    MediaQuery
                                                                        .of(
                                                                        context)
                                                                        .textScaleFactor,
                                                                color: Theme
                                                                    .of(
                                                                    context)
                                                                    .textTheme
                                                                    .bodyText1!
                                                                    .color)),
                                                      ]),
                                                    ),
                                                  )),
                                              Expanded(
                                                child: Center(
                                                    child: entryModel!.popular!
                                                        .elementAt(index)
                                                        .liked
                                                        ? IconButton(
                                                        splashRadius: 21,
                                                        onPressed: () {
                                                          Provider.of<
                                                              ItineraryEntryModel>(
                                                              context,
                                                              listen:
                                                              false)
                                                              .likeLocation(
                                                              entryModel!
                                                                  .popular!
                                                                  .elementAt(
                                                                  index)
                                                                  .id,
                                                              context);
                                                        },
                                                        iconSize: 20,
                                                        color: Color(
                                                            0xff931621),
                                                        icon: Icon(Icons
                                                            .favorite_rounded))
                                                        : IconButton(
                                                        onPressed: () {
                                                          Provider.of<
                                                              ItineraryEntryModel>(
                                                              context,
                                                              listen:
                                                              false)
                                                              .likeLocation(
                                                              entryModel!
                                                                  .popular!
                                                                  .elementAt(
                                                                  index)
                                                                  .id,
                                                              context);
                                                        },
                                                        splashRadius: 21,
                                                        iconSize: 20,
                                                        color: Theme
                                                            .of(
                                                            context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color,
                                                        icon: Icon(Icons
                                                            .favorite_outline_rounded))),
                                              ),
                                              Spacer(),
                                            ]))))));
                  } else {
                    return Container(
                        width: getSize(context),
                        child: ClipRect(
                            child: Card(
                                color: Theme
                                    .of(context)
                                    .primaryColorDark,
                                child: Banner(
                                    location: BannerLocation.topEnd,
                                    message: "Recommended",
                                    color: Theme
                                        .of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color!,
                                    textStyle: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        fontSize: 10 *
                                            MediaQuery
                                                .of(context)
                                                .textScaleFactor,
                                        color: Theme
                                            .of(context)
                                            .scaffoldBackgroundColor),
                                    child: InkWell(
                                        onTap: () {
                                          var provider =
                                          Provider.of<ItineraryEntryModel>(
                                              context,
                                              listen: false);
                                          showDialog(
                                              context: context,
                                              builder: (BuildContext context) {
                                                return AlertBoxRecommendation(
                                                    currentItinerary!,
                                                    provider,
                                                    entryModel!.recommendations!
                                                        .elementAt(index -
                                                        entryModel!.popular!
                                                            .length));
                                              });
                                        },
                                        hoverColor:
                                        Theme
                                            .of(context)
                                            .primaryColorLight,
                                        child: Column(
                                            mainAxisSize: MainAxisSize.min,
                                            children: [
                                              Expanded(
                                                  flex: 5,
                                                  child: Container(
                                                      decoration:
                                                      new BoxDecoration(
                                                          image:
                                                          new DecorationImage(
                                                            //TODO: operand can't be null (always false)
                                                            image: entryModel!
                                                                .recommendations!
                                                                .elementAt(
                                                                index -
                                                                    entryModel!
                                                                        .popular!
                                                                        .length)
                                                                .photoReference ==
                                                                ""
                                                                ? NetworkImage(
                                                                "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                                                    currentAdventure!
                                                                        .location
                                                                        .photoReference +
                                                                    "&maxwidth=700&key=" +
                                                                    googleMapsKey)
                                                                : NetworkImage(
                                                                "https://app.adventure-it.co.za/googleMapsApi/photo?photo_reference=" +
                                                                    entryModel!
                                                                        .recommendations!
                                                                        .elementAt(
                                                                        index -
                                                                            entryModel!
                                                                                .popular!
                                                                                .length)
                                                                        .photoReference +
                                                                    "&maxwidth=500&key=" +
                                                                    googleMapsKey),
                                                            fit: BoxFit.cover,
                                                          )))),
                                              Expanded(
                                                  flex: 3,
                                                  child: ListTile(
                                                    title: Text(
                                                        entryModel!
                                                            .recommendations!
                                                            .elementAt(index -
                                                            entryModel!
                                                                .popular!
                                                                .length)
                                                            .name,
                                                        textAlign: TextAlign
                                                            .center,
                                                        style: TextStyle(
                                                            fontSize: 20 *
                                                                MediaQuery
                                                                    .of(
                                                                    context)
                                                                    .textScaleFactor,
                                                            fontWeight:
                                                            FontWeight.bold,
                                                            color: Theme
                                                                .of(
                                                                context)
                                                                .textTheme
                                                                .bodyText1!
                                                                .color)),
                                                    // subtitle:Text(adventures.elementAt(index).description),
                                                    subtitle: RichText(
                                                      textAlign:
                                                      TextAlign.center,
                                                      text: TextSpan(children: [
                                                        WidgetSpan(
                                                            child: Icon(
                                                              Icons.location_on,
                                                              size: 15,
                                                              color:
                                                              Theme
                                                                  .of(context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color,
                                                            )),
                                                        TextSpan(
                                                            text: " " +
                                                                entryModel!
                                                                    .recommendations!
                                                                    .elementAt(
                                                                    index -
                                                                        entryModel!
                                                                            .popular!
                                                                            .length)
                                                                    .name,
                                                            style: TextStyle(
                                                                fontSize: 12 *
                                                                    MediaQuery
                                                                        .of(
                                                                        context)
                                                                        .textScaleFactor,
                                                                color: Theme
                                                                    .of(
                                                                    context)
                                                                    .textTheme
                                                                    .bodyText1!
                                                                    .color)),
                                                      ]),
                                                    ),
                                                  )),
                                              Expanded(
                                                child: Center(
                                                    child: entryModel!
                                                        .recommendations!
                                                        .elementAt(index -
                                                        entryModel!
                                                            .popular!
                                                            .length)
                                                        .liked
                                                        ? IconButton(
                                                        splashRadius: 21,
                                                        onPressed: () {
                                                          Provider.of<
                                                              ItineraryEntryModel>(
                                                              context,
                                                              listen:
                                                              false)
                                                              .likeLocation(
                                                              entryModel!
                                                                  .popular!
                                                                  .elementAt(
                                                                  index -
                                                                      entryModel!
                                                                          .popular!
                                                                          .length)
                                                                  .id,
                                                              context);
                                                        },
                                                        iconSize: 20,
                                                        color: Color(
                                                            0xff931621),
                                                        icon: Icon(Icons
                                                            .favorite_rounded))
                                                        : IconButton(
                                                        onPressed: () {
                                                          Provider.of<
                                                              ItineraryEntryModel>(
                                                              context,
                                                              listen:
                                                              false)
                                                              .likeLocation(
                                                              entryModel!
                                                                  .popular!
                                                                  .elementAt(
                                                                  index -
                                                                      entryModel!
                                                                          .popular!
                                                                          .length)
                                                                  .id,
                                                              context);
                                                        },
                                                        splashRadius: 21,
                                                        iconSize: 20,
                                                        color: Theme
                                                            .of(
                                                            context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color,
                                                        icon: Icon(Icons
                                                            .favorite_outline_rounded))),
                                              ),
                                              Spacer(),
                                            ]))))));
                  }
                }),
            Positioned(
                top: 50,
                left: 0,
                child: Container(
                    decoration: BoxDecoration(
                        color: Theme
                            .of(context)
                            .accentColor,
                        shape: BoxShape.circle),
                    child: IconButton(
                      color: Theme
                          .of(context)
                          .primaryColorDark,
                      icon: Icon(Icons.arrow_left_rounded),
                      onPressed: () {
                        _controller
                            .jumpTo(_controller.offset - getSize(context));
                      },
                    ))),
            Positioned(
                top: 50,
                right: 0,
                child: Container(
                    decoration: BoxDecoration(
                        color: Theme
                            .of(context)
                            .accentColor,
                        shape: BoxShape.circle),
                    child: IconButton(
                      color: Theme
                          .of(context)
                          .primaryColorDark,
                      icon: Icon(Icons.arrow_right_rounded),
                      onPressed: () {
                        _controller
                            .jumpTo(_controller.offset + getSize(context));
                      },
                    ))),
          ]));
    } else {
      return Center(
          child: Text(
              "What an adventurer! You're the first one to ever go here!",
              textAlign: TextAlign.center,
              style: TextStyle(
                  fontSize: 30 * MediaQuery
                      .of(context)
                      .textScaleFactor,
                  color: Theme
                      .of(context)
                      .textTheme
                      .bodyText1!
                      .color)));
    }
  }
}

class AlertBoxRecommendation extends StatefulWidget {
  late final Itinerary? currentItinerary;
  final ItineraryEntryModel itineraryEntryModel;
  late final RecommendedLocation? recommendedLocation;

  AlertBoxRecommendation(this.currentItinerary, this.itineraryEntryModel,
      this.recommendedLocation);

  @override
  _AlertBoxRecommended createState() =>
      _AlertBoxRecommended(currentItinerary!, recommendedLocation);
}

class _AlertBoxRecommended extends State<AlertBoxRecommendation> {
  Itinerary? currentItinerary;
  RecommendedLocation? recommendedLocation;
  DateTime? date;
  TimeOfDay? time;
  String? location;
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

  double getSize(context) {
    if (MediaQuery
        .of(context)
        .size
        .height >
        MediaQuery
            .of(context)
            .size
            .width) {
      return MediaQuery
          .of(context)
          .size
          .height * 0.65;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.70;
    }
  }

  _AlertBoxRecommended(this.currentItinerary, this.recommendedLocation) {
    locationController.text = recommendedLocation!.name;
  }

  String userID = UserApi.getInstance().getUserProfile()!.userID;

  final titleController = TextEditingController();
  final descriptionController = TextEditingController();
  final locationController = TextEditingController();
  final dateController = TextEditingController();
  final timeController = TextEditingController();

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
      firstDate: new DateTime(DateTime
          .now()
          .year - 5),
      lastDate: new DateTime(DateTime
          .now()
          .year + 5),
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

  String getTextDate(DateTime theDate) {
    String x = theDate.day.toString() +
        " " +
        months.elementAt(theDate.month - 1) +
        " " +
        theDate.year.toString();
    return x;
  }

  String getTextTime(TimeOfDay theTime) {
    final hours = time!.hour.toString().padLeft(2, '0');
    final minutes = time!.minute.toString().padLeft(2, '0');
    return '$hours:$minutes';
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme
            .of(context)
            .primaryColorDark,
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
                        color: Theme
                            .of(context)
                            .primaryColorDark),
                    backgroundColor: Theme
                        .of(context)
                        .accentColor,
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
                          color: Theme
                              .of(context)
                              .textTheme
                              .bodyText1!
                              .color,
                          fontSize: 25 * MediaQuery
                              .of(context)
                              .textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    Spacer(),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: titleController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Title')),
                    ),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLines: 3,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: descriptionController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Description')),
                    ),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.02),
                    Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                            MediaQuery
                                .of(context)
                                .size
                                .width * 0.02),
                        child: TextField(
                          maxLines: 1,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: dateController,
                          decoration: InputDecoration(
                              prefixIcon: Icon(Icons.calendar_today_rounded),
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Pick a date'),
                          onTap: () async {
                            DateTime? picked = await showDate();
                            if (picked != null) {
                              setState(() {
                                date = picked;
                                dateController.text = getTextDate(picked);
                              });
                            }
                          },
                        )),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.02),
                    Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                            MediaQuery
                                .of(context)
                                .size
                                .width * 0.02),
                        child: TextField(
                          maxLines: 1,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: timeController,
                          decoration: InputDecoration(
                              prefixIcon: Icon(Icons.access_time_rounded),
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Pick a time'),
                          onTap: () async {
                            TimeOfDay? picked = await showTime();
                            if (picked != null) {
                              setState(() {
                                time = picked;
                                timeController.text = getTextTime(picked);
                              });
                            }
                          },
                        )),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.02),
                    Container(
                        width: 300,
                        padding: EdgeInsets.symmetric(
                            horizontal:
                            MediaQuery
                                .of(context)
                                .size
                                .width * 0.02),
                        child: TextField(
                          maxLines: 1,
                          style: TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color),
                          controller: locationController,
                          decoration: InputDecoration(
                              prefixIcon: Icon(Icons.location_on_rounded),
                              hintStyle: TextStyle(
                                  color: Theme
                                      .of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme
                                  .of(context)
                                  .primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme
                                          .of(context)
                                          .accentColor)),
                              hintText: 'Find a location'),
                          onTap: () async {
                            showDialog(
                                context: context,
                                builder: (BuildContext context) {
                                  return AlertDialog(
                                      backgroundColor:
                                      Theme
                                          .of(context)
                                          .primaryColorDark,
                                      title: Stack(
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
                                                      color: Theme
                                                          .of(context)
                                                          .primaryColorDark),
                                                  backgroundColor:
                                                  Theme
                                                      .of(context)
                                                      .accentColor,
                                                ),
                                              ),
                                            ),
                                            Column(
                                                mainAxisSize: MainAxisSize.min,
                                                children: [
                                                  Text("Find Location",
                                                      textAlign:
                                                      TextAlign.center,
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
                                                        fontWeight:
                                                        FontWeight.bold,
                                                      )),
                                                  SizedBox(
                                                      height:
                                                      MediaQuery
                                                          .of(context)
                                                          .size
                                                          .height *
                                                          0.01,
                                                      width: 300),
                                                  TextField(
                                                    style: TextStyle(
                                                        color: Theme
                                                            .of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color),
                                                    decoration: InputDecoration(
                                                        hintStyle: TextStyle(
                                                            color: Theme
                                                                .of(context)
                                                                .textTheme
                                                                .bodyText2!
                                                                .color),
                                                        filled: true,
                                                        enabledBorder:
                                                        InputBorder.none,
                                                        errorBorder:
                                                        InputBorder.none,
                                                        disabledBorder:
                                                        InputBorder.none,
                                                        fillColor:
                                                        Theme
                                                            .of(context)
                                                            .primaryColorLight,
                                                        focusedBorder: OutlineInputBorder(
                                                            borderSide: new BorderSide(
                                                                color: Theme
                                                                    .of(
                                                                    context)
                                                                    .accentColor)),
                                                        hintText:
                                                        'Search for a place of interest'),
                                                    onChanged: (value) {
                                                      _debouncer.run(() {
                                                        Provider.of<
                                                            LocationModel>(
                                                            context,
                                                            listen: false)
                                                            .fetchAllSuggestions(
                                                            value);
                                                      });
                                                    },
                                                  ),
                                                ])
                                          ]),
                                      content: Container(
                                          width: 300,
                                          child: Consumer<LocationModel>(
                                              builder: (context, locationModel,
                                                  child) {
                                                return locationModel
                                                    .suggestions !=
                                                    null &&
                                                    locationModel.suggestions!
                                                        .length >
                                                        0
                                                    ? ListView.builder(
                                                    shrinkWrap: true,
                                                    itemCount: locationModel
                                                        .suggestions!.length,
                                                    itemBuilder:
                                                        (context, index) {
                                                      return InkWell(
                                                          hoverColor: Theme
                                                              .of(
                                                              context)
                                                              .primaryColorLight,
                                                          onTap: () {
                                                            setState(() {
                                                              this.location =
                                                                  locationModel
                                                                      .suggestions!
                                                                      .elementAt(
                                                                      index)
                                                                      .description;
                                                              locationController
                                                                  .text =
                                                              this.location!;
                                                            });
                                                            Navigator.of(
                                                                context)
                                                                .pop();
                                                          },
                                                          child: Padding(
                                                              padding: EdgeInsets
                                                                  .symmetric(
                                                                  vertical: MediaQuery
                                                                      .of(
                                                                      context)
                                                                      .size
                                                                      .height *
                                                                      0.01,
                                                                  horizontal: MediaQuery
                                                                      .of(
                                                                      context)
                                                                      .size
                                                                      .width *
                                                                      0.01),
                                                              child: ListTile(
                                                                  title: Text(
                                                                    locationModel
                                                                        .suggestions!
                                                                        .elementAt(
                                                                        index)
                                                                        .description,
                                                                    style: TextStyle(
                                                                        fontSize: 16 *
                                                                            MediaQuery
                                                                                .of(
                                                                                context)
                                                                                .textScaleFactor,
                                                                        fontWeight:
                                                                        FontWeight
                                                                            .bold,
                                                                        color: Theme
                                                                            .of(
                                                                            context)
                                                                            .textTheme
                                                                            .bodyText1!
                                                                            .color),
                                                                  ))));
                                                    })
                                                    : Container(height: 10);
                                              })));
                                });
                          },
                        )),
                    Spacer(),
                    Padding(
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            primary: Theme
                                .of(context)
                                .accentColor),
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () async {
                          await widget.itineraryEntryModel.addItineraryEntry(
                              currentItinerary!,
                              currentItinerary!.id,
                              titleController.text,
                              descriptionController.text,
                              location!,
                              date!,
                              time!,
                              userID);
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
}
