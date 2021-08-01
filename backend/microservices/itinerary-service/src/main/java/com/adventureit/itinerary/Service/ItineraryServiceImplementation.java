package com.adventureit.itinerary.Service;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import com.adventureit.itinerary.Responses.ItineraryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public ItineraryServiceImplementation(ItineraryRepository itineraryRepository, ItineraryEntryRepository itineraryEntryRepository) {
        this.itineraryRepository = itineraryRepository;
        this.itineraryEntryRepository = itineraryEntryRepository;
    }

    @Override
    public String createItinerary(String title, String description, UUID id, UUID advID, UUID userID) throws Exception {
        if (title == null) {
            throw new Exception("No title provided");
        }
        if (description == null) {
            throw new Exception("No description provided");
        }
        if (id == null) {
            throw new Exception("No ID provided");
        }
        if (userID == null) {
            throw new Exception("No Creator ID provided");
        }
        if (advID == null) {
            throw new Exception("No Adventure ID provided");
        }
        if (itineraryRepository.findItineraryById(id) != null) {
            throw new Exception("Itinerary already exists");
        }

        Itinerary itinerary = new Itinerary(title, description, id, advID, userID);
        itineraryRepository.save(itinerary);
        return "Itinerary successfully created";
    }

    @Override

    public String addItineraryEntry(String title, String description, UUID id, UUID entryContainerID, String location, LocalDateTime timestamp) throws Exception {
        if(title == null){

            throw new Exception("No title provided");
        }
        if (description == null) {
            throw new Exception("No description provided");
        }
        if (id == null) {
            throw new Exception("No ID provided");
        }
        if (entryContainerID == null) {
            throw new Exception("No Itinerary ID provided");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(entryContainerID);
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryByIdAndEntryContainerID(id, entryContainerID);
        if (itinerary == null) {
            throw new Exception("Itinerary does not exist");
        }
        if (entry != null) {
            throw new Exception("Itinerary Entry already exist");
        }


        ItineraryEntry newEntry = new ItineraryEntry(title,description,id,entryContainerID,location,timestamp);

        itineraryEntryRepository.save(newEntry);
        itineraryRepository.save(itinerary);
        return "Itinerary Entry successfully added";
    }

    @Override
    public String removeItineraryEntry(UUID id) throws Exception {
        if (id == null) {
            throw new Exception("No ID provided");
        }

        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(id);

        if (entry == null) {
            throw new Exception("Itinerary Entry does not exist");
        }

        itineraryEntryRepository.delete(itineraryEntryRepository.findItineraryEntryById(id));

        return "Itinerary Entry successfully removed";
    }

    @Override

    public String editItineraryEntry(UUID id, UUID entryContainerID, String title, String description, String location, LocalDateTime timestamp ) throws Exception {
        if(itineraryRepository.findItineraryById(entryContainerID) == null){

            throw new Exception("Itinerary does not exist.");
        }
        if (id == null) {
            throw new Exception("Entry ID not provided.");
        }
        if (entryContainerID == null) {
            throw new Exception("Itinerary ID not provided");
        }
        if (title == null) {
            throw new Exception("Title Field is null.");
        }
        if (description == null) {
            throw new Exception("Description Field is null.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(entryContainerID);
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryByIdAndEntryContainerID(id, entryContainerID);

        if (entry == null) {
            throw new Exception("Entry does not exist.");
        }

        ItineraryEntry newEntry = itineraryEntryRepository.findItineraryEntryById(id);

        if (!description.equals("")) {
            newEntry.setDescription(description);
        }
        if (!title.equals("")) {
            newEntry.setTitle(title);
        }
        if(!location.equals("")){
            newEntry.setLocation(location);
        }
        if(!(timestamp == null)){
            newEntry.setTimestamp(timestamp);
        }

        itineraryEntryRepository.save(newEntry);
        return "Entry successfully updated";
    }

    @Override
    public String softDelete(UUID id) throws Exception {
        if (id == null) {
            throw new Exception("Itinerary ID not provided.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id, false);

        if (itinerary == null) {
            throw new Exception("Itinerary does not exist.");
        }

        itinerary.setDeleted(true);
        itineraryRepository.save(itinerary);
        return "Itinerary moved to bin";
    }

    @Override
    public String hardDelete(UUID id) throws Exception {
        if (id == null) {
            throw new Exception("Itinerary ID not provided.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id, true);

        if (itinerary == null) {
            throw new Exception("Itinerary is not in trash.");
        }

        List<ItineraryEntry> entries = itineraryEntryRepository.findAllByEntryContainerID(id);

        itineraryRepository.delete(itinerary);

        for (ItineraryEntry entry:entries) {
            itineraryEntryRepository.delete(entry);
        }

        return "Itinerary deleted";
    }

    @Override
    public List<ItineraryResponseDTO> viewTrash(UUID id) throws Exception {

        List<Itinerary> itinerary = itineraryRepository.findAllByAdventureID(id);
        List<ItineraryResponseDTO> list = new ArrayList<>();
        for (Itinerary b:itinerary) {
            if(b.getDeleted()){
                list.add(new ItineraryResponseDTO(b.getTitle(),b.getDescription(),b.getId(),b.getCreatorID(),b.getAdventureID(),b.getDeleted()));

            }
        }
        return list;
    }

    public String restoreItinerary(UUID id) throws Exception {
        if (itineraryRepository.findItineraryById(id) == null) {
            throw new Exception("Itinerary does not exist.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(id);
        itinerary.setDeleted(false);
        itineraryRepository.save(itinerary);
        return "Itinerary was restored";
    }

    @Override
    public ItineraryResponseDTO viewItinerary(UUID id) throws Exception {
        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id, false);
        if (itinerary == null) {
            throw new Exception("Itinerary does not exist");
        }

        return new ItineraryResponseDTO(itinerary.getTitle(), itinerary.getDescription(), itinerary.getId(), itinerary.getCreatorID(), itinerary.getAdventureID(), itinerary.getDeleted());
    }

    @Override
    public void markCompleted(UUID id) throws Exception {
        if(itineraryEntryRepository.findItineraryEntryById(id) == null){
            throw new Exception("Entry does not exist");
        }

        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(id);
        entry.setCompleted(!entry.isCompleted());
        itineraryEntryRepository.save(entry);
    }

    @Override
    public String mockPopulate() {
        final UUID mockItineraryID1 = UUID.fromString("d99dde68-664a-4618-9bb6-4b5dca7d40a8");
        final UUID mockItineraryID2 = UUID.fromString("d99dde68-664a-4618-9bb6-4b4dca7d40a8");
        final UUID mockItineraryID3 = UUID.fromString("d99dde68-664a-4618-9bb6-4b5dca7d30a8");

        final UUID mockEntryID1 = UUID.fromString("96d2201d-69b6-4eb5-b9c2-cdcdc9b577e1");
        final UUID mockEntryID2 = UUID.fromString("b4ef54cc-7418-4ab7-bbde-4850dd4778a0");
        final UUID mockEntryID3 = UUID.fromString("2d5fc8a8-68d8-4616-8c0f-084376e4566c");

        final UUID mockAdventureID1 = UUID.fromString("948f3e05-4bca-49ba-8955-fb936992fe02");
        final UUID mockAdventureID2 = UUID.fromString("948f3e05-4bca-49ba-8955-fb936992fe02");
        final UUID mockAdventureID3 = UUID.fromString("948f3e05-4bca-49ba-8955-fb936992fe02");

        final UUID mockCreatorID1 = UUID.fromString("cbebcd3a-d15a-4e62-ac49-060e744f8896");
        final UUID mockCreatorID2 = UUID.fromString("0bb497d1-fb67-4cfa-bac1-0f1ac7a64fb2");
        final UUID mockCreatorID3 = UUID.fromString("b9655784-7591-49b1-9f57-a4ffd835d079");


        ItineraryEntry mockEntry1 = new ItineraryEntry("Mock Entry 1","Mock",mockEntryID1,mockItineraryID1,"Location 1",LocalDateTime.now());
        ItineraryEntry mockEntry2 = new ItineraryEntry("Mock Entry 2","Mock",mockEntryID2,mockItineraryID1,"Location 2",LocalDateTime.now());
        ItineraryEntry mockEntry3 = new ItineraryEntry("Mock Entry 3","Mock",mockEntryID3,mockItineraryID1,"Location 3",LocalDateTime.now());

        itineraryEntryRepository.save(mockEntry1);
        itineraryEntryRepository.save(mockEntry2);
        itineraryEntryRepository.save(mockEntry3);

        Itinerary mockItinerary1 = new Itinerary("Mock Itinerary 1", "Mock", mockItineraryID1, mockCreatorID1, mockAdventureID1);
        Itinerary mockItinerary2 = new Itinerary("Mock Itinerary 2", "Mock", mockItineraryID2, mockCreatorID2, mockAdventureID2);
        Itinerary mockItinerary3 = new Itinerary("Mock Itinerary 3", "Mock", mockItineraryID3, mockCreatorID3, mockAdventureID3);

        itineraryRepository.save(mockItinerary1);
        itineraryRepository.save(mockItinerary2);
        itineraryRepository.save(mockItinerary3);

        return "Mock Itineraries populated.";
    }

}
