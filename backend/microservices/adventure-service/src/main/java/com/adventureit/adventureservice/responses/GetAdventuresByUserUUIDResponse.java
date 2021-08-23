package com.adventureit.adventureservice.responses;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class GetAdventuresByUserUUIDResponse {
    private long id;
    private String name;
    private UUID adventureId;
    private UUID ownerId;
    private List<UUID> attendees;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private UUID location;

    public GetAdventuresByUserUUIDResponse(long id, String name, UUID adventureId, UUID ownerID, List<UUID> attendees, LocalDate startDate, LocalDate endDate, String description, UUID location){
        this.id = id;
        this.name = name;
        this.adventureId = adventureId;
        this.ownerId = ownerID;
        this.attendees = attendees;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.location = location;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdventureId(UUID adventureId) {
        this.adventureId = adventureId;
    }

    public void setAttendees(List<UUID> attendees) {
        this.attendees = attendees;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<UUID> getAttendees() {
        return attendees;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public UUID getLocation() {
        return location;
    }

    public void setLocation(UUID location) {
        this.location = location;
    }
}
