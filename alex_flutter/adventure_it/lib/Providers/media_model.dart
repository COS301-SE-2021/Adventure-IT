import 'dart:io';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/media.dart';
import 'package:adventure_it/api/mediaAPI.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:time_machine/time_machine.dart';


class MediaModel extends ChangeNotifier {
  List<Media>? _media = null;
  Adventure? adventure=null;

  MediaModel(Adventure a) {
    this.adventure=a;
    fetchAllMedia().then((media) => media != null? _media = media:List.empty());
  }

  List<Media>? get media => _media?.toList();

  Future fetchAllMedia() async {
    _media = await MediaApi.getAllMedia(adventure!);

    notifyListeners();
  }

  Future addMedia(List<PlatformFile> files) async {
    await MediaApi.addMedia(files,adventure!);

    fetchAllMedia();

    notifyListeners();
  }

  // Future deleteAdventure(Adventure adventure) async {
  //   await AdventureApi.removeAdventure(adventure.adventureId);
  //
  //   var index = _adventures!.indexWhere((element) => element.adventureId == adventure.adventureId);
  //   _adventures!.removeAt(index);
  //
  //   notifyListeners();
  // }

}

// class FileModel extends ChangeNotifier {
//   List<Media>? _media = null;
//   Adventure? adventure=null;
//
//   FileModel(Adventure a) {
//     this.adventure=a;
//     fetchAllFiles().then((media) => media != null? _media = media:List.empty());
//   }
//
//   List<Media>? get media => _media?.toList();
//
//   Future fetchAllFiles() async {
//     _media = await MediaApi.getAllFiles(adventure);
//
//     notifyListeners();
//   }
//
//   Future addFiles(List<PlatformFile> files) async {
//     await MediaApi.addFile(files,adventure);
//
//     fetchAllFiles();
//
//     notifyListeners();
//   }
//
// // Future deleteAdventure(Adventure adventure) async {
// //   await AdventureApi.removeAdventure(adventure.adventureId);
// //
// //   var index = _adventures!.indexWhere((element) => element.adventureId == adventure.adventureId);
// //   _adventures!.removeAt(index);
// //
// //   notifyListeners();
// // }
//
// }