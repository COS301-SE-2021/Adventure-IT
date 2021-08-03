package com.adventureit.checklist.Service;

import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Entity.ChecklistEntry;
import com.adventureit.checklist.Repository.ChecklistEntryRepository;
import com.adventureit.checklist.Repository.ChecklistRepository;
import com.adventureit.checklist.Responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.Responses.ChecklistResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ChecklistServiceImplementation implements ChecklistService {
    @Autowired
    private ChecklistRepository checklistRepository;
    private ChecklistEntryRepository checklistEntryRepository;

    @Autowired
    public ChecklistServiceImplementation(ChecklistRepository checklistRepository, ChecklistEntryRepository checklistEntryRepository){
        this.checklistRepository = checklistRepository;
        this.checklistEntryRepository = checklistEntryRepository;
    }

    @Override
    public String createChecklist(String title, String description, UUID id, UUID creatorID, UUID adventureID) throws Exception {
        if(title == null){
            throw new Exception("No title provided");
        }
        if(description == null){
            throw new Exception("No description provided");
        }
        if(id == null){
            throw new Exception("No ID provided");
        }
        if(creatorID == null){
            throw new Exception("No Creator ID provided");
        }
        if(adventureID == null){
            throw new Exception("No Adventure ID provided");
        }
        if(checklistRepository.findChecklistById(id) != null){
            throw new Exception("Checklist already exists");
        }

        Checklist checklist = new Checklist(title,description,id,creatorID,adventureID);
        checklistRepository.save(checklist);
        return "Checklist successfully created";
    }

    @Override
    public String addChecklistEntry(String title, UUID entryContainerID) throws Exception {
        if(title == null){
            throw new Exception("No title provided");
        }
        if(entryContainerID == null){
            throw new Exception("No Checklist ID provided");
        }

        Checklist checklist = checklistRepository.findChecklistById(entryContainerID);
        if(checklist == null){
            throw new Exception("Checklist does not exist");
        }

        ChecklistEntry newEntry = new ChecklistEntry(title,entryContainerID);
        checklistEntryRepository.save(newEntry);
        return "Checklist Entry successfully added";
    }

    @Override
    public String removeChecklistEntry(UUID id) throws Exception {
        if(id == null){
            throw new Exception("No ID provided");
        }


        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);
        if(entry == null){
            throw new Exception("Checklist Entry does not exist");
        }

        checklistEntryRepository.delete(checklistEntryRepository.findChecklistEntryById(id));
        return "Checklist Entry successfully removed";
    }

    @Override
    public String editChecklistEntry(UUID id, UUID entryContainerID, String title) throws Exception {
        if(checklistRepository.findChecklistById(entryContainerID) == null){
            throw new Exception("Checklist does not exist.");
        }
        if(id == null){
            throw new Exception("Entry ID not provided.");
        }
        if(entryContainerID == null){
            throw new Exception("Itinerary ID not provided");
        }
        if(title == null){
            throw new Exception("Description Field is null.");
        }

        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);
        if(entry == null){
            throw new Exception("Entry does not exist.");
        }


        if(!title.equals("")){
            entry.setTitle(title);
        }

        checklistEntryRepository.save(entry);
        return "Entry successfully updated";
    }

    @Override
    public void markChecklistEntry(UUID id, UUID entryContainerID) throws Exception {
        if(checklistRepository.findChecklistById(entryContainerID) == null){
            throw new Exception("Checklist does not exist.");
        }
        if(id == null){
            throw new Exception("Entry ID not provided.");
        }
        if(entryContainerID == null){
            throw new Exception("Itinerary ID not provided");
        }
        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);
        if(entry == null){
            throw new Exception("Entry does not exist.");
        }

        entry.setCompleted(!entry.getCompleted());
        checklistEntryRepository.save(entry);
    }

    @Override
    public String softDelete(UUID id,UUID userID) throws Exception {
        if(id == null){
            throw new Exception("Checklist ID not provided.");
        }

        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id,false);

        if(checklist == null){
            throw new Exception("Checklist does not exist.");
        }
        if(!userID.equals(checklist.getCreatorID())){
            throw new Exception("User not Authorised");
        }

        checklist.setDeleted(true);
        checklistRepository.save(checklist);
        return "Checklist moved to bin";
    }

    @Override
    public String hardDelete(UUID id,UUID userID) throws Exception {
        if(id == null){
            throw new Exception("Checklist ID not provided.");
        }

        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id,true);

        if(checklist == null){
            throw new Exception("Checklist is not in trash.");
        }
        if(!userID.equals(checklist.getCreatorID())){
            throw new Exception("User not Authorised");
        }

        List<ChecklistEntry> checklists = checklistEntryRepository.findAllByEntryContainerID(id);

        checklistRepository.delete(checklist);

        for (ChecklistEntry c:checklists) {
            checklistEntryRepository.delete(c);
        }

        return "Checklist deleted";
    }

    @Override
    public List<ChecklistResponseDTO> viewTrash(UUID id) throws Exception {
        List<Checklist> checklists = checklistRepository.findAllByAdventureID(id);
        List<ChecklistResponseDTO> list = new ArrayList<>();
        for (Checklist b:checklists) {
            if (b.isDeleted()){
                list.add(new ChecklistResponseDTO(b.getTitle(),b.getDescription(),b.getId(),b.getCreatorID(),b.getAdventureID(),b.isDeleted()));

            }
        }
        return list;
    }

    public String restoreChecklist(UUID id,UUID userID) throws Exception {
        if(checklistRepository.findChecklistById(id) == null){
            throw new Exception("Checklist does not exist.");
        }

        Checklist checklist = checklistRepository.findChecklistById(id);
        if(!userID.equals(checklist.getCreatorID())){
            throw new Exception("User not Authorised");
        }

        checklist.setDeleted(false);
        checklistRepository.save(checklist);
        return "Checklist was restored";
    }

    @Override
    public List<ChecklistEntryResponseDTO> viewChecklist(UUID id) throws Exception {
        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id, false);
        if(checklist == null){
            throw new Exception("Checklist does not exist");
        }

        List<ChecklistEntry> entries = checklistEntryRepository.findAllByEntryContainerID(id);
        List<ChecklistEntryResponseDTO> list = new ArrayList<>();

        entries.sort(new Comparator<ChecklistEntry>() {
            @Override
            public int compare(ChecklistEntry o1, ChecklistEntry o2) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });

        for (ChecklistEntry entry:entries) {
            list.add(new ChecklistEntryResponseDTO(entry.getId(),entry.getEntryContainerID(),entry.getTitle(),entry.getCompleted()));
        }

        return list;
    }

    @Override
    public String mockPopulate() {
        final UUID mockChecklistID1 = UUID.fromString("47a905f7-132e-430f-8ddb-9f6df642bdfd");
        final UUID mockChecklistID2 = UUID.fromString("ab98913a-ce6a-4066-be7b-bca303156afc");
        final UUID mockChecklistID3 = UUID.fromString("ea04e94c-a9cf-4adc-af46-57449eee46dd");

        final UUID mockEntryID1 = UUID.fromString("a2e545d4-896c-4995-b4fd-6ef067298ef7");
        final UUID mockEntryID2 = UUID.fromString("ec86f1d2-6646-4f9f-a7b4-709043cca763");
        final UUID mockEntryID3 = UUID.fromString("0992ef81-737b-494b-8fe4-a01e36b41ec5");

        final UUID mockAdventureID1 = UUID.fromString("1ebfd84a-1b91-40d5-82b5-31c55da48cad");
        final UUID mockAdventureID2 = UUID.fromString("8354124a-a1cb-430c-aa10-dcb8a0d3f105");
        final UUID mockAdventureID3 = UUID.fromString("12926f8c-7363-44aa-a900-01b613a7b9d6");

        final UUID mockCreatorID1 = UUID.fromString("a73a2c40-d1eb-4094-9ccb-86cb820a0f6d");
        final UUID mockCreatorID2 = UUID.fromString("d0ffba24-9db9-49b4-a50a-d70f68203f7d");
        final UUID mockCreatorID3 = UUID.fromString("6990e144-473f-473b-9af7-e455eaa33871");

        ChecklistEntry mockEntry1 = new ChecklistEntry("Mock Entry 1",mockEntryID1,mockChecklistID1);
        ChecklistEntry mockEntry2 = new ChecklistEntry("Mock Entry 2",mockEntryID2,mockChecklistID2);
        ChecklistEntry mockEntry3 = new ChecklistEntry("Mock Entry 3",mockEntryID3,mockChecklistID3);

        checklistEntryRepository.save(mockEntry1);
        checklistEntryRepository.save(mockEntry2);
        checklistEntryRepository.save(mockEntry3);

        Checklist mockChecklist1 = new Checklist("Mock Checklist 1","Mock",mockChecklistID1,mockCreatorID1,mockAdventureID1);
        Checklist mockChecklist2 = new Checklist("Mock Checklist 2","Mock",mockChecklistID2,mockCreatorID2,mockAdventureID2);
        Checklist mockChecklist3 = new Checklist("Mock Checklist 3","Mock",mockChecklistID3,mockCreatorID3,mockAdventureID3);

        checklistRepository.save(mockChecklist1);
        checklistRepository.save(mockChecklist2);
        checklistRepository.save(mockChecklist3);

        return "Mock Checklists populated.";
    }
}
