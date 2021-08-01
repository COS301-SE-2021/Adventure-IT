package com.adventureit.checklist.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Checklist{
    @Id
    private UUID id;
    private UUID creatorID;
    private UUID adventureID;
    private String title;
    private String description;
    boolean deleted;


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
    public Checklist(String title, String description, UUID id, UUID creatorID, UUID adventureID) {
        this.title = title;
        this.description = description;
        this.adventureID = adventureID;
        this.creatorID = creatorID;
        this.id = id;
        deleted = false;
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
    public Checklist(String title, String description, List<UUID> entries, UUID id, UUID creatorID, UUID adventureID) {
        this.title = title;
        this.description = description;
        this.adventureID = adventureID;
        this.creatorID = creatorID;
        this.id = id;
        deleted = false;
    }

    /**
     * Get a checklist entry at a provided index
     *
     * @param index
     * The index of the checklist entry to be retrieved
     * @return
     * The checklist entry corresponding to the provided index
     */
//    public ChecklistEntry getEntry(int index) {
//        return (ChecklistEntry)this.entries.get(index);
//    }

    /**
     * Add a single checklist entry to the checklist
     *
     * @param entry
     * The single entry to be added to the checklist
     */
//    public void addEntry(ChecklistEntry entry) {
//        this.entries.add(entry);
//    }

    /**
     * Getters and setters
     */

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(UUID creatorID) {
        this.creatorID = creatorID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }
}
