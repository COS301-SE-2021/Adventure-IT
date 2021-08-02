import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/itinerary.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'ItinerariesList.dart';

class ItineraryPage extends StatelessWidget {
  Itinerary? currentItinerary;
  Adventure? currentAdventure;

  ItineraryPage(Itinerary? i, Adventure? a) {
    this.currentItinerary = i;
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme
            .of(context)
            .scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text(currentItinerary!.title,
                    style: new TextStyle(
                        color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color))),
            actions: [
              IconButton(
                  onPressed: () {
                    {


                    }
                  },
                  icon: const Icon(Icons.edit),
                  color: Theme
                      .of(context)
                      .textTheme
                      .bodyText1!
                      .color),
            ],
            backgroundColor: Theme
                .of(context)
                .primaryColorDark),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery
                  .of(context)
                  .size
                  .height / 60),
              Container(
                height: MediaQuery
                    .of(context)
                    .size
                    .height * 0.75,
              ),
              Spacer(),
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
                                        Itineraries(
                                            currentAdventure)));
                          },
                          icon: const Icon(Icons.arrow_back_ios_new_rounded),
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
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertBox(currentItinerary!);
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
                  child: Container(
                  ), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery
                  .of(context)
                  .size
                  .height / 60),
            ]));
  }
}

class AlertBox extends StatefulWidget {
  Itinerary? currentItinerary;

  AlertBox(Itinerary i) {
    this.currentItinerary = i;
  }

  @override
  _AlertBox createState() => _AlertBox(currentItinerary!);
}

class _AlertBox extends State <AlertBox> {
  Itinerary? currentItinerary;
  DateTime? date = null;
  TimeOfDay? time=null;

  Map<int, Color> color =
  {
    50:Color.fromRGBO(32, 34, 45, .1),
    100:Color.fromRGBO(32, 34, 45, .2),
    200:Color.fromRGBO(32, 34, 45, .3),
    300:Color.fromRGBO(32, 34, 45, .4),
    400:Color.fromRGBO(32, 34, 45, .5),
    500:Color.fromRGBO(32, 34, 45, .6),
    600:Color.fromRGBO(32, 34, 45, .7),
    700:Color.fromRGBO(32, 34, 45, .8),
    800:Color.fromRGBO(32, 34, 45, .9),
    900:Color.fromRGBO(32, 34, 45, 1),
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



  _AlertBox(Itinerary i) {
    this.currentItinerary = i;
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
                      width: MediaQuery
                          .of(context)
                          .size
                          .width * 0.5,
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
                    ), SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.01),
                    Container(
                      width: MediaQuery
                          .of(context)
                          .size
                          .width * 0.5,
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
                    ), SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.03),
                    MaterialButton(
                        color: Theme
                            .of(context)
                            .accentColor,
                        onPressed: () async {
                          DateTime? picked = await showDate();
                          if (picked != null) {
                            setState(() => date = picked);
                          }
                        },
                        child: Text(
                            getTextDate(), style: new TextStyle(color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color, fontSize: 15 * MediaQuery
                            .of(context)
                            .textScaleFactor))
                    ),
                    SizedBox(height: MediaQuery
                        .of(context)
                        .size
                        .height * 0.03),
                    MaterialButton(
                        color: Theme
                            .of(context)
                            .accentColor,
                        onPressed: () async {
                          TimeOfDay? picked = await showTime();
                          if (picked != null) {
                            setState(() => time = picked);
                          }
                        },
                        child: Text(
                            getTextTime(), style: new TextStyle(color: Theme
                            .of(context)
                            .textTheme
                            .bodyText1!
                            .color, fontSize: 15 * MediaQuery
                            .of(context)
                            .textScaleFactor))
                    ),
                    Spacer(),
                    Padding(
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery
                              .of(context)
                              .size
                              .width * 0.02),
                      child: RaisedButton(
                        color: Theme
                            .of(context)
                            .accentColor,
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme
                                    .of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () {
                          Navigator.of(context).pop();
                        },
                      ),
                    ),
                    Spacer(),
                  ],
                ),
              )
            ],
          ),
        )
    );
  }

  Future<DateTime?> showDate()
  {
    return showDatePicker(
      context: context,
      builder: (BuildContext context, Widget ?child) {
        return Theme(
          data: ThemeData(
              primarySwatch: Colors.grey,
              splashColor: Color(0xff20222D),
              textTheme: TextTheme(
                subtitle1: TextStyle(color:Color(0xffA7AAB9)),
                button: TextStyle(color: Color(0xffA7AAB9), fontWeight: FontWeight.bold),
              ),
              textButtonTheme: TextButtonThemeData(
                  style: TextButton.styleFrom(
                    primary:  Color(0xffA7AAB9), // button text color
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


        ), child: child!,);
      },


      initialDate:
      date ?? DateTime.now(),
      firstDate: new DateTime(DateTime
          .now()
          .year - 5),
      lastDate: new DateTime(DateTime
          .now()
          .year + 5),
    );
  }

  Future <TimeOfDay?> showTime()
  {
    return showTimePicker(
      context: context,
      builder: (BuildContext context, Widget ?child) {
        return Theme(
          data: ThemeData(
              primarySwatch: MaterialColor(0xFF20222D,color),
              splashColor: Color(0xff20222D),
              timePickerTheme: TimePickerThemeData(
                  helpTextStyle: TextStyle(color:Color(0xffA7AAB9),)

              ),
              textTheme: TextTheme(
                  subtitle1: TextStyle(color:Color(0xffA7AAB9)),
                  bodyText2: TextStyle(color:Color(0xffA7AAB9)),
                  bodyText1: TextStyle(color:Color(0xffA7AAB9)),
                  subtitle2: TextStyle(color:Color(0xffA7AAB9)),
          button: TextStyle(color: Color(0xffA7AAB9), fontWeight: FontWeight.bold),
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


        ), child: child!,);
      },

      initialTime: TimeOfDay.now(),
    );
  }

  String getTextDate() {
    if (date == null) {
      return "Select Date";
    }
    else {
      String x = date!.day.toString() + " " +
          months.elementAt(date!.month - 1) + " " + date!.year.toString();
      return x;
    }
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
          .height * 0.49;
    } else {
      return MediaQuery
          .of(context)
          .size
          .height * 0.6;
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
}