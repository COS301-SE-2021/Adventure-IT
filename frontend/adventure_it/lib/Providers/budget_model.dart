import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/budget.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/api/budgetEntry.dart';
import 'package:adventure_it/api/report.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DeletedBudgetModel extends ChangeNotifier {
  List<Budget>? _deletedBudgets;
  List<UserProfile?>? _creators;
  BuildContext? context;

  DeletedBudgetModel(Adventure a,context) {
    fetchAllDeletedBudgets(a).then((deletedBudgets) => deletedBudgets != null
        ? _deletedBudgets = deletedBudgets
        : List.empty());
  }

  List<Budget>? get deletedBudgets => _deletedBudgets?.toList();

  List<UserProfile?>? get creators => _creators?.toList();

  Future fetchAllDeletedBudgets(Adventure a) async {
    _deletedBudgets = await BudgetApi.getDeletedBudgets(a.adventureId,context);
    var total =
        List<UserProfile?>.filled(deletedBudgets!.length, null, growable: true);
    total.removeRange(0, deletedBudgets!.length);
    for (var b in deletedBudgets!) {
      await UserApi.getInstance().findUser(b.creatorID,context).then((value) {
        total.add(value);
      });
    }

    this._creators = total;

    notifyListeners();
  }

  Future hardDeleteBudget(Budget budget) async {
    await BudgetApi.hardDeleteBudget(budget.id,context);

    var index =
        _deletedBudgets!.indexWhere((element) => element.id == budget.id);
    _deletedBudgets!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }

  Future restoreBudget(Budget budget) async {
    await BudgetApi.restoreBudget(budget.id,context);

    var index =
        _deletedBudgets!.indexWhere((element) => element.id == budget.id);
    _deletedBudgets!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }
}

class BudgetModel extends ChangeNotifier {
  List<Budget>? _budgets;
  List<int>? _categories;
  List<String>? _expenses;
  Adventure? adventure;
  BuildContext? context;

  BudgetModel(Adventure a, String userName,context) {
    this.adventure = a;
    this.context=context;
    fetchAllBudgets(a, userName).then((budgets) {
      budgets != null ? _budgets = budgets : List.empty();
    });
  }

  List<Budget>? get budgets => _budgets?.toList();

  List<String>? get expenses => _expenses?.toList();

  List<int>? get categories => _categories?.toList();

  Future fetchAllBudgets(Adventure a, String userName) async {
    _budgets = await BudgetApi.getBudgets(a,context);
    calculateCategories(a).then((categories) {
      categories != null ? _categories = categories : List<int>.filled(5, 0);
    });

    calculateExpenses(userName).then((expenses) => expenses != null
        ? _expenses = expenses
        : List<String>.filled(budgets!.length, "0"));

    notifyListeners();
  }

  Future calculateExpenses(String userName) async {
    var total = List<String>.filled(budgets!.length, "0", growable: true);
    total.removeRange(0, budgets!.length);
    for (var b in budgets!) {
      await BudgetApi.getTotalOfExpenses(b, userName,context).then((val) {
           double x=double.parse(val);
           String amount=x.toStringAsFixed(2);
           total.add(amount);
      });
    }

    this._expenses = total;

    notifyListeners();
  }

  Future calculateCategories(Adventure a) async {
    await BudgetApi.getNumberOfCategories(a,context).then((value) {
      this._categories = value;
    });

    notifyListeners();
  }

  Future softDeleteBudget(Budget budget) async {
    await BudgetApi.softDeleteBudget(budget.id,context);

    var index = _budgets!.indexWhere((element) => element.id == budget.id);
    _budgets!.removeAt(index);
    _expenses!.removeAt(index);
    calculateCategories(this.adventure!).then((categories) {
      if (categories != null) {
        _categories = categories;
      }
    });

    notifyListeners();
  }

  Future addBudget(
      Adventure adv, String a, String b, String c, String d, String uN) async {
    await BudgetApi.createBudget(a, b, c, d,context);

    await fetchAllBudgets(adv, uN);
  }
}

class BudgetEntryModel extends ChangeNotifier {
  List<BudgetEntry>? _entries;
  List<Report>? _reports;
  Budget? budget;
  BuildContext? context;

  BudgetEntryModel(Budget b,context) {
    this.budget = b;
    this.context=context;
    fetchAllEntries(b)
        .then((entries) => entries != null ? _entries = entries : List.empty());
  }

  List<BudgetEntry>? get entries => _entries?.toList();

  List<Report>? get reports => _reports?.toList();

  Future fetchAllEntries(Budget b) async {
    _entries = await BudgetApi.getEntries(b,context);

    fetchAllReports(b, UserApi.getInstance().getUserProfile()!.username)
        .then((entries) => entries != null ? _reports = entries : List.empty());
  }

  Future fetchAllReports(Budget b, String userID) async {
    _reports = await BudgetApi.getReport(b, userID,context);

    notifyListeners();
  }

  Future deleteBudgetEntry(BudgetEntry c) async {
    await BudgetApi.deleteEntry(c,UserApi.getInstance().getUserProfile()!.userID,context);

    var index = _entries!
        .indexWhere((element) => element.budgetEntryID == c.budgetEntryID);
    _entries!.removeAt(index);

    await fetchAllReports(
        budget!, UserApi.getInstance().getUserProfile()!.username);
  }

  Future addUTUBudgetEntry(Budget budget, String a, String b, String c,
      String d, String e, String f, String g) async {
    await BudgetApi.createUTUBudget(a, b, c, d, e, f, g,context);

    await fetchAllEntries(budget);
  }

  Future addUTOBudgetEntry(Budget budget, String a, String b, String c,
      String d, String e, String f, String g,context) async {
    await BudgetApi.createUTOBudget(a, b, c, d, e, f, g,context);

    await fetchAllEntries(budget);
  }

  Future editBudgetEntry(Budget budget, BudgetEntry be, String a, String b,
      String c, String d, String e, String f, String g, String h, String i,context) async {
    await BudgetApi.editBudgetEntry(a, b, c, d, e, f, g, h, i,context);

    var index = _entries!
        .indexWhere((element) => element.budgetEntryID == be.budgetEntryID);
    _entries!.removeAt(index);

    await fetchAllEntries(budget);
  }
}
