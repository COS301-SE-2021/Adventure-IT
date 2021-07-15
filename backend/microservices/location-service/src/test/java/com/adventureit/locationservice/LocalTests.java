package com.adventureit.locationservice;

import com.adventureit.locationservice.Repos.LocationRepository;
import com.adventureit.locationservice.Service.LocationServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationServiceImplementation locationServiceImplementation;

    @Test
    public void createLocation() throws IOException {
        locationServiceImplementation.createLocation(UUID.randomUUID(),"1066 Burnette Street");
    }

}
