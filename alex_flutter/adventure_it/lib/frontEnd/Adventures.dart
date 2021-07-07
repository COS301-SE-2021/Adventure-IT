import 'dart:html';

import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'AdventurePage.dart';
import 'Budgets.dart';
import 'CreateAdventure.dart';
import 'package:flutter/foundation.dart';

import '../api/budget.dart';

class HomePage_Pages extends StatelessWidget {

  HomePage_Pages();
  @override
  Widget build(BuildContext context) {
    final PageController controller = PageController(initialPage: 0);
    return PageView(
        scrollDirection: Axis.horizontal,
        controller: controller,
        children: <Widget>[
          HomePage_Pages_Adventures(),
        ]);
  }
}

class HomePage_Pages_Adventures extends StatelessWidget {
  HomePage_Pages_Adventures();
  @override
  Widget build(BuildContext context) {
    return Column(children: <Widget>[
      Container(
          alignment: Alignment.center,
          height: MediaQuery.of(context).size.height/6,
          child: Text("Adventures",
              style: TextStyle(fontSize: 35*MediaQuery.of(context).textScaleFactor, fontWeight: FontWeight.bold, color: Theme.of(context).textTheme.bodyText1!.color))),
      Container(
          alignment: Alignment.centerLeft,
          padding: EdgeInsets.only(left: MediaQuery.of(context).size.width/50,),),
      AdventureFutureBuilder(),
      SizedBox(height: MediaQuery.of(context).size.height/60),
      Container(
          decoration: BoxDecoration(
              color: Theme.of(context).accentColor,
              shape: BoxShape.circle
      ), child:IconButton(onPressed: (){{
        Navigator.pushReplacement(context,
            MaterialPageRoute(builder: (context) => CreateAdventureCaller()));
      }}, icon: const Icon(Icons.add), color: Theme.of(context).primaryColorDark)),
      SizedBox(height: MediaQuery.of(context).size.height/60),
    ]);
  }
}

class AdventureFutureBuilder extends StatefulWidget {

  @override
  _AdventureFutureBuilder createState() => _AdventureFutureBuilder();
}


class _AdventureFutureBuilder extends State<AdventureFutureBuilder> {
  Future<List<Adventure>>? adventuresFuture;
  _AdventureFutureBuilder();

  @override
  void initState() {
    super.initState();
    adventuresFuture = AdventureApi.getAdventuresByUUID("1660bd85-1c13-42c0-955c-63b1eda4e90b");
  }

  String getDate(Adventure a)
  {
    if(DateTime.parse(a.startDate).difference(DateTime.now()).inDays>0)
    {
        return "In "+DateTime.parse(a.startDate).difference(DateTime.now()).inDays.toString() +" days";
    }
    else if (DateTime.parse(a.endDate).difference(DateTime.now()).inDays>0&&DateTime.parse(a.startDate).difference(DateTime.now()).inDays<=0)
      {
        return "Currently On Adventure!";
      }
    else
      {
        return "Adventure Completed!";
      }
  }


  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: adventuresFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator( valueColor: new AlwaysStoppedAnimation<Color>(Theme.of(context).accentColor)));
          }
          print(snapshot.data);
          if (snapshot.hasData)
          {

            var adventures = snapshot.data as List<Adventure>;

            print(adventures);
            if(adventures!.length>0)
            {
              return Expanded(
                child: ListView(children: [
                  ...List.generate(
                      adventures!.length,
                          (index) =>
                             Dismissible(
                                  background: Container(
                               // color: Theme.of(context).primaryColor,
                               //   margin: const EdgeInsets.all(5),
                                 padding: EdgeInsets.all(MediaQuery.of(context).size.height/60),
                                 child: Row(

                                   children: [
                                     new Spacer(),
                                    Icon(
                                      Icons.delete,
                                      color: Theme.of(context).accentColor,
                                      size: 35*MediaQuery.of(context).textScaleFactor
                                    ),
                                   ],
                                 ),
                             ),
                                  direction: DismissDirection.endToStart,
                                  key:Key(adventures.elementAt(index).adventureId),
                                  child: Card(
                                    color: Theme.of(context).primaryColorDark,
                                    child: InkWell
                                    (
                                      hoverColor: Theme.of(context).primaryColorLight,
                                      onTap: ()
                                      {
                                        // Future<List<Budget>> budgetsFuture =
                                        // BudgetApi.getBudgets(
                                        //     adventures.elementAt(index));
                                        //     Navigator.pushReplacement(
                                        //         context,
                                        //         MaterialPageRoute(
                                        //           builder: (context) => Adventure_Budgets(
                                        //           budgetsFuture: budgetsFuture,
                                        //           adventure:
                                        //           adventures.elementAt(index))));


                                            Navigator.pushReplacement(
                                                context,
                                                MaterialPageRoute(
                                                  builder: (context) => AdventurePage(adventures.elementAt(index))));
                                        },
                                    child: Container
                                      (
                                      child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 1,
                                          child: Container(
                                          height:MediaQuery.of(context).size.height/6,
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
                                            title: Text(adventures.elementAt(index).name, style: TextStyle(fontSize: 25*MediaQuery.of(context).textScaleFactor, fontWeight: FontWeight.bold,color: Theme.of(context).textTheme.bodyText1!.color)),
                                            // subtitle:Text(adventures.elementAt(index).description),
                                            subtitle: Text(adventures.elementAt(index).description, style: TextStyle(fontSize: 15*MediaQuery.of(context).textScaleFactor,color:Theme.of(context).textTheme.bodyText1!.color)),
                                        ),
                                      ),
                                        Expanded(
                                          flex: 1,
                                          child: Text(getDate(adventures.elementAt(index)), textAlign: TextAlign.center,style: TextStyle(fontSize: 12*MediaQuery.of(context).textScaleFactor,color:Theme.of(context).textTheme.bodyText1!.color)),
                                        ),
                                    ],
                                  ),
                              ))),
                                  onDismissed: (direction) {
                                        AdventureApi.removeAdventure(adventures.elementAt(index).adventureId);
                                        adventures.removeAt(index);
                                        setState(() {});
                                    }))]));

            }

            else
            {
              return Center(child: Text("It seems you're not very adventurous...", style: TextStyle(fontSize: 30*MediaQuery.of(context).textScaleFactor, color:Theme.of(context).textTheme.bodyText1!.color)));
            }

          }
          else
          {
            return Center(child: Text("It seems you're not very adventurous...", style: TextStyle(fontSize: 30*MediaQuery.of(context).textScaleFactor, color:Theme.of(context).textTheme.bodyText1!.color)));
          }
        }
    );
  }
}