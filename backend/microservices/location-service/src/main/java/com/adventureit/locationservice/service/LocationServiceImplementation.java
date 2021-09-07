package com.adventureit.locationservice.service;

import com.adventureit.locationservice.entity.CurrentLocation;
import com.adventureit.locationservice.entity.Location;
import com.adventureit.locationservice.exceptions.NotFoundException;
import com.adventureit.locationservice.repository.CurrentLocationRepository;
import com.adventureit.locationservice.repository.LocationRepository;
import com.adventureit.locationservice.responses.CurrentLocationResponseDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("LocationServiceImplementation")
public class LocationServiceImplementation implements LocationService {
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CurrentLocationRepository currentLocationRepository;
    @Autowired
    private static HttpURLConnection connection;
    private final String APIKey = System.getenv("Google Maps API Key");

    @Override
    public UUID createLocation(String location) throws IOException, JSONException {
        String string1 = location.replace(" ","%20");
        String string2 = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + string1 + "&inputtype=textquery&fields=formatted_address,name,place_id,photos&key=AIzaSyD8xsVljufOFTmpnVZI2KzobIdAvKjWdTE";

        JSONObject json = new JSONObject(makeConnection(string2));
        Location location1 = new Location();

        String address = json.getJSONArray("candidates").getJSONObject(0).getString("formatted_address");
        String [] array = address.split(",");
        String country = array[array.length - 1].trim();

        if(json.getJSONArray("candidates").getJSONObject(0).has("photos")) {
            location1 = locationRepository.save(new Location(json.getJSONArray("candidates").getJSONObject(0).getJSONArray("photos").getJSONObject(0).getString("photo_reference"),address,json.getJSONArray("candidates").getJSONObject(0).getString("place_id"),country));
        }
        else {
            location1 = locationRepository.save(new Location("",address,json.getJSONArray("candidates").getJSONObject(0).getString("place_id"),country));
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

    @Override
    public void storeCurrentLocation(UUID userID, String latitude, String longitude) {
        CurrentLocation currentLocation = currentLocationRepository.findCurrentLocationByUserID(userID);
        if(currentLocation == null){
            currentLocationRepository.save(new CurrentLocation(userID,latitude,longitude));
        }
        else {
            currentLocation.setLatitude(latitude);
            currentLocation.setLongitude(longitude);
            currentLocation.setTimestamp(LocalDateTime.now());
            currentLocationRepository.save(currentLocation);
        }
    }

    @Override
    public CurrentLocationResponseDTO getCurrentLocation(UUID userID) {
        CurrentLocation currentLocation = currentLocationRepository.findCurrentLocationByUserID(userID);
        if(currentLocation == null){
            return null;
        }
        else{
            return new CurrentLocationResponseDTO(currentLocation.getId(),currentLocation.getUserID(),currentLocation.getLatitude(),currentLocation.getLongitude(),currentLocation.getTimestamp());
        }
    }

    @Override
    public boolean compareGeometry(UUID id,UUID userID) throws IOException, JSONException {
        Location location = locationRepository.findLocationById(id);
        if(location == null){
            throw new NotFoundException("Compare Geometry: Location does not exist");
        }

        CurrentLocationResponseDTO currentLocation = getCurrentLocation(userID);
        if(currentLocation == null){
            throw new NotFoundException("Compare Geometry: Current Location is not stored");
        }

        String lat;
        String lng;

        String string1 = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + location.getPlaceID() + "&fields=geometry&key=AIzaSyD8xsVljufOFTmpnVZI2KzobIdAvKjWdTE";
        JSONObject json = new JSONObject(makeConnection(string1));

       lat = json.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat");
       lng = json.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lng");

       double lat1 = Double.parseDouble(lat);
       double lng1 = Double.parseDouble(lng);

           if((lat1 >= Double.parseDouble(currentLocation.getLatitude()) || lat1 <= Double.parseDouble(currentLocation.getLatitude()) + 0.01) || (lat1 <= Double.parseDouble(currentLocation.getLatitude()) || lat1 >= Double.parseDouble(currentLocation.getLatitude()) - 0.01)){
               return (lng1 >= Double.parseDouble(currentLocation.getLongitude()) || lng1 <= Double.parseDouble(currentLocation.getLongitude()) + 0.01) || (lng1 <= Double.parseDouble(currentLocation.getLongitude()) || lng1 >= Double.parseDouble(currentLocation.getLongitude()) - 0.01);
           }
           else{
               return false;
           }
    }
}
