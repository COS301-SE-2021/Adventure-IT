import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:adventure_it/Providers/checklist_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/checklist.dart';
import 'package:adventure_it/api/checklistAPI.dart';

import 'ChecklistsList.dart';
import 'Navbar.dart';

//A single checklist with entries
class ChecklistPage extends StatelessWidget {
  late final Checklist? currentChecklist;
  late final Adventure? currentAdventure;

  ChecklistPage(Checklist? c, Adventure? a) {
    this.currentChecklist = c;
    this.currentAdventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => ChecklistEntryModel(currentChecklist!),
        builder: (context, widget) => Scaffold(
            drawer: NavDrawer(),
            backgroundColor: Theme.of(context).scaffoldBackgroundColor,
            appBar: AppBar(
                title: Center(
                    child: Text(currentChecklist!.title,
                        style: new TextStyle(
                            color:
                                Theme.of(context).textTheme.bodyText1!.color))),
                backgroundColor: Theme.of(context).primaryColorDark),
            body: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                  Container(
                      height: MediaQuery.of(context).size.height * 0.75,
                      child: GetChecklistEntries(currentChecklist!)),
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
                                            Checklists(currentAdventure)));
                              },
                              icon:
                                  const Icon(Icons.arrow_back_ios_new_rounded),
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
                                  var provider =
                                      Provider.of<ChecklistEntryModel>(context,
                                          listen: false);
                                  showDialog(
                                      context: context,
                                      builder: (BuildContext context) {
                                        return AlertBox(
                                            currentChecklist!, provider);
                                      });
                                }
                              },
                              icon: const Icon(Icons.add),
                              color: Theme.of(context).primaryColorDark)),
                    ),
                    Expanded(
                      flex: 1,
                      child: Container(), //Your widget here,
                    ),
                  ]),
                  SizedBox(height: MediaQuery.of(context).size.height / 60),
                ])));
  }
}

class AlertBox extends StatefulWidget {
  late final Checklist? currentChecklist;
  final ChecklistEntryModel checklistEntryModel;

  AlertBox(this.currentChecklist, this.checklistEntryModel);

  @override
  _AlertBox createState() => _AlertBox(currentChecklist!);
}

class _AlertBox extends State<AlertBox> {
  Checklist? currentChecklist;

  _AlertBox(this.currentChecklist);

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  final ChecklistApi api = new ChecklistApi();
  final descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme.of(context).primaryColorDark,
        content: Container(
          height: getSize(context),
          child: Stack(
            clipBehavior: Clip.none, children: <Widget>[
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
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            primary: Theme.of(context).accentColor),
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () async {
                          await widget.checklistEntryModel.addChecklistEntry(
                              currentChecklist!,
                              descriptionController.text,
                              currentChecklist!.id);
                          Navigator.pop(context);
                        },
                      ),
                    ),
                  ],
                ),
              )
            ],
          ),
        ));
  }
}

class GetChecklistEntries extends StatelessWidget {
  late final Checklist? checklist;
  final editController = TextEditingController();

  GetChecklistEntries(Checklist c) {
    this.checklist = c;
  }

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
    return Consumer<ChecklistEntryModel>(
        builder: (context, checklistEntry, child) {
      if (checklistEntry.entries == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (checklistEntry.entries!.length > 0) {
        return ListView.builder(
            itemCount: checklistEntry.entries!.length,
            itemBuilder: (context, index) => Dismissible(
                background: Container(
                  // color: Theme.of(context).primaryColor,
                  //   margin: const EdgeInsets.all(5),
                  padding:
                      EdgeInsets.all(MediaQuery.of(context).size.height / 60),
                  child: Row(
                    children: [
                      Icon(Icons.edit,
                          color: Theme.of(context).accentColor,
                          size: 35 * MediaQuery.of(context).textScaleFactor),
                      new Spacer(),
                      Icon(Icons.delete,
                          color: Theme.of(context).accentColor,
                          size: 35 * MediaQuery.of(context).textScaleFactor),
                    ],
                  ),
                ),
                key: Key(checklistEntry.entries!.elementAt(index).id),
                child: Card(
                    color: Theme.of(context).primaryColorDark,
                    child: InkWell(
                        hoverColor: Theme.of(context).primaryColorLight,
                        child: Container(
                          child: Row(
                            children: <Widget>[
                              Expanded(
                                flex: 4,
                                child: ListTile(
                                  leading: Checkbox(
                                      value: checklistEntry.entries!
                                          .elementAt(index)
                                          .completed,
                                      onChanged: (bool? value) {
                                        checklistEntry.markEntry(checklistEntry
                                            .entries!
                                            .elementAt(index));
                                      } // This is where we update the state when the checkbox is tapped
                                      ),
                                  title: Text(
                                      checklistEntry.entries!
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
                                ),
                              ),
                            ],
                          ),
                        ))),
                confirmDismiss: (DismissDirection direction) async {
                  if (direction == DismissDirection.endToStart) {
                    return await showDialog(
                      context: context,
                      builder: (BuildContext context) {
                        return AlertDialog(
                          backgroundColor: Theme.of(context).primaryColorDark,
                          title: Text("Confirm Removal",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color)),
                          content: Text(
                              "Are you sure you want to remove this checklist item for definite?",
                              style: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText1!
                                      .color)),
                          actions: <Widget>[
                            TextButton(
                                onPressed: () =>
                                    Navigator.of(context).pop(true),
                                child: Text("Remove",
                                    style: TextStyle(
                                        color: Theme.of(context)
                                            .textTheme
                                            .bodyText1!
                                            .color))),
                            TextButton(
                              onPressed: () => Navigator.of(context).pop(false),
                              child: Text("Cancel",
                                  style: TextStyle(
                                      color: Theme.of(context)
                                          .textTheme
                                          .bodyText1!
                                          .color)),
                            ),
                          ],
                        );
                      },
                    );
                  } else if (direction == DismissDirection.startToEnd) {
                    return showDialog(
                        context: context,
                        builder: (BuildContext context) {
                          return AlertDialog(
                              backgroundColor:
                                  Theme.of(context).primaryColorDark,
                              content: Container(
                                  height: getSize(context),
                                  child: Stack(
                                      clipBehavior: Clip.none, children: <Widget>[
                                        Positioned(
                                          right: -40.0,
                                          top: -40.0,
                                          child: InkResponse(
                                            onTap: () {
                                              Navigator.of(context).pop(false);
                                            },
                                            child: CircleAvatar(
                                              child: Icon(Icons.close,
                                                  color: Theme.of(context)
                                                      .primaryColorDark),
                                              backgroundColor:
                                                  Theme.of(context).accentColor,
                                            ),
                                          ),
                                        ),
                                        Column(
                                            // mainAxisSize: MainAxisSize.min,
                                            children: <Widget>[
                                              Text(
                                                  "Edit: " +
                                                      checklistEntry.entries!
                                                          .elementAt(index)
                                                          .title,
                                                  textAlign: TextAlign.center,
                                                  style: TextStyle(
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color,
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                  )),
                                              Spacer(),
                                              Container(
                                                width: MediaQuery.of(context)
                                                        .size
                                                        .width *
                                                    0.5,
                                                padding: EdgeInsets.symmetric(
                                                    horizontal:
                                                        MediaQuery.of(context)
                                                                .size
                                                                .width *
                                                            0.02),
                                                child: TextField(
                                                    style: TextStyle(
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color),
                                                    controller: editController,
                                                    decoration: InputDecoration(
                                                        hintStyle: TextStyle(
                                                            color: Theme.of(context)
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
                                                            Theme.of(context)
                                                                .primaryColorLight,
                                                        focusedBorder: OutlineInputBorder(
                                                            borderSide: new BorderSide(
                                                                color: Theme.of(context)
                                                                    .accentColor)),
                                                        hintText:
                                                            'Description')),
                                              ),
                                              Spacer(),
                                              Padding(
                                                  padding: EdgeInsets.symmetric(
                                                      horizontal:
                                                          MediaQuery.of(context)
                                                                  .size
                                                                  .width *
                                                              0.02),
                                                  child: ElevatedButton(
                                                      style: ElevatedButton.styleFrom(
                                                          primary: Theme.of(context).accentColor),
                                                      onPressed: () {
                                                        Navigator.of(context)
                                                            .pop(true);
                                                      },
                                                      child: Text("Edit",
                                                          style: TextStyle(
                                                              color: Theme.of(
                                                                      context)
                                                                  .textTheme
                                                                  .bodyText1!
                                                                  .color))))
                                            ])
                                      ])));
                        });
                  }
                },
                onDismissed: (direction) {
                  if (direction == DismissDirection.endToStart) {
                    Provider.of<ChecklistEntryModel>(context, listen: false)
                        .deleteChecklistEntry(
                            checklistEntry.entries!.elementAt(index));
                  } else if (direction == DismissDirection.startToEnd) {
                    Provider.of<ChecklistEntryModel>(context, listen: false)
                        .editChecklistEntry(
                            checklistEntry.entries!.elementAt(index),
                            checklist!,
                            editController.text);
                  }
                }));
      } else {
        return Center(
            child: Text("Let's make a list and check it twice!",
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}
