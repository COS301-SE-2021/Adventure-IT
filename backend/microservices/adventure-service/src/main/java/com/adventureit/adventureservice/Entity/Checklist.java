package com.adventureit.adventureservice.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    @OneToMany
    private List<ChecklistEntry> items;

    // Default constructor
    public Checklist(){}

    // Parameterized constructor: without prepopulated checklist entry list
    public Checklist(String title, String description) {
        this.title = title;
        this.description = description;
        this.items = new ArrayList<ChecklistEntry>();
    }

    // Parameterized constructor: with prepopulated checklist entry list
    public Checklist(String title, String description, List<ChecklistEntry> items) {
        this.title = title;
        this.description = description;
        this.items = items;
    }

    // Method for getting a checklist item at a provided index
    public ChecklistEntry getItem(int index) {
        return this.items.get(index);
    }

    // Method for adding a single checklist item
    public void addItem(ChecklistEntry item) {
        this.items.add(item);
    }

    // Getters and setters

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public List<ChecklistEntry> getItems() {
        return this.items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(List<ChecklistEntry> items) {
        this.items = items;
    }

}
