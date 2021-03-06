package com.adventureit.budgetservice.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Node {
    private UUID userId;
    private String name;
    private List<Edge> edges;
    private int num;
    private Node pred;

    public Node( String name) {
        this.name = name;
        this.edges = new ArrayList<>();
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

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
