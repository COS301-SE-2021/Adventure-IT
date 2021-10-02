import 'package:adventure_it/Providers/media_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/api/mediaAPI.dart';

import 'AdventurePage.dart';
import 'Navbar.dart';

class MediaPage extends StatelessWidget {
  late final Adventure? adventure;

  MediaPage(Adventure? a) {
    this.adventure = a;
  }

  Future<List<PlatformFile>?> openFileExplorer() async {
    try {
      return (await FilePicker.platform.pickFiles(
              allowMultiple: true,
              type: FileType.custom,
              onFileLoading: (FilePickerStatus status) => print(status),
              allowedExtensions: ['jpg', 'png', 'gif', 'mp4']))
          ?.files;
    } on PlatformException catch (e) {
      print("Unsupported operation" + e.toString());
      return null;
    } catch (ex) {
      print(ex);
      return null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => MediaModel(adventure!, context),
        builder: (context, widget) => Scaffold(
              drawer: NavDrawer(),
              backgroundColor: Theme.of(context).scaffoldBackgroundColor,
              appBar: AppBar(
                  title: Center(
                      child: Text("Media",
                          style: new TextStyle(
                              color: Theme.of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color))),
                  iconTheme: IconThemeData(
                      color: Theme.of(context).textTheme.bodyText1!.color),
                  backgroundColor: Theme.of(context).primaryColorDark),
              body: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Spacer(),
                    Container(
                        height: MediaQuery.of(context).size.height * 0.80,
                        width: MediaQuery.of(context).size.width * 0.95,
                        child: MediaList(adventure)),
                    SizedBox(height: MediaQuery.of(context).size.height / 60),
                    Spacer(),
                    Row(children: [
                      Expanded(
                        flex: 1,
                        child: Container(
                            decoration: BoxDecoration(
                                color: Theme.of(context).accentColor,
                                shape: BoxShape.circle),
                            child: IconButton(
                                onPressed: () {
                                  Navigator.pushReplacement(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              AdventurePage(adventure)));
                                },
                                icon: const Icon(
                                    Icons.arrow_back_ios_new_rounded),
                                color: Theme.of(context).primaryColorDark)),
                      ),
                      Expanded(
                        flex: 1,
                        child: Container(
                          decoration: BoxDecoration(
                              color: Theme.of(context).accentColor,
                              shape: BoxShape.circle),
                          child: IconButton(
                              onPressed: () {
                                {
                                  openFileExplorer().then((value) {
                                    if (value != null) {
                                      Provider.of<MediaModel>(context,
                                              listen: false)
                                          .addMedia(value);
                                    }
                                  });
                                }
                              },
                              icon: const Icon(Icons.add),
                              color: Theme.of(context).primaryColorDark),
                        ),
                      ),
                      Expanded(
                        flex: 1,
                        child: Container(),
                      ),
                    ]),
                    SizedBox(height: MediaQuery.of(context).size.height / 60),
                  ]),
            ));
  }
}

class MediaList extends StatelessWidget {
  late final Adventure? adventure;

  MediaList(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MediaModel>(builder: (context, mediaModel, child) {
      if (mediaModel.media == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (mediaModel.media!.length > 0) {
        return Container(
            padding: EdgeInsets.symmetric(
                horizontal: MediaQuery.of(context).size.width * 0.1),
            child: GridView.builder(
              itemCount: mediaModel.media!.length,
              itemBuilder: (BuildContext context, int index) => new Card(
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(24)),
                  clipBehavior: Clip.antiAlias,
                  color: Theme.of(context).primaryColorDark,
                  child: Column(children: [
                    Expanded(
                      flex: 8,
                      child: Ink.image(
                          image: mediaModel.media!
                                  .elementAt(index)
                                  .type
                                  .contains("mp4")
                              ? Image.asset("custom_images/playbutton.png").image
                              : NetworkImage(mainApi +
                                  "/media/mediaUploaded/" +
                                  mediaModel.media!.elementAt(index).id),
                          fit: BoxFit.cover),
                    ),
                    Expanded(
                        flex: 6,
                        child: Padding(
                            padding: EdgeInsets.all(2),
                            child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Spacer(),
                                  Text(mediaModel.media!.elementAt(index).name,
                                      textAlign: TextAlign.center,
                                      overflow: TextOverflow.ellipsis,
                                      maxLines: 1,
                                      style: TextStyle(
                                          color: Theme.of(context)
                                              .textTheme
                                              .bodyText1!
                                              .color)),
                                  Spacer(),
                                  Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.start,
                                      children: [
                                        Spacer(),
                                        Expanded(
                                            flex: 6,
                                            child: ElevatedButton(
                                                child: Icon(
                                                  Icons.download,
                                                  color: Theme.of(context)
                                                      .textTheme
                                                      .bodyText1!
                                                      .color,
                                                ),
                                                style: ElevatedButton.styleFrom(
                                                  primary: Theme.of(context)
                                                      .accentColor,
                                                  padding: EdgeInsets.symmetric(
                                                      horizontal:
                                                          MediaQuery.of(context)
                                                                  .size
                                                                  .width *
                                                              0.02,
                                                      vertical:
                                                          MediaQuery.of(context)
                                                                  .size
                                                                  .height *
                                                              0.005),
                                                ),
                                                onPressed: () {
                                                  if (kIsWeb) {
                                                    MediaApi
                                                        .web_requestMediaDownload(
                                                            mediaModel.media!
                                                                .elementAt(
                                                                    index));
                                                  } else {
                                                    MediaApi
                                                        .requestMediaDownload(
                                                            context,
                                                            mediaModel.media!
                                                                .elementAt(
                                                                    index));
                                                  }
                                                })),
                                        Spacer(),
                                        Expanded(
                                            flex: 6,
                                            child: ElevatedButton(
                                              child: Icon(
                                                Icons.delete,
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color,
                                              ),
                                              style: ElevatedButton.styleFrom(
                                                side: BorderSide(
                                                    width: 1.0,
                                                    color: Theme.of(context)
                                                        .accentColor),
                                                primary: Theme.of(context)
                                                    .primaryColorDark,
                                                padding: EdgeInsets.symmetric(
                                                    horizontal:
                                                        MediaQuery.of(context)
                                                                .size
                                                                .width *
                                                            0.02,
                                                    vertical:
                                                        MediaQuery.of(context)
                                                                .size
                                                                .height *
                                                            0.005),
                                              ),
                                              onPressed: () {
                                                Provider.of<MediaModel>(context,
                                                        listen: false)
                                                    .removeMedia(mediaModel
                                                        .media!
                                                        .elementAt(index)
                                                        .id);
                                              },
                                            )),
                                        Spacer(),
                                      ]),
                                ]))),
                    Spacer(),
                  ])),
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: MediaQuery.of(context).size.height >
                        MediaQuery.of(context).size.width
                    ? 2
                    : 4,
                crossAxisSpacing: 4.0,
                mainAxisSpacing: 4.0,
              ),
            ));
      } else {
        return Center(
            child: Text("A picture's worth a thousand words!",
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}
