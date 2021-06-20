import 'dart:convert';
import '/constants.dart';
import '/API/budget.dart';
import '/API/adventures.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';

class AdventureApi {

  static Future<List<Budget>> getBudgets(Adventure a) async {
    List <Budget> b;
    for(int i = 0; i <a.containers.length; i++)
    {

      http.Response response = await _getBudgetResponse(a.containers.elementAt(i));
      if (response.statusCode == 200) {

        b.add(json.decode(response.body) as Budget);
       }
      else{
        throw Exception('Cannot load budget');
      }

    }
    return b;
  }


  static Future<http.Response> _getBudgetResponse(budgetID) async {

    return http.get(Uri.http(adventureApi, '/budget//'+budgetID));
  }
}