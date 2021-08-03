
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/gestures.dart';


import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';
import 'Register.dart';

//Shows page used to create a new adventure
class CreateAdventureCaller extends StatefulWidget {


  @override
  CreateAdventure createState() => CreateAdventure();
}

class CreateAdventure extends State<CreateAdventureCaller> {
  DateTimeRange? dates;
  final initialDateRange = DateTimeRange(
    start: DateTime.now(),
    end: DateTime.now().add(Duration(hours: 24 * 7)),
  );

  List<String> months=["January","February", "March", "April","May", "June", "July", "August", "September","October", "November", "December"];
  String getText()
  {
    if (dates==null)
    {
      return "Select Dates";
    }
    else if (dates!.start==dates!.end)
    {
      String x=dates!.start.day.toString()+" "+months.elementAt(dates!.start.month-1)+" "+dates!.start.year.toString();
      return x;
    }
    else
    {
      String x=dates!.start.day.toString()+" "+months.elementAt(dates!.start.month-1)+" "+dates!.start.year.toString()+" to "+dates!.end.day.toString()+" "+months.elementAt(dates!.end.month-1)+" "+dates!.end.year.toString();
      return x;
    }
  }
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

  //controllers for the form fields
  String ownerID = "1660bd85-1c13-42c0-955c-63b1eda4e90b";

  final AdventureApi api = new AdventureApi();
  Future<CreateAdventure>? _futureAdventure;
  final nameController = TextEditingController();
  final descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {

    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(title: Center(child: Text("Create Adventure",
          style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)
        )), backgroundColor: Theme.of(context).primaryColorDark),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height*0.05),
              Container(
                width: MediaQuery.of(context).size.width/2,
                height: MediaQuery.of(context).size.height/4,
                child: CircleAvatar(
                  radius: 90,
                  backgroundImage: ExactAssetImage('assets/adventure.PNG'),
                ),
                decoration: new BoxDecoration(
                  shape: BoxShape.circle,
                  border: new Border.all(
                    color: Theme.of(context).accentColor,
                    width: 3.0,
                  ),
                ),
              ),
              SizedBox(height: MediaQuery.of(context).size.height*0.05),
              SizedBox(
                width: 350,
                child: TextField(
                    style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color, fontSize: 15*MediaQuery.of(context).textScaleFactor),
                    controller: nameController,
                    decoration: InputDecoration(
                        hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color,fontSize: 15*MediaQuery.of(context).textScaleFactor),
                        filled: true,
                        enabledBorder: InputBorder.none,
                        errorBorder: InputBorder.none,
                        disabledBorder: InputBorder.none,
                        fillColor: Theme.of(context).primaryColorLight,
                        focusedBorder: OutlineInputBorder( borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Adventure Name')),
              ),
              SizedBox(height: MediaQuery.of(context).size.height*0.01),
              SizedBox(
                width: 350,
                child: TextField(
                    maxLength: 255,
                    maxLengthEnforced: true,
                    maxLines: 4,
                    style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color,fontSize: 15*MediaQuery.of(context).textScaleFactor),
                    controller: descriptionController,
                    decoration: InputDecoration(
                        hintStyle: TextStyle(color: Theme.of(context).textTheme.bodyText2!.color,fontSize: 15*MediaQuery.of(context).textScaleFactor),
                        filled: true,
                        enabledBorder: InputBorder.none,
                        errorBorder: InputBorder.none,
                        disabledBorder: InputBorder.none,
                        fillColor: Theme.of(context).primaryColorLight,
                        focusedBorder: OutlineInputBorder(borderSide: new BorderSide(color: Theme.of(context).accentColor)), hintText: 'Adventure Description')),
              ), SizedBox(height: MediaQuery.of(context).size.height*0.02),


              SizedBox(height: MediaQuery.of(context).size.height*0.02),
              MaterialButton(
                  color: Theme.of(context).accentColor,
                  onPressed: () async {
                    DateTimeRange? picked = await showDateRangePicker(
                        context: context,
                        builder: (BuildContext context, Widget ?child) {
                          return Theme(
                            data: ThemeData(
                                primarySwatch: MaterialColor(0xFF20222D,color),
                                splashColor: Color(0xff20222D),
                                scaffoldBackgroundColor: Color(0xff484D64),
                                canvasColor: Color(0xff484D64),
                                textTheme: TextTheme(
                                  subtitle1: TextStyle(color:Color(0xffA7AAB9)),
                                    bodyText2: TextStyle(color:Color(0xffA7AAB9)),
                                    bodyText1: TextStyle(color:Color(0xffA7AAB9))
                                  subtitle2: TextStyle(color:Color(0xffA7AAB9)),
                                  button: TextStyle(color: Color(0xffA7AAB9), fontWeight: FontWeight.bold),
                                ),
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
                            backgroundColor:Color(0xff484D64),
                            highlightColor: Color(0xff484D64)


                          ) child: child!,);
                        },
                        initialDateRange: dates ?? initialDateRange
                        firstDate: new DateTime(DateTime.now().year - 5),
                        lastDate: new DateTime(DateTime.now().year + 5)
                    );
                    if (picked!=null) {
                      print(picked);
                      setState(()=>dates=picked);

                    }
                  },//
                  child: Text(getText(),style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color,fontSize: 15*MediaQuery.of(context).textScaleFactor))
              ),
              SizedBox(height: MediaQuery.of(context).size.height*0.05),
              Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: <Widget>[
                    Spacer( flex: 2),
              Expanded(
                flex: 3,
             child: ElevatedButton(
                  child: Text("Add",
                      style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                  style: ElevatedButton.styleFrom(
                    primary: Theme.of(context).accentColor,
                    padding: EdgeInsets.symmetric(horizontal: 3, vertical: 20),
                  ),
                  onPressed: () {
                    setState(() {
                      _futureAdventure = api.createAdventure(nameController.text, ownerID, dates!.start.toString(), dates!.end.toString(), descriptionController.text) as Future<CreateAdventure>?;
                    });
                    Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => HomepageStartupCaller()),
                    );
                  })),
                Spacer( flex: 2),
                Expanded(
                  flex: 3,
                child: ElevatedButton(
                    child: Text("Cancel",
                        style: new TextStyle(color: Theme.of(context).textTheme.bodyText1!.color)),
                    style: ElevatedButton.styleFrom(
                      primary: Theme.of(context).accentColor,
                      padding: EdgeInsets.symmetric(horizontal: 3, vertical: 20),
                    ),
                    onPressed: () {
                      Navigator.pushReplacement(
                        context,
                        MaterialPageRoute(
                            builder: (context) => HomepageStartupCaller()),
                      );
                    })),
                    Spacer( flex: 2),])

    ])));

  }
}
