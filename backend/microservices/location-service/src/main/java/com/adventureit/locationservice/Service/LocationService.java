package com.adventureit.locationservice.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

public interface LocationService {
    public void createLocation(UUID id, String location) throws IOException;
}
