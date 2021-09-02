import 'package:adventure_it/api/adventureAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:adventure_it/Providers/adventure_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';
import 'CreateAdventure.dart';
import 'Navbar.dart';

//Shows list of adventures
//TODO: upper camel case error
class AdventureAttendees extends StatelessWidget {

Adventure? currentAdventure;
List<UserProfile>? attendees;

AdventureAttendees(Adventure a) {
  this.currentAdventure = a;
}

@override
Widget build(BuildContext context) {
  return ChangeNotifierProvider.value(
      value: AdventureAttendeesModel(this.currentAdventure!),
      builder: (context, widget) =>
          Scaffold(
              drawer: NavDrawer(),
              backgroundColor: Theme
                  .of(context)
                  .scaffoldBackgroundColor,
              appBar: AppBar(
                  title: Center(
                      child: Text("Adventurers",
                          style: new TextStyle(
                              color:
                              Theme
                                  .of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color))),
                  backgroundColor: Theme
                      .of(context)
                      .primaryColorDark),
              body: Consumer<AdventureAttendeesModel>(
                  builder: (context, attendeeModel, child) {
                    if (attendeeModel.attendees == null) {
                      return Center(
                          child: CircularProgressIndicator(
                              valueColor: new AlwaysStoppedAnimation<Color>(
                                  Theme
                                      .of(context)
                                      .accentColor)));
                    }
                    else {
                      return Carousel(this.currentAdventure!, attendeeModel);
                    }
                  })));
}}

class Carousel extends StatefulWidget {
  Adventure? currentAdventure;
  AdventureAttendeesModel? model;

  Carousel(Adventure a, AdventureAttendeesModel m) {
    this.currentAdventure = a;
    this.model = m;
  }

  @override
  _Carousel createState() =>
      _Carousel(this.currentAdventure!, this.model!);
}

class _Carousel extends State <Carousel> {
  CarouselController carouselController = CarouselController();
  Adventure? currentAdventure;
  AdventureAttendeesModel? attendeeModel;
  int i = 0;

  _Carousel(Adventure a, AdventureAttendeesModel m) {
    this.currentAdventure = a;
    this.attendeeModel = m;
    this.i=(attendeeModel!.attendees!.length / 2).toInt();
  }

  @override
  Widget build(BuildContext context) {
    return Column(children: [
      SizedBox(
        height: MediaQuery
            .of(context)
            .size
            .height * 0.01,
      ),
      Container(
          height: MediaQuery
              .of(context)
              .size
              .height * 0.25,
          width: MediaQuery
              .of(context)
              .size
              .width,
          child: Expanded(
              flex: 1,
              child: CarouselSlider.builder(
                carouselController: carouselController,
                options: CarouselOptions(
                  autoPlay: false,
                  enlargeCenterPage: true,
                  viewportFraction: 0.2,
                  aspectRatio: 2.0,
                  initialPage: (attendeeModel!.attendees!.length / 2).toInt(),
                  enableInfiniteScroll: false,
                ),
                itemCount: attendeeModel!.attendees!.length,
                itemBuilder: (BuildContext context, int index,
                    int pageViewIndex) =>
                    Container(
                        height: MediaQuery
                            .of(context)
                            .size
                            .height *
                            0.15,
                        width: MediaQuery
                            .of(context)
                            .size
                            .height *
                            0.15,
                        decoration: BoxDecoration(
                            border: Border.all(
                              color: Theme
                                  .of(context)
                                  .accentColor,
                              width: 2,
                            ),
                            shape: BoxShape.circle,
                            image: DecorationImage(
                                image: AssetImage("logo.png"),
                                fit: BoxFit.cover))),
              ))),
      Row(children: [
        Expanded(child: Container()),
        Expanded(
            child: IconButton(
                onPressed: () {
                  if (i - 1 < 0) {
                    setState(() {
                      i = attendeeModel!.attendees!.length - 1;
                      carouselController.previousPage();
                    });

                  } else {
                    setState(() {
                      i = i - 1;
                      carouselController.previousPage();
                    });

                  }
                },
                icon:
                const Icon(Icons.arrow_left_rounded, size: 40),
                color:
                Theme
                    .of(context)
                    .textTheme
                    .bodyText1!
                    .color)),
        Expanded(
          flex: 10,
          child: Container(
              color: Theme
                  .of(context)
                  .primaryColorDark,
              child: Text(
                  attendeeModel!.attendees!.elementAt(i).username,
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    color: Theme
                        .of(context)
                        .textTheme
                        .bodyText1!
                        .color,
                    fontSize: 30,
                  ))),
        ),
        Expanded(
            child: IconButton(
                onPressed: () {

                  if (i + 1 == attendeeModel!.attendees!.length) {
                    setState(() {
                      i=0;
                      carouselController.nextPage();
                    });
                  } else {
                    setState(() {
                      i=i+1;
                      carouselController.nextPage();
                    });
                  }

                },
                icon:
                const Icon(Icons.arrow_right_rounded, size: 40),
                color:
                Theme
                    .of(context)
                    .textTheme
                    .bodyText1!
                    .color)),
        Expanded(child: Container()),
      ]),
      Expanded(child: Container()),
      SizedBox(height: MediaQuery
          .of(context)
          .size
          .height / 60),
      Row(
        children: [
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
                                  AdventurePage(
                                      this.currentAdventure)));
                    },
                    icon: const Icon(
                        Icons.arrow_back_ios_new_rounded),
                    color: Theme
                        .of(context)
                        .primaryColorDark)),
          ),
          Expanded(flex: 1, child: Container()),
          Expanded(flex: 1, child: Container()),
        ],
      ),
      SizedBox(height: MediaQuery
          .of(context)
          .size
          .height / 60),
    ]);
  }
}
//new Tab(icon: new Image.asset(choice.icon.image), text: choice.title),
