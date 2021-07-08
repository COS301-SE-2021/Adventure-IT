package com.adventureit.checklist.Service;

import com.adventureit.adventureservice.Entity.Entry;
import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Entity.ChecklistEntry;
import com.adventureit.checklist.Repository.ChecklistEntryRepository;
import com.adventureit.checklist.Repository.ChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public String createChecklist(String title, String description, UUID id, UUID creatorID, UUID adventureID, ArrayList<UUID> entries) throws Exception {
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

        Checklist checklist = new Checklist(title,description,entries,id,creatorID,adventureID);
        checklistRepository.save(checklist);
        return "Checklist successfully created";
    }

//    @Override
//    public String removeChecklist(UUID id) throws Exception {
//        if(id == null){
//            throw new Exception("No ID provided");
//        }
//        if(checklistRepository.findChecklistById(id) == null){
//            throw new Exception("Checklist does not exist");
//        }
//
//        Checklist checklist = checklistRepository.findChecklistById(id);
//        checklistRepository.delete(checklist);
//        return "Checklist successfully removed";
//    }

    @Override
    public String addChecklistEntry(String title, UUID id, UUID entryContainerID) throws Exception {
        if(title == null){
            throw new Exception("No title provided");
        }
        if(id == null){
            throw new Exception("No ID provided");
        }
        if(entryContainerID == null){
            throw new Exception("No Checklist ID provided");
        }

        Checklist checklist = checklistRepository.findChecklistById(entryContainerID);
        if(checklist == null){
            throw new Exception("Checklist does not exist");
        }
        if(checklist.getEntries().contains(id)){
            throw new Exception("Checklist Entry already exist");
        }

        ChecklistEntry entry = new ChecklistEntry(title,id,entryContainerID);
        checklistEntryRepository.save(entry);
        checklist.getEntries().add(id);
        checklistRepository.save(checklist);
        return "Checklist Entry successfully added";
    }

    @Override
    public String removeChecklistEntry(UUID id, UUID entryContainerID) throws Exception {
        if(id == null){
            throw new Exception("No ID provided");
        }
        if(entryContainerID == null){
            throw new Exception("No Checklist ID provided");
        }

        Checklist checklist = checklistRepository.findChecklistById(entryContainerID);
        if(checklist == null){
            throw new Exception("Checklist does not exist");
        }
        if(!checklist.getEntries().contains(id)){
            throw new Exception("Checklist Entry does not exist");
        }

        checklistEntryRepository.delete(checklistEntryRepository.findChecklistEntryById(id));
        checklist.getEntries().remove(id);
        checklistRepository.save(checklist);
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

        Checklist checklist = checklistRepository.findChecklistById(entryContainerID);

        if(!checklist.getEntries().contains(id)){
            throw new Exception("Entry does not exist.");
        }

        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);

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

        Checklist checklist = checklistRepository.findChecklistById(entryContainerID);

        if(!checklist.getEntries().contains(id)){
            throw new Exception("Entry does not exist.");
        }

        ChecklistEntry entry = checklistEntryRepository.findChecklistEntryById(id);
        entry.setCompleted(!entry.getCompleted());
        checklistEntryRepository.save(entry);
    }

    @Override
    public String softDelete(UUID id) throws Exception {
        if(id == null){
            throw new Exception("Checklist ID not provided.");
        }

        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id,false);

        if(checklist == null){
            throw new Exception("Checklist does not exist.");
        }

        checklist.setDeleted(true);
        checklistRepository.save(checklist);
        return "Checklist moved to bin";
    }

    @Override
    public String hardDelete(UUID id) throws Exception {
        if(id == null){
            throw new Exception("Checklist ID not provided.");
        }

        Checklist checklist = checklistRepository.findChecklistByIdAndDeleted(id,true);

        if(checklist == null){
            throw new Exception("Checklist is not in trash.");
        }

        ArrayList<UUID> entries = new ArrayList<>(checklist.getEntries());
        checklistRepository.delete(checklist);
        for (UUID b : entries) {
            checklistEntryRepository.delete((checklistEntryRepository.findChecklistEntryById(b)));
        }

        return "Checklist deleted";
    }

    @Override
    public List<Checklist> viewTrash(UUID id) throws Exception {
        return checklistRepository.findAllByDeletedEquals(true);
    }

    public String restoreBudget(UUID id) throws Exception {
        if(checklistRepository.findChecklistById(id) == null){
            throw new Exception("Checklist does not exist.");
        }

        Checklist checklist = checklistRepository.findChecklistById(id);
        checklist.setDeleted(false);
        checklistRepository.save(checklist);
        return "Checklist was restored";
    }

}
