package com.adventureit.checklist.Controllers;

import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Repository.ChecklistRepository;
import com.adventureit.checklist.Requests.*;
import com.adventureit.checklist.Responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.Responses.ChecklistResponseDTO;
import com.adventureit.checklist.Service.ChecklistServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {
    @Autowired
    ChecklistServiceImplementation checklistServiceImplementation;
    @Autowired
    ChecklistRepository checklistRepository;

    @GetMapping("/test")
    String test(){
        return "Checklist Controller is functional";
    }

    @GetMapping("/populate")
    public String populate(){
        return checklistServiceImplementation.mockPopulate();
    }

    @GetMapping("/viewChecklistsByAdventure/{id}")
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(@PathVariable UUID id) throws Exception {
        List<Checklist> checklists = checklistRepository.findAllByAdventureID(id);
        List<ChecklistResponseDTO> list = new ArrayList<>();
        for (Checklist c:checklists) {
            if(!c.isDeleted()){
                list.add(new ChecklistResponseDTO(c.getTitle(),c.getDescription(),c.getId(),c.getCreatorID(),c.getAdventureID(),c.isDeleted()));
            }
        }
        return list;
    }

    @GetMapping("/viewChecklist/{id}")
    public List<ChecklistEntryResponseDTO> viewCheckist(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.viewChecklist(id);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID) throws Exception {
        return checklistServiceImplementation.softDelete(id,userID);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ChecklistResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.viewTrash(id);
    }

    @GetMapping("/restoreChecklist/{id}/{userID}")
    public String restoreChecklist(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        return checklistServiceImplementation.restoreChecklist(id,userID);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        return checklistServiceImplementation.hardDelete(id,userID);
    }

    @PostMapping("/create")
    public String createChecklist(@RequestBody CreateChecklistRequest req) throws Exception {
        return checklistServiceImplementation.createChecklist(req.getTitle(),req.getDescription(),req.getCreatorID(),req.getAdventureID());
    }

    @PostMapping("/addEntry")
    public String addEntry(@RequestBody AddChecklistEntryRequest req) throws Exception {
        return checklistServiceImplementation.addChecklistEntry(req.getTitle(),req.getEntryContainerID());
    }

    @GetMapping("/removeEntry/{id}")
    public String removeEntry(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.removeChecklistEntry(id);
    }

    @PostMapping("/editEntry")
    public String editEntry(@RequestBody EditChecklistEntryRequest req) throws Exception {
        return checklistServiceImplementation.editChecklistEntry(req.getId(),req.getEntryContainerID(),req.getTitle());
    }


    @GetMapping("/markEntry/{id}")
    public void markEntry(@PathVariable UUID id) throws Exception {
        checklistServiceImplementation.markChecklistEntry(id);

    }
}
