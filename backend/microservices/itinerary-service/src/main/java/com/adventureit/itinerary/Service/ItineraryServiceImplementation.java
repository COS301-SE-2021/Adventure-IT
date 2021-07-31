package com.adventureit.itinerary.Service;

import com.adventureit.itinerary.Entity.Itinerary;
import com.adventureit.itinerary.Entity.ItineraryEntry;
import com.adventureit.itinerary.Repository.ItineraryEntryRepository;
import com.adventureit.itinerary.Repository.ItineraryRepository;
import com.adventureit.itinerary.Responses.ItineraryResponseDTO;
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
    public List<ItineraryResponseDTO> viewTrash(UUID id) throws Exception {
        List<Itinerary> itinerary = itineraryRepository.findAllByDeletedEquals(true);
        List<ItineraryResponseDTO> list = new ArrayList<>();
        for (Itinerary b:itinerary) {
            if (b.getAdventureID() == id){
                list.add(new ItineraryResponseDTO(b.getTitle(),b.getDescription(),b.getId(),b.getCreatorID(),b.getAdventureID(),b.getEntries(),b.getDeleted()));

            }
        }
        return list;
    }

    public String restoreItinerary(UUID id) throws Exception {
        if(itineraryRepository.findItineraryById(id) == null){
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
        if(itinerary == null){
            throw new Exception("Itinerary does not exist");
        }

        return new ItineraryResponseDTO(itinerary.getTitle(),itinerary.getDescription(),itinerary.getId(),itinerary.getCreatorID(), itinerary.getAdventureID(),itinerary.getEntries(),itinerary.getDeleted());
    }

    @Override
    public String mockPopulate() {
        final UUID mockItineraryID1 = UUID.fromString("d99dde68-664a-4618-9bb6-4b5dca7d40a8");
        final UUID mockItineraryID2 = UUID.fromString("1e635afb-bde0-4f1a-b705-714218590a63");
        final UUID mockItineraryID3 = UUID.fromString("dc8ab797-5fce-4c25-9023-f0004a6fdb3b");

        final UUID mockEntryID1 = UUID.fromString("96d2201d-69b6-4eb5-b9c2-cdcdc9b577e1");
        final UUID mockEntryID2 = UUID.fromString("b4ef54cc-7418-4ab7-bbde-4850dd4778a0");
        final UUID mockEntryID3 = UUID.fromString("2d5fc8a8-68d8-4616-8c0f-084376e4566c");

        final UUID mockAdventureID1 = UUID.fromString("4b251a3e-9b8a-4d66-8b31-4aeab52f1489");
        final UUID mockAdventureID2 = UUID.fromString("7a56ff82-ff67-4588-8e4d-9af54b1e1b81");
        final UUID mockAdventureID3 = UUID.fromString("a2797bde-faca-43a8-b421-69c2d4bb0756");

        final UUID mockCreatorID1 = UUID.fromString("cbebcd3a-d15a-4e62-ac49-060e744f8896");
        final UUID mockCreatorID2 = UUID.fromString("0bb497d1-fb67-4cfa-bac1-0f1ac7a64fb2");
        final UUID mockCreatorID3 = UUID.fromString("b9655784-7591-49b1-9f57-a4ffd835d079");

        ItineraryEntry mockEntry1 = new ItineraryEntry("Mock Entry 1","Mock",mockEntryID1,mockItineraryID1);
        ItineraryEntry mockEntry2 = new ItineraryEntry("Mock Entry 2","Mock",mockEntryID2,mockItineraryID2);
        ItineraryEntry mockEntry3 = new ItineraryEntry("Mock Entry 3","Mock",mockEntryID3,mockItineraryID3);

        itineraryEntryRepository.save(mockEntry1);
        itineraryEntryRepository.save(mockEntry2);
        itineraryEntryRepository.save(mockEntry3);

        Itinerary mockItinerary1 = new Itinerary("Mock Itinerary 1","Mock",mockItineraryID1,mockCreatorID1,mockAdventureID1);
        Itinerary mockItinerary2 = new Itinerary("Mock Itinerary 2","Mock",mockItineraryID2,mockCreatorID2,mockAdventureID2);
        Itinerary mockItinerary3 = new Itinerary("Mock Itinerary 3","Mock",mockItineraryID3,mockCreatorID3,mockAdventureID3);

        itineraryRepository.save(mockItinerary1);
        itineraryRepository.save(mockItinerary2);
        itineraryRepository.save(mockItinerary3);

        return "Mock Itineraries populated.";
    }

}
