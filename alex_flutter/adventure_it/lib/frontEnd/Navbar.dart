import 'package:flutter/material.dart';

class NavDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
      DrawerHeader(
      child: SizedBox(),
        decoration: BoxDecoration(
          color: Colors.green,
          image: DecorationImage(
              fit: BoxFit.fill,
              image: AssetImage('assets/adventure.PNG'))),
    ),],
      ),
    );
  }
}