import 'dart:html';

import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context){
    return MaterialApp(
      theme: ThemeData(primaryColor: Colors.black),
      home: Login());
  }
}

class Login extends StatelessWidget {
  @override
  Widget build(BuildContext context){
    return Scaffold(
        appBar: AppBar(
            title: Center(child: Text("Login"))
        ),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              TextField(decoration: InputDecoration( border: OutlineInputBorder(), hintText: 'Email')),
              TextField(obscureText: true, decoration: InputDecoration( border: OutlineInputBorder(), hintText: 'Password')),
              ElevatedButton(
                child: Text("Log In"),
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => HomePage()),
                  );
                }
              )
            ]
        )
    );
  }
}

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context){
    return MaterialApp(
        theme: Theme.of(context),
        home: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: Text("Home Page"),
            leading:
              IconButton(onPressed: (){
                Navigator.pop(context);
              }, icon: const Icon(Icons.logout))
          ),
          body: HomePage_Pages()
      )
    );
  }
}

class HomePage_Pages extends StatelessWidget {
  @override
  Widget build(BuildContext context){
    final PageController controller = PageController(initialPage: 0);
    return PageView(
      scrollDirection: Axis.horizontal,
      controller: controller,
      children: <Widget>[
          HomePage_Pages_Adventures(),
          Text("Some Other Page 1"),
          Text("Some Other Page 2")
      ]
    );
  }
}

class HomePage_Pages_Adventures extends StatelessWidget {
  @override
  Widget build(BuildContext context){
    return Column(children: <Widget>[

      Container(
          alignment: Alignment.center,
          height: 100,
          child:
            Text("Adventures",
                style:
                  TextStyle(
                    fontSize: 35,
                    fontWeight: FontWeight.bold
                  )
            )
      ),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left:20.0),
          child:
          Text("adventures I have created",
              style:
              TextStyle(
                  fontSize: 20
              )
          )
      ),
      Row(
        children: <Widget>[
          Expanded(
            child: Container(
              margin: EdgeInsets.all(10.0),
              padding: EdgeInsets.all(10.0),
              height: 50,
              color: Colors.blue,
              alignment: Alignment.centerLeft,
              child:
                Text("Test", style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: Colors.white
                ))
            ),
          ),
        ]
      ),
      Row(
          children: <Widget>[
            Expanded(
              child: Container(
                  margin: EdgeInsets.only(bottom: 5.0, left:10.0, right:10.0),
                  padding: EdgeInsets.all(10.0),
                  height: 50,
                  color: Colors.blue,
                  alignment: Alignment.centerLeft,
                  child:
                  Text("Test", style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.white
                  ))
              ),
            ),
          ]
      ),
      SizedBox(height: 100),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left:20.0),
          child:
          Text("adventures I am attending",
              style:
              TextStyle(
                  fontSize: 20
              )
          )
      ),
      Row(
          children: <Widget>[
            Expanded(
              child: Container(
                  margin: EdgeInsets.all(10.0),
                  padding: EdgeInsets.all(10.0),
                  height: 50,
                  color: Colors.blue,
                  alignment: Alignment.centerLeft,
                  child:
                  Text("Test", style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.white
                  ))
              ),
            ),
          ]
      ),
      Row(
          children: <Widget>[
            Expanded(
              child: Container(
                  margin: EdgeInsets.only(bottom: 5.0, left:10.0, right:10.0),
                  padding: EdgeInsets.all(10.0),
                  height: 50,
                  color: Colors.blue,
                  alignment: Alignment.centerLeft,
                  child:
                  Text("Test", style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.white
                  ))
              ),
            ),
          ]
      ),
    ]
    );
  }
}