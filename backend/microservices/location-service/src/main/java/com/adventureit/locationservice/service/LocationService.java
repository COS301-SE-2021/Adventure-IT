package com.adventureit.locationservice.service;

import com.adventureit.locationservice.responses.CurrentLocationResponseDTO;
import com.adventureit.locationservice.responses.LocationResponseDTO;
import com.adventureit.locationservice.responses.ShortestPathResponseDTO;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface LocationService {
    public UUID createLocation(String location) throws IOException, JSONException;
    public ShortestPathResponseDTO shortestPath(UUID id, List<UUID> locations) throws IOException, JSONException;
    public String makeConnection(String string) throws IOException;
    public List<String> getOrder(UUID id, List<UUID> locations , JSONObject json) throws IOException, JSONException;
    public String getTotalDistance(JSONObject json) throws JSONException;
    public String getTotalDuration(JSONObject json) throws JSONException;
    public LocationResponseDTO getLocation(UUID id);
    public void addLike(UUID id);
    public void addVisit(UUID id);
    public void storeCurrentLocation(UUID userID, String latitude, String longitude);
    public CurrentLocationResponseDTO getCurrentLocation(UUID userID);
    public boolean compareGeometry(UUID id, UUID userID) throws IOException, JSONException;
    public List<String> getTypes(String placeID) throws IOException, JSONException;
}
