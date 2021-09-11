package com.adventureit.budgetservice.graph;

import com.adventureit.budgetservice.entity.BudgetEntry;
import com.adventureit.budgetservice.entity.UTOExpense;
import com.adventureit.budgetservice.entity.UTUExpense;

import java.util.*;

public class BudgetGraph{
    List<Node> nodes;
    Stack<Node> stack = new Stack<Node>();
    private int i = 0;

    public BudgetGraph() {

    }

    public List<Node> generateGraph(List<BudgetEntry> budgets){
        this.i = 0;
        List<Node> nodes = new ArrayList<Node>();
        budgets.sort(new Comparator<BudgetEntry>() {
            @Override
            public int compare(BudgetEntry o1, BudgetEntry o2) {
                return o1.getPayer().compareTo(o2.getPayer());
            }
        });
        String name = budgets.get(0).getPayer();

        for(int i = 0; i<budgets.size();i++){
            if(nodes.isEmpty()){
                nodes.add(new Node(name));

                if(budgets.get(0).getClass().equals(UTUExpense.class)){
                    UTUExpense entry = (UTUExpense)budgets.get(0);
                    if(!checkList(entry.getPayee(),nodes)){
                        nodes.add(new Node(entry.getPayee()));
                    }
                }else if(budgets.get(0).getClass().equals(UTOExpense.class)){
                    UTOExpense entry = (UTOExpense)budgets.get(0);
                    if(!checkList(entry.getPayee(),nodes)){
                        nodes.add(new Node(entry.getPayee()));
                    }
                }
            }
            else{

                if(budgets.get(i).getClass().equals(UTUExpense.class)){
                    UTUExpense entry = (UTUExpense)budgets.get(i);
                    if(!checkList(entry.getPayee(),nodes)){
                        nodes.add(new Node(entry.getPayee()));
                    }
                }else if(budgets.get(i).getClass().equals(UTOExpense.class)){
                    UTOExpense entry = (UTOExpense)budgets.get(i);
                    if(!checkList(entry.getPayee(),nodes)){
                        nodes.add(new Node(entry.getPayee()));
                    }
                }

                if(!checkList(budgets.get(i).getPayer(),nodes)){
                    nodes.add(new Node(budgets.get(i).getPayer()));
                }
            }

        }



        for( int i = 0 ; i< budgets.size();i++){
            Node payer = null;
            Node payee = null;
            if (budgets.get(i).getClass().equals(UTUExpense.class)){
                UTUExpense entry = (UTUExpense) budgets.get(i);
                payee = findNode(nodes, entry.getPayee());
                payer = findNode(nodes,entry.getPayer());
                payer.addEdge(new Edge(payer,payee,entry.getAmount(),entry.getId()));
            }
            else if (budgets.get(i).getClass().equals(UTOExpense.class)){
                UTOExpense entry = (UTOExpense) budgets.get(i);
                payee = findNode(nodes, entry.getPayee());
                payer = findNode(nodes,entry.getPayer());
                payer.addEdge(new Edge(payer,payee,entry.getAmount(),entry.getId()));
            }
        }
        this.nodes = nodes;
        return nodes;
    }

    public boolean checkList(String name, List<Node> list){
        for(int i = 0;i< list.size();i++){
            if(list.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Node findNode(List<Node> nodes,String name){
        for (int i = 0; i <nodes.size();i++){
            if (nodes.get(i).getName().equals(name)){
                return nodes.get(i);
            }
        }
        return null;
    }


    public List<Edge> summarizeGraph(){
        List<Node> cycleNodes = new ArrayList<>();
        List<Edge> cycleEdges = new ArrayList<>();

        for (Node node : this.nodes) {
            node.setNum(0);
        }

        Node cycleNode = checkNode(this.nodes.get(0));
        if(cycleNode == null){
            return cycleEdges;
        }else{
            System.out.println("Cycle detected");
            String start = cycleNode.getName();
            Node ptr = cycleNode.getPred();
            for (int j = 0;j<ptr.getEdges().size();j++){
                Edge tempEdge = (Edge)ptr.getEdges().get(j);
                if(tempEdge.getPayee().getName().equals(cycleNode.getName())){
                    cycleEdges.add(tempEdge);
                }
            }

            cycleNodes.add(cycleNode);
            while(!ptr.getName().equals(start)){
                cycleNodes.add(ptr);
                String tempName = ptr.getName();
                ptr = ptr.getPred();
                for (int k = 0;k<ptr.getEdges().size();k++){
                    Edge tempEdge = (Edge)ptr.getEdges().get(k);
                    if(tempEdge.getPayee().getName().equals(tempName)){
                        cycleEdges.add(tempEdge);
                    }
                }
            }
            return summarizeEdges(cycleEdges);


        }

    }

    public Node checkNode(Node node){
        node.setNum(this.i++);
        for (int i = 0 ;i< node.getEdges().size();i++){
            Edge edge = (Edge)node.getEdges().get(i);
            if(edge.getPayee().getNum()==0){
                edge.getPayee().setPred(node);
                return checkNode(edge.getPayee());
            }else if(edge.getPayee().getNum()!= Integer.MAX_VALUE){
                edge.getPayee().setPred(node);
                return edge.getPayee();
            }

        }
        node.setNum(Integer.MAX_VALUE);
        return null;
    }

    public List<Edge> summarizeEdges(List<Edge> edges){
        double smallest = Double.MAX_VALUE;
        for (int i =0;i<edges.size();i++){
            if(edges.get(i).getAmount()<smallest){
                smallest= edges.get(i).getAmount();
            }
        }

        for (int j = 0 ; j<edges.size();j++){
            double currentAmount = edges.get(j).getAmount();
            edges.get(j).setAmount(currentAmount - smallest);
        }
        return edges;
    }
}