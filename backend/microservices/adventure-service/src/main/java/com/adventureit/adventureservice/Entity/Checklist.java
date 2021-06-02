package com.adventureit.adventureservice.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Checklist extends EntryContainer {
    private String title;
    private String description;
    @OneToMany
    private List<ChecklistEntry> entries;

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
     * @param creatorID
     * The UUID of the user who is creating the checklist
     * @param adventureID
     * The UUID of the adventure to which the checklist belongs
     */
    public Checklist(String title, String description, UUID creatorID, UUID adventureID) {
        this.title = title;
        this.description = description;
        this.entries = new ArrayList<ChecklistEntry>();
        this.setCreatorID(creatorID);
        this.setAdventureID(adventureID);
    }

    /**
     * Parameterised constructor
     *
     * @param title
     * The title of the checklist
     * @param description
     * The description of the checklist
     * @param entries
     * A prepopulated list of checklist entries to be added to this list
     * @param creatorID
     * The UUID of the user who is creating the checklist
     * @param adventureID
     * The UUID of the adventure to which the checklist belongs
     */
    public Checklist(String title, String description, List<ChecklistEntry> entries, UUID creatorID, UUID adventureID) {
        this.title = title;
        this.description = description;
        this.entries = entries;
        this.setCreatorID(creatorID);
        this.setAdventureID(adventureID);
    }

    /**
     * Get a checklist entry at a provided index
     *
     * @param index
     * The index of the checklist entry to be retrieved
     * @return
     * The checklist entry corresponding to the provided index
     */
    public ChecklistEntry getEntry(int index) {
        return this.entries.get(index);
    }

    /**
     * Add a single checklist entry to the checklist
     *
     * @param entry
     * The single entry to be added to the checklist
     */
    public void addEntry(ChecklistEntry entry) {
        this.entries.add(entry);
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

    public List<ChecklistEntry> getEntries() {
        return this.entries;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEntries(List<ChecklistEntry> entries) {
        this.entries = entries;
    }

}
