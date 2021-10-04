package com.adventureit.budgetservice.entity;

import javax.persistence.*;
import java.util.UUID;

/**
 * The Budget class
 * This class defines the attributes of the Budget entity to set up the table in the database
 */
@Entity
public class Budget{
    @Id
    private UUID budgetID;
    private UUID creatorID;
    private UUID adventureID;
    private String name;
    private boolean deleted;
    private String description;

    /**
     * Default constructor
     */
    public Budget(){}

    /**
     * Budget model Constructor which takes in the following parameters:
     * @param budgetId - The UUID of the budget
     * @param name - The name of the budget
     * @param description - The description of the budget
     * @param creatorID - The UUID of the creator of the budget
     * @param adventureID - The UUID of the adventure the budget belongs to
     */
    public Budget(UUID budgetId,String name, String description, UUID creatorID, UUID adventureID){
        this.budgetID = budgetId;
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.name = name;
        this.deleted = false;
        this.description = description;
    }

    /**
     * Budget model Constructor which takes in the following parameters:
     * @param name - The name of the budget
     * @param description - The description of the budget
     * @param creatorID - The UUID of the creator of the budget
     * @param adventureID - The UUID of the adventure the budget belongs to
     */
    public Budget(String name, String description, UUID creatorID, UUID adventureID) {
        this.budgetID = UUID.randomUUID();
        this.creatorID = creatorID;
        this.adventureID = adventureID;
        this.name = name;
        this.deleted = false;
        this.description = description;
    }

    /**
     * Getters and Setters
     */
    public UUID getBudgetId() {
        return budgetID;
    }

    public UUID getCreatorID() {
        return creatorID;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
