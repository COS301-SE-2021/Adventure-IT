package com.adventureit.adventureservice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChecklistEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private Boolean completed;

    // Default constructor
    public ChecklistEntry(){}

    // Parameterized constructor: with only title
    public ChecklistEntry(String title){
        this.title = title;
        this.completed = false;
    }

    // Parameterized constructor: with title and completed
    public ChecklistEntry(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    // Method to toggle completed value
    public void toggleCompleted(){
        this.completed = !this.completed;
    }

    // Getters and setters

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
