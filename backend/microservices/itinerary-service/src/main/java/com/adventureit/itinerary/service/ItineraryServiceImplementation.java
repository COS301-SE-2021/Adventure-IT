package com.adventureit.itinerary.service;

import com.adventureit.itinerary.entity.Itinerary;
import com.adventureit.itinerary.entity.ItineraryEntry;
import com.adventureit.itinerary.exceptions.NotFoundException;
import com.adventureit.itinerary.exceptions.NullFieldException;
import com.adventureit.itinerary.exceptions.RegistrationException;
import com.adventureit.itinerary.exceptions.UnauthorisedException;
import com.adventureit.itinerary.repository.ItineraryEntryRepository;
import com.adventureit.itinerary.repository.ItineraryRepository;
import com.adventureit.shareddtos.itinerary.responses.ItineraryEntryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.ItineraryResponseDTO;
import com.adventureit.shareddtos.itinerary.responses.StartDateEndDateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ItineraryServiceImplementation implements ItineraryService {

    @Autowired
    private final ItineraryRepository itineraryRepository;
    @Autowired
    private final ItineraryEntryRepository itineraryEntryRepository;

    @Autowired
    public ItineraryServiceImplementation(ItineraryRepository itineraryRepository, ItineraryEntryRepository itineraryEntryRepository) {
        this.itineraryRepository = itineraryRepository;
        this.itineraryEntryRepository = itineraryEntryRepository;
    }

    @Override
    public String createItinerary(String title, String description, UUID advID, UUID userID) {
        if (title == null) {
            throw new NullFieldException("Create Itinerary: No title provided");
        }
        if (description == null) {
            throw new NullFieldException("Create Itinerary: No description provided");
        }
        if (userID == null) {
            throw new NullFieldException("Create Itinerary: No Creator ID provided");
        }
        if (advID == null) {
            throw new NullFieldException("Create Itinerary: No Adventure ID provided");
        }

        Itinerary itinerary = new Itinerary(title, description, advID, userID);
        itineraryRepository.save(itinerary);
        return "Itinerary successfully created";
    }

    @Override
    public UUID addItineraryEntry(String title, String description, UUID entryContainerID, String location, String timestamp) {
        if (title == null) {
            throw new NullFieldException("Add Itinerary Entry: No title provided");
        }
        if (description == null) {
            throw new NullFieldException("Add Itinerary Entry: No description provided");
        }
        if (entryContainerID == null) {
            throw new NullFieldException("Add Itinerary Entry: No Itinerary ID provided");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(entryContainerID);
        if (itinerary == null) {
            throw new NotFoundException("Add Itinerary Entry: Itinerary does not exist");
        }

        LocalDateTime newTimestamp = LocalDateTime.parse(timestamp);
        ItineraryEntry newEntry = new ItineraryEntry(title, description, entryContainerID, null, newTimestamp);

        ItineraryEntry entry = itineraryEntryRepository.save(newEntry);
        itineraryRepository.save(itinerary);
        return entry.getId();
    }

    @Override
    public String removeItineraryEntry(UUID id) {
        if (id == null) {
            throw new NullFieldException("Remove Itinerary Entry: No ID provided");
        }

        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(id);

        if (entry == null) {
            throw new NotFoundException("Remove Itinerary Entry: Itinerary Entry does not exist");
        }

        itineraryEntryRepository.delete(entry);

        return "Itinerary Entry successfully removed";
    }

    @Override

    public String editItineraryEntry(UUID id, UUID entryContainerID, String title, String description, UUID location, LocalDateTime timestamp) {
        if (itineraryRepository.findItineraryById(entryContainerID) == null) {
            throw new NullFieldException("Edit Itinerary Entry: Itinerary does not exist.");
        }
        if (id == null) {
            throw new NullFieldException("Edit Itinerary Entry: Entry ID not provided.");
        }
        if (entryContainerID == null) {
            throw new NullFieldException("Edit Itinerary Entry: Itinerary ID not provided");
        }
        if (title == null) {
            throw new NullFieldException("Edit Itinerary Entry: Title Field is null.");
        }
        if (description == null) {
            throw new NullFieldException("Edit Itinerary Entry: Description Field is null.");
        }

        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryByIdAndEntryContainerID(id, entryContainerID);

        if (entry == null) {
            throw new NotFoundException("Edit Itinerary Entry: Entry does not exist.");
        }

        ItineraryEntry newEntry = itineraryEntryRepository.findItineraryEntryById(id);

        if (!description.equals("")) {
            newEntry.setDescription(description);
        }
        if (!title.equals("")) {
            newEntry.setTitle(title);
        }
        if (location != null) {
            newEntry.setLocation(location);
        }
        if (timestamp != null) {
            newEntry.setTimestamp(timestamp);
        }

        itineraryEntryRepository.save(newEntry);
        return "Entry successfully updated";
    }

    @Override
    public String softDelete(UUID id, UUID userID) {
        if (id == null) {
            throw new NullFieldException("Soft Delete: Itinerary ID not provided.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id, false);

        if (itinerary == null) {
            throw new NotFoundException("Soft Delete: Itinerary does not exist.");
        }
        if (!userID.equals(itinerary.getCreatorID())) {
            throw new UnauthorisedException("Soft Delete: User not Authorised");
        }

        itinerary.setDeleted(true);
        itineraryRepository.save(itinerary);
        return "Itinerary moved to bin";
    }

    @Override
    public String hardDelete(UUID id, UUID userID) {
        if (id == null) {
            throw new NullFieldException("Hard Delete: Itinerary ID not provided.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id, true);

        if (itinerary == null) {
            throw new NotFoundException("Hard Delete: Itinerary is not in trash.");
        }
        if (!userID.equals(itinerary.getCreatorID())) {
            throw new NotFoundException("Hard Delete: User not Authorised");
        }

        List<ItineraryEntry> entries = itineraryEntryRepository.findAllByEntryContainerID(id);

        itineraryRepository.delete(itinerary);

        for (ItineraryEntry entry : entries) {
            itineraryEntryRepository.delete(entry);
        }

        return "Itinerary deleted";
    }

    @Override
    public List<ItineraryResponseDTO> viewTrash(UUID id) {
        List<Itinerary> itinerary = itineraryRepository.findAllByAdventureID(id);
        List<ItineraryResponseDTO> list = new ArrayList<>();

        for (Itinerary b : itinerary) {
            if (b.getDeleted().equals(true)) {
                list.add(new ItineraryResponseDTO(b.getTitle(), b.getDescription(), b.getId(), b.getCreatorID(), b.getAdventureID(), b.getDeleted()));
            }
        }

        return list;
    }

    public String restoreItinerary(UUID id, UUID userID) {
        if (itineraryRepository.findItineraryById(id) == null) {
            throw new NotFoundException("Restore Itinerary: Itinerary does not exist.");
        }

        Itinerary itinerary = itineraryRepository.findItineraryById(id);

        if (!userID.equals(itinerary.getCreatorID())) {
            throw new UnauthorisedException("Restore Itinerary: User not Authorised");
        }

        itinerary.setDeleted(false);
        itineraryRepository.save(itinerary);
        return "Itinerary was restored";
    }

    @Override
    public List<ItineraryEntryResponseDTO> viewItinerary(UUID id) {
        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id, false);
        if (itinerary == null) {
            throw new NotFoundException("View Itinerary: Itinerary does not exist");
        }

        List<ItineraryEntry> entries = itineraryEntryRepository.findAllByEntryContainerID(id);
        List<ItineraryEntryResponseDTO> list = new ArrayList<>();

        for (ItineraryEntry entry : entries) {
            list.add(new ItineraryEntryResponseDTO(entry.getId(), entry.getEntryContainerID(), entry.getTitle(), entry.getDescription(), entry.isCompleted(), entry.getLocation(), entry.getTimestamp(), entry.getRegisteredUsers()));

            list.sort(Comparator.comparing(ItineraryEntryResponseDTO::getTimestamp));
        }

        return list;
    }

    @Override
    public void markCompleted(UUID id) {
        if (itineraryEntryRepository.findItineraryEntryById(id) == null) {
            throw new NotFoundException("Mark Completed: Entry does not exist");
        }

        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(id);
        entry.setCompleted(!entry.isCompleted());
        itineraryEntryRepository.save(entry);
    }

    @Override
    public ItineraryEntryResponseDTO nextItem(UUID id, UUID userID) {
        ItineraryEntry next = null;
        List<ItineraryEntry> entries = new ArrayList<>();
        List<ItineraryEntry> temp;

        List<Itinerary> itineraries = itineraryRepository.findAllByAdventureID(id);

        for (Itinerary i : itineraries) {
            if (i.getDeleted().equals(false)) {
                temp = itineraryEntryRepository.findAllByEntryContainerID(i.getId());
                entries.addAll(temp);
            }
        }

        entries.sort(Comparator.comparing(ItineraryEntry::getTimestamp));

        for (ItineraryEntry entry : entries) {
            if (entry.getRegisteredUsers().containsKey(userID)&&entry.getTimestamp().compareTo(LocalDateTime.now()) > 0) {
                next = entry;
                break;
            }
        }

        if (next == null) {
            throw new NotFoundException("Next Item: No items available");
        }

        return new ItineraryEntryResponseDTO(next.getId(), next.getEntryContainerID(), next.getTitle(), next.getDescription(), next.isCompleted(), next.getLocation(), next.getTimestamp(), next.getRegisteredUsers());
    }

    @Override
    public void setItineraryEntryLocation(UUID itineraryID, UUID locationID) {
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(itineraryID);
        entry.setLocation(locationID);
        itineraryEntryRepository.save(entry);
    }

    @Override
    public ItineraryResponseDTO getItineraryById(UUID itineraryContainerID) {
        Itinerary entry = itineraryRepository.getItineraryById(itineraryContainerID);
        return new ItineraryResponseDTO(entry.getTitle(), entry.getDescription(), entry.getId(), entry.getCreatorID(), entry.getAdventureID(), entry.getDeleted());
    }

    @Override
    public ItineraryResponseDTO getItineraryByEntryId(UUID itineraryEntryID) {
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(itineraryEntryID);
        Itinerary container = itineraryRepository.getItineraryById(entry.getEntryContainerID());
        return new ItineraryResponseDTO(container.getTitle(), container.getDescription(), container.getId(), container.getCreatorID(), container.getAdventureID(), container.getDeleted());
    }

    @Override
    public List<ItineraryResponseDTO> viewItinerariesByAdventure(UUID id) {
        List<Itinerary> itineraries = itineraryRepository.findAllByAdventureID(id);
        List<ItineraryResponseDTO> list = new ArrayList<>();

        for (Itinerary c : itineraries) {
            if (c.getDeleted().equals(false)) {
                list.add(new ItineraryResponseDTO(c.getTitle(), c.getDescription(), c.getId(), c.getCreatorID(), c.getAdventureID(), c.getDeleted()));
            }
        }

        return list;
    }

    @Override
    public ItineraryEntryResponseDTO getItineraryEntry(UUID id) {
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(id);
        if (entry == null) {
            throw new NotFoundException("Get Itinerary Entry: Entry does not exist");
        }

        return new ItineraryEntryResponseDTO(entry.getId(), entry.getEntryContainerID(), entry.getTitle(), entry.getDescription(), entry.isCompleted(), entry.getLocation(), entry.getTimestamp(), entry.getRegisteredUsers());
    }

    @Override
    public void checkUserOff(UUID entryID, UUID userID) {
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(entryID);
        if (entry == null) {
            throw new NotFoundException("Get Itinerary Entry: Entry does not exist");
        }

        if (!entry.getRegisteredUsers().containsKey(userID)) {
            throw new RegistrationException("Get Itinerary Entry: User not registered");
        }

        Map<UUID, Boolean> list = entry.getRegisteredUsers();

        if (list.size() != 0) {
            for (Map.Entry<UUID, Boolean> item : list.entrySet()){
                if(item.getValue().equals(false)){
                    entry.setCompleted(false);
                    break;
                }
                entry.setCompleted(true);
            }
        }

        entry.getRegisteredUsers().replace(userID, true);
        itineraryEntryRepository.save(entry);
    }

    @Override
    public String registerUser(UUID entryID, UUID userID) {
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(entryID);
        if (entry == null) {
            throw new NotFoundException("Register User: Entry does not exist");
        }

        if (entry.getRegisteredUsers().containsKey(userID)) {
            throw new RegistrationException("Register User: User already registered");
        }

        entry.getRegisteredUsers().put(userID, false);
        itineraryEntryRepository.save(entry);

        return "Successfully Registered";
    }

    @Override
    public String deregisterUser(UUID entryID, UUID userID) {
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(entryID);
        if (entry == null) {
            throw new NotFoundException("Deregister User: Entry does not exist");
        }

        if (!entry.getRegisteredUsers().containsKey(userID)) {
            throw new RegistrationException("Deregister User: User not registered");
        }

        entry.getRegisteredUsers().remove(userID);
        itineraryEntryRepository.save(entry);

        return "Successfully Deregistered";
    }

    @Override
    public StartDateEndDateResponseDTO getStartAndEndDate(UUID id) {
        Itinerary itinerary = itineraryRepository.findItineraryByIdAndDeleted(id, false);
        if (itinerary == null) {
            throw new NotFoundException("Get Start And End Date: Itinerary does not exist");
        }

        List<ItineraryEntry> entries = itineraryEntryRepository.findAllByEntryContainerID(id);

        if (entries != null) {
            entries.sort(Comparator.comparing(ItineraryEntry::getTimestamp));
            LocalDateTime startDate = entries.get(0).getTimestamp();
            LocalDateTime endDate = entries.get(entries.size() - 1).getTimestamp();
            return new StartDateEndDateResponseDTO(startDate, endDate);
        } else {
            throw new NotFoundException("Get Start And End Date: Not Itinerary entries");

        }
    }

    @Override
    public Map<UUID, Boolean> getRegisteredUsers(UUID id) {
        ItineraryEntry entry = itineraryEntryRepository.findItineraryEntryById(id);
        if (entry == null) {
            throw new NotFoundException("Get Registered Users: Entry does not exist");
        }

        if(entry.getRegisteredUsers() == null ){
            throw new NotFoundException("Get Registered Users: Registered users list is null");
        }

        return entry.getRegisteredUsers();
    }

    @Override
    public void deleteAllByAdventure(UUID id) {
        List<Itinerary> itineraries = itineraryRepository.findAllByAdventureID(id);
        List<ItineraryEntry> entries = new ArrayList<>();
        if(itineraries == null || itineraries.isEmpty()){
            return;
        }

        for (Itinerary itinerary:itineraries) {
            entries.addAll(itineraryEntryRepository.findAllByEntryContainerID(itinerary.getId()));
            itineraryRepository.delete(itinerary);
        }

        if(entries.isEmpty()){
            return;
        }

        for (ItineraryEntry entry:entries) {
            itineraryEntryRepository.delete(entry);
        }
    }

    @Override
    public String mockPopulate() {
        final String MOCK_ADV_ID = "948f3e05-4bca-49ba-8955-fb936992fe02";
        final UUID mockItineraryID1 = UUID.fromString("d99dde68-664a-4618-9bb6-4b5dca7d40a8");
        final UUID mockItineraryID2 = UUID.fromString("d99dde68-664a-4618-9bb6-4b4dca7d40a8");
        final UUID mockItineraryID3 = UUID.fromString("d99dde68-664a-4618-9bb6-4b5dca7d30a8");

        final UUID mockEntryID1 = UUID.fromString("96d2201d-69b6-4eb5-b9c2-cdcdc9b577e1");
        final UUID mockEntryID2 = UUID.fromString("b4ef54cc-7418-4ab7-bbde-4850dd4778a0");
        final UUID mockEntryID3 = UUID.fromString("2d5fc8a8-68d8-4616-8c0f-084376e4566c");

        final UUID mockAdventureID1 = UUID.fromString(MOCK_ADV_ID);
        final UUID mockAdventureID2 = UUID.fromString(MOCK_ADV_ID);
        final UUID mockAdventureID3 = UUID.fromString(MOCK_ADV_ID);

        final UUID mockCreatorID1 = UUID.fromString("cbebcd3a-d15a-4e62-ac49-060e744f8896");
        final UUID mockCreatorID2 = UUID.fromString("0bb497d1-fb67-4cfa-bac1-0f1ac7a64fb2");
        final UUID mockCreatorID3 = UUID.fromString("b9655784-7591-49b1-9f57-a4ffd835d079");


        ItineraryEntry mockEntry1 = new ItineraryEntry("Mock Entry 1", "Mock", mockEntryID1, mockItineraryID1, UUID.randomUUID(), LocalDateTime.now());
        ItineraryEntry mockEntry2 = new ItineraryEntry("Mock Entry 2", "Mock", mockEntryID2, mockItineraryID1, UUID.randomUUID(), LocalDateTime.now());
        ItineraryEntry mockEntry3 = new ItineraryEntry("Mock Entry 3", "Mock", mockEntryID3, mockItineraryID1, UUID.randomUUID(), LocalDateTime.now());

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
