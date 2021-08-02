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

  _AlertBox(Itinerary i) {
    this.currentItinerary = i;
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
                    Text("Add Item To Checklist",
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
                          DateTime? picked = await showDatePicker(
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
                                        primary: Color(0xff20222D), // button text color
                                      )),
                                  accentColor: Color(0xff6A7AC7),
                                  colorScheme: ColorScheme.light(
                                      primary: Color(0xff20222D),
                                      primaryVariant: Color(0xff20222D),
                                      secondaryVariant: Color(0xff20222D),
                                      onSecondary: Color(0xff20222D),
                                      onPrimary: Color(0xffA7AAB9),
                                      surface: Color(0xff20222D)
                                      onSurface: Color(0xffA7AAB9),
                                      secondary: Color(0xff6A7AC7)),
                                  dialogBackgroundColor: Color(0xff484D64),


                                ) child: child!,);
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
}