package com.adventureit.checklist.Entity;

import com.adventureit.adventureservice.Entity.Entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class ChecklistEntry extends Entry {
    private String title;
    private Boolean completed;

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
        this.setId(id);
        this.setEntryContainerID(entryContainerID);
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

}
