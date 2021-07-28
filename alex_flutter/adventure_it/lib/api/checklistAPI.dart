import 'dart:convert';
import '/constants.dart';
import '/api/checklist.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class checklistApi {
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
    return http.get(Uri.http(checklistApi, '/viewChecklistsByAdventure/' + adventureID));
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



  static Future<http.Response> _getDeletedChecklistsResponse(adventureId) async {

    return http.get(Uri.http(checklistApi, '/checklist/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteChecklistRequest(checklistID) async {

    return http.get(Uri.http(checklistApi, '/checklist/softDelete/' + checklistID));
  }

  static Future<http.Response> _hardDeleteChecklistRequest(checklistID) async {

    return http.get(Uri.http(checklistApi, '/checklist/hardDelete/' + checklistID));
  }


  static Future<http.Response> _restoreChecklistRequest(checklistID) async {

    return http.get(Uri.http(checklistApi, '/checklist/restoreBudget/' + checklistID));
  }
}