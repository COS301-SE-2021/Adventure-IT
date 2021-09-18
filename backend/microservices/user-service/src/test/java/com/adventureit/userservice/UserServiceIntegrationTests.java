package com.adventureit.userservice;

import com.adventureit.userservice.controller.UserController;
import com.adventureit.shareddtos.user.requests.RegisterUserRequest;
import com.adventureit.shareddtos.user.responses.GetUserByUUIDDTO;
import com.adventureit.shareddtos.user.responses.RegisterUserResponse;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/","service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true","user-microservice.application-name=USER-MICROSERVICE", "user-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5","user-microservice.datasource.username=postgres","user-microservice.datasource.password=310PB!Gq%f&J","user-microservice.datasource.hikari.maximum-pool-size=2","user-microservice.jpa.hibernate.ddl-auto=update","user-microservice.jpa.show-sql=false","user-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect","user-microservice.jpa.properties.hibernate.format_sql=true" })
class UserServiceIntegrationTests {
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


    @Test
    @Order(1)
    @Description("Ensure that the User Controller loads")
     void userControllerLoads(){
        Assertions.assertNotNull(userController);
    }

    @Test
    @Order(2)
    @Description("Ensure that the controller is accepting traffic and responding")
    void httpTest_returnResponse(){
        Assertions.assertEquals("User controller is working",this.restTemplate.getForObject("http://localhost:" + port + "/user/test", String.class));
    }

    @Test
    @Order(3)
    @Description("Ensure that a valid user can be registered")
     void registerValidUser_returnValidResponse(){
        RegisterUserRequest req = new RegisterUserRequest(this.validUserUUID1, this.validUserFirstName1, this.validUserLastName1, this.validUserName1,this.validUserEmail1);
        RegisterUserResponse res =this.restTemplate.postForObject("http://localhost:" + port + "/user/registerUser", req,RegisterUserResponse.class);
        Assertions.assertTrue(res.isSuccess());
    }

    @Test
    @Order(4)
    @Description("Ensure that registering with a null RegisterUserRequest responds with unsuccessful")
    void registerNullUser_returnInvalidResponse(){
        RegisterUserRequest req = new RegisterUserRequest(null, null, null, null,null);
        RegisterUserResponse res =this.restTemplate.postForObject("http://localhost:" + port + "/user/RegisterUser", req,RegisterUserResponse.class);
        Assertions.assertFalse(res.isSuccess());
    }

    @Test
    @Order(5)
    @Description("When getting user with valid UUID, return user object")
    void getValidUserUUID_returnUser(){
        GetUserByUUIDDTO res2 = this.restTemplate.getForObject("http://localhost:" + port + "/user/getUser/" + this.validUserUUID1, GetUserByUUIDDTO.class);
        Assertions.assertEquals(res2.getUserID(), this.validUserUUID1);
        Assertions.assertEquals(res2.getFirstname(), this.validUserFirstName1);
        Assertions.assertEquals(res2.getLastname(), this.validUserLastName1);
        Assertions.assertEquals(res2.getUsername(), this.validUserName1);
        Assertions.assertEquals(res2.getEmail(), this.validUserEmail1);
    }

    @Test
    @Order(6)
    @Description("When getting user with valid Username, return user UUID")
    void getValidUserUsername_returnUserUUID(){

        UUID res2 = this.restTemplate.getForObject("http://localhost:" + port + "/user/getByUserName/" + this.validUserName1, UUID.class);
        System.out.println(res2);
        Assertions.assertEquals(res2, this.validUserUUID1);
    }

    @Test
    @Order(99)
    @Description("Delete users when testing is complete")
    void deleteTestUser(){
        this.restTemplate.getForObject("http://localhost:" + port + "/user/deleteUser/" + this.validUserUUID1, void.class);
        this.restTemplate.getForObject("http://localhost:" + port + "/user/deleteUser/" + this.validUserUUID2, void.class);
    }



}
