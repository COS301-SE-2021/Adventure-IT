package com.adventureit.checklist.Controllers;

import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Repository.ChecklistRepository;
import com.adventureit.checklist.Requests.CreateChecklistRequest;
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
    public ChecklistResponseDTO viewCheckist(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.viewChecklist(id);
    }

    @GetMapping("/softDelete/{id}")
    public String softDelete(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.softDelete(id);
    }
    //
    @GetMapping("/viewTrash/{id}")
    public List<ChecklistResponseDTO> viewTrash(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.viewTrash(id);
    }

    @GetMapping("/restoreChecklist/{id}")
    public String restoreChecklist(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.restoreChecklist(id);
    }

    @GetMapping("/hardDelete/{id}")
    public String hardDelete(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.hardDelete(id);
    }
    @PostMapping("/create")
    public String createItinerary(@RequestBody CreateChecklistRequest req) throws Exception {
        return checklistServiceImplementation.createChecklist(req.getTitle(),req.getDescription(),req.getId(),req.getCreatorID(),req.getAdventureID());
    }

}
