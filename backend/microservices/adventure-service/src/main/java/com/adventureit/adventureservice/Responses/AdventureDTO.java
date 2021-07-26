package com.adventureit.adventureservice.Responses;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AdventureDTO {


    private final String name;
    private final UUID adventureId;
    private final UUID ownerId;
    private final List<UUID> attendees;
    private final List<UUID> Containers;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;

    public AdventureDTO(String name, UUID adventureId, UUID ownerId, List<UUID> attendees, List<UUID> containers, LocalDate startDate, LocalDate endDate, String description) {

        this.name = name;
        this.adventureId = adventureId;
        this.ownerId = ownerId;
        this.attendees = attendees;
        this.Containers = containers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public UUID getAdventureId() {
        return adventureId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public List<UUID> getAttendees() {
        return attendees;
    }

    public List<UUID> getContainers() {
        return Containers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }
}
