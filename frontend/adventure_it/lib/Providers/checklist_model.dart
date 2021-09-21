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
  BuildContext? context;

  DeletedChecklistModel(Adventure a,context) {
    this.context=context;
    fetchAllDeletedChecklists(a).then((deletedChecklists) =>
        deletedChecklists != null
            ? _deletedChecklists = deletedChecklists
            : List.empty());
  }

  List<Checklist>? get deletedChecklists => _deletedChecklists?.toList();
  List<UserProfile?>? get creators => _creators?.toList();

  Future fetchAllDeletedChecklists(Adventure a) async {
    _deletedChecklists = await ChecklistApi.getDeletedChecklist(a.adventureId,context);

    var total = List<UserProfile?>.filled(deletedChecklists!.length, null, growable: true);
    total.removeRange(0, deletedChecklists!.length);
    for (var b in deletedChecklists!) {
      await UserApi.getInstance().findUser(b.creatorID,context).then((value) {
        total.add(value);
      });
    }

    this._creators = total;

    notifyListeners();
  }

  Future hardDeleteChecklist(Checklist c) async {
    await ChecklistApi.hardDeleteChecklist(c.id,context);

    var index = _deletedChecklists!.indexWhere((element) => element.id == c.id);
    _deletedChecklists!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }

  Future restoreChecklist(Checklist check) async {
    await ChecklistApi.restoreChecklist(check.id,context);

    var index =
        _deletedChecklists!.indexWhere((element) => element.id == check.id);
    _deletedChecklists!.removeAt(index);
    _creators!.removeAt(index);

    notifyListeners();
  }
}

class ChecklistModel extends ChangeNotifier {
  List<Checklist>? _checklists;
  BuildContext? context;

  ChecklistModel(Adventure a,context) {
    this.context=context;
    fetchAllChecklists(a).then((checklists) =>
        checklists != null ? _checklists = checklists : List.empty());
  }

  List<Checklist>? get checklists => _checklists?.toList();

  Future fetchAllChecklists(Adventure a) async {
    _checklists = await ChecklistApi.getChecklists(a,context);

    notifyListeners();
  }

  Future addChecklist(
      Adventure adv, String a, String b, String c, String d) async {
    await ChecklistApi.createChecklist(a, b, c, d,context);

    await fetchAllChecklists(adv);
  }

  Future softDeleteChecklist(Checklist c) async {
    await ChecklistApi.softDeleteChecklist(c.id,context);

    var index = _checklists!.indexWhere((element) => element.id == c.id);
    _checklists!.removeAt(index);

    notifyListeners();
  }
}

class ChecklistEntryModel extends ChangeNotifier {
  List<ChecklistEntry>? _entries;
  Checklist? c;
  BuildContext? context;

  ChecklistEntryModel(Checklist c, context) {
    this.c = c;
    this.context=context;
    fetchAllEntries(c)
        .then((entries) => entries != null ? _entries = entries : List.empty());
  }

  List<ChecklistEntry>? get entries => _entries?.toList();

  Future fetchAllEntries(Checklist c) async {
    _entries = await ChecklistApi.getChecklistEntries(c,context);

    notifyListeners();
  }

  Future addChecklistEntry(Checklist c, String a, String b, String u) async {
    await ChecklistApi.createChecklistEntry(a, b, u,context);

    await fetchAllEntries(c);
  }

  Future editChecklistEntry(ChecklistEntry e, Checklist c, String s, String u) async {
    await ChecklistApi.checklistEdit(e, s, u,context);

    fetchAllEntries(c);

    notifyListeners();
  }

  Future deleteChecklistEntry(ChecklistEntry c) async {
    await ChecklistApi.deleteChecklistEntry(c,context);

    var index = _entries!.indexWhere((element) => element.id == c.id);
    _entries!.removeAt(index);

    notifyListeners();
  }

  Future markEntry(ChecklistEntry c) async {
    await ChecklistApi.completeEntry(c.id,context);

    fetchAllEntries(this.c!);

    notifyListeners();
  }
}
