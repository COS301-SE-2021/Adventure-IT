import 'package:adventure_it/api/checklist.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/checklistAPI.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';

class DeletedChecklistModel extends ChangeNotifier{
  List<Checklist>? _deletedChecklists=null;
  DeletedChecklistModel(Adventure a) {

    fetchAllDeletedChecklists(a).then((deletedChecklists) => deletedChecklists != null? _deletedChecklists = deletedChecklists:List.empty());
  }

  List<Checklist>? get deletedChecklists => _deletedChecklists?.toList();

  Future fetchAllDeletedChecklists(Adventure a) async {
    _deletedChecklists = await ChecklistApi.getDeletedChecklist(a);

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
    fetchAllChecklists(a).then((checklists) => checklists != null? _checklists = checklists:List.empty());
  }

  List<Checklist>? get checklists => _checklists?.toList();


  Future fetchAllChecklists(Adventure a) async {
    _checklists = await ChecklistApi.getChecklists(a);

    notifyListeners();
  }



  Future softDeleteChecklist(Checklist c) async {
    await ChecklistApi.softDeleteChecklist(c.id);

    var index = _checklists!.indexWhere((element) => element.id == c.id);
    _checklists!.removeAt(index);

    notifyListeners();
  }



}