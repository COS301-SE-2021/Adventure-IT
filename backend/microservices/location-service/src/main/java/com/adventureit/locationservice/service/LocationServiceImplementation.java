package com.adventureit.locationservice.service;

import com.adventureit.locationservice.entity.Location;
import com.adventureit.locationservice.exceptions.NotFoundException;
import com.adventureit.locationservice.repository.LocationRepository;
import com.adventureit.locationservice.responses.LocationResponseDTO;
import com.adventureit.locationservice.responses.ShortestPathResponseDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("LocationServiceImplementation")
public class LocationServiceImplementation implements LocationService {
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    private static HttpURLConnection connection;
    private final String APIKey = System.getenv("Google Maps API Key");

    @Override
    public UUID createLocation(String location) throws IOException, JSONException {
        String string1 = location.replace(" ","%20");
        String string2 = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + string1 + "&inputtype=textquery&fields=formatted_address,name,place_id,photos&key=AIzaSyD8xsVljufOFTmpnVZI2KzobIdAvKjWdTE";

        JSONObject json = new JSONObject(makeConnection(string2));
        Location location1 = new Location();

        if(json.getJSONArray("candidates").getJSONObject(0).has("photos")) {
            location1 = locationRepository.save(new Location(json.getJSONArray("candidates").getJSONObject(0).getJSONArray("photos").getJSONObject(0).getString("photo_reference"),json.getJSONArray("candidates").getJSONObject(0).getString("formatted_address"),json.getJSONArray("candidates").getJSONObject(0).getString("place_id")));
        }
        else {
            location1 = locationRepository.save(new Location("",json.getJSONArray("candidates").getJSONObject(0).getString("formatted_address"),json.getJSONArray("candidates").getJSONObject(0).getString("place_id")));
        }

        return location1.getId();
    }

    @Override
    @Transactional
    public ShortestPathResponseDTO shortestPath(UUID id, List<UUID> locations) throws IOException, JSONException {
        String string1 = "https://maps.googleapis.com/maps/api/directions/json?units=metric&origin=place_id:"  + locationRepository.findLocationById(id).getPlaceID() + "&destination=place_id:" + locationRepository.findLocationById(id).getPlaceID() + "&waypoints=optimize:true|place_id:";

        for (int i = 0; i < (locations.size() -1) ; i++){
            string1 = string1 + locationRepository.findLocationById(locations.get(i)).getPlaceID() + "|place_id:";
        }

        string1 = string1 + locationRepository.findLocationById(locations.get(locations.size()-1)).getPlaceID() + "&key=" + APIKey;

        JSONObject json = new JSONObject(makeConnection(string1));

        return new ShortestPathResponseDTO(getOrder(id,locations,json),getTotalDistance(json),getTotalDuration(json));
    }

    @Override
    public List<String> getOrder(UUID id,List<UUID> locations ,JSONObject json) throws JSONException {
        JSONArray array = json.getJSONArray("routes").getJSONObject(0).getJSONArray("waypoint_order");

        int[] numbers = new int[array.length()];
        for (int i = 0; i < array.length(); ++i) {
            numbers[i] = array.optInt(i);
        }

        List<String> order = new ArrayList<>();
        order.add(locationRepository.findLocationById(id).getFormattedAddress());

        for (int k: numbers) {
            order.add(locationRepository.findLocationById(locations.get(k)).getFormattedAddress());
        }

        order.add(locationRepository.findLocationById(id).getFormattedAddress());

        return order;
    }

    @Override
    public String getTotalDistance(JSONObject json) throws JSONException {
        int sum = 0;
        JSONArray array = json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");

        for (int i = 0; i < array.length(); ++i) {
            sum += Integer. parseInt(array.getJSONObject(i).getJSONObject("distance").getString("value"));
        }

        return String.format("%.1f", (sum/1000.0)) + " km";
    }

    @Override
    public String getTotalDuration(JSONObject json) throws JSONException {
        int sum = 0;
        JSONArray array = json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");

        for (int i = 0; i < array.length(); ++i) {
            sum += Integer. parseInt(array.getJSONObject(i).getJSONObject("duration").getString("value"));
        }

        return Integer.toString(sum/3600) + " hour(s) " + Integer.toString((int)Math.ceil((sum%3600)/60.0)) + " minute(s)";
    }

    @Override
    public String makeConnection(String string) throws IOException {
        URL url = new URL(string);

        BufferedReader reader;
        String line;
        StringBuffer response = new StringBuffer();

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        int status = connection.getResponseCode();

        if(status > 299){
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();
        }
        else{
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();
        }

        connection.disconnect();

        return response.toString();
    }

    @Override
    public LocationResponseDTO getLocation(UUID id){
        Location location = locationRepository.findLocationById(id);
        if(location == null){
            throw new NotFoundException("Get Location: Location does not exist");
        }

        return new LocationResponseDTO(location.getId(),location.getPhotoReference(),location.getFormattedAddress(),location.getPlaceID());
    }
}