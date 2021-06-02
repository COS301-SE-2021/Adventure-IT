package com.adventureit.adventureservice.Responses;

public class RemoveChecklistResponse {
    private Boolean success;

    /**
     * Parameterized Constructor
     * @param success
     * Whether or not the checklist was removed
     */
    public RemoveChecklistResponse(Boolean success){
        this.success = success;
    }

    public Boolean isSuccess() {
        return success;
    }
}
