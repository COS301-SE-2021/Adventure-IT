package com.adventureit.adventureservice.Service;


import com.adventureit.adventureservice.Entity.Adventure;
import com.adventureit.adventureservice.Exceptions.AdventureNotFoundException;
import com.adventureit.adventureservice.Repository.AdventureRepository;
import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Requests.GetAdventureByUUIDRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import com.adventureit.adventureservice.Exceptions.NullFieldException;
import com.adventureit.adventureservice.Responses.GetAdventureByUUIDResponse;
import com.adventureit.adventureservice.Responses.GetAllAdventuresResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service()
public class AdventureServiceImplementation implements AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;
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
    public CreateAdventureResponse createAdventure(CreateAdventureRequest req) throws NullFieldException {
        if(req.getOwnerId() == null ){
            throw new NullFieldException("Create Adventure Request: Owner Id NULL");
        }
        else if (req.getId() == null) {
            throw new NullFieldException("Create Adventure Request: Adventure Id NULL");
        }
        else if (req.getName() == null){
            throw new NullFieldException("Create Adventure Request: Adventure Name NULL");
        }
        Adventure persistedAdventure = this.adventureRepository.save(new Adventure(req.getName(), req.getId(), req.getOwnerId()));
        return new CreateAdventureResponse(true);
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
    public GetAdventureByUUIDResponse getAdventureByUUID (GetAdventureByUUIDRequest req) throws Exception{

        if(req.getId() == null){
            throw new NullFieldException("Get Adventure By UUID Request: Adventure ID NULL");
        }

        Adventure retrievedAdventure = this.adventureRepository.findById(req.getId());

        if(retrievedAdventure == null){
            throw new AdventureNotFoundException("Get Adventure by UUID: Adventure with UUID [" + req.getId() + "] not found");
        }
        return new GetAdventureByUUIDResponse(true, retrievedAdventure);
    }

//    /**
//     * @param req
//     * Attributes used from the req attribute:
//     * Adventure ID (advID)
//     * User ID (userID)
//     *
//     * Using the request object from the AddUserToAdventure service will:
//     * 1. Add user to array list in adventure
//     *
//     * @return AddUserToAdventureResponse object will indicate whether the user
//     * has been successfully added to the adventure or whether an error occured
//     */
//    @Override
//    public AddUserToAdventureResponse AddUserToAdventure(AddUserToAdventureRequest req)
//    {
//        Adventure adventure = new Adventure();
//        UUID advID = req.getAdventureID();
//        GetAdventureByUUIDRequest request0 = new GetAdventureByUUIDRequest(advID);
//        //GetAdventureByUUIDResponse res0 = adventure.getAdventureByUUID(request0);
//        UUID userID = req.getUserid();
//        GetUserByUUIDRequest request = new GetUserByUUIDRequest(userID);
//        GetUserByUUIDResponse res = user.GetUserByUUID(request);
//        User userToBeAdded = null;
//        if(res.isSuccess())
//        {   userToBeAdded = res.getUser();  }
//        return new AddUserToAdventureResponse(true, userToBeAdded.getFirstname()+" "+userToBeAdded.getLastname()+" has been added to adventure: Adventure1");
//
//    }

    @Override
    public GetAllAdventuresResponse getAllAdventures() throws Exception{
        List<Adventure> allAdventures = adventureRepository.findAll();
        if(allAdventures.size() == 0){
            AdventureNotFoundException notFound = new AdventureNotFoundException("Get All Adventure: No adventures found");
            throw notFound;
        }
        return new GetAllAdventuresResponse(allAdventures);
    };

    @Override
    public void mockPopulate(){
        this.adventureRepository.save(new Adventure("Mock Adventure 1", UUID.randomUUID(), UUID.randomUUID()));
        this.adventureRepository.save(new Adventure("Mock Adventure 2", UUID.randomUUID(), UUID.randomUUID()));
        this.adventureRepository.save(new Adventure("Mock Adventure 3", UUID.randomUUID(), UUID.randomUUID()));
    }

}
