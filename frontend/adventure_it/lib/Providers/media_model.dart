import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/media.dart';
import 'package:adventure_it/api/document.dart';
import 'package:adventure_it/api/mediaAPI.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';

class MediaModel extends ChangeNotifier {
  List<Media>? _media;
  Adventure? adventure;
  BuildContext? context;

  MediaModel(Adventure a, context) {
    this.adventure = a;
    this.context=context;
    fetchAllMedia()
        .then((media) => media != null ? _media = media : List.empty());
  }

  List<Media>? get media => _media?.toList();

  Future fetchAllMedia() async {
    _media = await MediaApi.getAllMedia(adventure!,context);

    notifyListeners();
  }

  Future addMedia(List<PlatformFile> files) async {
    await MediaApi.addMedia(files, adventure!,context);

    fetchAllMedia();

    notifyListeners();
  }

  Future removeMedia(String id) async {
    await MediaApi.removeMedia(id,context);

    var index = _media!.indexWhere((element) => element.id == id);
    _media!.removeAt(index);

    notifyListeners();
  }
}

class FileModel extends ChangeNotifier {
  List<Media>? _files;
  Adventure? adventure;
  BuildContext? context;

  FileModel(Adventure a,context) {
    this.adventure = a;
    this.context=context;
    fetchAllFiles().then((file) => file != null ? _files = file : List.empty());
  }

  List<Media>? get files => _files?.toList();

  Future fetchAllFiles() async {
    _files = await FileApi.getAllFiles(adventure!,context);

    notifyListeners();
  }

  Future addFiles(List<PlatformFile> files) async {
    await FileApi.addFile(files, adventure!,context);

    fetchAllFiles();

    notifyListeners();
  }

  Future removeFiles(String id) async {
    await FileApi.removeFile(id,context);

    var index = _files!.indexWhere((element) => element.id == id);
    _files!.removeAt(index);

    notifyListeners();
  }
}

class DocumentModel extends ChangeNotifier {
  List<Documents>? _documents;
  BuildContext? context;

  DocumentModel(context) {
    this.context=context;
    fetchAllDocuments().then((documents) =>
        documents != null ? _documents = documents : List.empty());
  }

  List<Documents>? get documents => _documents?.toList();

  Future fetchAllDocuments() async {
    _documents = await DocumentApi.getAllDocuments(context);

    notifyListeners();
  }

  Future addDocument(List<PlatformFile> files) async {
    await DocumentApi.addDocument(files,context);

    fetchAllDocuments();

    notifyListeners();
  }

  Future removeDocument(String id) async {
    await DocumentApi.removeDocument(id,context);

    var index = _documents!.indexWhere((element) => element.id == id);
    _documents!.removeAt(index);

    notifyListeners();
  }
}

