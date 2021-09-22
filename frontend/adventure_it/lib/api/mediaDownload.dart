import 'dart:io';
import 'dart:isolate';
import 'dart:ui';
import 'package:path_provider/path_provider.dart' as path_provider;
import 'package:flutter_downloader/flutter_downloader.dart';
import 'package:filesystem_picker/filesystem_picker.dart' as filesystem_picker;

import '../constants.dart';

class MediaDownload {
  // Start: Singleton Design Pattern
  static MediaDownload _instance = new MediaDownload._();

  // Private constructor
  MediaDownload._() {
    initializeFlutterDownloader();
  }

  static MediaDownload getInstance() {
    return _instance;
  }

  // End: Singleton Design Pattern

  Future<void> initializeFlutterDownloader() async {
    await FlutterDownloader.initialize(debug: true);
  }

  Future<void> testDownload(context) async {
    Directory? rootDirectory =
        await path_provider.getApplicationDocumentsDirectory();
    String? targetDirectory = await filesystem_picker.FilesystemPicker.open(
        title: 'Save to folder',
        context: context,
        rootDirectory: rootDirectory,
        pickText: 'Save file to this folder');
    print(targetDirectory);
    await FlutterDownloader.enqueue(
      url: mainApi + '/media/uploaded/1ad40efd-2ea2-4cea-908c-47ee31daa9d1',
      savedDir: targetDirectory!,
      showNotification:
          true, // show download progress in status bar (for Android)
      openFileFromNotification:
          true, // click on notification to open downloaded file (for Android)
    );
    FlutterDownloader.registerCallback(downloadCallback);
  }

  Directory findRoot(FileSystemEntity entity) {
    final Directory parent = entity.parent;
    if (parent.path == entity.path) return parent;
    return findRoot(parent);
  }

  static void downloadCallback(
      String id, DownloadTaskStatus status, int progress) {
    final SendPort? send =
        IsolateNameServer.lookupPortByName('downloader_send_port');
    send!.send([id, status, progress]);
  }
}
