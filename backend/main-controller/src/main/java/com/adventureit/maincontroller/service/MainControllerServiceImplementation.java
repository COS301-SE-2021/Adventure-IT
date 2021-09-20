package com.adventureit.maincontroller.service;

import com.adventureit.maincontroller.exceptions.ControllerNotAvailable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MainControllerServiceImplementation {
    private static final String FUNCTIONAL_CONTROLLER = "Controller is functional";
    private static final String NOT_OK = "Not OK";

    Logger logger;

    public String pingControllers(String[] ports, RestTemplate temp){
        String internetPort = "http://localhost";
        String result;
        String value = "";
        for (String port : ports) {
            switch (port) {
                case "9001":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/adventure/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Adventure Service is not running");
                        return NOT_OK;
                    }

                    break;
                case "9002":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/user/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "User Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9004":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/notification/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Notification Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9005":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/media/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Media Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9006":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/location/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Location Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9007":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/budget/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Budget Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9008":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/checklist/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Checklist Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9009":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/itinerary/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Itinerary Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9010":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/chat/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Chat Service is not running");
                        return NOT_OK;
                    }
                    break;
                case "9012":
                    try {
                        result = temp.getForObject(internetPort + ":" + port + "/timeline/test", String.class);
                        assert result != null;
                        if (result.contains(FUNCTIONAL_CONTROLLER)) {
                            value = "OK";
                        } else {
                            return NOT_OK;
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Timeline Service is not running");
                        return NOT_OK;
                    }
                    break;

                default: return "OK";
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
