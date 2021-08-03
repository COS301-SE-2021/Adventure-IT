import 'dart:convert';
import '/constants.dart';
import '/api/budget.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

import 'createBudget.dart';

class BudgetApi {
  static Future<List<Budget>> getBudgets(Adventure? a) async {
    http.Response response =
    await _getBudgets(a!.adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of budgets: ${response.body}');
    }

    List<Budget> budgets = (jsonDecode(response.body) as List)
        .map((x) => Budget.fromJson(x))
        .toList();

    return budgets;
  }

  static Future<http.Response> _getBudgets(adventureID) async {
    return http.get(Uri.http(budgetApi, '/budget/viewBudgetsByAdventure/' + adventureID));
  }

  static Future<List<Budget>> getDeletedBudgets(adventureId) async {
    http.Response response =
    await _getDeletedBudgetsResponse(adventureId);

    print(response.body);

    if (response.statusCode != 200) {
      throw Exception('Failed to load list of deleted budgets: ${response.body}');
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

  static Future hardDeleteBudget(budgetID) async {
    http.Response response = await _hardDeleteBudgetRequest(budgetID);


    if (response.statusCode != 200) {
      throw Exception('Failed to hardDelete budget: ${response.body}');
    }

  }



  static Future<http.Response> _getDeletedBudgetsResponse(adventureId) async {

    return http.get(Uri.http(budgetApi, '/budget/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteBudgetRequest(budgetID) async {

    return http.get(Uri.http(budgetApi, '/budget/softDelete/' + budgetID));
  }

  static Future<http.Response> _hardDeleteBudgetRequest(budgetID) async {

    return http.get(Uri.http(budgetApi, '/budget/hardDelete/' + budgetID));
  }


  static Future<http.Response> _restoreBudgetRequest(budgetID) async {

    return http.get(Uri.http(budgetApi, '/budget/restoreBudget/' + budgetID));
  }

  static Future <String> getTotalOfExpenses(Budget b) async {
    http.Response response =
    await _getTotalOfExpenses(b.id);

    if (response.statusCode != 200) {
      throw Exception('Failed to load expenses: ${response.body}');
    }


    return response.body;
  }

  static Future<http.Response> _getTotalOfExpenses(budgetID) async {

    return http.get(Uri.http(budgetApi, '/budget/expenseTotal/' + budgetID));
  }

  Future<CreateBudget> createBudget(String name, String description, String creatorID, String adventureID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9002/api/budget/create'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'title': name,
        'description': description,
        'advID': adventureID,
        'userID': creatorID
      }),


    );

    if (response.statusCode == 201) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateBudget(name: name, description: description, adventureID: adventureID, creatorID: creatorID);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      throw Exception('Failed to create a budget.');
    }
  }
}
