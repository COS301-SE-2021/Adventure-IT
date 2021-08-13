package com.adventureit.chat.Entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class GroupChat extends Chat {

    private String name;
    @OneToMany
    private List<ColorPair> colors;
    UUID adventureID;

    public GroupChat(){}

    public GroupChat(UUID id, UUID adventureID, List<UUID> participants,List<ColorPair> colors, String name){
        super(id,participants);
        this.name = name;
        this.colors = colors;
        this.adventureID = adventureID;
    }

    public GroupChat(UUID adventureID, List<UUID> participants,List<ColorPair> colors, String name){
        super(participants);
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
}
