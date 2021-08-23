import 'dart:convert';
import 'package:adventure_it/api/createUTOBudgetEntry.dart';

import 'package:adventure_it/api/report.dart';
import 'package:adventure_it/api/user_api.dart';

import '/constants.dart';
import '/api/budget.dart';
import '/api/adventure.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

import 'createBudget.dart';

import 'budgetEntry.dart';
import 'createUTUBudgetEntry.dart';

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
    return http.get(Uri.http(mainApi, '/budget/viewBudgetsByAdventure/' + adventureID));
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

  static Future <List<int>> getNumberOfCategories (Adventure a) async
  {
    http.Response response = await _getNumberOfCategories(a.adventureId);
    if (response.statusCode != 200) {
      throw Exception('Failed to get categories: ${response.body}');
    }
    List<dynamic> categories = (jsonDecode(response.body) as List);
    List<int> intList = categories.map((s) => s as int).toList();
    return intList;
  }

  static Future<http.Response> _getNumberOfCategories(adventureID) async {

    return http.get(Uri.http(mainApi, '/budget/getEntriesPerCategory/' + adventureID));
  }



  static Future<http.Response> _getDeletedBudgetsResponse(adventureId) async {

    return http.get(Uri.http(mainApi, '/budget/viewTrash/' + adventureId));
  }

  static Future<http.Response> _deleteBudgetRequest(budgetID) async {

    return http.get(Uri.http(mainApi, '/budget/softDelete/' + budgetID + '/' + UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future<http.Response> _hardDeleteBudgetRequest(budgetID) async {

    return http.get(Uri.http(mainApi, '/budget/hardDelete/' + budgetID + '/' + UserApi.getInstance().getUserProfile()!.userID));
  }


  static Future<http.Response> _restoreBudgetRequest(budgetID) async {

    return http.get(Uri.http(mainApi, '/budget/restoreBudget/' + budgetID + '/' + UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future <String> getTotalOfExpenses(Budget b, String userID) async {
    http.Response response =
    await _getTotalOfExpenses(b.id,userID);

    if (response.statusCode != 200) {
      return "0";
    }

    return response.body;
  }

  static Future<http.Response> _getTotalOfExpenses(budgetID,userID) async {

    return http.get(Uri.http(budgetApi, '/budget/calculateExpense/'+budgetID+"/"+userID));
  }

  static Future <List<BudgetEntry>> getEntries(Budget b) async {
    http.Response response =
    await _getEntries(b.id);

    if (response.statusCode != 200) {
      throw Exception('Failed to get budget entries: ${response.body}');
    }

    List<BudgetEntry> budgetEntries = (jsonDecode(response.body) as List)
        .map((x) => BudgetEntry.fromJson(x))
        .toList();

    return budgetEntries;
  }

  static Future<http.Response> _getEntries(budgetID) async {

    return http.get(Uri.http(mainApi, '/budget/viewBudget/' + budgetID));
  }

  static Future deleteEntry(BudgetEntry i) async {
    http.Response response = await _deleteBudgetEntryRequest(i.budgetEntryID);


    if (response.statusCode != 200) {
      throw Exception('Failed to delete budget entry ${response.body}');
    }

  }

  static Future<http.Response> _deleteBudgetEntryRequest(BudgetEntryID) async {

    return http.get(Uri.http(mainApi, '/budget/removeEntry/' + BudgetEntryID));
  }

  static Future<List<Report>?> getReport(Budget b, String userID) async {
    http.Response response = await _getReport(b,userID);


    if (response.statusCode != 200) {
      return null;
    }

    List<Report> reportEntries = (jsonDecode(response.body) as List)
        .map((x) => Report.fromJson(x))
        .toList();

    return reportEntries;

  }

  static Future<http.Response> _getReport(Budget b, String userID) async {

    return http.get(Uri.http(mainApi, '/budget/generateIndividualReport/' +b.id+"/"+userID));
  }

  static Future<CreateBudget> createBudget(String name, String description, String creatorID, String adventureID) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/budget/create'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'name': name,
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
      return CreateBudget(name: name, description: description, creatorID: creatorID, adventureID: adventureID);
    }
    else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create a budget.');
    }
  }

  static Future<CreateUTOBudgetEntry> createUTOBudget(String entryContainerID, String payer, String amount, String title, String description, String category, String payee) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/budget/addUTOExpense'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'entryContainerID': entryContainerID,
        'payer': payer,
        'amount': amount,
        'title': title,
        'description': description,
        'category': category,
        'payee': payee
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateUTOBudgetEntry(entryContainerID: entryContainerID, payer: payer, amount: amount, title: title, description: description, category: category, payee: payee);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create a UTO budget entry.');
    }
  }

  static Future<CreateUTUBudgetEntry> createUTUBudget(String entryContainerID, String payer, String amount, String title, String description, String category, String payee) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/budget/addUTUExpense'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'entryContainerID': entryContainerID,
        'payer': payer,
        'amount': amount,
        'title': title,
        'description': description,
        'category': category,
        'payee': payee
      }),
    );

    if (response.statusCode == 200) {
      // If the server did return a 201 CREATED response,
      // then parse the JSON.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      return CreateUTUBudgetEntry(entryContainerID: entryContainerID, payer: payer, amount: amount, title: title, description: description, category: category, payee: payee);
    } else {
      // If the server did not return a 201 CREATED response,
      // then throw an exception.
      print('Status code: ${response.statusCode}');
      print('Body: ${response.body}');
      throw Exception('Failed to create a UTU budget entry.');
    }
  }

  static Future<http.Response> editBudgetEntry(String id, String entryContainerID, String payer, String amount, String title, String description, String payee) async {
    final response = await http.post(
      Uri.parse('http://localhost:9999/budget/editBudget'), //get uri
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        'id': id,
        'entryContainerID': entryContainerID,
        'payer': payer,
        'amount': amount,
        'title': title,
        'description': description,
        'payee': payee
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
      throw Exception('Failed to edit the budget entry.');
    }
  }
}