package com.adventureit.locationservice;

import com.adventureit.locationservice.repository.LocationRepository;
import com.adventureit.locationservice.service.LocationServiceImplementation;
import org.mockito.Mockito;

public class LocationServiceUnitTests {
    private final LocationRepository mockLocationRepository = Mockito.mock(LocationRepository.class);
    private final LocationServiceImplementation mockLocationService = Mockito.mock(LocationServiceImplementation.class);
}
