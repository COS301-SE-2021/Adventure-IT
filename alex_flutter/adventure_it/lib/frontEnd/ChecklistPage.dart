
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/checklist.dart';
import 'package:adventure_it/api/checklistAPI.dart';
import 'package:adventure_it/api/createChecklist.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/frontEnd/ChecklistsList.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';
class ChecklistPage extends StatelessWidget {
  Checklist? currentChecklist;
  Adventure? currentAdventure;

  ChecklistPage(Checklist? c,Adventure? a) {
    this.currentChecklist = c;
    this.currentAdventure=a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text(currentChecklist!.title,
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            actions:[IconButton(
                onPressed: () {
                  {


                  }
                },
                icon: const Icon(Icons.edit),
                color: Theme.of(context).textTheme.bodyText1!.color),],
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height / 60),
              Container(
                height: MediaQuery.of(context).size.height * 0.75,
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
                                    builder: (context) => Checklists(
                                        currentAdventure)));
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
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertBox(currentChecklist!);
                                  });
                            }
                          },
                          icon: const Icon(Icons.add),
                          color: Theme.of(context).primaryColorDark)),
                ),
                Expanded(
                  flex: 1,
                  child: Container(
                  ), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class AlertBox extends StatelessWidget {
  Checklist? currentChecklist;

  AlertBox(Checklist c) {
    this.currentChecklist = c;
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  //controllers for the form fields
  String userID = "1660bd85-1c13-42c0-955c-63b1eda4e90b";
  String advID = "aa722689-6dbb-474a-a50b-55261570027e";

  final ChecklistApi api = new ChecklistApi();
  Future<CreateChecklist>? _futureChecklist;
  final nameController = TextEditingController();
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
                      Text("Add Item To Checklist",
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

                      Spacer(),
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
                          onPressed: () {
                            /*setState(() {
                              _futureChecklist = api.createChecklist(nameController.text, descriptionController.text, userID, advID);
                            });*/
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