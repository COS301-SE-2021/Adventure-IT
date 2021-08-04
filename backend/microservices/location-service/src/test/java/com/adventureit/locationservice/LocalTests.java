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
        locationServiceImplementation.createLocation("Eeufees Rd, Groenkloof 358-Jr, Pretoria, 0027");
        locationServiceImplementation.createLocation("Government Ave, Pretoria, 0002");
        locationServiceImplementation.createLocation("2 Cussonia Street, Brummeria, Pretoria, 0184");
        locationServiceImplementation.createLocation("Christina De Wit Ave, Road, Pretoria, 0027");
        locationServiceImplementation.createLocation("1066, Burnette Street, Pretoria");
    }

    @Test
    public void shortestPath() throws JSONException, IOException {
        UUID id1 = UUID.fromString("f5b76b34-1c15-4623-a4c0-094b710db4ce");
        UUID id2 = UUID.fromString("c9589a3b-f794-4c43-984f-09dcec12aefe");
        UUID id3 = UUID.fromString("f2c39d25-2e50-4a9b-b589-d7d271bd3ff2");
        UUID id4 = UUID.fromString("ecc1cf1b-ae9e-4409-8e5b-51f7013881af");
        locationServiceImplementation.shortestPath(UUID.fromString("d650e30e-4bc3-42f2-8c2a-43eaef957e70"),new ArrayList<>(List.of(id1,id2,id3,id4)));
    }

//    @Test
//    public void delete(){
//        locationRepository.deleteAll();
//
//    }
}
