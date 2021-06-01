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

    /**
     * Default constructor
     */
    public Checklist(){}

    /**
     * Parameterised constructor
     *
     * @param title
     * The title of the checklist
     * @param description
     * The description of the checklist
     */
    public Checklist(String title, String description) {
        this.title = title;
        this.description = description;
        this.items = new ArrayList<ChecklistEntry>();
    }

    /**
     * Parameterised constructor
     *
     * @param title
     * The title of the checklist
     * @param description
     * The description of the checklist
     * @param items
     * A prepopulated list of checklist entries to be added to this list
     */
    public Checklist(String title, String description, List<ChecklistEntry> items) {
        this.title = title;
        this.description = description;
        this.items = items;
    }

    /**
     * Get a checklist entry at a provided index
     *
     * @param index
     * The index of the checklist entry to be retrieved
     * @return
     * The checklist entry corresponding to the provided index
     */
    public ChecklistEntry getItem(int index) {
        return this.items.get(index);
    }

    /**
     * Add a single checklist entry to the checklist
     *
     * @param item
     * The single item to be added to the checklist
     */
    public void addItem(ChecklistEntry item) {
        this.items.add(item);
    }

    /**
     * Getters and setters
     */

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
