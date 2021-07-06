package com.adventureit.checklist.Service;

import com.adventureit.checklist.Entity.Checklist;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface ChecklistService {

    public String createChecklist(String title, String description, UUID id, UUID creatorID, UUID adventureID) throws Exception;
    public String removeChecklist(UUID id) throws Exception;
    public String addChecklistEntry(String title, UUID id, UUID entryContainerID) throws Exception;
    public String removeChecklistEntry(UUID id, UUID entryContainerID) throws Exception;
    public String editChecklistEntry(UUID id, UUID entryContainerID, String description) throws Exception;
    public void markChecklistEntry(UUID id, UUID entryContainerID) throws Exception;

}
