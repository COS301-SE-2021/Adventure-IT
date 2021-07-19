package com.adventureit.locationservice;

import com.adventureit.locationservice.Repos.LocationRepository;
import com.adventureit.locationservice.Service.LocationServiceImplementation;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationServiceImplementation locationServiceImplementation;

    @Test
    public void createLocation() throws IOException, JSONException {
        locationServiceImplementation.createLocation(UUID.randomUUID(),"Eeufees Rd, Groenkloof 358-Jr, Pretoria, 0027");
        locationServiceImplementation.createLocation(UUID.randomUUID(),"Government Ave, Pretoria, 0002");
        locationServiceImplementation.createLocation(UUID.randomUUID(),"2 Cussonia Street, Brummeria, Pretoria, 0184");
        locationServiceImplementation.createLocation(UUID.randomUUID(),"Christina De Wit Ave, Road, Pretoria, 0027");
    }

    @Test
    public void shortestPath() throws JSONException, IOException {
        UUID id1 = UUID.fromString("47bd0046-6b1d-4d71-b778-a4e0fd4af0a2");
        UUID id2 = UUID.fromString("28866507-22ee-4ffb-b205-7ada06d39491");
        UUID id3 = UUID.fromString("a03d8710-1ed5-4fbd-8dfa-5dc7c1be73fd");
        UUID id4 = UUID.fromString("3bd0db1c-1ca0-4f9e-8045-942d310be0dd");
        locationServiceImplementation.shortestPath(UUID.fromString("c5d3c334-30a2-4993-b032-3dad6134acc0"),new ArrayList<>(List.of(id1,id2,id3,id4)));
    }

//    @Test
//    public void delete(){
//        locationRepository.deleteAll();
//
//    }
}
