import 'package:adventure_it/api/checklist.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/checklistAPI.dart';
import 'package:adventure_it/api/checklistEntry.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';

class DeletedChecklistModel extends ChangeNotifier{
  List<Checklist>? _deletedChecklists=null;
  DeletedChecklistModel(Adventure a) {

    fetchAllDeletedChecklists(a).then((deletedChecklists) => deletedChecklists != null? _deletedChecklists = deletedChecklists:List.empty());
  }

  List<Checklist>? get deletedChecklists => _deletedChecklists?.toList();

  Future fetchAllDeletedChecklists(Adventure a) async {
    _deletedChecklists = await ChecklistApi.getDeletedChecklist(a.adventureId);

    notifyListeners();
  }

  Future hardDeleteChecklist(Checklist c) async {
    await ChecklistApi.hardDeleteChecklist(c.id);

    var index = _deletedChecklists!.indexWhere((element) => element.id == c.id);
    _deletedChecklists!.removeAt(index);

    notifyListeners();
  }

  Future restoreChecklist(Checklist check) async {
    await ChecklistApi.restoreChecklist(check.id);
    print('in here');

    var index = _deletedChecklists!.indexWhere((element) => element.id == check.id);
    _deletedChecklists!.removeAt(index);

    notifyListeners();
  }

}

class ChecklistModel extends ChangeNotifier {
  List<Checklist>? _checklists = null;


  ChecklistModel(Adventure a) {
    fetchAllChecklists(a).then((checklists) =>
    checklists != null ? _checklists = checklists : List.empty());
  }

  List<Checklist>? get checklists => _checklists?.toList();


  Future fetchAllChecklists(Adventure a) async {
    _checklists = await ChecklistApi.getChecklists(a);

    notifyListeners();
  }

  Future addChecklist(Adventure adv, String a, String b, String c, String d) async {
    await ChecklistApi.createChecklist(a, b, c, d);

    for(var index=0; index < checklists!.length; index++) {
      checklists!.removeAt(index);
    }

    fetchAllChecklists(adv);

    notifyListeners();
  }

  Future softDeleteChecklist(Checklist c) async {
    await ChecklistApi.softDeleteChecklist(c.id);

    var index = _checklists!.indexWhere((element) => element.id == c.id);
    _checklists!.removeAt(index);

    notifyListeners();
  }
}





class ChecklistEntryModel extends ChangeNotifier {
  List<ChecklistEntry>? _entries = null;
  Checklist? c;


  ChecklistEntryModel(Checklist c) {
    this.c=c;
    fetchAllEntries(c).then((entries) =>
    entries != null
        ? _entries = entries
        : List.empty());
  }


  List<ChecklistEntry>? get entries => _entries?.toList();


  Future fetchAllEntries(Checklist c) async {
    _entries = await ChecklistApi.getChecklistEntries(c);

    notifyListeners();
  }

  Future addChecklistEntry(Checklist c, String a, String b) async {
    await ChecklistApi.addChecklistEntry(a, b);

    for(var index=0; index < _entries!.length; index++) {
      _entries!.removeAt(index);
    }

    fetchAllEntries(c);

    notifyListeners();
}

  Future editChecklistEntry(ChecklistEntry e, Checklist c, String s) async {
    await ChecklistApi.checklistEdit(e, s);

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
    var index = _entries!.indexWhere((element) => element.id == c.id);

    _entries!.elementAt(index).completed=!_entries!.elementAt(index).completed;


    notifyListeners();
  }
}