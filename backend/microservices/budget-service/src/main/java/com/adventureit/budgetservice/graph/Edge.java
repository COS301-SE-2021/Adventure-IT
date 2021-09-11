package com.adventureit.budgetservice.graph;

import java.util.UUID;

public class Edge {
    private Node payer;
    private Node payee;
    private double amount;
    private UUID entryId;
    private boolean changed;

    public Edge(Node payer, Node payee, double amount, UUID entryId) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.entryId = entryId;
        this.changed = false;
    }

    public UUID getEntryId() {
        return entryId;
    }

    public void setEntryId(UUID entryId) {
        this.entryId = entryId;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public Node getPayer() {
        return payer;
    }

    public void setPayer(Node payer) {
        this.payer = payer;
    }

    public Node getPayee() {
        return payee;
    }

    public void setPayee(Node payee) {
        this.payee = payee;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}


