package com.adventureit.budgetservice.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class BudgetGraph {
    List<Node> nodes;
    Stack<Node> stack = new Stack<Node>();
    private int i = 0;

    public BudgetGraph() {
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(Node newNode){
        nodes.add(newNode);
    }

    public List<Edge> summarizeGraph(){
        List<Node> cycleNodes = new ArrayList<Node>();
        for (int i = 0; i< nodes.size();i++){
            nodes.get(i).setNum(0);
        }
        checkNode(nodes.get(0));

    }

    public Node checkNode(Node node){
        for (int i = 0 ;i< node.getEdges().size();i++){
            Edge edge = (Edge)node.getEdges().get(i);
            if(edge.getPayee().getNum()==0){
                edge.getPayee().setPred(node);
                return checkNode(edge.getPayee());
            }else if(edge.getPayee().getNum()!= Integer.MAX_VALUE){
                edge.getPayee().setPred(node);
                return edge.getPayee();
            }
            node.setNum(Integer.MAX_VALUE);
        }
        return null;
    }
}
