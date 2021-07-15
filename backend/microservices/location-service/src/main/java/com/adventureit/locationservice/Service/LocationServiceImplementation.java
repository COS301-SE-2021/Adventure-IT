package com.adventureit.locationservice.Service;

import com.adventureit.locationservice.Entity.Location;
import com.adventureit.locationservice.Repos.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service("LocationServiceImplementation")
public class LocationServiceImplementation implements LocationService {
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    private static HttpURLConnection connection;
    private final String APIKey = "AIzaSyD8xsVljufOFTmpnVZI2KzobIdAvKjWdTE";

    @Override
    public void createLocation(UUID id, String location) throws IOException {
        String string1 = location.replace(" ","%20");
        String string2 = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + string1 + "&inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours,geometry&key=" + APIKey;
        URL url = new URL(string2);

        BufferedReader reader;
        String line;
        StringBuffer response = new StringBuffer();

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
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
            System.out.println(response.toString());
            locationRepository.save(new Location(id, response.toString()));
        }

        connection.disconnect();
    }

}
