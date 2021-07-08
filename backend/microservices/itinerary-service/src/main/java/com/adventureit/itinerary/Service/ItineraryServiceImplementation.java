package com.adventureit.itinerary.Service;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(itinerary.getEntries().contains(id)){
            throw new Exception("Itinerary Entry already exist");
        }

        ItineraryEntry entry = new ItineraryEntry(title,description,id,entryContainerID);
        itineraryEntryRepository.save(entry);
        itinerary.getEntries().add(id);
        itineraryRepository.save(itinerary);
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
        if(!itinerary.getEntries().contains(id)){
            throw new Exception("Itinerary Entry does not exist");
        }

        itineraryEntryRepository.delete(itineraryEntryRepository.findItineraryEntryById(id));
        itinerary.getEntries().remove(id);
        itineraryRepository.save(itinerary);

        return "Itinerary Entry successfully removed";
    }

    @Override
    public String editItineraryEntry(UUID id, UUID entryContainerID, String title, String description) throws Exception {
        if(itineraryRepository.findItineraryById(entryContainerID) == null){
            throw new Exception("Itinerary does not exist.");
        }
        if(id == null){
            throw new Exception("Entry ID not provided.");
        }
        if(entryContainerID == null){
            throw new Exception("Itinerary ID not provided");
        }
        if(title == null){
            throw new Exception("Title Field is null.");
        }
        if(description == null){
            throw new Exception("Description Field is null.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(entryContainerID);

        if(!itinerary.getEntries().contains(id)){
            throw new Exception("Entry does not exist.");
        }

        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(id);

        if(!description.equals("")){
            entry.setDescription(description);
        }
        if(!title.equals("")){
            entry.setTitle(title);
        }

        itineraryEntryRepository.save(entry);
        return "Entry successfully updated";
    }

    @Override
    public String softDelete(UUID id) throws Exception {
        if(id == null){
            throw new Exception("Itinerary ID not provided.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id,false);

        if(itinerary == null){
            throw new Exception("Itinerary does not exist.");
        }

        itinerary.setDeleted(true);
        itineraryRepository.save(itinerary);
        return "Itinerary moved to bin";
    }

    @Override
    public String hardDelete(UUID id) throws Exception {
        if(id == null){
            throw new Exception("Itinerary ID not provided.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id,true);

        if(itinerary == null){
            throw new Exception("Itinerary is not in trash.");
        }

        ArrayList<UUID> entries = new ArrayList<>(itinerary.getEntries());
        itineraryRepository.delete(itinerary);
        for (UUID b : entries) {
            itineraryEntryRepository.delete((itineraryEntryRepository.findItineraryEntryById(b)));
        }

        return "Itinerary deleted";
    }

    @Override
    public List<Itinerary> viewTrash(UUID id) throws Exception {
        return itineraryRepository.findAllByDeletedEquals(true);
    }

    public String restoreBudget(UUID id) throws Exception {
        if(itineraryRepository.findItineraryById(id) == null){
            throw new Exception("Itinerary does not exist.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(id);
        itinerary.setDeleted(false);
        itineraryRepository.save(itinerary);
        return "Itinerary was restored";
    }

}
