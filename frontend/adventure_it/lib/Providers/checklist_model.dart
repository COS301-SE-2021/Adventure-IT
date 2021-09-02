import 'package:adventure_it/api/checklist.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/checklistAPI.dart';
import 'package:adventure_it/api/checklistEntry.dart';
import 'package:adventure_it/api/userAPI.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:flutter/cupertino.dart';

class DeletedChecklistModel extends ChangeNotifier {
  List<Checklist>? _deletedChecklists;
  List<UserProfile?>? _creators;

  DeletedChecklistModel(Adventure a) {
    fetchAllDeletedChecklists(a).then((deletedChecklists) =>
        deletedChecklists != null
            ? _deletedChecklists = deletedChecklists
            : List.empty());
  }

  List<Checklist>? get deletedChecklists => _deletedChecklists?.toList();
  List<UserProfile?>? get creators => _creators?.toList();

  Future fetchAllDeletedChecklists(Adventure a) async {
    _deletedChecklists = await ChecklistApi.getDeletedChecklist(a.adventureId);

    var total = List<UserProfile?>.filled(deletedChecklists!.length, null, growable: true);
    total.removeRange(0, deletedChecklists!.length);
    for (var b in deletedChecklists!) {
      await UserApi.getInstance().findUser(b.creatorID).then((value) {
        total.add(value);
      });
    }

    this._creators = total;

    notifyListeners();
  }

  Future hardDeleteChecklist(Checklist c) async {
    await ChecklistApi.hardDeleteChecklist(c.id);

    var index = _deletedChecklists!.indexWhere((element) => element.id == c.id);
    _deletedChecklists!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }

  Future restoreChecklist(Checklist check) async {
    await ChecklistApi.restoreChecklist(check.id);

    var index =
        _deletedChecklists!.indexWhere((element) => element.id == check.id);
    _deletedChecklists!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }
}

class ChecklistModel extends ChangeNotifier {
  List<Checklist>? _checklists;

  ChecklistModel(Adventure a) {
    fetchAllChecklists(a).then((checklists) =>
        checklists != null ? _checklists = checklists : List.empty());
  }

  List<Checklist>? get checklists => _checklists?.toList();

  Future fetchAllChecklists(Adventure a) async {
    _checklists = await ChecklistApi.getChecklists(a);

    notifyListeners();
  }

  Future addChecklist(
      Adventure adv, String a, String b, String c, String d) async {
    await ChecklistApi.createChecklist(a, b, c, d);

    await fetchAllChecklists(adv);
  }

  Future softDeleteChecklist(Checklist c) async {
    await ChecklistApi.softDeleteChecklist(c.id);

    var index = _checklists!.indexWhere((element) => element.id == c.id);
    _checklists!.removeAt(index);

    notifyListeners();
  }
}

class ChecklistEntryModel extends ChangeNotifier {
  List<ChecklistEntry>? _entries;
  Checklist? c;

  ChecklistEntryModel(Checklist c) {
    this.c = c;
    fetchAllEntries(c)
        .then((entries) => entries != null ? _entries = entries : List.empty());
  }

  List<ChecklistEntry>? get entries => _entries?.toList();

  Future fetchAllEntries(Checklist c) async {
    _entries = await ChecklistApi.getChecklistEntries(c);

    notifyListeners();
  }

  Future addChecklistEntry(Checklist c, String a, String b) async {
    await ChecklistApi.createChecklistEntry(a, b);

    await fetchAllEntries(c);
  }

  Future editChecklistEntry(ChecklistEntry e, Checklist c, String s, String u) async {
    await ChecklistApi.checklistEdit(e, s, u);

    var index = _entries!.indexWhere((element) => element.id == e.id);
    _entries!.removeAt(index);

    fetchAllEntries(c);

    notifyListeners();
  }

  Future deleteChecklistEntry(ChecklistEntry c) async {
    await ChecklistApi.deleteChecklistEntry(c);

    var index = _entries!.indexWhere((element) => element.id == c.id);
    _entries!.removeAt(index);

    notifyListeners();
  }

  Future markEntry(ChecklistEntry c) async {
    await ChecklistApi.completeEntry(c.id);

    fetchAllEntries(this.c!);

    notifyListeners();
  }
}
