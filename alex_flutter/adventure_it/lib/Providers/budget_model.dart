import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/budget.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/api/budgetEntry.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';

class DeletedBudgetModel extends ChangeNotifier
{
  List<Budget>? _deletedBudgets=null;
  DeletedBudgetModel(Adventure a) {
    fetchAllDeletedBudgets(a).then((deletedBudgets) => deletedBudgets != null? _deletedBudgets = deletedBudgets:List.empty());
  }
  List<Budget>? get deletedBudgets => _deletedBudgets?.toList();

  Future fetchAllDeletedBudgets(Adventure a) async {
    _deletedBudgets = await BudgetApi.getDeletedBudgets(a.adventureId);


    notifyListeners();
  }

  Future hardDeleteBudget(Budget budget) async {
    await BudgetApi.hardDeleteBudget(budget.id);

    var index = _deletedBudgets!.indexWhere((element) => element.id == budget.id);
    _deletedBudgets!.removeAt(index);

    notifyListeners();
  }

  Future restoreBudget(Budget budget) async {
    await BudgetApi.restoreBudget(budget.id);
    print('in here');

    var index = _deletedBudgets!.indexWhere((element) => element.id == budget.id);
    _deletedBudgets!.removeAt(index);

    notifyListeners();
  }

}

class BudgetModel extends ChangeNotifier {

  List<Budget>? _budgets = null;


  BudgetModel(Adventure a) {
    fetchAllBudgets(a).then((budgets) => budgets != null? _budgets = budgets:List.empty());
  }

  List<Budget>? get budgets => _budgets?.toList();


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

    var index = _budgets!.indexWhere((element) => element.id == budget.id);
    _budgets!.removeAt(index);

    notifyListeners();
  }



}

class BudgetEntryModel extends ChangeNotifier {
  List<BudgetEntry>? _entries = null;


  BudgetEntryModel(Budget b) {
    fetchAllEntries(b).then((entries) =>
    entries != null
        ? _entries = entries
        : List.empty());
  }


  List<BudgetEntry>? get entries => _entries?.toList();


  Future fetchAllEntries(Budget b) async {
    _entries = await BudgetApi.getEntries(b);

    notifyListeners();
  }


  Future deleteBudgetEntry(BudgetEntry c) async {
    await BudgetApi.deleteBudgetEntry(c.budgetEntryID);

    var index = _entries!.indexWhere((element) => element.budgetEntryID == c.budgetEntryID);
    _entries!.removeAt(index);

    notifyListeners();
  }
}