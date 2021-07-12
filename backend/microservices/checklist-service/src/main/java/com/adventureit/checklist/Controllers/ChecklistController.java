package com.adventureit.checklist.Controllers;

import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Repository.ChecklistRepository;
import com.adventureit.checklist.Responses.ChecklistResponseDTO;
import com.adventureit.checklist.Service.ChecklistServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            list.add(new ChecklistResponseDTO(c.getTitle(),c.getDescription(),c.getId(),c.getCreatorID(),c.getAdventureID(),c.getEntries(),c.isDeleted()));
        }
        return list;
    }

    @GetMapping("/viewChecklist/{id}")
    public ChecklistResponseDTO viewCheckist(@PathVariable UUID id) throws Exception {
        return checklistServiceImplementation.viewChecklist(id);
    }

}
