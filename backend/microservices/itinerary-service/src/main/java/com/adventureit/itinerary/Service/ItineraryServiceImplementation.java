package com.adventureit.itinerary.Service;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItineraryServiceImplementation implements ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;
    @Autowired
    private ItineraryEntryRepository itineraryEntryRepository;

    @Autowired
    public ItineraryServiceImplementation(ItineraryRepository itineraryRepository, ItineraryEntryRepository itineraryEntryRepository){
        this.itineraryRepository = itineraryRepository;
        this.itineraryEntryRepository = itineraryEntryRepository;
    }

    @Override
    public String createItinerary(String title, String description, UUID id, UUID advID, UUID userID) throws Exception {
        if(title == null){
            throw new Exception("No title provided");
        }
        if(description == null){
            throw new Exception("No description provided");
        }
        if(id == null){
            throw new Exception("No ID provided");
        }
        if(userID == null){
            throw new Exception("No Creator ID provided");
        }
        if(advID == null){
            throw new Exception("No Adventure ID provided");
        }
        if(itineraryRepository.findItineraryById(id) != null){
            throw new Exception("Itinerary already exists");
        }

        Itinerary itinerary = new Itinerary(title,description,id,advID,userID);
        itineraryRepository.save(itinerary);
        return "Itinerary successfully created";
    }

    @Override
    public String removeItinerary(UUID id) throws Exception {
        if(id == null){
            throw new Exception("No ID provided");
        }
        if(itineraryRepository.findItineraryById(id) == null){
            throw new Exception("Itinerary does not exist");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(id);
        itineraryRepository.delete(itinerary);
        return "Itinerary successfully removed";
    }

    @Override
    public String addItineraryEntry(String title, String description, UUID id, UUID entryContainerID) throws Exception {
        if(title == null){
            throw new Exception("No title provided");
        }
        if(description == null){
            throw new Exception("No description provided");
        }
        if(id == null){
            throw new Exception("No ID provided");
        }
        if(entryContainerID == null){
            throw new Exception("No Itinerary ID provided");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(entryContainerID);
        if(itinerary == null){
            throw new Exception("Itinerary does not exist");
        }
        if(itinerary.CheckIfEntryExists(itinerary.getEntries(), id)){
            throw new Exception("Itinerary Entry already exist");
        }

        ItineraryEntry entry = new ItineraryEntry(title,description,id,entryContainerID);
        itinerary.getEntries().add(entry);
        itineraryRepository.save(itinerary);
        itineraryEntryRepository.save(entry);
        return "Itinerary Entry successfully added";
    }

    @Override
    public String removeItineraryEntry(UUID id, UUID entryContainerID) throws Exception {
        if(id == null){
            throw new Exception("No ID provided");
        }
        if(entryContainerID == null){
            throw new Exception("No Itinerary ID provided");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(entryContainerID);
        if(itinerary == null){
            throw new Exception("Itinerary does not exist");
        }
        if(!itinerary.CheckIfEntryExists(itinerary.getEntries(), id)){
            throw new Exception("Itinerary Entry does not exist");
        }

        ItineraryEntry entry = (ItineraryEntry) itinerary.getEntry(itinerary.getEntries(),id);
        itinerary.getEntries().remove(entry);
        itineraryRepository.save(itinerary);
        itineraryEntryRepository.delete(entry);

        return "Itinerary Entry successfully removed";
    }
}
