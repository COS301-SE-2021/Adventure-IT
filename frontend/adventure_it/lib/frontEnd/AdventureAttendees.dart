import 'package:adventure_it/api/currentLocation.dart';
import 'package:adventure_it/api/locationAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:adventure_it/Providers/adventure_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:provider/provider.dart';
import '../constants.dart';
import 'AdventurePage.dart';
import 'Navbar.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:location/location.dart';

//Shows list of adventures
//TODO: upper camel case error
class AdventureAttendees extends StatelessWidget {
  Adventure? currentAdventure;
  List<UserProfile>? attendees;
  Location location = Location();
  LocationData? currentLocation;
  CurrentLocation? current;

  AdventureAttendees(Adventure a) {
    this.currentAdventure = a;

    bool serviceEnabled;
    PermissionStatus permissionGranted;

    location.serviceEnabled().then((value) {
      serviceEnabled = value;
      if (!serviceEnabled) {
        location.requestService().then((value) => serviceEnabled = value);
      }
      location.hasPermission().then((value) {
        permissionGranted = value;
        if (permissionGranted == PermissionStatus.denied) {
          location
              .requestPermission()
              .then((value) => permissionGranted = value);
        }
        if (permissionGranted == PermissionStatus.granted && serviceEnabled) {
          location.getLocation().then((value) {
            currentLocation = value;
            LocationApi.setCurrentLocation(currentLocation!);
          });
        }
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider.value(
        value: AdventureAttendeesModel(this.currentAdventure!),
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
              if (attendeeModel.attendees == null ||
                  attendeeModel.locations == null) {
                return Center(
                    child: CircularProgressIndicator(
                        valueColor: new AlwaysStoppedAnimation<Color>(
                            Theme.of(context).accentColor)));
              } else {
                return Carousel(this.currentAdventure!, attendeeModel);
              }
            })));
  }
}

class Carousel extends StatefulWidget {
  Adventure? currentAdventure;
  AdventureAttendeesModel? model;

  Carousel(Adventure a, AdventureAttendeesModel m) {
    this.currentAdventure = a;
    this.model = m;
  }

  @override
  _Carousel createState() => _Carousel(this.currentAdventure!, this.model!);
}

class _Carousel extends State<Carousel> {
  CarouselController carouselController = CarouselController();
  Adventure? currentAdventure;
  AdventureAttendeesModel? attendeeModel;
  int i = 0;
  final Set<Marker> _markers = {};
  CameraPosition? _initialPosition;

  _Carousel(Adventure a, AdventureAttendeesModel m) {
    this.currentAdventure = a;
    this.attendeeModel = m;
    this.i = (attendeeModel!.attendees!.length / 2).toInt();
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

  LatLng getTarget(AdventureAttendeesModel attendeeModel) {
    return LatLng(
        double.parse(attendeeModel.locations!
            .elementAt(attendeeModel.locations!.indexWhere((element) {
              return element.userID ==
                  attendeeModel.attendees!.elementAt(i).userID;
            }))
            .latitude),
        double.parse(attendeeModel.locations!
            .elementAt(attendeeModel.locations!.indexWhere((element) {
              return element.userID ==
                  attendeeModel.attendees!.elementAt(i).userID;
            }))
            .longitude));
  }

  GoogleMapController? controller;

  String getTime(String timestamp) {
    print(timestamp);
    DateTime date = DateTime.parse(timestamp);

    String x = date.hour.toString() + ":";

    if (date.minute < 10) {
      x = x + "0" + date.minute.toString();
    } else {
      x = x + date.minute.toString();
    }

    x = x +
        " " +
        date.day.toString() +
        " " +
        months.elementAt(date.month - 1) +
        " " +
        date.year.toString();
    return x;
  }

  @override
  Widget build(BuildContext context) {
    return Column(children: [
      SizedBox(
        height: MediaQuery.of(context).size.height * 0.01,
      ),
      Container(
          height: MediaQuery.of(context).size.height * 0.25,
          width: MediaQuery.of(context).size.width,
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
            itemBuilder: (BuildContext context, int index, int pageViewIndex) =>
                CachedNetworkImage(  useOldImageOnUrlChange: true,
                    imageUrl: userApi+"/user/viewPicture/"+attendeeModel!.attendees!.elementAt(index).profileID,
                    imageBuilder: (context, imageProvider) => Container(
                        width: 100,
                        height: 100,
                        decoration: new BoxDecoration(
                            border: Border.all(
                              color: Theme.of(context).accentColor,
                              width: 3,
                            ),
                            shape: BoxShape.circle,
                            image: DecorationImage(
                                fit: BoxFit.fill,
                                image: imageProvider
                            ))),

                    placeholder: (context, url) => Container(
                        width: 100,
                        height: 100,
                        decoration: new BoxDecoration(
                            border: Border.all(
                              color: Theme.of(context).accentColor,
                              width: 3,
                            ),
                            shape: BoxShape.circle,
                            image: DecorationImage(
                                fit: BoxFit.fitWidth,
                                image: AssetImage("pfp.png")
                            ))),

                    errorWidget: (context, url, error) => Container(
                        width: 100,
                        height: 100,
                        decoration: new BoxDecoration(
                            border: Border.all(
                              color: Theme.of(context).accentColor,
                              width: 3,
                            ),
                            shape: BoxShape.circle,
                            image: DecorationImage(
                                fit: BoxFit.fitWidth,
                                image: AssetImage("pfp.png")
                            )))),
          )),
      Row(children: [
        Spacer(),
        Expanded(
            child: IconButton(
                onPressed: () {
                  if (i - 1 < 0) {
                    setState(() {
                      i = attendeeModel!.attendees!.length - 1;
                      carouselController.previousPage();
                      _markers.clear();
                      _markers.add(Marker(
                          markerId: MarkerId(
                              attendeeModel!.attendees!.elementAt(i).username),
                          infoWindow: InfoWindow(
                              title: attendeeModel!.attendees!
                                  .elementAt(i)
                                  .username),
                          position: getTarget(attendeeModel!)));
                      _initialPosition = CameraPosition(
                          zoom: 15, target: getTarget(attendeeModel!));
                      controller!.animateCamera(
                          CameraUpdate.newCameraPosition(_initialPosition!));
                    });
                  } else {
                    setState(() {
                      i = i - 1;
                      carouselController.previousPage();
                      _markers.clear();
                      _markers.add(Marker(
                          markerId: MarkerId(
                              attendeeModel!.attendees!.elementAt(i).username),
                          infoWindow: InfoWindow(
                              title: attendeeModel!.attendees!
                                  .elementAt(i)
                                  .username),
                          position: getTarget(attendeeModel!)));
                      _initialPosition = CameraPosition(
                          zoom: 15, target: getTarget(attendeeModel!));
                      controller!.animateCamera(
                          CameraUpdate.newCameraPosition(_initialPosition!));
                    });
                  }
                },
                icon: const Icon(Icons.arrow_left_rounded, size: 40),
                color: Theme.of(context).textTheme.bodyText1!.color)),
        Spacer(),
        Expanded(
          flex: 10,
          child: ClipRRect(
            borderRadius: BorderRadius.all(Radius.circular(20.0)),
            child: Container(
                padding: EdgeInsets.all(10),
                color: Theme.of(context).primaryColorDark,
                child: Column(children: [
                  Text(attendeeModel!.attendees!.elementAt(i).username,
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color,
                        fontSize: 30,
                      )),
                  Text(
                      "Last Check In: " +
                          getTime(attendeeModel!.locations!
                              .elementAt(attendeeModel!.locations!
                                  .indexWhere((element) {
                                return element.userID ==
                                    attendeeModel!.attendees!
                                        .elementAt(i)
                                        .userID;
                              }))
                              .timestamp),
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color,
                        fontSize: 12,
                      )),
                  Text(
                      "Latitude(" +
                          attendeeModel!.locations!
                              .elementAt(attendeeModel!.locations!
                                  .indexWhere((element) {
                                return element.userID ==
                                    attendeeModel!.attendees!
                                        .elementAt(i)
                                        .userID;
                              }))
                              .latitude +
                          ") , Longitude (" +
                          attendeeModel!.locations!
                              .elementAt(attendeeModel!.locations!
                                  .indexWhere((element) {
                                return element.userID ==
                                    attendeeModel!.attendees!
                                        .elementAt(i)
                                        .userID;
                              }))
                              .longitude +
                          ")",
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color,
                        fontSize: 12,
                      ))
                ])),
          ),
        ),
        Spacer(),
        Expanded(
            child: IconButton(
                onPressed: () {
                  if (i + 1 == attendeeModel!.attendees!.length) {
                    setState(() {
                      i = 0;
                      carouselController.nextPage();
                      _markers.clear();
                      _markers.add(Marker(
                          markerId: MarkerId(
                              attendeeModel!.attendees!.elementAt(i).username),
                          infoWindow: InfoWindow(
                              title: attendeeModel!.attendees!
                                  .elementAt(i)
                                  .username),
                          position: getTarget(attendeeModel!)));
                      _initialPosition = CameraPosition(
                          zoom: 15, target: getTarget(attendeeModel!));
                      controller!.animateCamera(
                          CameraUpdate.newCameraPosition(_initialPosition!));
                    });
                  } else {
                    setState(() {
                      i = i + 1;
                      carouselController.nextPage();
                      _markers.clear();
                      _markers.add(Marker(
                          markerId: MarkerId(
                              attendeeModel!.attendees!.elementAt(i).username),
                          infoWindow: InfoWindow(
                              title: attendeeModel!.attendees!
                                  .elementAt(i)
                                  .username),
                          position: getTarget(attendeeModel!)));
                      _initialPosition = CameraPosition(
                          zoom: 15, target: getTarget(attendeeModel!));
                      controller!.animateCamera(
                          CameraUpdate.newCameraPosition(_initialPosition!));
                    });
                  }
                },
                icon: const Icon(Icons.arrow_right_rounded, size: 40),
                color: Theme.of(context).textTheme.bodyText1!.color)),
        Spacer(),
      ]),
      Expanded(
        child: ClipRRect(
            borderRadius: BorderRadius.all(Radius.circular(20.0)),
            child: Container(
                decoration: BoxDecoration(
                    border: Border.all(
                  color: Theme.of(context).accentColor,
                  width: 2,
                )),
                width: MediaQuery.of(context).size.width * 0.8,
                child: GoogleMap(
                    mapType: MapType.normal,
                    zoomGesturesEnabled: true,
                    scrollGesturesEnabled: true,
                    markers: _markers,
                    onMapCreated: (GoogleMapController controller) {
                      setState(() {
                        _markers.clear();
                        _markers.add(Marker(
                            markerId: MarkerId(attendeeModel!.attendees!
                                .elementAt(i)
                                .username),
                            infoWindow: InfoWindow(
                                title: attendeeModel!.attendees!
                                    .elementAt(i)
                                    .username),
                            position: getTarget(attendeeModel!)));
                        this.controller = controller;
                      });
                    },
                    initialCameraPosition: _initialPosition == null
                        ? CameraPosition(
                            zoom: 15, target: getTarget(attendeeModel!))
                        : _initialPosition!))),
      ),
      SizedBox(height: MediaQuery.of(context).size.height / 60),
      Row(
        children: [
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
                                  AdventurePage(this.currentAdventure)));
                    },
                    icon: const Icon(Icons.arrow_back_ios_new_rounded),
                    color: Theme.of(context).primaryColorDark)),
          ),
          Expanded(
            flex: 1,
            child: Container(),
          ),
          Expanded(flex: 1, child: Container()),
        ],
      ),
      SizedBox(height: MediaQuery.of(context).size.height / 60),
    ]);
  }
}
//new Tab(icon: new Image.asset(choice.icon.image), text: choice.title),
