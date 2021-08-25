package com.adventureit.checklist.service;

import com.adventureit.checklist.requests.ChecklistDTO;
import com.adventureit.checklist.responses.ChecklistEntryResponseDTO;
import com.adventureit.checklist.responses.ChecklistResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ChecklistService {

    public String addChecklistEntry(String title, UUID entryContainerID);
    public String createChecklist(String title, String description, UUID creatorID, UUID adventureID);
    public String removeChecklistEntry(UUID id);
    public String editChecklistEntry(UUID id, UUID entryContainerID, String description);
    public void markChecklistEntry(UUID id);
    public String softDelete(UUID id,UUID userID);
    public String hardDelete(UUID id,UUID userID);
    public List<ChecklistResponseDTO> viewTrash(UUID id);
    public String restoreChecklist(UUID id,UUID userID);
    public List<ChecklistEntryResponseDTO> viewChecklist(UUID id);
    public String mockPopulate();
    public ChecklistDTO getChecklistByChecklistId(UUID checklistId);
    public List<ChecklistResponseDTO> viewChecklistsByAdventure(UUID id);
}
