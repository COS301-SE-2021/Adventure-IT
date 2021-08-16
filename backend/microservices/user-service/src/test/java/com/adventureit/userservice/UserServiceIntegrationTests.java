package com.adventureit.userservice;

import com.adventureit.userservice.Controller.UserController;
import com.adventureit.userservice.Repository.UserRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTests {
    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

//    @Test
//    @Order(1)
//    @Description("Ensure that the User Controller loads")
//    public void userControllerLoads() throws Exception {
//        Assertions.assertNotNull(userController);
//    }
//
//    @Test
//    @Order(2)
//    @Description("Ensure that the controller is accepting traffic and responding")
//    public void httpTest_returnResponse(){
//        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/api/test", String.class),"User controller is working");
//    }

}
