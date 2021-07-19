package com.adventureit.locationservice.Service;

import com.adventureit.locationservice.Entity.Location;
import com.adventureit.locationservice.Repos.LocationRepository;
import com.adventureit.locationservice.Responses.ShortestPathResponseDTO;
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
    public void createLocation(UUID id, String location) throws IOException, JSONException {
        String string1 = location.replace(" ","%20");
        String string2 = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + string1 + "&inputtype=textquery&fields=formatted_address,name,place_id&key=" + APIKey;

        JSONObject json = new JSONObject(makeConnection(string2));
        locationRepository.save(new Location(id,json.getJSONArray("candidates").getJSONObject(0).getString("name"),json.getJSONArray("candidates").getJSONObject(0).getString("formatted_address"),json.getJSONArray("candidates").getJSONObject(0).getString("place_id")));
    }

    @Override
    @Transactional
    public ShortestPathResponseDTO shortestPath(UUID id, List<UUID> locations) throws IOException, JSONException {
        String string1 = "https://maps.googleapis.com/maps/api/directions/json?units=metric&origin=place_id:"  + locationRepository.findLocationById(id).getPlace_id() + "&destination=place_id:" + locationRepository.findLocationById(id).getPlace_id() + "&waypoints=optimize:true|place_id:";

        for (int i = 0; i < (locations.size() -1) ; i++){
            string1 = string1 + locationRepository.findLocationById(locations.get(i)).getPlace_id() + "|place_id:";
        }

        string1 = string1 + locationRepository.findLocationById(locations.get(locations.size()-1)).getPlace_id() + "&key=" + APIKey;

        JSONObject json = new JSONObject(makeConnection(string1));

        System.out.println(getOrder(id,locations,json));
        System.out.println(getTotalDistance(json));
        System.out.println(getTotalDuration(json));

        return new ShortestPathResponseDTO(getOrder(id,locations,json),getTotalDistance(json),getTotalDuration(json));
    }

    @Override
    public List<String> getOrder(UUID id,List<UUID> locations ,JSONObject json) throws IOException, JSONException {
        JSONArray array = json.getJSONArray("routes").getJSONObject(0).getJSONArray("waypoint_order");

        int[] numbers = new int[array.length()];
        for (int i = 0; i < array.length(); ++i) {
            numbers[i] = array.optInt(i);
        }

        List<String> order = new ArrayList<>();
        order.add(locationRepository.findLocationById(id).getName());

        for (int k: numbers) {
            order.add(locationRepository.findLocationById(locations.get(k)).getName());
        }

        order.add(locationRepository.findLocationById(id).getName());

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
}
