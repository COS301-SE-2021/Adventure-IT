package com.adventureit.checklist.service;

import com.adventureit.checklist.entity.Checklist;
import com.adventureit.checklist.entity.ChecklistEntry;
import com.adventureit.checklist.exceptions.NotFoundException;
import com.adventureit.checklist.exceptions.NullFieldException;
import com.adventureit.checklist.exceptions.UnauthorisedException;
import com.adventureit.checklist.repository.ChecklistEntryRepository;
import com.adventureit.checklist.repository.ChecklistRepository;
import com.adventureit.checklist.requests.ChecklistDTO;
import com.adventureit.checklist.responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.responses.ChecklistResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ChecklistServiceImplementation implements ChecklistService {
    @Autowired
    private final ChecklistRepository checklistRepository;
    @Autowired
    private final ChecklistEntryRepository checklistEntryRepository;

    @Autowired
    public ChecklistServiceImplementation(ChecklistRepository checklistRepository, ChecklistEntryRepository checklistEntryRepository){
        this.checklistRepository = checklistRepository;
        this.checklistEntryRepository = checklistEntryRepository;
    }

    @Override
    public String createChecklist(String title, String description, UUID creatorID, UUID adventureID){
        if(title == null){
            throw new NullFieldException("Create Checklist: No title provided");
        }
        if(description == null){
            throw new NullFieldException("Create Checklist: No description provided");
        }
        if(creatorID == null){
            throw new NullFieldException("Create Checklist: No Creator ID provided");
        }
        if(adventureID == null){
            throw new NullFieldException("Create Checklist: No Adventure ID provided");
        }

        Checklist checklist = new Checklist(title,description,creatorID,adventureID);
        checklistRepository.save(checklist);
        return "Checklist successfully created";
    }

    @Override
    public String addChecklistEntry(String title, UUID entryContainerID){
        if(title == null){
            throw new NullFieldException("Add Checklist Entry: No title provided");
        }
        if(entryContainerID == null){
            throw new NullFieldException("Add Checklist Entry: No Checklist ID provided");
        }

        Checklist checklist = checklistRepository.findChecklistById(entryContainerID);
        if(checklist == null){
            throw new NotFoundException("Add Checklist Entry: Checklist does not exist");
        }

        ChecklistEntry newEntry = new ChecklistEntry(title,entryContainerID);
        checklistEntryRepository.save(newEntry);
        return "Checklist Entry successfully added";
    }

    @Override
    public String removeChecklistEntry(UUID id){
        if(id == null){
            throw new NullFieldException("Remove Checklist Entry: No ID provided");
        }

        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);
        if(entry == null){
            throw new NotFoundException("Remove Checklist Entry: Checklist Entry does not exist");
        }

        checklistEntryRepository.delete(checklistEntryRepository.findChecklistEntryById(id));
        return "Checklist Entry successfully removed";
    }

    @Override
    public String editChecklistEntry(UUID id, UUID entryContainerID, String title){
        if(checklistRepository.findChecklistById(entryContainerID) == null){
            throw new NotFoundException("Edit Checklist Entry: Checklist does not exist.");
        }
        if(id == null){
            throw new NullFieldException("Edit Checklist Entry: Entry ID not provided.");
        }
        if(entryContainerID == null){
            throw new NullFieldException("Edit Checklist Entry: Itinerary ID not provided");
        }
        if(title == null){
            throw new NullFieldException("Edit Checklist Entry: Description Field is null.");
        }

        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);
        if(entry == null){
            throw new NotFoundException("Edit Checklist Entry: Entry does not exist.");
        }

        if(!title.equals("")){
            entry.setTitle(title);
        }

        checklistEntryRepository.save(entry);
        return "Entry successfully updated";
    }

    @Override
    public void markChecklistEntry(UUID id){
        if(id == null){
            throw new NullFieldException("Mark Checklist Entry: Entry ID not provided.");
        }
        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);
        if(entry == null){
            throw new NotFoundException("Mark Checklist Entry: Entry does not exist.");
        }

        entry.setCompleted(!entry.getCompleted());
        checklistEntryRepository.save(entry);
    }

    @Override
    public String softDelete(UUID id,UUID userID){
        if(id == null){
            throw new NullFieldException("Soft Delete Checklist: Checklist ID not provided.");
        }

        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id,false);

        if(checklist == null){
            throw new NullFieldException("Soft Delete Checklist: Checklist does not exist.");
        }
        if(!userID.equals(checklist.getCreatorID())){
            throw new UnauthorisedException("Soft Delete Checklist: User not Authorised");
        }

        checklist.setDeleted(true);
        checklistRepository.save(checklist);
        return "Checklist moved to bin";
    }

    @Override
    public String hardDelete(UUID id,UUID userID){
        if(id == null){
            throw new NullFieldException("Hard Delete Checklist: Checklist ID not provided.");
        }

        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id,true);

        if(checklist == null){
            throw new NotFoundException("Hard Delete Checklist: Checklist is not in trash.");
        }
        if(!userID.equals(checklist.getCreatorID())){
            throw new UnauthorisedException("Hard Delete Checklist: User not Authorised");
        }

        List<ChecklistEntry> checklists = checklistEntryRepository.findAllByEntryContainerID(id);

        checklistRepository.delete(checklist);

        for (ChecklistEntry c:checklists) {
            checklistEntryRepository.delete(c);
        }

        return "Checklist deleted";
    }

    @Override
    public List<ChecklistResponseDTO> viewTrash(UUID id){
        List<Checklist> checklists = checklistRepository.findAllByAdventureID(id);
        List<ChecklistResponseDTO> list = new ArrayList<>();
        for (Checklist b:checklists) {
            if (b.isDeleted()){
                list.add(new ChecklistResponseDTO(b.getTitle(),b.getDescription(),b.getId(),b.getCreatorID(),b.getAdventureID(),b.isDeleted()));

            }
        }
        return list;
    }

    public String restoreChecklist(UUID id,UUID userID){
        if(checklistRepository.findChecklistById(id) == null){
            throw new NotFoundException("Restore Checklist: Checklist does not exist.");
        }

        Checklist checklist = checklistRepository.findChecklistById(id);
        if(!userID.equals(checklist.getCreatorID())){
            throw new UnauthorisedException("Restore Checklist: User not Authorised");
        }

        checklist.setDeleted(false);
        checklistRepository.save(checklist);
        return "Checklist was restored";
    }

    @Override
    public List<ChecklistEntryResponseDTO> viewChecklist(UUID id){
        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id, false);
        if(checklist == null){
            throw new NotFoundException("View Checklist: Checklist does not exist");
        }

        List<ChecklistEntry> entries = checklistEntryRepository.findAllByEntryContainerID(id);
        List<ChecklistEntryResponseDTO> list = new ArrayList<>();

        entries.sort(Comparator.comparing(ChecklistEntry::getTimestamp));

        for (ChecklistEntry entry:entries) {
            list.add(new ChecklistEntryResponseDTO(entry.getId(),entry.getEntryContainerID(),entry.getTitle(),entry.getCompleted()));
        }

        return list;
    }

    @Override
    public ChecklistDTO getChecklistByChecklistId(UUID checklistId){
        Checklist check = checklistRepository.findChecklistById(checklistId);
        return new ChecklistDTO(check.getId(),check.getCreatorID(),check.getAdventureID(),check.getTitle(),check.getDescription(),check.isDeleted());
    }

    @Override
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(UUID id) {
        List<Checklist> checklists = checklistRepository.findAllByAdventureID(id);
        List<ChecklistResponseDTO> list = new ArrayList<>();
        for (Checklist c:checklists) {
            if(!c.isDeleted()){
                list.add(new ChecklistResponseDTO(c.getTitle(),c.getDescription(),c.getId(),c.getCreatorID(),c.getAdventureID(),c.isDeleted()));
            }
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
