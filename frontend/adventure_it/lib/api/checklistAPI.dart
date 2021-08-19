import 'dart:convert';
import 'package:adventure_it/api/createChecklistEntry.dart';
import 'package:adventure_it/api/user_api.dart';

import '/constants.dart';
import '/api/checklist.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

import 'checklistEntry.dart';

import 'createChecklist.dart';
import 'editChecklistEntry.dart';

class ChecklistApi {
  static Future<List<Checklist>> getChecklists(Adventure? a) async {
    http.Response response =
    await _getChecklists(a!.adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of checklists: ${response.body}');
    }

    List<Checklist> checklists = (jsonDecode(response.body) as List)
        .map((x) => Checklist.fromJson(x))
        .toList();

    return checklists;
  }

  static Future<http.Response> _getChecklists(adventureID) async {
    return http.get(Uri.http(mainApi, 'checklist/viewChecklistsByAdventure/' + adventureID));
  }

  static Future<List<Checklist>> getDeletedChecklist(adventureId) async {
    http.Response response =
    await _getDeletedChecklistsResponse(adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of checklists: ${response.body}');
    }

    List<Checklist> budgets = (jsonDecode(response.body) as List)
        .map((x) => Checklist.fromJson(x))
        .toList();

    return budgets;

  }

  static Future restoreChecklist(checklistID) async {

    http.Response response = await _restoreChecklistRequest(checklistID);


    if (response.statusCode != 200) {
      throw Exception('Failed to restore checklist: ${response.body}');
    }
  }

  static Future softDeleteChecklist(checklistID) async {
    http.Response response = await _deleteChecklistRequest(checklistID);


    if (response.statusCode != 200) {
      throw Exception('Failed to softDelete checklist: ${response.body}');
    }

  }

  static Future hardDeleteChecklist(checklistID) async {
    http.Response response = await _hardDeleteChecklistRequest(checklistID);


    if (response.statusCode != 200) {
      throw Exception('Failed to hardDelete checklist: ${response.body}');
    }

  }

  static Future completeEntry (String checklistID) async {
    http.Response response=await _completeEntry(checklistID);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of entries for checklist: ${response.body}');
    }
}

  static Future<http.Response> _completeEntry(checklistID) async {

    return http.get(Uri.http(mainApi, '/checklist/markEntry/' + checklistID));
  }

  static Future<List<ChecklistEntry>> getChecklistEntries(Checklist i) async {
    http.Response response =
    await _getChecklistEntries(i!.id);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of entries for checklist: ${response.body}');
    }


    List<ChecklistEntry> checklists = (jsonDecode(response.body) as List)
        .map((x) => ChecklistEntry.fromJson(x))
        .toList();

    return checklists;
  }

  static Future<http.Response> _getChecklistEntries(checklistID) async {

    return http.get(Uri.http(mainApi, '/checklist/viewChecklist/' + checklistID));
  }

  static Future deleteChecklistEntry(ChecklistEntry i) async {
    http.Response response = await _deleteChecklistEntryRequest(i.id);


    if (response.statusCode != 200) {
      throw Exception('Failed to delete checklist entry ${response.body}');
    }

  }

  static Future<http.Response> _deleteChecklistEntryRequest(ChecklistEntryID) async {

    return http.get(Uri.http(mainApi, '/checklist/removeEntry/' + ChecklistEntryID));
  }



  static Future<http.Response> _getDeletedChecklistsResponse(adventureId) async {

    return http.get(Uri.http(mainApi, '/checklist/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteChecklistRequest(checklistID) async {

    return http.get(Uri.http(mainApi, '/checklist/softDelete/' + checklistID + '/' + UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<http.Response> _hardDeleteChecklistRequest(checklistID) async {

    return http.get(Uri.http(mainApi, '/checklist/hardDelete/' + checklistID + '/' + UserApi.getInstance().getUserProfile()!.userID));
  }


  static Future<http.Response> _restoreChecklistRequest(checklistID) async {

    return http.get(Uri.http(mainApi, '/checklist/restoreChecklist/' + checklistID + '/' + UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<CreateChecklist> createChecklist(String title, String description, String creatorID, String adventureID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/checklist/create'), //get uri
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
      return CreateChecklist(title: title, description: description, adventureID: adventureID, creatorID: creatorID);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create a checklist.');
    }
  }

  static Future<CreateChecklistEntry> createChecklistEntry(String title, String entryContainerID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/checklist/addEntry'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'title': title,
        'entryContainerID': entryContainerID
      }),


    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateChecklistEntry(title: title, entryContainerID: entryContainerID);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create a checklist entry.');
    }
  }

  static Future<http.Response> editChecklistEntry(String id, String entryContainerID, String title) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/checklist/editEntry'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'id': id,
        'title': title,
        'entryContainerID': entryContainerID
      }),


    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return response;
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to edit a checklist entry.');
    }
  }

  static Future checklistEdit(ChecklistEntry c, String s) async {
    http.Response response = (await editChecklistEntry(c.id, c.entryContainerID, s));


    if (response.statusCode != 200) {
      throw Exception('Failed to edit checklist entry ${response.body}');
    }
  }
}