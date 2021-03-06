package com.adventureit.maincontroller.responses;

import com.adventureit.shareddtos.location.responses.LocationResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdventureResponseDTO {
    private long id;
    private String name;
    private UUID adventureId;
    private UUID ownerId;
    private List<UUID> attendees;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private LocationResponseDTO location;

    public AdventureResponseDTO(String name, String description, UUID adventureId, UUID ownerId, LocalDate sd, LocalDate ed) {
        this.name = name;
        this.description = description;
        this.adventureId = adventureId;
        this.ownerId = ownerId;
        this.attendees = new ArrayList<>(List.of(ownerId));
        this.startDate = sd;
        this.endDate = ed;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public UUID getAdventureId() {
        return adventureId;
    }


    public List<UUID> getAttendees() {
        return attendees;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public void setLocation(LocationResponseDTO location) {
        this.location = location;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttendees(List<UUID> attendees) {
        this.attendees = attendees;
    }

    public void setAdventureId(UUID adventureId) {
        this.adventureId = adventureId;
    }


    public LocationResponseDTO getLocation() {
        return location;
    }
}
