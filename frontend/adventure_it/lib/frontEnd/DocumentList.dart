import 'package:flutter/material.dart';
import 'package:adventure_it/Providers/media_model.dart';
import 'package:adventure_it/constants.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:provider/provider.dart';
import 'package:adventure_it/api/mediaAPI.dart';

import 'Navbar.dart';
import 'Profile.dart';

class DocumentPage extends StatelessWidget {
  DocumentPage();

  Future<List<PlatformFile>?> openFileExplorer() async {
    try {
      return (await FilePicker.platform.pickFiles(
              allowMultiple: true,
              type: FileType.custom,
              onFileLoading: (FilePickerStatus status) => print(status),
              allowedExtensions: ['jpg', 'png', 'gif', 'pdf']))
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
        create: (context) => DocumentModel(),
        builder: (context, widget) => Scaffold(
              drawer: NavDrawer(),
              backgroundColor: Theme.of(context).scaffoldBackgroundColor,
              appBar: AppBar(
                  title: Center(
                      child: Text("Your Documents",
                          style: new TextStyle(
                              color: Theme.of(context)
                                  .textTheme
                                  .bodyText1!
                                  .color))),
                  backgroundColor: Theme.of(context).primaryColorDark),
              body: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Spacer(),
                    Container(
                        height: MediaQuery.of(context).size.height * 0.80,
                        width: MediaQuery.of(context).size.width * 0.95,
                        child: DocumentList()),
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
                                              ProfileCaller()));
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
                                      Provider.of<DocumentModel>(context,
                                              listen: false)
                                          .addDocument(value);
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

class DocumentList extends StatelessWidget {
  DocumentList();

  @override
  Widget build(BuildContext context) {
    return Consumer<DocumentModel>(builder: (context, documentModel, child) {
      if (documentModel.documents == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (documentModel.documents!.length > 0) {
        return Container(
            padding: EdgeInsets.symmetric(
                horizontal: MediaQuery.of(context).size.width * 0.1),
            child: GridView.builder(
              itemCount: documentModel.documents!.length,
              itemBuilder: (BuildContext context, int index) => new Card(
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(24)),
                  clipBehavior: Clip.antiAlias,
                  color: Theme.of(context).primaryColorDark,
                  child: Column(children: [
                    Expanded(
                      flex: 9,
                      child: Ink.image(
                          image: documentModel.documents!
                              .elementAt(index)
                              .type
                              .contains("pdf")
                              ? Image.asset("assets/pdf button.png").image
                              : NetworkImage("http://" +
                              mediaApi +
                              "/media/mediaUploaded/" +
                              documentModel.documents!.elementAt(index).id),
                          fit: BoxFit.cover),
                    ),
                    Expanded(
                        flex: 4,
                        child: Padding(
                            padding: EdgeInsets.all(2),
                            child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Spacer(),
                                  Text(documentModel.documents!.elementAt(index).name,
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
                                      mainAxisAlignment: MainAxisAlignment.start,
                                      children: [
                                        Spacer(),
                                        Expanded(
                                            flex: 6,
                                            child: ElevatedButton(
                                                child: Text("Download",
                                                    style: new TextStyle(
                                                        fontWeight: FontWeight.bold,
                                                        color: Theme.of(context)
                                                            .textTheme
                                                            .bodyText1!
                                                            .color)),
                                                style: ElevatedButton.styleFrom(
                                                  primary: Theme.of(context)
                                                      .accentColor,
                                                  padding: EdgeInsets.symmetric(
                                                      horizontal:
                                                      MediaQuery.of(context)
                                                          .size
                                                          .width *
                                                          0.03,
                                                      vertical:
                                                      MediaQuery.of(context)
                                                          .size
                                                          .height *
                                                          0.01),
                                                ),
                                                onPressed: () {
                                                  if (kIsWeb) {
                                                    DocumentApi
                                                        .web_requestDocumentDownload(
                                                        documentModel.documents!
                                                            .elementAt(
                                                            index));
                                                  } else {
                                                    DocumentApi
                                                        .requestDocumentDownload(
                                                        context,
                                                        documentModel.documents!
                                                            .elementAt(
                                                            index));
                                                  }
                                                })),
                                        Spacer(),
                                        Expanded(
                                            flex: 6,
                                            child: ElevatedButton(
                                              child: Text("Remove",
                                                  style: new TextStyle(
                                                      fontWeight: FontWeight.bold,
                                                      color: Theme.of(context)
                                                          .accentColor)),
                                              style: ElevatedButton.styleFrom(
                                                side: BorderSide(width: 1.0, color: Theme.of(context)
                                                    .accentColor),
                                                primary:
                                                Theme.of(context).primaryColorDark,
                                                padding: EdgeInsets.symmetric(
                                                    horizontal:
                                                    MediaQuery.of(context)
                                                        .size
                                                        .width *
                                                        0.03,
                                                    vertical: MediaQuery.of(context)
                                                        .size
                                                        .height *
                                                        0.01),
                                              ),
                                              onPressed: () {
                                                Provider.of<DocumentModel>(context,
                                                    listen: false)
                                                    .removeDocument(documentModel.documents!
                                                    .elementAt(index)
                                                    .id);
                                              },
                                            )),
                                        Spacer(),
                                      ]),
                                ]))),Spacer(),
                  ])),
              gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: MediaQuery.of(context).size.height >
                    MediaQuery.of(context).size.width
                    ? 1
                    : 2,
                crossAxisSpacing: 4.0,
                mainAxisSpacing: 4.0,
              ),
            ));
      } else {
        return Center(
            child: Text("Some things should be kept private",
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}
