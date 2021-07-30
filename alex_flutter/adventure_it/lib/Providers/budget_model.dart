import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/budget.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';


class BudgetModel extends ChangeNotifier {

  List<Budget>? _budgets = null;
  List<Budget>? _deletedBudgets=null;

  BudgetModel(Adventure a) {
    fetchAllBudgets(a).then((budgets) {
      budgets != null ? _budgets = budgets : _budgets = List.empty();
    });
    fetchAllDeletedBudgets(a).then((deletedBudgets) {
      deletedBudgets != null ? _deletedBudgets = deletedBudgets : _deletedBudgets = List.empty();
    });
  }

  List<Budget>? get budgets => _budgets?.toList();
  List<Budget>? get deletedBudgets => _deletedBudgets?.toList();

  Future fetchAllBudgets(Adventure a) async {
    _budgets = await BudgetApi.getBudgets(a);

    notifyListeners();
  }

  Future fetchAllDeletedBudgets(Adventure a) async {
    _deletedBudgets = await BudgetApi.getDeletedBudgets(a);

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

    var index = _budgets!.indexWhere((element) => element.id == budget.id);
    _budgets!.removeAt(index);

    notifyListeners();
  }

  Future hardDeleteBudget(Budget budget) async {
    await BudgetApi.hardDeleteBudget(budget.id);

    var index = _deletedBudgets!.indexWhere((element) => element.id == budget.id);
    _deletedBudgets!.removeAt(index);

    notifyListeners();
  }

}