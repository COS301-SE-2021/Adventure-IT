package com.adventureit.adventureservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChecklistEntry extends Entry{
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
    public ChecklistEntry(String title,  long entryContainerID){
        this.title = title;
        this.completed = false;
        this.setEntryContainerID(entryContainerID);
    }

    /**
     * Create a checklist entry with a title and status of completion
     * @param title
     * The title of the checklist entry
     * @param completed
     * The status of the completion of the checklist entry
     * @param entryContainerID
     * The ID of the checklist to which this entry belongs
     */
    public ChecklistEntry(String title, Boolean completed,  long entryContainerID) {
        this.title = title;
        this.completed = completed;
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
