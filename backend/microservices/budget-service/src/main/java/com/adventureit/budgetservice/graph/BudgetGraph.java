package com.adventureit.budgetservice.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class BudgetGraph {
    List<Node> nodes;
    Stack<Node> stack = new Stack<Node>();

    public BudgetGraph() {
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(Node newNode){
        nodes.add(newNode);
    }

    public List<UUID> summarizeGraph(){


        for (int i = 0; i< nodes.size();i++){
            nodes.get(i).setVisited(false);
        }

    }

    public void checkNode(Node node){
        if(!node.isVisited()){
            stack.push(node);
            for(int i =0;i< node.getEdges().size();i++){
                Edge edge = (Edge)node.getEdges().get(i);
                checkNode(edge.getPayee());
            }
        }
    }
}
