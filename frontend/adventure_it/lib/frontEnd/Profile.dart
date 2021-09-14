import 'package:adventure_it/Providers/user_model.dart';
import 'package:adventure_it/api/locationAPI.dart';
import 'package:flutter/material.dart';
import 'package:adventure_it/api/userProfile.dart';
import 'package:provider/provider.dart';
import '../constants.dart';
import 'DocumentList.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'Navbar.dart';
import 'package:adventure_it/api/userAPI.dart';

//User's profile page
class ProfileCaller extends StatefulWidget {
  @override
  Profile createState() => Profile();
}

class Profile extends State<ProfileCaller> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Profile",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            iconTheme: IconThemeData(
                color: Theme.of(context).textTheme.bodyText1!.color),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(children: [
          ProfileFutureBuilderCaller(),
          Container(
              padding: const EdgeInsets.only(left: 100.0, top: 0.0),
              child:
                  Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Center(
                          child: SizedBox(
                        width: MediaQuery.of(context).size.height * 0.2,
                      ))
                    ])
                //_buildList()
              ])),
        ]));
  }
}

class ProfileFutureBuilderCaller extends StatefulWidget {
  @override
  ProfileFutureBuilder createState() => ProfileFutureBuilder();
}

class ProfileFutureBuilder extends State<ProfileFutureBuilderCaller> {
  UserProfile? user;

  Future<List<PlatformFile>?> openFileExplorer() async {
    try {
      return (await FilePicker.platform.pickFiles(
              allowMultiple: false,
              type: FileType.custom,
              onFileLoading: (FilePickerStatus status) => print(status),
              allowedExtensions: ['jpg', 'png', 'gif']))
          ?.files;
    } on PlatformException catch (e) {
      print("Unsupported operation" + e.toString());
      return null;
    } catch (ex) {
      print(ex);
      return null;
    }
  }

  // final UserApi api = new UserApi();

  @override
  void initState() {
    super.initState();
    user = UserApi.getInstance().getUserProfile();
  }

  @override
  Widget build(BuildContext context) {
    if (user == null) {
      return Center(
          child: CircularProgressIndicator(
              valueColor: new AlwaysStoppedAnimation<Color>(
                  Theme.of(context).accentColor)));
    } else {
      return ChangeNotifierProvider(
          create: (context) => UserModel(),
          child: Consumer<UserModel>(builder: (context, userModel, child) {
            if (userModel.profile == null) {
              return Center(
                  child: CircularProgressIndicator(
                      valueColor: new AlwaysStoppedAnimation<Color>(
                          Theme.of(context).accentColor)));
            } else {
              return ClipRRect(
                  borderRadius: BorderRadius.all(Radius.circular(20.0)),
                  child: Container(
                      margin: EdgeInsets.symmetric(vertical: 10),
                      width: MediaQuery.of(context).size.width * 0.9,
                      height: MediaQuery.of(context).size.height * 0.45,
                      child: Card(
                          color: Theme.of(context).primaryColorDark,
                          child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                MaterialButton(
                                    child: Text("Flags",
                                        style: new TextStyle(
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color)),
                                    color: Theme.of(context).accentColor,
                                    padding: EdgeInsets.symmetric(
                                        horizontal: MediaQuery.of(context)
                                            .size
                                            .width *
                                            0.05,
                                        vertical: MediaQuery.of(context)
                                            .size
                                            .width *
                                            0.01),
                                    onPressed: () {
                                      LocationApi.getFlagList();
                                    }),
                                Center(
                                    child: Column(children: <Widget>[
                                  Stack(clipBehavior: Clip.none, children: <
                                      Widget>[
                                    CachedNetworkImage(
                                        useOldImageOnUrlChange: true,
                                        imageUrl: userApi +
                                            "/user/viewPicture/" +
                                            userModel.profile!.profileID,
                                        imageBuilder: (context,
                                                imageProvider) =>
                                            Container(
                                                width: 100,
                                                height: 100,
                                                decoration: new BoxDecoration(
                                                    border: Border.all(
                                                      color: Theme.of(context)
                                                          .accentColor,
                                                      width: 3,
                                                    ),
                                                    shape: BoxShape.circle,
                                                    image: DecorationImage(
                                                        fit: BoxFit.fill,
                                                        image: imageProvider))),
                                        placeholder: (context, url) =>
                                            Container(
                                                width: 100,
                                                height: 100,
                                                decoration: new BoxDecoration(
                                                    border: Border.all(
                                                      color: Theme.of(context)
                                                          .accentColor,
                                                      width: 3,
                                                    ),
                                                    shape: BoxShape.circle,
                                                    image: DecorationImage(
                                                        fit: BoxFit.fitWidth,
                                                        image: AssetImage(
                                                            "pfp.png")))),
                                        errorWidget: (context, url, error) =>
                                            Container(
                                                width: 100,
                                                height: 100,
                                                decoration: new BoxDecoration(
                                                    border: Border.all(
                                                      color: Theme.of(context)
                                                          .accentColor,
                                                      width: 3,
                                                    ),
                                                    shape: BoxShape.circle,
                                                    image: DecorationImage(
                                                        fit: BoxFit.fitWidth,
                                                        image: AssetImage(
                                                            "pfp.png"))))),
                                    Positioned(
                                        right: -8,
                                        top: -8,
                                        child: IconButton(
                                            iconSize: 20,
                                            icon: const Icon(Icons.add_a_photo),
                                            color: Theme.of(context)
                                                .textTheme
                                                .bodyText1!
                                                .color,
                                            onPressed: () {
                                              openFileExplorer()
                                                  .then((value) async {
                                                if (value != null) {
                                                  final UserModel provider =
                                                      Provider.of<UserModel>(
                                                          context,
                                                          listen: false);
                                                  await provider
                                                      .addProfilePicture(value);
                                                }
                                              });
                                            })),
                                  ]),
                                  Container(
                                      child: Column(
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          children: [
                                        SizedBox(
                                            height: MediaQuery.of(context)
                                                    .size
                                                    .height *
                                                0.01),
                                        //mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                        Text(user!.username,
                                            textAlign: TextAlign.center,
                                            style: new TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color,
                                                fontSize: MediaQuery.of(context)
                                                        .size
                                                        .height *
                                                    0.06)),
                                        SizedBox(
                                            height: MediaQuery.of(context)
                                                    .size
                                                    .height *
                                                0.005),
                                        Text(
                                            user!.firstname +
                                                " " +
                                                user!.lastname,
                                            textAlign: TextAlign.center,
                                            style: new TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color,
                                                fontSize: MediaQuery.of(context)
                                                        .size
                                                        .height *
                                                    0.03)),
                                        SizedBox(
                                            height: MediaQuery.of(context)
                                                    .size
                                                    .height *
                                                0.005),
                                        Text(user!.email,
                                            textAlign: TextAlign.center,
                                            style: new TextStyle(
                                                color: Theme.of(context)
                                                    .textTheme
                                                    .bodyText1!
                                                    .color,
                                                fontSize: MediaQuery.of(context)
                                                        .size
                                                        .height *
                                                    0.02)),
                                        SizedBox(
                                            height: MediaQuery.of(context)
                                                    .size
                                                    .height *
                                                0.01),
                                        Row(children: [
                                          Expanded(
                                              child: Container(
                                                  decoration: BoxDecoration(
                                                      color: Theme.of(context)
                                                          .accentColor,
                                                      shape: BoxShape.circle),
                                                  child: IconButton(
                                                    icon: const Icon(
                                                        Icons.attach_file),
                                                    color: Theme.of(context)
                                                        .primaryColorDark,
                                                    onPressed: () {
                                                      Navigator.pushReplacement(
                                                          context,
                                                          MaterialPageRoute(
                                                              builder: (context) =>
                                                                  DocumentPage()));
                                                    },
                                                    padding:
                                                        EdgeInsets.symmetric(
                                                            horizontal: 5,
                                                            vertical: 5),
                                                  ))),
                                          Expanded(
                                              child: Container(
                                                  decoration: BoxDecoration(
                                                      color: Theme.of(context)
                                                          .accentColor,
                                                      shape: BoxShape.circle),
                                                  child: IconButton(
                                                      onPressed: () {
                                                        {
                                                          var provider = Provider
                                                              .of<UserModel>(
                                                                  context,
                                                                  listen:
                                                                      false);
                                                          showDialog(
                                                              context: context,
                                                              builder:
                                                                  (BuildContext
                                                                      context) {
                                                                return AlertBox(
                                                                    user!,
                                                                    provider);
                                                              });
                                                        }
                                                      },
                                                      padding:
                                                          EdgeInsets.symmetric(
                                                              horizontal: 5,
                                                              vertical: 5),
                                                      icon: const Icon(
                                                          Icons.edit),
                                                      color: Theme.of(context)
                                                          .primaryColorDark))),
                                        ])
                                      ]))
                                ]))
                              ]))));
            }
          }));
    }
  }
}

class AlertBox extends StatefulWidget {
  late final UserProfile? user;
  final UserModel userModel;

  AlertBox(this.user, this.userModel);

  @override
  _AlertBox createState() => _AlertBox(user!);
}

class _AlertBox extends State<AlertBox> {
  UserProfile? user;

  _AlertBox(this.user);

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  final usernameController = TextEditingController();
  final firstNameController = TextEditingController();
  final lastNameController = TextEditingController();
  final emailController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme.of(context).primaryColorDark,
        content: Container(
            height: getSize(context),
            child: Stack(clipBehavior: Clip.none, children: <Widget>[
              Positioned(
                right: -40.0,
                top: -40.0,
                child: InkResponse(
                  onTap: () {
                    Navigator.of(context).pop(false);
                  },
                  child: CircleAvatar(
                    child: Icon(Icons.close,
                        color: Theme.of(context).primaryColorDark),
                    backgroundColor: Theme.of(context).accentColor,
                  ),
                ),
              ),
              Center(
                  child: Column(
                  // mainAxisSize: MainAxisSize.min,
                  children: <Widget>[
                    Text(
                        "Edit Profile: " + user!.username,
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color,
                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.05),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
                          controller: usernameController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme.of(context).primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Username')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
                          controller: firstNameController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme.of(context).primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'First name')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
                          controller: lastNameController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme.of(context).primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Last name')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: 300,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                              Theme.of(context).textTheme.bodyText1!.color),
                          controller: emailController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              fillColor: Theme.of(context).primaryColorLight,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Email')),
                    ),
                    Spacer(),
                    Padding(
                        padding: EdgeInsets.symmetric(
                            horizontal:
                                MediaQuery.of(context).size.width * 0.02),
                        child: ElevatedButton(
                            style: ElevatedButton.styleFrom(
                                primary: Theme.of(context).accentColor),
                            onPressed: () async {
                              await widget.userModel.editProfile(user!.userID,
                                  usernameController.text,
                                  firstNameController.text,
                                  lastNameController.text,
                                  emailController.text);
                              Navigator.pushReplacement(
                                  context,
                                  MaterialPageRoute(
                                  builder: (context) => ProfileCaller()));
                            },
                            child: Text("Edit",
                                style: TextStyle(
                                    color: Theme.of(context)
                                        .textTheme
                                        .bodyText1!
                                        .color))))
                  ]))
            ])));
  }
}
