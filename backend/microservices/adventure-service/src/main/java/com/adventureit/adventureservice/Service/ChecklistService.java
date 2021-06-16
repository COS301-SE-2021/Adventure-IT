package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Entity.Checklist;
import com.adventureit.adventureservice.Repository.ChecklistRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChecklistService {
    // TODO: Add autowiring for adventure service
    private ChecklistRepository checklistRepository;

    /**
     * Creating a checklist
     *
     * @param adventureID
     * The UUID of the adventure to which a checklist must be added
     *
     * @param userID
     * The UUID of the user attempting to create a checklist
     *
     * @param title
     * The title of the to-be-created checklist
     *
     * @param description
     * The description of the to-be-created checklist
     *
     * @return
     * The newly created (and persisted) checklist
     */
    public Checklist createChecklist(UUID adventureID, UUID userID, String title, String description) throws Exception {
        // TODO: get adventure, add checklist to adventure (throw exception if adventure doesn't exist)
        return checklistRepository.save(new Checklist(title, description, userID, adventureID));
    }

    /**
     * Removing a checklist
     *
     * @param id
     * The ID of the checklist to be removed
     *
     * @param userID
     * The UUID of the user attempting to remove the checklist
     *
     * @param adventureID
     * The UUID of the adventure from which the checklist must be removed
     *
     * @return
     * True if the removal was successful
     */
    public boolean removeChecklist(Long id, UUID userID, UUID adventureID) throws Exception{
        // TODO: get adventure, remove from adventure, check if user is owner, throw appropriate exception
        checklistRepository.delete(checklistRepository.findByid(id));
        return true;
    }
}
