package com.adventureit.maincontroller.service;

import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class MainControllerServiceImplementation {
    private static final String FUNCTIONAL_CONTROLLER = "Controller is functional";
    private static final String NOT_OK = "Not OK";

    public String pingControllers(String[] ports, RestTemplate temp){
        String internetPort = "http://localhost";
        String result;
        String value = "";
        for (String port : ports) {
            if (port.equals("9001")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/adventure/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Adventure Service is not running");
                    return NOT_OK;
                }

            } else if (port.equals("9002")) {
                try{
                    result = temp.getForObject(internetPort + ":" + port + "/user/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("User Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9004")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/notification/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Notification Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9005")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/media/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Media Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9006")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/location/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Location Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9007")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/budget/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Budget Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9008")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/checklist/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Checklist Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9009")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/itinerary/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Itinerary Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9010")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/chat/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Chat Service is not running");
                    return NOT_OK;
                }
            } else if (port.equals("9012")) {
                try {
                    result = temp.getForObject(internetPort + ":" + port + "/timeline/test", String.class);
                    assert result != null;
                    if (result.contains(FUNCTIONAL_CONTROLLER)) {
                        value = "OK";
                    } else {
                        return NOT_OK;
                    }
                }catch(Exception e){
                    System.out.println("Timeline Service is not running");
                    return NOT_OK;
                }
            }
        }
        return value;
    }

    public String pingCheck(String[] ports, RestTemplate temp) throws ControllerNotAvailable, InterruptedException {
        int runs = 0;
        while(pingControllers(ports,temp).equals(NOT_OK)){
            if(runs ==4){
                throw new ControllerNotAvailable("Controller is out of service");
            }
            TimeUnit.SECONDS.sleep(5);
            runs++;
        }
        return "All is Good";
    }

}
