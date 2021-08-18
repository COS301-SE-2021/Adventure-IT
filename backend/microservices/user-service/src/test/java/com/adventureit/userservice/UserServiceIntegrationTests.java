package com.adventureit.userservice;

import com.adventureit.userservice.Controller.UserController;
import com.adventureit.userservice.Entities.Users;
import com.adventureit.userservice.Repository.UserRepository;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.FriendDTO;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTests {
    @Autowired
    private UserController userController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    final UUID validUserUUID1 = UUID.fromString("c0b97070-bbc3-400a-8c5e-444c5cd163b7");
    final String validUserFirstName1 = "firstname_test1";
    final String validUserLastName1 = "lastname_test1";
    final String validUserName1 = "username_test1";
    final String validUserEmail1 = "test@emailaddress.com1";

    final UUID validUserUUID2 = UUID.fromString("ab71dff4-f8c9-4237-b7a0-2c0111f0dbd3");
    final String validUserFirstName2 = "firstname_test2";
    final String validUserLastName2 = "lastname_test2";
    final String validUserName2 = "username_test2";
    final String validUserEmail2 = "test@emailaddress.com2";

    @Test
    @Order(1)
    @Description("Ensure that the User Controller loads")
    public void userControllerLoads() throws Exception {
        Assertions.assertNotNull(userController);
    }

    @Test
    @Order(2)
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/user/test", String.class),"User controller is working");
    }

    @Test
    @Order(3)
    @Description("Ensure that a valid user can be registered")
    public void registerValidUser_returnValidResponse(){
        RegisterUserRequest req = new RegisterUserRequest(this.validUserUUID1, this.validUserFirstName1, this.validUserLastName1, this.validUserName1,this.validUserEmail1);
        RegisterUserResponse res =this.restTemplate.postForObject("http://localhost:" + port + "/user/RegisterUser", req,RegisterUserResponse.class);
        Assertions.assertEquals(res.isSuccess(), true);
    }

    @Test
    @Order(4)
    @Description("Ensure that registering with a null RegisterUserRequest responds with unsuccessful")
    public void registerNullUser_returnInvalidResponse(){
        RegisterUserRequest req = new RegisterUserRequest(null, null, null, null,null);
        RegisterUserResponse res =this.restTemplate.postForObject("http://localhost:" + port + "/user/RegisterUser", req,RegisterUserResponse.class);
        Assertions.assertEquals(res.isSuccess(), false);
    }

    @Test
    @Order(5)
    @Description("When getting user with valid UUID, return user object")
    public void getValidUserUUID_returnUser(){
        GetUserByUUIDDTO res2 = this.restTemplate.getForObject("http://localhost:" + port + "/user/GetUser/" + this.validUserUUID1, GetUserByUUIDDTO.class);
        Assertions.assertEquals(res2.getUserID(), this.validUserUUID1);
        Assertions.assertEquals(res2.getFirstname(), this.validUserFirstName1);
        Assertions.assertEquals(res2.getLastname(), this.validUserLastName1);
        Assertions.assertEquals(res2.getUsername(), this.validUserName1);
        Assertions.assertEquals(res2.getEmail(), this.validUserEmail1);
    }

    @Test
    @Order(6)
    @Description("When getting user with valid Username, return user UUID")
    public void getValidUserUsername_returnUserUUID(){
        UUID res2 = this.restTemplate.getForObject("http://localhost:" + port + "/user/getByUserName/" + this.validUserName1, UUID.class);
        System.out.println(res2);
        Assertions.assertEquals(res2, this.validUserUUID1);
    }

    @Test
    @Order(99)
    @Description("Delete users when testing is complete")
    public void deleteTestUser(){
        this.restTemplate.getForObject("http://localhost:" + port + "/user/deleteUser/" + this.validUserUUID1, void.class);
        this.restTemplate.getForObject("http://localhost:" + port + "/user/deleteUser/" + this.validUserUUID2, void.class);
    }



}
