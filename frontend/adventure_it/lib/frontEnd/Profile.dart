import 'package:flutter/material.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'DocumentList.dart';
import 'Navbar.dart';
import 'package:adventure_it/api/userAPI.dart';

//User's profile page
class ProfileCaller extends StatefulWidget {
  @override
  Profile createState() => Profile();
}

class Profile extends State<ProfileCaller> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Profile",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            iconTheme: IconThemeData(
                color: Theme.of(context).textTheme.bodyText1!.color),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(children: [
          ProfileFutureBuilderCaller(),
          Container(
              padding: const EdgeInsets.only(left: 100.0, top: 0.0),
              child:
                  Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Center(
                          child: SizedBox(
                        width: MediaQuery.of(context).size.height * 0.2,
                      ))
                    ])
                //_buildList()
              ])),
        ]));
  }
}

class ProfileFutureBuilderCaller extends StatefulWidget {
  @override
  ProfileFutureBuilder createState() => ProfileFutureBuilder();
}

class ProfileFutureBuilder extends State<ProfileFutureBuilderCaller> {
  UserProfile? user;

  // final UserApi api = new UserApi();

  @override
  void initState() {
    super.initState();
    user = UserApi.getInstance().getUserProfile();
  }

  @override
  Widget build(BuildContext context) {
    if (user == null) {
      return Center(
          child: CircularProgressIndicator(
              valueColor: new AlwaysStoppedAnimation<Color>(
                  Theme.of(context).accentColor)));
    } else {
      return Container(
          margin: EdgeInsets.symmetric(
              vertical: MediaQuery.of(context).size.height * 0.01),
          width: MediaQuery.of(context).size.width * 0.9,
          height: MediaQuery.of(context).size.height * 0.55,
          child: Card(
              color: Theme.of(context).primaryColorDark,
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Center(
                        child: Column(children: <Widget>[
                      Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        height: MediaQuery.of(context).size.height * 0.2,
                        decoration: new BoxDecoration(
                          shape: BoxShape.circle,
                          image: DecorationImage(
                              image: ExactAssetImage('assets/logo.png'),
                              fit: BoxFit.contain),
                        ),
                      ),
                      Container(
                          child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                            SizedBox(
                                height:
                                    MediaQuery.of(context).size.height * 0.01),
                            //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                            Text(user!.firstname + " " + user!.lastname,
                                textAlign: TextAlign.center,
                                style: new TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color,
                                    fontSize:
                                        MediaQuery.of(context).size.height *
                                            0.04)),
                            SizedBox(
                                height:
                                    MediaQuery.of(context).size.height * 0.005),
                            Text(user!.username,
                                textAlign: TextAlign.center,
                                style: new TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color,
                                    fontSize:
                                        MediaQuery.of(context).size.height *
                                            0.04)),
                            SizedBox(
                                height:
                                    MediaQuery.of(context).size.height * 0.01),
                            Row(children: [
                              Expanded(
                                  child: Text(user!.email,
                                      textAlign: TextAlign.center,
                                      style: new TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color,
                                          fontSize: MediaQuery.of(context)
                                                  .size
                                                  .height *
                                              0.03))),
                              Expanded(
                                  child: Container(
                                      margin: EdgeInsets.symmetric(
                                          horizontal: MediaQuery.of(context)
                                                  .size
                                                  .width *
                                              0.1),
                                      child: MaterialButton(
                                          child: Text("My Documents",
                                              style: new TextStyle(
                                                  color: Theme.of(context)
                                                      .textTheme
                                                      .bodyText1!
                                                      .color)),
                                          color: Theme.of(context).accentColor,
                                          padding: EdgeInsets.symmetric(
                                              horizontal: MediaQuery.of(context)
                                                      .size
                                                      .width *
                                                  0.05,
                                              vertical: MediaQuery.of(context)
                                                      .size
                                                      .width *
                                                  0.01),
                                          onPressed: () {
                                            Navigator.pushReplacement(
                                              context,
                                              MaterialPageRoute(
                                                  builder: (context) =>
                                                      DocumentPage()),
                                            );
                                          }))),
                            ]),
                            SizedBox(
                                height:
                                    MediaQuery.of(context).size.height * 0.03),
                            Row(children: [
                              // Expanded(
                              //     child: Text(user.phoneNumber,
                              //         textAlign: TextAlign.center,
                              //         style: new TextStyle(
                              //             color: Theme.of(context).textTheme.bodyText1!.color,
                              //             fontSize: MediaQuery.of(context).size.height * 0.03))),
                              Expanded(
                                  child: Container(
                                      margin: EdgeInsets.symmetric(
                                          horizontal: MediaQuery.of(context)
                                                  .size
                                                  .width *
                                              0.1),
                                      child: MaterialButton(
                                          onPressed: () {
                                            {
                                              showDialog(
                                                  context: context,
                                                  builder:
                                                      (BuildContext context) {
                                                    return AlertBox(user!);
                                                  });
                                            }
                                          },
                                          color: Theme.of(context).accentColor,
                                          padding: EdgeInsets.symmetric(
                                              horizontal: MediaQuery.of(context)
                                                      .size
                                                      .width *
                                                  0.05,
                                              vertical: MediaQuery.of(context)
                                                      .size
                                                      .width *
                                                  0.01),
                                          child: Text("Edit Profile",
                                              textAlign: TextAlign.center,
                                              style: new TextStyle(
                                                  color: Theme.of(context)
                                                      .textTheme
                                                      .bodyText1!
                                                      .color))))),
                            ])
                          ]))
                    ]))
                  ])));
    }
  }
}

class AlertBox extends StatefulWidget {
  late final UserProfile? user;

  AlertBox(this.user);

  @override
  _AlertBox createState() => _AlertBox(user!);
}

class _AlertBox extends State<AlertBox> {
  UserProfile? user;

  _AlertBox(this.user);

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme.of(context).primaryColorDark,
        content: Container(
            height: getSize(context),
            child: Stack(clipBehavior: Clip.none, children: <Widget>[
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
                  ),
                ),
              ),
              Center(
                  child: Column(
                  // mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Text(
                        "Edit Profile: " + user!.username,
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color,
                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.05),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
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
                              hintText: 'Username')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
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
                              hintText: 'First name')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
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
                              hintText: 'Last name')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
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
                              hintText: 'Email')),
                    ),
                    Spacer(),
                    Padding(
                        padding: EdgeInsets.symmetric(
                            horizontal:
                                MediaQuery.of(context).size.width * 0.02),
                        child: ElevatedButton(
                            style: ElevatedButton.styleFrom(
                                primary: Theme.of(context).accentColor),
                            onPressed: () {
                              Navigator.of(context).pop(true);
                            },
                            child: Text("Edit",
                                style: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color))))
                  ]))
            ])));
  }
}
