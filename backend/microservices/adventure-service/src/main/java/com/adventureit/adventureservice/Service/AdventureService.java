package com.adventureit.adventureservice.Service;

import com.adventureit.adventureservice.Requests.CreateAdventureRequest;
import com.adventureit.adventureservice.Responses.CreateAdventureResponse;
import org.springframework.stereotype.Service;

@Service("AdventureServiceImplementation")
public interface AdventureService {

    public CreateAdventureResponse createAdventure(CreateAdventureRequest req);
}
