package com.adventureit.timelineservice.Service;

import com.adventureit.timelineservice.Entity.Timeline;
import com.adventureit.timelineservice.Entity.TimelineType;
import com.adventureit.timelineservice.Exceptions.TimelineDoesNotExistException;
import com.adventureit.timelineservice.Repository.TimelineRepository;
import com.adventureit.timelineservice.Responses.TimelineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TimelineServiceImplementation {


    private TimelineRepository repo;

    @Autowired
    public TimelineServiceImplementation(TimelineRepository repo) {
        this.repo = repo;
    }

    public String createTimelineEntry(UUID adventureID, UUID userID, String description, TimelineType type){
        UUID timelineID = UUID.randomUUID();
        LocalDateTime time= LocalDateTime.now();
        repo.save(new Timeline(timelineID,adventureID,userID,description,time,type));
        return "New timeline entry created for adventure "+adventureID+" by user "+userID;
    }

    public List<TimelineDTO> GetTimelineByAdventureID(UUID adventureID){
        List<Timeline> list =repo.findAllByAdventureID(adventureID);

        if(list == null){
             throw new TimelineDoesNotExistException("Timeline does not exist for adventure: "+ adventureID);
        }
        List<TimelineDTO> returnList = new ArrayList<>();
        for(Timeline entry: list){
            returnList.add(new TimelineDTO(entry.getTimelineID(),entry.getAdventureID(),entry.getUserID(),entry.getDescrpition(),entry.getTimestamp(),entry.getType()));
        }
        return returnList;
    }

    public String deleteTimelineByAdventureID(UUID adventureID){
        List<Timeline> list = repo. findAllByAdventureID(adventureID);
        if(list == null){
            throw new TimelineDoesNotExistException("Timeline does not exist for adventure: "+ adventureID);
        }

        repo.removeAllByAdventureID(adventureID);
        return "Timeline for adventure: "+adventureID+" has been deleted";
    }


}
