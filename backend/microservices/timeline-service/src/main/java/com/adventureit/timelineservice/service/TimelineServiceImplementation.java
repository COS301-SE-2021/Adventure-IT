package com.adventureit.timelineservice.service;

import com.adventureit.timelineservice.entity.Timeline;
import com.adventureit.shareddtos.timeline.TimelineType;
import com.adventureit.timelineservice.exceptions.TimelineDoesNotExistException;
import com.adventureit.timelineservice.repository.TimelineRepository;
import com.adventureit.shareddtos.timeline.responses.TimelineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("TimelineServiceImplementation")
public class TimelineServiceImplementation {

    private final TimelineRepository repo;

    @Autowired
    public TimelineServiceImplementation(TimelineRepository repo) {
        this.repo = repo;
    }

    public String createTimelineEntry(UUID adventureID, String description, TimelineType type){
        UUID timelineID = UUID.randomUUID();
        LocalDateTime time= LocalDateTime.now();
        repo.save(new Timeline(timelineID,adventureID,description,time,type));
        return "New timeline entry created for adventure "+adventureID;
    }


    public List<TimelineDTO> getTimelineByAdventureID(UUID adventureID){
        List<Timeline> list = repo.findAllByAdventureId(adventureID);

        if(list == null){
             throw new TimelineDoesNotExistException("Timeline does not exist for adventure: "+ adventureID);
        }
        List<TimelineDTO> returnList = new ArrayList<>();
        for(Timeline entry: list){
            returnList.add(new TimelineDTO(entry.getTimelineId(),entry.getAdventureId(),entry.getDescription(),entry.getTimestamp(),entry.getType()));
        }
        return returnList;
    }

    @Transactional
    public String deleteTimelineByAdventureID(UUID adventureID){
        List<Timeline> list = repo.findAllByAdventureId(adventureID);
        if(list == null){
            throw new TimelineDoesNotExistException("Timeline does not exist for adventure: "+ adventureID);
        }

        repo.removeAllByAdventureId(adventureID);
        return "Timeline for adventure: "+adventureID+" has been deleted";
    }
}
