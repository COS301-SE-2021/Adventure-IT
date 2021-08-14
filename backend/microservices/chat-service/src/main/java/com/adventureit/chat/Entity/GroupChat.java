package com.adventureit.chat.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class GroupChat{

    @Id
    @NotNull
    UUID groupChatId;
    @ElementCollection(fetch= FetchType.EAGER)
    List<UUID> participants = new ArrayList<>();
    @ElementCollection
    List<UUID> messages = new ArrayList<>();

    private String name;
    @OneToMany
    private List<ColorPair> colors;
    UUID adventureID;

    public GroupChat(){}

    public GroupChat(UUID groupChatId, UUID adventureID, List<UUID> participants, List<ColorPair> colors, String name){
        this.groupChatId = groupChatId;
        this.participants=participants;
        this.name = name;
        this.colors = colors;
        this.adventureID = adventureID;
    }

    public GroupChat(UUID adventureID, List<UUID> participants,List<ColorPair> colors, String name){
        this.participants=participants;
        this.name = name;
        this.colors = colors;
        this.adventureID = adventureID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor(UUID userID){
        for (ColorPair cp : colors){
            if(cp.getUserID().equals(userID))
                return cp.getColor();
        }
        return -1;
    }

    public List<ColorPair> getColors() {
        return colors;
    }

    public void setColors(List<ColorPair> colors) {
        this.colors = colors;
    }

    public UUID getAdventureID() {
        return adventureID;
    }

    public void setAdventureID(UUID adventureID) {
        this.adventureID = adventureID;
    }

    public UUID getGroupChatId() {
        return groupChatId;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

    public List<UUID> getMessages() {
        return messages;
    }
}
