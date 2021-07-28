import 'dart:convert';
import '/constants.dart';
import '/api/checklist.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class ChecklistApi {
  static Future<List<Budget>> getBudgets(Adventure? a) async {

  }

  static Future<List<Budget>> getDeletedBudgets(adventureId) async {
    http.Response response =
    await _getDeletedBudgetsResponse(adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of adventures: ${response.body}');
    }

    List<Budget> budgets = (jsonDecode(response.body) as List)
        .map((x) => Budget.fromJson(x))
        .toList();

    return budgets;

  }

  static Future restoreBudget(budgetID) async {

    http.Response response = await _restoreBudgetRequest(budgetID);


    if (response.statusCode != 200) {
      throw Exception('Failed to restore budget: ${response.body}');
    }
  }

  static Future softDeleteBudget(budgetID) async {
    http.Response response = await _deleteBudgetRequest(budgetID);


    if (response.statusCode != 200) {
      throw Exception('Failed to softDelete budget: ${response.body}');
    }

  }



  static Future<http.Response> _getDeletedBudgetsResponse(adventureId) async {

    return http.get(Uri.http(budgetApi, '/budget/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteBudgetRequest(budgetID) async {

    return http.get(Uri.http(budgetApi, '/budget/softDelete/' + budgetID));
  }


  static Future<http.Response> _restoreBudgetRequest(budgetID) async {

    return http.get(Uri.http(budgetApi, '/budget/restoreBudget/' + budgetID));
  }
}
