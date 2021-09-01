package com.adventureit.budgetservice.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BudgetGraph {
    List<Node> nodes;

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
}
