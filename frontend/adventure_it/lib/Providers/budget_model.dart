import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/budget.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:adventure_it/api/budgetEntry.dart';
import 'package:adventure_it/api/report.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:flutter/cupertino.dart';

class DeletedBudgetModel extends ChangeNotifier {
  List<Budget>? _deletedBudgets = null;

  DeletedBudgetModel(Adventure a) {
    fetchAllDeletedBudgets(a).then((deletedBudgets) =>
    deletedBudgets != null
        ? _deletedBudgets = deletedBudgets
        : List.empty());
  }

  List<Budget>? get deletedBudgets => _deletedBudgets?.toList();

  Future fetchAllDeletedBudgets(Adventure a) async {
    _deletedBudgets = await BudgetApi.getDeletedBudgets(a.adventureId);

    notifyListeners();
  }

  Future hardDeleteBudget(Budget budget) async {
    await BudgetApi.hardDeleteBudget(budget.id);

    var index =
    _deletedBudgets!.indexWhere((element) => element.id == budget.id);
    _deletedBudgets!.removeAt(index);

    notifyListeners();
  }

  Future restoreBudget(Budget budget) async {
    await BudgetApi.restoreBudget(budget.id);

    var index =
    _deletedBudgets!.indexWhere((element) => element.id == budget.id);
    _deletedBudgets!.removeAt(index);

    notifyListeners();
  }
}

class BudgetModel extends ChangeNotifier {
  List<Budget>? _budgets = null;
  List<int>? _categories = null;
  List<String>? _expenses = null;
  Adventure? adventure = null;

  BudgetModel(Adventure a, String userName) {
    this.adventure = a;
    fetchAllBudgets(a, userName).then((budgets) {
      budgets != null ? _budgets = budgets : List.empty();
    });
  }

  List<Budget>? get budgets => _budgets?.toList();

  List<String>? get expenses => _expenses?.toList();

  List<int>? get categories => _categories?.toList();

  Future fetchAllBudgets(Adventure a, String userName) async {
    _budgets = await BudgetApi.getBudgets(a);
    calculateCategories(a).then((categories) {
      categories != null ? _categories = categories : List<int>.filled(5, 0);
    });
    calculateExpenses(userName).then((expenses) =>
    expenses != null
        ? _expenses = expenses
        : List<String>.filled(budgets!.length, "0"));

    notifyListeners();
  }

  Future calculateExpenses(String userName) async {
    var total = List<String>.filled(budgets!.length, "0", growable: true);
    total.removeRange(0, budgets!.length);
    for (var b in budgets!) {
      await BudgetApi.getTotalOfExpenses(b, userName).then((value) {
        total.add(value);
      });
    }

    this._expenses = total;

    notifyListeners();
  }

  Future calculateCategories(Adventure a) async {
    await BudgetApi.getNumberOfCategories(a).then((value) {
      _categories = value;
    });

    notifyListeners();
  }

  Future softDeleteBudget(Budget budget) async {
    await BudgetApi.softDeleteBudget(budget.id);

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

  Future addBudget(Adventure adv, String a, String b, String c, String d,
      String uN) async {
    await BudgetApi.createBudget(a, b, c, d);

    await fetchAllBudgets(adv, uN);
  }
}

class BudgetEntryModel extends ChangeNotifier {
  List<BudgetEntry>? _entries = null;
  List<Report>? _reports = null;
  Budget? budget = null;

  BudgetEntryModel(Budget b) {
    this.budget = b;
    fetchAllEntries(b)
        .then((entries) => entries != null ? _entries = entries : List.empty());

  }

  List<BudgetEntry>? get entries => _entries?.toList();
  List<Report>? get reports => _reports?.toList();

  Future fetchAllEntries(Budget b) async {
    _entries = await BudgetApi.getEntries(b);

    fetchAllReports(b, UserApi.getInstance().getUserProfile()!.username)
        .then((entries) => entries != null ? _reports = entries : List.empty());

    notifyListeners();
  }

  Future fetchAllReports(Budget b, String userID) async {
    _reports = await BudgetApi.getReport(b, userID);

    notifyListeners();
  }

  Future deleteBudgetEntry(BudgetEntry c) async {
    await BudgetApi.deleteEntry(c);

    var index = _entries!
        .indexWhere((element) => element.budgetEntryID == c.budgetEntryID);
    _entries!.removeAt(index);

    await fetchAllReports(
        this.budget!, UserApi.getInstance().getUserProfile()!.username)
        .then((entries) {
      if (entries != null) {
        _reports = entries;
      }
    });

    notifyListeners();
  }

  Future addUTUBudgetEntry(Budget budget, String a, String b, String c,
      String d, String e, String f, String g) async {
    await BudgetApi.createUTUBudget(
        a,
        b,
        c,
        d,
        e,
        f,
        g);

    await fetchAllEntries(budget);
  }

  Future addUTOBudgetEntry(Budget budget, String a, String b, String c,
      String d, String e, String f, String g) async {
    await BudgetApi.createUTOBudget(
        a,
        b,
        c,
        d,
        e,
        f,
        g);

    await fetchAllEntries(budget);
  }

  Future editBudgetEntry(Budget budget, BudgetEntry be, String a, String b,
      String c, String d, String e, String f, String g) async {
    await BudgetApi.editBudgetEntry(
        a,
        b,
        c,
        d,
        e,
        f,
        g);

    var index = _entries!
        .indexWhere((element) => element.budgetEntryID == be.budgetEntryID);
    _entries!.removeAt(index);

    await fetchAllEntries(budget);
  }
}
