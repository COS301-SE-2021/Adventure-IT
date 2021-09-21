package com.adventureit.maincontroller.responses;

import com.adventureit.shareddtos.chat.ColorPairDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainGroupChatResponseDTO {
    UUID id;
    UUID adventureID;
    List<UUID> participants = new ArrayList<>();
    private String name;
    private List<ColorPairDTO> colors;

    public MainGroupChatResponseDTO(UUID id, UUID adventureID, List<UUID> participants, String name, List<ColorPairDTO> colors) {
        this.id = id;
        this.adventureID = adventureID;
        this.participants = participants;
        this.name = name;
        this.colors = colors;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UUID> participants) {
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColorPairDTO> getColors() {
        return colors;
    }

    public void setColors(List<ColorPairDTO> colors) {
        this.colors = colors;
    }
}
