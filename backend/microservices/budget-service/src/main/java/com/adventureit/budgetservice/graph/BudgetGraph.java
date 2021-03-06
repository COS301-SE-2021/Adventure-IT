package com.adventureit.budgetservice.graph;

import com.adventureit.budgetservice.entity.*;

import java.util.*;

public class BudgetGraph{
    List<Node> nodes;
    private int i = 0;

    public BudgetGraph() {
        this.nodes = new ArrayList<>();
    }

    public List<Node> generateGraph(List<ReportBudgetEntity> budgets){
        this.i = 0;
        List<Node> nodeArrayList = new ArrayList<>();
        if(budgets.isEmpty()){
            return nodeArrayList;
        }

        budgets.sort(Comparator.comparing(ReportBudgetEntity::getPayer));
        String name = budgets.get(0).getPayer();

        for(int j = 0; j<budgets.size();j++){
            if(nodeArrayList.isEmpty()){
                nodeArrayList.add(new Node(name));

                if(budgets.get(0).getClass().equals(ReportUTUExpense.class)){
                    ReportUTUExpense entry = (ReportUTUExpense)budgets.get(0);
                    if(!checkList(entry.getPayee(),nodeArrayList)){
                        nodeArrayList.add(new Node(entry.getPayee()));
                    }
                }else if(budgets.get(0).getClass().equals(ReportUTOExpense.class)){
                    ReportUTOExpense entry = (ReportUTOExpense)budgets.get(0);
                    if(!checkList(entry.getPayee(),nodeArrayList)){
                        nodeArrayList.add(new Node(entry.getPayee()));
                    }
                }
            }
            else{

                if(budgets.get(j).getClass().equals(ReportUTUExpense.class)){
                    ReportUTUExpense entry = (ReportUTUExpense)budgets.get(j);
                    if(!checkList(entry.getPayee(),nodeArrayList)){
                        nodeArrayList.add(new Node(entry.getPayee()));
                    }
                }else if(budgets.get(j).getClass().equals(ReportUTOExpense.class)){
                    ReportUTOExpense entry = (ReportUTOExpense)budgets.get(j);
                    if(!checkList(entry.getPayee(),nodeArrayList)){
                        nodeArrayList.add(new Node(entry.getPayee()));
                    }
                }

                if(!checkList(budgets.get(j).getPayer(),nodeArrayList)){
                    nodeArrayList.add(new Node(budgets.get(j).getPayer()));
                }
            }

        }



        for( int j = 0 ; j< budgets.size();j++){
            Node payer = null;
            Node payee = null;
            if (budgets.get(j).getClass().equals(ReportUTUExpense.class)){
                ReportUTUExpense entry = (ReportUTUExpense) budgets.get(j);
                payee = findNode(nodeArrayList, entry.getPayee());
                payer = findNode(nodeArrayList,entry.getPayer());
                payer.addEdge(new Edge(payer,payee,entry.getAmount(),entry.getId()));
            }
            else if (budgets.get(j).getClass().equals(ReportUTOExpense.class)){
                ReportUTOExpense entry = (ReportUTOExpense) budgets.get(j);
                payee = findNode(nodeArrayList, entry.getPayee());
                payer = findNode(nodeArrayList,entry.getPayer());
                payer.addEdge(new Edge(payer,payee,entry.getAmount(),entry.getId()));
            }
        }
        this.nodes = nodeArrayList;
        return nodeArrayList;
    }

    public boolean checkList(String name, List<Node> list){
        for(int j = 0;j< list.size();j++){
            if(list.get(j).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Node findNode(List<Node> nodes,String name){
        for (int j = 0; j <nodes.size(); j++){
            if (nodes.get(j).getName().equals(name)){
                return nodes.get(j);
            }
        }
        return null;
    }


    public List<Edge> summarizeGraph(){
        List<Node> cycleNodes = new ArrayList<>();
        List<Edge> cycleEdges = new ArrayList<>();
        if(this.nodes == null||this.nodes.isEmpty()){
            return cycleEdges;
        }

        for (Node node : this.nodes) {
            node.setNum(0);
        }

        Node cycleNode = checkNode(this.nodes.get(0));
        if(cycleNode == null){
            return cycleEdges;
        }else{
            String start = cycleNode.getName();
            Node ptr = cycleNode.getPred();
            for (int j = 0;j<ptr.getEdges().size();j++){
                Edge tempEdge = ptr.getEdges().get(j);
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
                    Edge tempEdge = ptr.getEdges().get(k);
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
        for (int j = 0 ;j< node.getEdges().size();j++){
            Edge edge = node.getEdges().get(j);
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
        for (int j =0;j<edges.size();j++){
            if(edges.get(j).getAmount()<smallest){
                smallest= edges.get(j).getAmount();
            }
        }

        for (int j = 0 ; j<edges.size();j++){
            double currentAmount = edges.get(j).getAmount();
            edges.get(j).setAmount(currentAmount - smallest);
        }
        return edges;
    }
}
