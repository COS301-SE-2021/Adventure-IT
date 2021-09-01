package com.adventureit.budgetservice.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Node {
    private UUID userId;
    private String name;
    private List<Edge> edges;
    private boolean visited;
    private Node pred;

    public Node(UUID userId, String name) {
        this.userId = userId;
        this.name = name;
        this.visited = false;
        this.edges = new ArrayList<Edge>();
    }

    public void addEdge(Edge e){
        this.edges.add(e);
    }

    public Node getPred() {
        return pred;
    }

    public void setPred(Node pred) {
        this.pred = pred;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getEdges() {
        return edges;
    }

    public void setEdges(List edges) {
        this.edges = edges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
