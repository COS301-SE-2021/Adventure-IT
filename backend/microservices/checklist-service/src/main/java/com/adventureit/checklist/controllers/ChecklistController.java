package com.adventureit.checklist.controllers;

import com.adventureit.checklist.repository.ChecklistRepository;
import com.adventureit.checklist.requests.*;
import com.adventureit.checklist.responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.responses.ChecklistResponseDTO;
import com.adventureit.checklist.service.ChecklistServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String test(){
        return "Checklist Controller is functional";
    }

    @GetMapping("/populate")
    public String populate(){
        return checklistServiceImplementation.mockPopulate();
    }

    @GetMapping("/viewChecklistsByAdventure/{id}")
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(@PathVariable UUID id) {
        return checklistServiceImplementation.viewChecklistsByAdventure(id);
    }

    @GetMapping("/viewChecklist/{id}")
    public List<ChecklistEntryResponseDTO> viewChecklist(@PathVariable UUID id){
        return checklistServiceImplementation.viewChecklist(id);
    }

    @GetMapping("/softDelete/{id}/{userID}")
    public String softDelete(@PathVariable UUID id, @PathVariable UUID userID){
        return checklistServiceImplementation.softDelete(id,userID);
    }

    @GetMapping("/viewTrash/{id}")
    public List<ChecklistResponseDTO> viewTrash(@PathVariable UUID id){
        return checklistServiceImplementation.viewTrash(id);
    }

    @GetMapping("/restoreChecklist/{id}/{userID}")
    public String restoreChecklist(@PathVariable UUID id,@PathVariable UUID userID){
        return checklistServiceImplementation.restoreChecklist(id,userID);
    }

    @GetMapping("/hardDelete/{id}/{userID}")
    public String hardDelete(@PathVariable UUID id,@PathVariable UUID userID){
        return checklistServiceImplementation.hardDelete(id,userID);
    }

    @PostMapping("/create")
    public String createChecklist(@RequestBody CreateChecklistRequest req){
        return checklistServiceImplementation.createChecklist(req.getTitle(),req.getDescription(),req.getCreatorID(),req.getAdventureID());
    }

    @PostMapping("/addEntry")
    public String addEntry(@RequestBody AddChecklistEntryRequest req){
        return checklistServiceImplementation.addChecklistEntry(req.getTitle(),req.getEntryContainerID());
    }

    @GetMapping("/removeEntry/{id}")
    public String removeEntry(@PathVariable UUID id){
        return checklistServiceImplementation.removeChecklistEntry(id);
    }

    @PostMapping("/editEntry")
    public String editEntry(@RequestBody EditChecklistEntryRequest req){
        return checklistServiceImplementation.editChecklistEntry(req.getId(),req.getEntryContainerID(),req.getTitle());
    }

    @GetMapping("/markEntry/{id}")
    public void markEntry(@PathVariable UUID id){
        checklistServiceImplementation.markChecklistEntry(id);
    }

    @GetMapping("/getChecklist/{id}")
    public ChecklistDTO getChecklistByChecklistId(@PathVariable UUID id){
        return checklistServiceImplementation.getChecklistByChecklistId(id);
    }

    @GetMapping("/getChecklistByEntry/{id}")
    public ChecklistDTO getChecklistByChecklistEntryId(@PathVariable UUID id){
        return checklistServiceImplementation.getChecklistByChecklistEntryId(id);
    }
}
