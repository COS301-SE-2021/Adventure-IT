package com.adventureit.checklist.Service;

import com.adventureit.checklist.Entity.Checklist;
import com.adventureit.checklist.Responses.ChecklistResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ChecklistService {

    public String createChecklist(String title, String description, UUID id, UUID creatorID, UUID adventureID) throws Exception;
    public String addChecklistEntry(String title, UUID id, UUID entryContainerID) throws Exception;
    public String removeChecklistEntry(UUID id, UUID entryContainerID) throws Exception;
    public String editChecklistEntry(UUID id, UUID entryContainerID, String description) throws Exception;
    public void markChecklistEntry(UUID id, UUID entryContainerID) throws Exception;
    public String softDelete(UUID id) throws Exception;
    public String hardDelete(UUID id) throws Exception;
    public List<Checklist> viewTrash(UUID id) throws Exception;
    public String restoreChecklist(UUID id) throws Exception;
    public ChecklistResponseDTO viewChecklist(UUID id) throws Exception;
    public String mockPopulate();
}
