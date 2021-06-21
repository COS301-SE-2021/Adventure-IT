import 'dart:convert';
import '/constants.dart';
import '/api/budget.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class BudgetApi {
  static Future<List<Budget>> getBudgets(Adventure? a) async {
    List<Budget> b = [];
    for (int i = 0; i < a!.containers.length; i++) {
      http.Response response =
          await _getBudgetResponse(a!.containers.elementAt(i));
      print(response.body);
      if (response.statusCode == 200) {
        print("polly");
        Map<String, dynamic> budgetMap = json.decode(response.body);
        var budget = Budget.fromJson(budgetMap);
        if (!budget.deleted) {
          b.add(Budget.fromJson(budgetMap));
        }
        print("in here in here in here in here in here");
      } else {
        print(response.statusCode);
        throw Exception('Cannot load budget');
      }
    }
    return b;
  }

  static Future<List<Budget>> getDeletedBudgets(adventureId) async {
    print('Inside get deleted budgets');
    List<Budget> b = [];
    http.Response response = await _getDeletedBudgetsResponse(adventureId);
    print('get deleted budgets api call successful');
    if (response.statusCode == 200) {
      print('response code: 200, mapping response: ' + response.body);
      b = (json.decode(response.body) as List)
          .map((i) => Budget.fromJson(i))
          .toList();
      print("mapping response completed");
      return b;
    } else {
      throw Exception('Cannot load deleted budgets');
    }
  }

  static void restoreBudget(budgetID) async {
    print('Inside get restore budgets');
    await _restoreBudgetRequest(budgetID);
  }

  static void softDeleteBudget(budgetID) async {
    await _deleteBudgetRequest(budgetID);
  }

  static void createBudget(budgetName) async {
    await _createBudgetRequest(budgetName);
  }

  static Future<http.Response> _getBudgetResponse(budgetID) async {
    print("look!" + budgetID);
    return http.get(Uri.http(budgetApi, '/budget/viewBudget/' + budgetID));
  }

  static Future<http.Response> _getDeletedBudgetsResponse(adventureId) async {
    print('Inside get deleted budgets api call');
    return http.get(Uri.http(budgetApi, '/budget/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteBudgetRequest(budgetID) async {
    print('Inside delete budget api call');
    return http.get(Uri.http(budgetApi, '/budget/softDelete/' + budgetID));
  }

  static Future<http.Response> _createBudgetRequest(budgetName) async {
    print('Inside create budget api call');
    return http.get(Uri.http(budgetApi, '/budget/mockCreate/' + budgetName));
  }

  static Future<http.Response> _restoreBudgetRequest(budgetID) async {
    print('Inside restore budget api call');
    return http.get(Uri.http(budgetApi, '/budget/restoreBudget/' + budgetID));
  }
}
