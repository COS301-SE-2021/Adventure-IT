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

  AdventureAttendees(Adventure a) {
    this.currentAdventure = a;
  }

  CarouselController buttonCarouselController = CarouselController();

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => AdventureAttendeesModel(currentAdventure!),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text("Adventurers",
                        style: new TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color))),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Consumer<AdventureAttendeesModel>(
                builder: (context, attendeeModel, child) {
              return Column(children: [
                SizedBox(
                  height: MediaQuery.of(context).size.height / 10,
                ),
                CarouselSlider(
                  items: attendeeModel.attendees!.map((i) {
                    return Container(
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        image: DecorationImage(
                          image: AssetImage("logo.png"),
                        )
                      )
                    );
                  }).toList(),
                  carouselController: buttonCarouselController,
                  options: CarouselOptions(
                    autoPlay: false,
                    enlargeCenterPage: true,
                    viewportFraction: 0.9,
                    aspectRatio: 2.0,
                    initialPage: 2,
                    enableInfiniteScroll: true,
                  ),
                ),
                SizedBox(
                  height: MediaQuery.of(context).size.height / 10,
                ),
              ]);
            })));
  }
}
//new Tab(icon: new Image.asset(choice.icon.image), text: choice.title),
