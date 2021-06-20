import 'dart:convert';
import '/constants.dart';
import '/API/budget.dart';
import '/API/adventures.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class BudgetApi {

  static Future<List<Budget>> getBudgets(Adventure? a) async {
    List <Budget> b = [];
    for(int i = 0; i <a!.containers.length; i++)
    {

      http.Response response = await _getBudgetResponse(a!.containers.elementAt(i));
      print(response.body);
      if (response.statusCode == 200) {

        print("polly");
        Map<String, dynamic> budgetMap = json.decode(response.body);
        b.add(Budget.fromJson(budgetMap));
        print("in here in here in here in here in here");
       }
      else{
        print(response.statusCode);
        throw Exception('Cannot load budget');
      }

    }
    return b;
  }


  static Future<http.Response> _getBudgetResponse(budgetID) async {
    print("look!"+budgetID);
    return http.get(Uri.http(budgetApi, '/budget/viewBudget/'+budgetID));
  }
}