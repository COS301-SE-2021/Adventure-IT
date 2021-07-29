import 'package:adventure_it/Providers/budget_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';
import 'HomepageStartup.dart';

import '../api/budget.dart';

class Budgets extends StatelessWidget {
  Adventure? adventure;

  Budgets(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Budgets",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
          SizedBox(height: MediaQuery.of(context).size.height / 60),
            Container( height: MediaQuery.of(context).size.height*0.75, child: BudgetList(adventure)),
            SizedBox(height: MediaQuery.of(context).size.height / 60),
         Row(

             children:[

              Expanded(
                  flex: 2,
                    child: Container(
                        decoration: BoxDecoration(
                            color: Theme.of(context).accentColor,
                            shape: BoxShape.circle),
                        child: IconButton(
                            onPressed: () {

                              Navigator.pushReplacement(
                              context,
                              MaterialPageRoute(
                              builder: (context) => AdventurePage(
                              adventure)));

                              },
                            icon: const Icon(Icons.arrow_back_ios_new_rounded),
                            color: Theme.of(context).primaryColorDark)),
                  ) ,
               Expanded(flex: 3, child:Container()),//Your widget here,
            Expanded(
                flex: 1,
                  child: Container(
                      decoration: BoxDecoration(
                          color: Theme.of(context).accentColor,
                          shape: BoxShape.circle),
                      child: IconButton(
                          onPressed: () {
                            {

                            }
                          },
                          icon: const Icon(Icons.add),
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

                           }
                         },
                         icon: const Icon(Icons.remove),
                         color: Theme.of(context).primaryColorDark)),
               ),
               Expanded(flex: 3, child:Container()),//Your widget //Your widget here,
              Expanded(
                  flex: 2,
                    child: Container(
                        decoration: BoxDecoration(
                            color: Theme.of(context).accentColor,
                            shape: BoxShape.circle),
                        child: IconButton(
                            onPressed: () {
                              {

                              }
                            },
                            icon: const Icon(Icons.delete_outline_rounded),
                            color: Theme.of(context).primaryColorDark)),//Your widget here,
              ),]),
            SizedBox(height: MediaQuery.of(context).size.height / 60),
          ])
    );
  }
}

class BudgetList extends StatelessWidget {

  Adventure? a;

  BudgetList(Adventure? adventure)
  {
    this.a=adventure;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => BudgetModel(a!),
        child: Consumer<BudgetModel>(
            builder: (context, budgetModel, child) {
          Center(
              child: CircularProgressIndicator(
                  valueColor: new AlwaysStoppedAnimation<Color>(
                      Theme.of(context).accentColor)));
          if (budgetModel.budgets!.length > 0) {
            return Expanded(
                flex: 2,
                child: ListView(children: [
                  ...List.generate(
                      budgetModel.budgets!.length,
                      (index) => Dismissible(
                          background: Container(
                            // color: Theme.of(context).primaryColor,
                            //   margin: const EdgeInsets.all(5),
                            padding: EdgeInsets.all(
                                MediaQuery.of(context).size.height / 60),
                            child: Row(
                              children: [
                                new Spacer(),
                                Icon(Icons.delete,
                                    color: Theme.of(context).accentColor,
                                    size: 35 *
                                        MediaQuery.of(context).textScaleFactor),
                              ],
                            ),
                          ),
                          direction: DismissDirection.endToStart,
                          key: Key(budgetModel.budgets
                              .elementAt(index)
                              .id),
                          child: Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                  hoverColor:
                                      Theme.of(context).primaryColorLight,
                                  onTap: () {

                                    // Navigator.pushReplacement(
                                    //     context,
                                    //     MaterialPageRoute(
                                    //         builder: (context) => BudgetPage(
                                    //             budgetModel.budgets
                                    //                 .elementAt(index))));
                                  },
                                  child: Container(

                                    child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 4,
                                          child: ListTile(
                                            title: Text(
                                                budgetModel.budgets
                                                    .elementAt(index)
                                                    .name,
                                                style: TextStyle(
                                                    fontSize: 25 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    fontWeight: FontWeight.bold,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                            // subtitle:Text(adventures.elementAt(index).description),
                                            subtitle: Text(
                                                budgetModel.budgets
                                                    .elementAt(index)
                                                    .description,
                                                style: TextStyle(
                                                    fontSize: 15 *
                                                        MediaQuery.of(context)
                                                            .textScaleFactor,
                                                    color: Theme.of(context)
                                                        .textTheme
                                                        .bodyText1!
                                                        .color)),
                                          ),
                                        ),
                                        // Expanded(
                                        //   flex: 1,
                                        //   child: Text(
                                        //       getDate(adventureModel.adventures
                                        //           .elementAt(index)),
                                        //       textAlign: TextAlign.center,
                                        //       style: TextStyle(
                                        //           fontSize: 12 *
                                        //               MediaQuery.of(context)
                                        //                   .textScaleFactor,
                                        //           color: Theme.of(context)
                                        //               .textTheme
                                        //               .bodyText1!
                                        //               .color)),
                                        // ),
                                      ],
                                    ),
                                  ))),
                          onDismissed: (direction) {
                            Provider.of<BudgetModel>(context, listen: false)
                                .softDeleteBudget(
                                    budgetModel.budgets.elementAt(index));
                          }))
                ]));
          } else {

                return Center(child: Text("Start planning how to spend your money!",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        }));
  }
}
