import 'package:adventure_it/Providers/media_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import '../api/mediaAPI.dart';
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
            child: StaggeredGridView.countBuilder(
          crossAxisCount: 4,
          itemCount: documentModel.documents!.length,
          itemBuilder: (BuildContext context, int index) => new Card(
              color: Theme.of(context).primaryColorDark,
              child: InkWell(
                  onTap: () {
                    if (kIsWeb) {
                      DocumentApi.web_requestDocumentDownload(
                          documentModel.documents!.elementAt(index));
                    } else {
                      DocumentApi.requestDocumentDownload(
                          context, documentModel.documents!.elementAt(index));
                    }
                  },
                  child: Stack(overflow: Overflow.visible, children: <Widget>[

                    Center(
                        child: Container(
                            height: double.infinity,
                            width: double.infinity,
                            decoration: new BoxDecoration(
                              image: new DecorationImage(
                                  fit: BoxFit.cover,
                                  image: documentModel.documents!
                                          .elementAt(index)
                                          .type
                                          .contains("pdf")
                                      ? Image.asset("assets/logo.png").image
                                      : NetworkImage("http://" +
                                          mediaApi +
                                          "/media/documentUploaded/" +
                                          documentModel.documents!
                                              .elementAt(index)
                                              .id)),
                            ),
                            child: Padding(
                                padding: const EdgeInsets.all(4),
                                child: Text(
                                    documentModel.documents!.elementAt(index).name,
                                    textAlign: TextAlign.center,
                                    style: TextStyle(
                                      color: Theme.of(context)
                                          .scaffoldBackgroundColor,
                                      fontSize: 12 *
                                          MediaQuery.of(context)
                                              .textScaleFactor,
                                      fontWeight: FontWeight.bold,
                                    ))))),
                    Positioned(
                      right: -5.0,
                      top: -5.0,
                      child: InkResponse(
                        onTap: (){
                          Provider.of<MediaModel>(context,
                              listen: false)
                              .removeMedia(documentModel.documents!.elementAt(index).id);
                        },
                        child: CircleAvatar(
                          radius:MediaQuery.of(context).size.width*0.02,
                          child: Icon(Icons.close,
                              color: Theme.of(context).primaryColorDark),
                          backgroundColor: Theme.of(context).accentColor,
                        ),
                      ),
                    ),
                  ]))),
          staggeredTileBuilder: (int index) =>
              new StaggeredTile.count(2, index.isEven ? 2 : 1),
          mainAxisSpacing: 4.0,
          crossAxisSpacing: 4.0,
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
