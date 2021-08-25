package com.adventureit.adventureservice.service;


import com.adventureit.adventureservice.entity.Adventure;
import com.adventureit.adventureservice.exceptions.AdventureNotFoundException;
import com.adventureit.adventureservice.repository.AdventureRepository;
import com.adventureit.adventureservice.requests.CreateAdventureRequest;
import com.adventureit.adventureservice.requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.responses.*;
import com.adventureit.adventureservice.exceptions.NullFieldException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AdventureServiceImplementation implements AdventureService {

    private final AdventureRepository adventureRepository;

    public AdventureServiceImplementation(AdventureRepository adventureRepository){
        this.adventureRepository = adventureRepository;
    }

    /**
     *
     * @param req
     * Attributes which will be attained from the req param will include:
     * Name of the Adventure (name)
     * ID of the Adventure (id)
     * Owner of the Adventure (owner)
     * Groups of the Adventure (group)
     * Containers of the Adventure (Containers)
     *
     * Using the request object the CreateAdventure Service will:
     * 1. Check if id is not null
     * 2. Check if name is not empty or null
     * 3. Check if Containers is not null
     * 4. Check if group is not null
     * 5. Check if owner is not null
     * 6. Create and add new Adventure to database
     *
     * @return CreateAdventureResponse Object which will indicate whether
     * registration was successful or if an error occurred
     */

    @Override
    public CreateAdventureResponse createAdventure(CreateAdventureRequest req) {
        if(req.getOwnerId() == null ){
            throw new NullFieldException("Create Adventure Request: Owner Id NULL");
        }
        else if (req.getName() == null){
            throw new NullFieldException("Create Adventure Request: Adventure Name NULL");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        LocalDate sd = LocalDate.parse(req.getStartDate(),formatter);
        LocalDate ed = LocalDate.parse(req.getEndDate(),formatter);
        Adventure persistedAdventure = new Adventure(req.getName(),req.getDescription(), UUID.randomUUID() , req.getOwnerId(), sd, ed, null);
        adventureRepository.save(persistedAdventure);
        return new CreateAdventureResponse(true, persistedAdventure);
    }

    /**
     * Get Adventure By UUID is currently a mock service which returns a set adventure until a persistence layer is created
     * The service will acquire the id from the request object then return an adventure with a set name, owner, checklist,
     * container and group.
     *
     * NB: This currently only for testing purposes.
     *
     * When the persistence layer is created the Service will search the database for a specific Adventure.
     * @param req a GetAdventureByUUID request will be sent in with the id of the adventure that should be retrieved
     * @return returns a GetAdventureByUUID response which currently is a set adventure for testing purposes
     */
    @Override
    public GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req) {

        if(req.getId() == null){
            throw new NullFieldException("Get Adventure By UUID Request: Adventure ID NULL");
        }

        Adventure retrievedAdventure = this.adventureRepository.findByAdventureId(req.getId());

        if(retrievedAdventure == null){
            throw new AdventureNotFoundException("Get Adventure by UUID: Adventure with UUID [" + req.getId() + "] not found");
        }
        return new GetAdventureByUUIDResponse(true, retrievedAdventure);
    }

    @Override
    public List<GetAllAdventuresResponse> getAllAdventures(){
        List<Adventure> allAdventures = adventureRepository.findAll();
        if(allAdventures.size() == 0){
            throw new AdventureNotFoundException("Get All Adventure: No adventures found");
        }

        allAdventures.sort(Comparator.comparing(Adventure::getStartDate));

        List<GetAllAdventuresResponse> list = new ArrayList<>();
        for (Adventure a:allAdventures) {
            list.add(new GetAllAdventuresResponse(a.getId(),a.getName(),a.getAdventureId(),a.getOwnerId(),a.getAttendees(),a.getStartDate(),a.getEndDate(),a.getDescription(),a.getLocation()));
        }

        return list;
    }

    @Override
    public List<GetAdventuresByUserUUIDResponse> getAdventureByOwnerUUID(UUID ownerID){
        List<Adventure> userAdventures = adventureRepository.findByOwnerId(ownerID);
        return sortAdventures(userAdventures);
    }



    @Override
    public List<GetAdventuresByUserUUIDResponse> getAdventureByAttendeeUUID(UUID attendeeID) {
        List<Adventure> userAdventures = adventureRepository.findByAttendees(attendeeID);
        return sortAdventures(userAdventures);
    }

    @Override
    public List<GetAdventuresByUserUUIDResponse> getAllAdventuresByUUID(UUID id) {
        List<Adventure> userAdventures = adventureRepository.findAllByOwnerIdOrAttendeesContains(id,id);

        return sortAdventures(userAdventures);
    }

    @Transactional
    public RemoveAdventureResponse removeAdventure(UUID id, UUID userID) throws Exception {
        Adventure retrievedAdventure = adventureRepository.findAdventureByAdventureId(id);
        if(retrievedAdventure == null){
            throw new AdventureNotFoundException("Remove Adventure: Adventure not found");
        }
        if(!retrievedAdventure.getAttendees().contains(userID)){
            // TODO: Define dedicated exception
            throw new Exception("User does not belong to Adventure");
        }

        retrievedAdventure.getAttendees().remove(userID);

        if(retrievedAdventure.getAttendees().isEmpty()){
            adventureRepository.deleteAdventureByAdventureId(id);
        }

        return new RemoveAdventureResponse(true, "Adventure successfully removed");
    }

    @Override
    public List<UUID> getAttendees(UUID id) throws Exception {
        Adventure adventure  = adventureRepository.findAdventureByAdventureId(id);
        if(adventure == null){
            // TODO: Define dedicated exception
            throw new Exception("Adventure does not exist");
        }

        return adventure.getAttendees();
    }

    @Override
    public void setAdventureLocation(UUID adventureID, UUID locationID) {
        Adventure adventure = adventureRepository.findAdventureByAdventureId(adventureID);
        adventure.setLocation(locationID);
        adventureRepository.save(adventure);
    }

    @Override
    public void addAttendees(UUID adventureID, UUID userID) throws Exception {
        Adventure adventure = adventureRepository.findAdventureByAdventureId(adventureID);
        if(adventure == null){
            // TODO: Define dedicated exception
            throw new Exception("Adventure does not exist");
        }

        adventure.getAttendees().add(userID);
        adventureRepository.save(adventure);
    }

    // Helper function for sorting adventures, throws an exception if there are no adventures
    private List<GetAdventuresByUserUUIDResponse> sortAdventures(List<Adventure> userAdventures) {
        if(userAdventures.size() == 0){
            throw new AdventureNotFoundException("Get Adventures by User UUID: No adventures found");
        }

        userAdventures.sort(Comparator.comparing(Adventure::getStartDate));

        List<GetAdventuresByUserUUIDResponse> list = new ArrayList<>();
        for (Adventure a:userAdventures) {
            list.add(new GetAdventuresByUserUUIDResponse(a.getId(),a.getName(),a.getAdventureId(),a.getOwnerId(),a.getAttendees(),a.getStartDate(),a.getEndDate(),a.getDescription(),a.getLocation()));
        }

        return list;
    }

}