import 'dart:convert';
import '/constants.dart';
import '/API/budget.dart';
import '/API/adventures.dart';
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
        b.add(Budget.fromJson(budgetMap));
        print("in here in here in here in here in here");
      } else {
        print(response.statusCode);
        throw Exception('Cannot load budget');
      }
    }
    return b;
  }

  static Future<List<Budget>> getDeletedBudgets() async {
    print('Inside get deleted budgets');
    List<Budget> b = [];
    http.Response response = await _getDeletedBudgetsResponse();
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

  static Future<http.Response> _getBudgetResponse(budgetID) async {
    print("look!" + budgetID);
    return http.get(Uri.http(budgetApi, '/budget/viewBudget/' + budgetID));
  }

  static Future<http.Response> _getDeletedBudgetsResponse() async {
    print('Inside get deleted budgets api call');
    return http.get(Uri.http(budgetApi, '/budget/viewTrash/'));
  }
}