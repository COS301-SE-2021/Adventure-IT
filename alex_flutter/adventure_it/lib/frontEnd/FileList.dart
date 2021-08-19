
import 'package:adventure_it/Providers/media_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/mediaAPI.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';

import 'package:flutter/material.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';
import 'Navbar.dart';
class Files extends StatelessWidget {
  Adventure? adventure;

  Files(Adventure? a) {
    this.adventure = a;
  }

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
        create: (context) => FileModel(adventure!),
        builder: (context, widget) => Scaffold(
          drawer: NavDrawer(),
          backgroundColor: Theme.of(context).scaffoldBackgroundColor,
          appBar: AppBar(
              title: Center(
                  child: Text("Files",
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
                                  Provider.of<FileModel>(context,
                                      listen: false)
                                      .addFiles(value);
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
  Adventure? adventure;

  MediaList(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<FileModel>(builder: (context, fileModel, child) {
      if (fileModel.files == null) {
        return Center(
            child: CircularProgressIndicator(
                valueColor: new AlwaysStoppedAnimation<Color>(
                    Theme.of(context).accentColor)));
      } else if (fileModel.files!.length > 0) {
        return Container(
            child: StaggeredGridView.countBuilder(
              crossAxisCount: 4,
              itemCount: fileModel.files!.length,
              itemBuilder: (BuildContext context, int index) => new Card(
                  color: Theme.of(context).primaryColorDark,
                  child: InkWell(
                      onTap: () {
                        if (kIsWeb) {
                          FileApi.web_requestFileDownload(
                              fileModel.files!.elementAt(index));
                        } else {
                          FileApi.requestFileDownload(
                              context, fileModel.files!.elementAt(index));
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
                                      image: fileModel.files!
                                          .elementAt(index)
                                          .type
                                          .contains("pdf")
                                          ? Image.asset("assets/logo.png").image
                                          : NetworkImage("http://" +
                                          mediaApi +
                                          "/media/fileUploaded/" +
                                          fileModel.files!
                                              .elementAt(index)
                                              .id)),
                                ),
                                child: Padding(
                                    padding: const EdgeInsets.all(4),
                                    child: Text(
                                        fileModel.files!.elementAt(index).name,
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
                          right: -10.0,
                          top: -10.0,
                          child: InkResponse(
                            onTap: (){
                              Provider.of<FileModel>(context,
                                  listen: false)
                                  .removeFiles(fileModel.files!.elementAt(index).id);
                            },
                            child: CircleAvatar(
                              radius:MediaQuery.of(context).size.width*0.02,
                              child: Icon(Icons.close,
                                  size: MediaQuery.of(context).size.width*0.02,
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
            child: Text("File everything under 'F' for Fun!" ,
                textAlign: TextAlign.center,
                style: TextStyle(
                    fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                    color: Theme.of(context).textTheme.bodyText1!.color)));
      }
    });
  }
}
