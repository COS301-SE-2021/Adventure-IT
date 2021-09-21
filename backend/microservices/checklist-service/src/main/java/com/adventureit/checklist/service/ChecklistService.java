package com.adventureit.checklist.service;

import com.adventureit.shareddtos.checklist.requests.ChecklistDTO;
import com.adventureit.shareddtos.checklist.responses.ChecklistEntryResponseDTO;
import com.adventureit.shareddtos.checklist.responses.ChecklistResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ChecklistService {

     String addChecklistEntry(String title, UUID entryContainerID);
     String createChecklist(String title, String description, UUID creatorID, UUID adventureID);
     String removeChecklistEntry(UUID id);
     String editChecklistEntry(UUID id, UUID entryContainerID, String description);
     void markChecklistEntry(UUID id);
     String softDelete(UUID id,UUID userID);
     String hardDelete(UUID id,UUID userID);
     List<ChecklistResponseDTO> viewTrash(UUID id);
     String restoreChecklist(UUID id,UUID userID);
     List<ChecklistEntryResponseDTO> viewChecklist(UUID id);
     String mockPopulate();
     ChecklistDTO getChecklistByChecklistId(UUID checklistId);
     List<ChecklistResponseDTO> viewChecklistsByAdventure(UUID id);
    ChecklistDTO getChecklistByChecklistEntryId(UUID checklistEntryId);
}
