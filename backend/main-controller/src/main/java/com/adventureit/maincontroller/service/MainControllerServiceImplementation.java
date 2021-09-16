package com.adventureit.maincontroller.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class MainControllerServiceImplementation {

    public String pingControllers(String[] ports, RestTemplate temp){
        String IP = "http://localhost";
        String result;
        String value = "";
        for (String port : ports) {
            if (port.equals("9001")) {
                result = temp.getForObject(IP + ":" + port + "/adventure/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9002")) {
                result = temp.getForObject(IP + ":" + port + "/user/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9004")) {
                result = temp.getForObject(IP + ":" + port + "/notification/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9005")) {
                result = temp.getForObject(IP + ":" + port + "/media/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9006")) {
                result = temp.getForObject(IP + ":" + port + "/location/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9007")) {
                result = temp.getForObject(IP + ":" + port + "/budget/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9008")) {
                result = temp.getForObject(IP + ":" + port + "/checklist/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9009")) {
                result = temp.getForObject(IP + ":" + port + "/itinerary/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9010")) {
                result = temp.getForObject(IP + ":" + port + "/chat/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            } else if (port.equals("9012")) {
                result = temp.getForObject(IP + ":" + port + "/timeline/test", String.class);
                if (result.contains("Controller is functional")) {
                    value = "OK";
                } else {
                    return "Not OK";
                }
            }
        }
        return value;
    }

    public String pingCheck(String[] ports, RestTemplate temp) throws Exception {
        int runs = 0;
        while(pingControllers(ports,temp).equals("Not OK")){
            if(runs ==5){
                throw new Exception("Controller is out of service");
            }
            TimeUnit.SECONDS.sleep(1);
            runs++;
        }
        return "All is Good";
    }

}
