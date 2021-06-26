import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'Budgets.dart';

import '../api/budget.dart';

class HomePage_Pages extends StatelessWidget {
  Future<List<Adventure>>? adventuresFuture;

  HomePage_Pages(
      {@required this.adventuresFuture,});
  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          HomePage_Pages_Adventures(
              adventuresFuture: adventuresFuture),
        ]);
  }
}

class HomePage_Pages_Adventures extends StatelessWidget {
  Future<List<Adventure>>? adventuresFuture;
  HomePage_Pages_Adventures(
      {@required this.adventuresFuture});
  @override
  Widget build(BuildContext context) {
    return Column(children: <Widget>[
      Container(
          alignment: Alignment.center,
          height: 100,
          child: Text("Adventures",
              style: TextStyle(fontSize: 35, fontWeight: FontWeight.bold, color: Theme.of(context).textTheme.bodyText1!.color))),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left: 20.0),),
      AdventureFutureBuilder(adventuresFuture: adventuresFuture),
      Container(
          decoration: BoxDecoration(
              color: Theme.of(context).accentColor,
              shape: BoxShape.circle
      ), child:IconButton(onPressed: (){}, icon: const Icon(Icons.add), color: Theme.of(context).primaryColorDark))
    ]);
  }
}

class AdventureFutureBuilder extends StatelessWidget {
  Future<List<Adventure>>? adventuresFuture;
  AdventureFutureBuilder({@required this.adventuresFuture});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: adventuresFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator( valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).accentColor)));
          }
          if (snapshot.hasData)
          {
            var adventures = snapshot.data as List<Adventure>;
            print(adventures);
            if(adventures.length>0)
            {
              return Expanded(
                child: ListView(children: [
                  ...List.generate(
                      adventures.length,
                          (index) =>
                              Card(
                                child: InkWell(
                                  hoverColor: Theme.of(context).primaryColorLight,
                                    onTap: () {
                                      Future<List<Budget>> budgetsFuture =
                                      BudgetApi.getBudgets(
                                          adventures.elementAt(index));
                                      Navigator.pushReplacement(
                                          context,
                                          MaterialPageRoute(
                                              builder: (context) => Adventure_Budgets(
                                                  budgetsFuture: budgetsFuture,
                                                  adventure:
                                                  adventures.elementAt(index))));
                                    },
                                child: Container(
                                  decoration: new BoxDecoration(
                                      color: Theme.of(context).primaryColorDark
                                  ),
                                  child: Row(
                                    children: <Widget>[
                                      Expanded(
                                        flex: 1,
                                        child: Container(
                                          height:90,
                                          child: CircleAvatar(
                                            radius: 50,
                                            backgroundImage: AssetImage('assets/adventure.PNG'),
                                          ),
                                          decoration: new BoxDecoration(
                                            shape: BoxShape.circle,
                                            border: new Border.all(
                                              color: Theme.of(context).accentColor,
                                              width: 3.0,
                                            ),
                                          ),
                                        ),
                                        ),
                                      Expanded(
                                        flex: 4,
                                        child: ListTile(
                                          title: Text(adventures.elementAt(index).name, style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold,color: Theme.of(context).textTheme.bodyText1!.color)),
                                          // subtitle:Text(adventures.elementAt(index).description),
                                          subtitle: Text("Subtitle subtitle subtitle subtitle subtitle subtitle subtitle subtitle", style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color)),
                                        ),
                                      ),
                                      Expanded(
                                        flex: 1,
                                        child: Text("Date", style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color)),
                                        ),
                                    ],
                                  ),
                              ))))]));

            }
            else
            {
              return Center(child: Text("It seems you're not very adventurous...", style: TextStyle(fontSize: 30, color:Theme.of(context).textTheme.bodyText1!.color)));
            }
          }
          else
          {
            return Center(child: Text("Something went wrong",style: TextStyle(color:Theme.of(context).textTheme.bodyText1!.color)));
          }
        }
    );
  }
}