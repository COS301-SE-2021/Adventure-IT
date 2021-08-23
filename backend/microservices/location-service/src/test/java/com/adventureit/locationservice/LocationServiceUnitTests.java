package com.adventureit.locationservice;

import com.adventureit.locationservice.Repository.LocationRepository;
import com.adventureit.locationservice.Service.LocationServiceImplementation;
import org.mockito.Mockito;

public class LocationServiceUnitTests {
    private final LocationRepository mockLocationRepository = Mockito.mock(LocationRepository.class);
    private final LocationServiceImplementation mockLocationService = Mockito.mock(LocationServiceImplementation.class);
}
