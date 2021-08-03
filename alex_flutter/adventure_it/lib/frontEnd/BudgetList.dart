import 'package:adventure_it/Providers/budget_model.dart';
import 'package:adventure_it/api/adventure.dart';
import 'package:adventure_it/api/adventure_api.dart';
import 'package:adventure_it/api/createBudget.dart';
import 'package:adventure_it/constants.dart';
import 'package:adventure_it/api/budgetAPI.dart';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:provider/provider.dart';
import 'AdventurePage.dart';
import 'BudgetPage.dart';
import 'BudgetTrash.dart';
import 'HomepageStartup.dart';
import 'package:pie_chart/pie_chart.dart';

import '../api/budget.dart';
import 'Navbar.dart';

class Budgets extends StatelessWidget {
  Adventure? adventure;

  Budgets(Adventure? a) {
    this.adventure = a;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer: NavDrawer(),
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        appBar: AppBar(
            title: Center(
                child: Text("Budgets",
                    style: new TextStyle(
                        color: Theme.of(context).textTheme.bodyText1!.color))),
            backgroundColor: Theme.of(context).primaryColorDark),
        body:
        Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
                  Spacer(),
              Container(
                  height: MediaQuery.of(context).size.height * 0.80,
                  child: BudgetList(adventure)),
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
                          icon: const Icon(Icons.arrow_back_ios_new_rounded),
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
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertBox(adventure!);
                                  });
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
                            Navigator.pushReplacement(
                                context,
                                MaterialPageRoute(
                                    builder: (context) =>
                                        BudgetTrash(adventure)));
                          },
                          icon: const Icon(Icons.delete_outline_rounded),
                          color: Theme.of(context)
                              .primaryColorDark)), //Your widget here,
                ),
              ]),
              SizedBox(height: MediaQuery.of(context).size.height / 60),
            ]));
  }
}

class PieChartCaller extends StatefulWidget
{
  List<Budget>? budgets;
  PieChartCaller(List<Budget>? b) {
    this.budgets=b;
  }

  @override
  _PieChart createState()=>_PieChart(budgets);
}

class _PieChart extends State<PieChartCaller>
{
  List <int> categories=[0,0,0,0,0];
  List <Budget>? budgets;
  _PieChart(List<Budget>? b) {
    this.budgets = b;

    for(int i=0;i<budgets!.length;i++)
      {
        List <int> temp=List.empty();
        List <int> toBe=List.empty();
        BudgetApi.getNumberOfCategories(budgets!.elementAt(i).id).then((value) => temp=value);
        for(int j=0;j<categories.length;j++)
          {
            toBe.add(categories.elementAt(j)+temp.elementAt(j));
          }
        categories=toBe;
      }
  }



  @override
  Widget build(BuildContext context) {
    Map<String, double> dataMap = new Map();
    dataMap.putIfAbsent("Accommodation", () => categories.elementAt(0)*1.0);
    dataMap.putIfAbsent("Activities", () => categories.elementAt(1)*1.0);
    dataMap.putIfAbsent("Food", () => categories.elementAt(2)*1.0);
    dataMap.putIfAbsent("Other", () => categories.elementAt(3)*1.0);
    dataMap.putIfAbsent("Transport", () => categories.elementAt(4)*1.0);
    List <Color> colorList=[Color(0xff3063b4),Color(0xffb59194),Color(0xff931621),Color(0xff419D78),Color(0xffC44536)];

    return Card(
        child: Column(
        children: <Widget>[
        Expanded(
        child: PieChart(dataMap: dataMap,
          animationDuration: Duration(milliseconds: 800),
          chartLegendSpacing: 32.0,
          chartRadius: MediaQuery.of(context).size.width / 2.7,
          colorList: colorList,
          chartType: ChartType.disc,
          ),
    )]));
  }



}



class BudgetList extends StatelessWidget {
  Adventure? a;

  BudgetList(Adventure? adventure) {
    this.a = adventure;
  }

  String expenses(Budget b, String userName)
  {
    String total="0";
    BudgetApi.getTotalOfExpenses(b,userName).then((value) => total=value);

    return total;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => BudgetModel(a!),
        child: Consumer<BudgetModel>(builder: (context, budgetModel, child) {
          if (budgetModel.budgets == null) {
            return Center(
                child: CircularProgressIndicator(
                    valueColor: new AlwaysStoppedAnimation<Color>(
                        Theme.of(context).accentColor)));
          } else if (budgetModel.budgets!.length > 0) {
            return Column( children: [
                Expanded(
                    flex: 2,
                    child: PieChartCaller(budgetModel.budgets)
                ),
              Expanded(
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
                          key: Key(budgetModel.budgets!.elementAt(index).id),
                          child: Card(
                              color: Theme.of(context).primaryColorDark,
                              child: InkWell(
                                  hoverColor:
                                      Theme.of(context).primaryColorLight,
                                  onTap: () {
                                    Navigator.pushReplacement(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) => BudgetPage(
                                                budgetModel.budgets
                                                    !.elementAt(index),a)));
                                  },
                                  child: Container(
                                    child: Row(
                                      children: <Widget>[
                                        Expanded(
                                          flex: 4,
                                          child: ListTile(
                                            title: Text(//
                                                budgetModel.budgets!
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
                                                budgetModel.budgets!
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
                                        Expanded(
                                          flex: 1,
                                          child: Text(
                                              "Total: "+ expenses(budgetModel.budgets
                                              !.elementAt(index),"1660bd85-1c13-42c0-955c-63b1eda4e90b"),
                                              textAlign: TextAlign.center,
                                              style: TextStyle(
                                                  fontSize: 12 *
                                                      MediaQuery.of(context)
                                                          .textScaleFactor,
                                                  color: Theme.of(context)
                                                      .textTheme
                                                      .bodyText1!
                                                      .color)),
                                        ),
                                      ],
                                    ),
                                  ))),
                          onDismissed: (direction) {
                            Provider.of<BudgetModel>(context, listen: false)
                                .softDeleteBudget(
                                    budgetModel.budgets!.elementAt(index));
                          }))
                ]))]);
          } else {
            return Center(
                child: Text("Start planning how to spend your money!",
                    textAlign: TextAlign.center,
                    style: TextStyle(
                        fontSize: 30 * MediaQuery.of(context).textScaleFactor,
                        color: Theme.of(context).textTheme.bodyText1!.color)));
          }
        }));
  }
}

class AlertBox extends StatefulWidget {
  Adventure? adventure;

  AlertBox(Adventure a) {
    adventure = a;
  }

  @override
  _AlertBox createState() => _AlertBox(adventure!);
}

class _AlertBox extends State<AlertBox> {
  bool isChecked = false;
  Adventure? adventure;

  _AlertBox(Adventure a) {
    this.adventure = a;
  }

  double getSize(context) {
    if (MediaQuery.of(context).size.height >
        MediaQuery.of(context).size.width) {
      return MediaQuery.of(context).size.height * 0.49;
    } else {
      return MediaQuery.of(context).size.height * 0.6;
    }
  }

  //controllers for the form fields
  String userID = "1660bd85-1c13-42c0-955c-63b1eda4e90b";
  String advID = "aa722689-6dbb-474a-a50b-55261570027e";

  final BudgetApi api = new BudgetApi();
  Future<CreateBudget>? _futureBudget;
  final nameController = TextEditingController();
  final descriptionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
        backgroundColor: Theme.of(context).primaryColorDark,
        content: Container(
          height: getSize(context),
          child: Stack(
            overflow: Overflow.visible,
            children: <Widget>[
              Positioned(
                right: -40.0,
                top: -40.0,
                child: InkResponse(
                  onTap: () {
                    Navigator.of(context).pop();
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
                    Text("Create Budget",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: Theme.of(context).textTheme.bodyText1!.color,
                          fontSize: 25 * MediaQuery.of(context).textScaleFactor,
                          fontWeight: FontWeight.bold,
                        )),
                    Spacer(),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                          controller: nameController,
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
                              hintText: 'Title')),
                    ),
                    SizedBox(height: MediaQuery.of(context).size.height * 0.02),
                    Container(
                      width: MediaQuery.of(context).size.width * 0.5,
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: TextField(
                          maxLength: 255,
                          maxLengthEnforced: true,
                          maxLines: 3,
                          style: TextStyle(
                              color:
                                  Theme.of(context).textTheme.bodyText1!.color),
                          controller: descriptionController,
                          decoration: InputDecoration(
                              hintStyle: TextStyle(
                                  color: Theme.of(context)
                                      .textTheme
                                      .bodyText2!
                                      .color),
                              filled: true,
                              fillColor: Theme.of(context).primaryColorLight,
                              enabledBorder: InputBorder.none,
                              errorBorder: InputBorder.none,
                              disabledBorder: InputBorder.none,
                              focusedBorder: OutlineInputBorder(
                                  borderSide: new BorderSide(
                                      color: Theme.of(context).accentColor)),
                              hintText: 'Description')),
                    ),
                    Spacer(),
                    Padding(
                      padding: EdgeInsets.symmetric(
                          horizontal: MediaQuery.of(context).size.width * 0.02),
                      child: RaisedButton(
                        color: Theme.of(context).accentColor,
                        child: Text("Create",
                            style: TextStyle(
                                color: Theme.of(context)
                                    .textTheme
                                    .bodyText1!
                                    .color)),
                        onPressed: () {
                          setState(() {
                            _futureBudget = api.createBudget(nameController.text, descriptionController.text, userID, advID);
                          });
                          Navigator.of(context).pop();
                        },
                      ),
                    ),
                    Spacer(),
                  ],
                ),
              )
            ],
          ),
        ));
  }
}
