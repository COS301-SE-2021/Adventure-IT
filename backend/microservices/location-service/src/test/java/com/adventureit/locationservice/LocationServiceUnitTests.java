package com.adventureit.locationservice;


import com.adventureit.locationservice.Entity.Location;
import com.adventureit.locationservice.Repos.LocationRepository;
import com.adventureit.locationservice.Service.LocationServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Description;

public class LocationServiceUnitTests {
    private final LocationRepository mockLocationRepository = Mockito.mock(LocationRepository.class);
    private final LocationServiceImplementation mockLocationService = Mockito.mock(LocationServiceImplementation.class);
}
