import 'package:adventure_it/Providers/checklist_model.dart';
import 'package:adventure_it/Providers/checklist_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/checklistAPI.dart';
import 'package:adventure_it/api/createChecklist.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/frontEnd/ChecklistsTrash.dart';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'ChecklistPage.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';
import 'AdventurePage.dart';

import '../api/budget.dart';
import 'Navbar.dart';

class Checklists extends StatelessWidget {
  Adventure? adventure;

  Checklists(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => ChecklistModel(adventure!),
        builder: (context, widget) => Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Checklists",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body:
           Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
              SizedBox(height: MediaQuery.of(context).size.height / 60),
              Container(
                  height: MediaQuery.of(context).size.height * 0.75,
                  child: ChecklistList(adventure)),
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
                                        AdventurePage(adventure)));
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
                              var provider = Provider.of<ChecklistModel>(context, listen: false);
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertBox(adventure!, provider);
                                  });
                            }
                          },
                          icon: const Icon(Icons.add),
                          color: Theme.of(context).primaryColorDark),
                  ),
                ),
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
                                        ChecklistsTrash(adventure)));
                          },
                          icon: const Icon(Icons.delete_outline_rounded),
                          color: Theme.of(context)
                              .primaryColorDark)), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]),),);
  }
}

class ChecklistList extends StatelessWidget {
  Adventure? a;

  ChecklistList(Adventure? adventure) {
    this.a = adventure;
  }

  @override
  Widget build(BuildContext context) {
    return
            Consumer<ChecklistModel>(builder: (context, checklistModel, child) {
              if (checklistModel.checklists != null)
                print(checklistModel.checklists!.length);

          if (checklistModel.checklists == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (checklistModel.checklists!.length > 0) {
            return ListView.builder(
                    itemCount:                      checklistModel.checklists!.length,
                      itemBuilder: (context, index) => Dismissible(
                          background: Container(
                            // color: Theme.of(context).primaryColor,
                            //   margin: const EdgeInsets.all(5),
                            padding: EdgeInsets.all(
                                MediaQuery.of(context).size.height / 60),
                            child: Row(
                              children: [
                                new Spacer(),
                                Icon(Icons.delete,
                                    color: Theme.of(context).accentColor,
                                    size: 35 *
                                        MediaQuery.of(context).textScaleFactor),
                              ],
                            ),
                          ),
                          direction: DismissDirection.endToStart,
                          key: Key(
                              checklistModel.checklists!.elementAt(index).id),
                          child: Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                  hoverColor:
                                      Theme.of(context).primaryColorLight,
                                  onTap: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) => ChecklistPage(
                                                checklistModel.checklists!
                                                    .elementAt(index),a)));
                                  },
                                  child: Container(
                                    child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 4,
                                          child: ListTile(
                                            title: Text(
                                                checklistModel
                                                    .checklists!
                                                    .elementAt(index)
                                                    .title,
                                                style: TextStyle(
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                            // subtitle:Text(adventures.elementAt(index).description),
                                            subtitle: Text(
                                                checklistModel.checklists!
                                                    .elementAt(index)
                                                    .description,
                                                style: TextStyle(
                                                    fontSize: 15 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ))),
                          onDismissed: (direction) {
                            Provider.of<ChecklistModel>(context, listen: false)
                                .softDeleteChecklist(checklistModel.checklists!
                                    .elementAt(index));
                          }));
          } else {
            return Center(
                child: Text("Let's get you organised!",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        });
  }
}

class AlertBox extends StatefulWidget {
  Adventure? adventure;
  final ChecklistModel checklistModel;

  AlertBox(this.adventure, this.checklistModel);

  @override
  _AlertBox createState() => _AlertBox(adventure!);

}
class _AlertBox extends State <AlertBox> {
  Adventure? adventure;

  _AlertBox(this.adventure);

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  //controllers for the form fields
  String userID = UserApi.getInstance().getUserProfile()!.userID;

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
                    Text("Create Checklist",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color,
                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.07),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                          controller: nameController,
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
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLengthEnforced: true,
                          maxLines: 4,
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
                              fillColor: Theme.of(context).primaryColorLight,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Description')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.05),
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
                          await widget.checklistModel.addChecklist(adventure!, nameController.text, descriptionController.text, userID, adventure!.adventureId);
                          Navigator.pop(context);
                        },
                      ),
                    )
                  ],
                ),
              )
            ],
          ),
        ));
  }
}
