package com.adventureit.adventureservice.service;


import com.adventureit.adventureservice.entity.Adventure;
import com.adventureit.adventureservice.exceptions.AdventureNotFoundException;
import com.adventureit.adventureservice.exceptions.AttendeeAlreadyExistsException;
import com.adventureit.adventureservice.exceptions.UserNotInAdventureException;
import com.adventureit.adventureservice.repository.AdventureRepository;
import com.adventureit.shareddtos.adventure.AdventureDTO;
import com.adventureit.shareddtos.adventure.requests.EditAdventureRequest;
import com.adventureit.shareddtos.adventure.requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.exceptions.NullFieldException;
import com.adventureit.shareddtos.adventure.requests.CreateAdventureRequest;
import com.adventureit.shareddtos.adventure.responses.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The Adventure service implementation
 * All functions are implemented here here
 */
@Service
public class AdventureServiceImplementation implements AdventureService {

    private final AdventureRepository adventureRepository;

    private static final String ADVENTURE_ERROR = "Adventure does not exist";
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
        return new CreateAdventureResponse(true,  createAdventureDTO(persistedAdventure));
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
        return new GetAdventureByUUIDResponse(true, createAdventureDTO(retrievedAdventure));
    }

    /**
     * This function returns all existing adventures
     */
    @Override
    public List<GetAllAdventuresResponse> getAllAdventures(){
        List<Adventure> allAdventures = adventureRepository.findAll();
        if(allAdventures.isEmpty()){
            return new ArrayList<>();
        }

        allAdventures.sort(Comparator.comparing(Adventure::getStartDate));

        List<GetAllAdventuresResponse> list = new ArrayList<>();
        for (Adventure a:allAdventures) {
            list.add(new GetAllAdventuresResponse(a.getId(),a.getName(),a.getAdventureId(),a.getOwnerId(),a.getAttendees(),a.getStartDate(),a.getEndDate(),a.getDescription(),a.getLocation()));
        }

        return list;
    }

    /**
     * @param ownerID
     * Gets all adventures that a single user is the owner of
     *
     * @return List of GetAdventuresByUserUUIDResponse DTOs
     */
    @Override
    public List<GetAdventuresByUserUUIDResponse> getAdventureByOwnerUUID(UUID ownerID){
        List<Adventure> userAdventures = adventureRepository.findByOwnerId(ownerID);
        return sortAdventures(userAdventures);
    }

    /**
     * @param attendeeID
     * Gets all adventures that a single user is an attendee of
     *
     * @return List of GetAdventuresByUserUUIDResponse DTOs
     */
    @Override
    public List<GetAdventuresByUserUUIDResponse> getAdventureByAttendeeUUID(UUID attendeeID) {
        List<Adventure> userAdventures = adventureRepository.findByAttendees(attendeeID);
        return sortAdventures(userAdventures);
    }

    /**
     * @param id
     * Gets all adventures that a UUID is associated with
     *
     * @return List of GetAdventuresByUserUUIDResponse DTOs
     */
    @Override
    public List<GetAdventuresByUserUUIDResponse> getAllAdventuresByUUID(UUID id) {
        List<Adventure> userAdventures = adventureRepository.findAllByOwnerIdOrAttendeesContains(id,id);
        return sortAdventures(userAdventures);
    }

    /**
     * @param id
     * @param userID
     * Removes an adventure from a specific users list and removes them from the adventure
     *
     * @return RemoveAdventureResponse
     */
    @Transactional
    public RemoveAdventureResponse removeAdventure(UUID id, UUID userID) {
        Adventure retrievedAdventure = adventureRepository.findAdventureByAdventureId(id);
        if(retrievedAdventure == null){
            throw new AdventureNotFoundException("Remove Adventure: Adventure not found");
        }
        if(!retrievedAdventure.getAttendees().contains(userID)){
            throw new UserNotInAdventureException("User does not belong to Adventure");
        }

        retrievedAdventure.getAttendees().remove(userID);

        if(retrievedAdventure.getAttendees().isEmpty()){
            adventureRepository.deleteAdventureByAdventureId(id);
        }

        return new RemoveAdventureResponse(true, "Adventure successfully removed");
    }

    /**
     * @param id
     * Gets a list of attendees of an adventure
     *
     * @return List of UUIDs
     */
    @Override
    public List<UUID> getAttendees(UUID id) {
        Adventure adventure  = adventureRepository.findAdventureByAdventureId(id);
        if(adventure == null){
            throw new AdventureNotFoundException(ADVENTURE_ERROR);
        }

        return adventure.getAttendees();
    }

    /**
     * @param adventureID
     * @param locationID
     * Sets the location UUID of an adventure
     *
     */
    @Override
    public void setAdventureLocation(UUID adventureID, UUID locationID) {
        Adventure adventure = adventureRepository.findAdventureByAdventureId(adventureID);
        adventure.setLocation(locationID);
        adventureRepository.save(adventure);
    }

    /**
     * @param adventureID
     * @param userID
     *Adds an attendee to an adventure
     *
     */
    @Override
    public void addAttendees(UUID adventureID, UUID userID) {
        Adventure adventure = adventureRepository.findAdventureByAdventureId(adventureID);
        if(adventure == null){
            throw new AdventureNotFoundException(ADVENTURE_ERROR);
        }

        if(adventure.getAttendees().contains(userID)){
            throw new AttendeeAlreadyExistsException("Add Attendees: Attendee already added");
        }

        adventure.getAttendees().add(userID);
        adventureRepository.save(adventure);
    }

    /**
     * @param adventureID
     * @param userID
     * Removes an attendee from an adventure
     *
     * @return String
     */
    @Override
    public String removeAttendees(UUID adventureID, UUID userID) {
        Adventure adventure = adventureRepository.findAdventureByAdventureId(adventureID);
        if(adventure == null){
            throw new AdventureNotFoundException(ADVENTURE_ERROR);
        }

        adventure.getAttendees().remove(userID);
        adventureRepository.save(adventure);
        return "User has been removed";
    }

    /**
     * @param userAdventures
     * Helper function for sorting adventures, throws an exception if there are no adventures
     *
     * @return List of GetAdventuresByUserUUIDResponse DTOs
     */
    private List<GetAdventuresByUserUUIDResponse> sortAdventures(List<Adventure> userAdventures) {
        if(userAdventures.isEmpty()){
            return new ArrayList<>();
        }

        userAdventures.sort(Comparator.comparing(Adventure::getStartDate));

        List<GetAdventuresByUserUUIDResponse> list = new ArrayList<>();
        for (Adventure a:userAdventures) {
            list.add(new GetAdventuresByUserUUIDResponse(a.getId(),a.getName(),a.getAdventureId(),a.getOwnerId(),a.getAttendees(),a.getStartDate(),a.getEndDate(),a.getDescription(),a.getLocation()));
        }

        return list;
    }

    /**
     * @param req
     * Allows a user to edit the details of an adventure
     *
     * @return List of GetAdventuresByUserUUIDResponse DTOs
     */
    @Override
    public String editAdventure(EditAdventureRequest req) {
        Adventure adventure = adventureRepository.findAdventureByAdventureId(req.getAdventureId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

        if(req.getDescription().equals("")){

        }else{
            adventure.setDescription(req.getDescription());
        }

        if(req.getName().equals("")){

        }else{
            adventure.setName(req.getName());
        }

        if(req.getStartDate() == null ||req.getStartDate().equals("")){

        }else{
            LocalDate sd = LocalDate.parse(req.getStartDate(),formatter);
            adventure.setStartDate(sd);
        }

        if(req.getEndDate().equals("")||req.getEndDate()==null){

        }else{
            LocalDate ed = LocalDate.parse(req.getEndDate(),formatter);
            adventure.setEndDate(ed);
        }

        adventureRepository.save(adventure);
        return "Adventure successfully edited";
    }

    /**
     * @param adv
     * Helper function for converting an Adventure object into a DTO
     *
     * @return AdventureDTO
     */
    public AdventureDTO createAdventureDTO(Adventure adv){
        return new AdventureDTO(adv.getName(), adv.getDescription(), adv.getAdventureId(), adv.getOwnerId(), adv.getStartDate(), adv.getEndDate(), adv.getLocation());
    }
}
