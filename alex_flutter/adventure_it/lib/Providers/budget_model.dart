import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/budget.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';


class BudgetModel extends ChangeNotifier {
  List<Budget> _budgets = List.empty();

  BudgetModel() {
    fetchAllBudgets().then((budgets) => budgets != null? _budgets = budgets:_budgets);
  }

  List<Budget> get budgets => _budgets.toList();

  Future fetchAllBudgets(Adventure a) async {
    _budgets = await BudgetApi.getBudgets(a);

    notifyListeners();
  }

  // Future addAdventure(Adventure adventure) async {
  //   Adventure newAdventure = await AdventureApi.createAdventure(adventure);
  //   _adventures.add(newAdventure);
  //
  //   notifyListeners();
  // }


  Future softDeleteBudget(Budget budget) async {
    await BudgetApi.softDeleteBudget(budget.id);

    var index = _budgets.indexWhere((element) => element.id == budget.id);
    _budgets.removeAt(index);

    notifyListeners();
  }

  Future hardDeleteBudget(Budget budget) async {
    await BudgetApi.hardDeleteBudget(budget.id);

    var index = _budgets.indexWhere((element) => element.id == budget.id);
    _budgets.removeAt(index);

    notifyListeners();
  }

}