import 'dart:convert';
import 'dart:async';
import 'package:adventure_it/api/createChecklistEntry.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:flutter/material.dart';
import '/constants.dart';
import '/api/checklist.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'checklistEntry.dart';
import 'createChecklist.dart';

class ChecklistApi {
  static Future<List<Checklist>> getChecklists(Adventure? a, context) async {
    http.Response response = await _getChecklists(a!.adventureId);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to get list of checklists!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to load list of checklists: ${response.body}');
    }

    List<Checklist> checklists = (jsonDecode(response.body) as List)
        .map((x) => Checklist.fromJson(x))
        .toList();

    return checklists;
  }

  static Future<http.Response> _getChecklists(adventureID) async {
    return http.get(Uri.parse(
        mainApi + '/checklist/viewChecklistsByAdventure/' + adventureID));
  }

  static Future<List<Checklist>> getDeletedChecklist(
      adventureId, context) async {
    http.Response response = await _getDeletedChecklistsResponse(adventureId);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to get list of deleted checklists!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to load list of checklists: ${response.body}');
    }

    List<Checklist> budgets = (jsonDecode(response.body) as List)
        .map((x) => Checklist.fromJson(x))
        .toList();

    return budgets;
  }

  static Future restoreChecklist(checklistID, context) async {
    http.Response response = await _restoreChecklistRequest(checklistID);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to restore checklist to adventure!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to restore checklist: ${response.body}');
    }
  }

  static Future softDeleteChecklist(checklistID, context) async {
    http.Response response = await _deleteChecklistRequest(checklistID);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to remove checklist from adventure!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to softDelete checklist: ${response.body}');
    }
  }

  static Future hardDeleteChecklist(checklistID, context) async {
    http.Response response = await _hardDeleteChecklistRequest(checklistID);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to delete checklist for forever!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to hardDelete checklist: ${response.body}');
    }
  }

  static Future completeEntry(String checklistID, context) async {
    http.Response response = await _completeEntry(checklistID);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to tick off checklist item!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception(
          'Failed to load list of entries for checklist: ${response.body}');
    }
  }

  static Future<http.Response> _completeEntry(checklistID) async {
    return http.get(Uri.parse(mainApi + '/checklist/markEntry/' + checklistID));
  }

  static Future<List<ChecklistEntry>> getChecklistEntries(
      Checklist i, context) async {
    http.Response response = await _getChecklistEntries(i.id);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to load list of checklist items!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception(
          'Failed to load list of entries for checklist: ${response.body}');
    }

    List<ChecklistEntry> checklists = (jsonDecode(response.body) as List)
        .map((x) => ChecklistEntry.fromJson(x))
        .toList();

    return checklists;
  }

  static Future<http.Response> _getChecklistEntries(checklistID) async {
    return http
        .get(Uri.parse(mainApi + '/checklist/viewChecklist/' + checklistID));
  }

  static Future deleteChecklistEntry(ChecklistEntry i, context) async {
    http.Response response = await _deleteChecklistEntryRequest(i.id);

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to remove checklist item from checklist!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to delete checklist entry ${response.body}');
    }
  }

  static Future<http.Response> _deleteChecklistEntryRequest(
      ChecklistEntryID) async {
    String userID = UserApi.getInstance().getUserProfile()!.userID;
    return http.get(Uri.parse(
        mainApi + '/checklist/removeEntry/' + ChecklistEntryID + '/' + userID));
  }

  static Future<http.Response> _getDeletedChecklistsResponse(
      adventureId) async {
    return http.get(Uri.parse(mainApi + '/checklist/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteChecklistRequest(checklistID) async {
    return http.get(Uri.parse(mainApi +
        '/checklist/softDelete/' +
        checklistID +
        '/' +
        UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<http.Response> _hardDeleteChecklistRequest(checklistID) async {
    return http.get(Uri.parse(mainApi +
        '/checklist/hardDelete/' +
        checklistID +
        '/' +
        UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<http.Response> _restoreChecklistRequest(checklistID) async {
    return http.get(Uri.parse(mainApi +
        '/checklist/restoreChecklist/' +
        checklistID +
        '/' +
        UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<CreateChecklist> createChecklist(String title,
      String description, String creatorID, String adventureID, context) async {
    final response = await http.post(
      Uri.parse(mainApi + '/checklist/create'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'title': title,
        'description': description,
        'adventureID': adventureID,
        'creatorID': creatorID
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateChecklist(
          title: title,
          description: description,
          adventureID: adventureID,
          creatorID: creatorID);
    } else {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to create checklist!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create a checklist.');
    }
  }

  static Future<CreateChecklistEntry> createChecklistEntry(
      String title, String entryContainerID, String userId, context) async {
    final response = await http.post(
      Uri.parse(mainApi + '/checklist/addEntry'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'title': title,
        'entryContainerID': entryContainerID,
        'userId': userId
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateChecklistEntry(
          title: title, entryContainerID: entryContainerID, userId: userId);
    } else {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to create checklist item!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create a checklist entry.');
    }
  }

  static Future<http.Response> editChecklistEntry(String id,
      String entryContainerID, String title, String userId, context) async {
    final response = await http.post(
      Uri.parse(mainApi + '/checklist/editEntry'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'id': id,
        'title': title,
        'entryContainerID': entryContainerID,
        'userId': userId
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return response;
    } else {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to edit checklist item!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to edit a checklist entry.');
    }
  }

  static Future checklistEdit(
      ChecklistEntry c, String s, String userId, context) async {
    http.Response response = (await editChecklistEntry(
        c.id, c.entryContainerID, s, userId, context));

    if (response.statusCode != 200) {
      SnackBar snackBar = SnackBar(
          content: Text('Failed to edit checklist item!',
              style: TextStyle(
                  color: Theme.of(context).textTheme.bodyText1!.color,
                  fontWeight: FontWeight.bold)),
          backgroundColor: Theme.of(context).primaryColorDark);
      ScaffoldMessenger.of(context).showSnackBar(snackBar);
      throw Exception('Failed to edit checklist entry ${response.body}');
    }
  }
}
