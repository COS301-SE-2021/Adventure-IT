import 'dart:async';
import 'dart:convert';
import 'dart:core';
import 'dart:io';
import 'dart:isolate';
import 'dart:ui';
import 'package:filesystem_picker/filesystem_picker.dart';
import 'package:flutter_downloader/flutter_downloader.dart';
import 'package:mime/mime.dart';
import 'dart:html' as html;
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/placeSearch.dart';
import 'package:adventure_it/api/registerUser.dart';
import 'package:adventure_it/api/user_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:file_picker/file_picker.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'package:path_provider/path_provider.dart';
import 'package:downloads_path_provider/downloads_path_provider.dart';

import 'loginUser.dart';
import 'media.dart';
import 'userProfile.dart';

class MediaApi {
  static Future<List<Media>> getAllMedia(Adventure a) async {
    http.Response response = await _getAllMedia(a.adventureId);

    if (response.statusCode != 200) {
      throw Exception('Failed to load media: ${response.body}');
    }

    List<Media> media = (jsonDecode(response.body) as List)
        .map((x) => Media.fromJson(x))
        .toList();

    return media;
  }

  static Future<http.Response> _getAllMedia(String adventureID) async {
    return http
        .get(Uri.http(mediaApi, "/media/getAdventureMediaList/" + adventureID));
  }

  static Future removeMedia(String id) async {
    http.Response response = await _removeMedia(id);

    if (response.statusCode != 200) {
      throw Exception('Failed to remove media: ${response.body}');
    }

  }

  static Future<http.Response> _removeMedia(String id) async {
    return http
        .get(Uri.http(mediaApi, "/media/deleteMedia/"+id+"/"+UserApi.getInstance().getUserProfile()!.userID));
  }

  static Future addMedia(List<PlatformFile> files, Adventure a) async {
    for (int i = 0; i < files.length; i++) {
      http.Response response = await _addMedia(files.elementAt(i), a);

      if (response.statusCode != 200) {
        throw Exception('Failed to upload media: ${response.body}');
      }
    }
  }

  static Future<http.Response> _addMedia(PlatformFile file, Adventure a) async {
    final mimeType = lookupMimeType(file.name); // 'image/jpeg'
    var request = http.MultipartRequest(
        'POST', Uri.parse('http://' + mediaApi + '/media/uploadMedia'));
    request.fields['userid'] = UserApi.getInstance().getUserProfile()!.userID;
    request.fields['adventureid'] = a.adventureId;
    request.files.add(http.MultipartFile.fromBytes(
        'file', file.bytes!.cast<int>(),
        filename: file.name, contentType: new MediaType.parse(mimeType!)));

    var x = await request.send();
    return await http.Response.fromStream(x);
  }

  static Future<void> requestMediaDownload(context, Media currentMedia) async {
    Directory? rootDirectory = await DownloadsPathProvider.downloadsDirectory;
    String? filepath = await FilesystemPicker.open(
        title: 'Save to Downloads folder',
        context: context,
        rootDirectory: rootDirectory!,
        fsType: FilesystemType.folder,
        pickText: 'Save file to this folder');
    if (filepath != null) {
      String? _taskid = await FlutterDownloader.enqueue(
        url: 'http://' + mediaApi + "/media/mediaUploaded/" + currentMedia.id,
        fileName: currentMedia.name,
        savedDir: filepath,
        showNotification: true,
        openFileFromNotification: true,
      );
      print(_taskid);
    }
  }

  static void web_requestMediaDownload(Media currentMedia) {
    html.window.open(
        'http://' + mediaApi + "/media/mediaUploaded/" + currentMedia.id,
        currentMedia.name);
  }

  static void downloadCallback(
      String id, DownloadTaskStatus status, int progress) {
    final SendPort? send =
        IsolateNameServer.lookupPortByName('downloader_send_port');
    send!.send([id, status, progress]);
  }
}
