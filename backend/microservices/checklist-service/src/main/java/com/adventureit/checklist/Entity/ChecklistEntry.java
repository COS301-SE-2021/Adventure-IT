package com.adventureit.checklist.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ChecklistEntry {
    @Id
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private Boolean completed;
    LocalDateTime timestamp;

    /**
     * Default constructor
     */
    public ChecklistEntry(){}

    /**
     * Create a checklist entry with only a title, defaulting its completion status to false
     *
     * @param title
     * The title of the checklist entry
     * @param entryContainerID
     * The ID of the checklist to which this entry belongs
     */
    public ChecklistEntry(String title, UUID id, UUID entryContainerID){
        this.title = title;
        this.completed = false;
        this.id = id;
        this.entryContainerID = entryContainerID;
        timestamp = LocalDateTime.now();
    }

    public ChecklistEntry(String title, UUID entryContainerID){
        this.title = title;
        this.completed = false;
        this.id = UUID.randomUUID();
        this.entryContainerID = entryContainerID;
        timestamp = LocalDateTime.now();
    }

    /**
     * Toggles the value of the completion state of the checklist entry
     */
    public void toggleCompleted(){
        this.completed = !this.completed;
    }

    /**
     * Getters and setters
     */

    public String getTitle() {
        return title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed){
        this.completed = completed;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
