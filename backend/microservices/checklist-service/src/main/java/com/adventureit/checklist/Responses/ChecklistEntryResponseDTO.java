package com.adventureit.checklist.Responses;

import java.util.UUID;

public class ChecklistEntryResponseDTO {
    private UUID id;
    private UUID entryContainerID;
    private String title;
    private Boolean completed;

    public ChecklistEntryResponseDTO(UUID id, UUID entryContainerID, String title, Boolean completed){
        this.id = id;
        this.entryContainerID = entryContainerID;
        this.title = title;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getEntryContainerID() {
        return entryContainerID;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public UUID getId() {
        return id;
    }

    public void setEntryContainerID(UUID entryContainerID) {
        this.entryContainerID = entryContainerID;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
