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
@TestPropertySource(properties = {"firebase-path=C:\\\\Users\\\\kevin\\\\Documents\\\\Enviroment variables\\\\adventure-it-bc0b6-firebase-adminsdk-o2fq8-ad3a51fb5e.json",
        "service-registry-client.instance.hostname=localhost","service-registry-client.client.service-url.defaultZone=http://localhost:8761/eureka/",
        "service-registry-client.client.register-with-eureka=true", "service-registry-client.client.fetch-registry=true",
        "user-microservice.application-name=USER-MICROSERVICE",
        "user-microservice.datasource.url=jdbc:postgresql://adventure-it-db.c9gozrkqo8dv.us-east-2.rds.amazonaws.com/adventureit?socketTimeout=5",
        "user-microservice.datasource.username=postgres","user-microservice.datasource.password=310PB!Gq%f&J",
        "user-microservice.datasource.hikari.maximum-pool-size=2","user-microservice.jpa.hibernate.ddl-auto=update",
        "user-microservice.jpa.show-sql=false","user-microservice.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
        "user-microservice.jpa.properties.hibernate.format_sql=true", "firebase-type: service_account", "firebase-project_id: adventure-it-bc0b6", "firebase-private_key_id: ad3a51fb5ea33531bf0dae65cb89dc82ea839f0c",
        "firebase-private_key: -----BEGIN PRIVATE KEY-----\\nMIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDScrmjF/vCUU7e\\nBef/Vd/oaskhCeSH2o0JqoR5S5eU6JB9YFQeLDhrg1awnSIQbqe29n+XyILYQlHF\\nwlVjo1LzZkCkY8qfl0KMZl1q9PMEEK7VSfN7LQjMC+wOjc55HrL2a1NIZ3Dw52Ln\\nsfA6Mb1R4aiZeQBsuVSUdKwXZMsydzl17kbLf1d99I4HI7SsaBvnsk3IhiI9Lk7F\\nBkD9qLhR6xbRDVBms6QSZiNMeqavrZryinnODsH+kBWecpp5GN1LIsPVeap2S5tM\\nX6iY8CKDFKbC3igSn9kVvEFYL8n1/qRrFwkut7kKVynX4thXwnDVfv8IWQx5tVMV\\nnHECGsBTAgMBAAECggEAXF3fTY6ewNyIBZnJCEBMPMn1yir544jQo1/0sfo0Jzbm\\nCClp8i5Nex7Tw0PMajLvKLZLLTbj+wAsvOQ9LzTFmTAVijGEgwRUQKRDN0kYin26\\nBsJk+/i5pjlLW93wtCd9u/tCPAKuxwV/2xq1ygz/v7sQEYBS5+V2Eoyc5c2nA9gV\\nc08ShvNaqoavU/NlOE3wke1BKegGrqZqSAlUiriNNSVtZhuz6EH1AzH38qXV8YJj\\nX+Dsu8HGCfsY0vZi5PvzaG8PMrqLhELCax1CGW0bbguJB6xd30KrtCSayumIEc7H\\nNMVZ2Aaii2OedErGEIDEd8K2ut35ZVijzbbU+77hiQKBgQDx3mEnglnIxBd7jNLE\\nx+5UA5ZCQUQMN99dSppiEVwV3xltDF9S+jcdK2jQsRKyrEZqnbo3EChAtXwwFiLr\\npESVeEAL7vPZP4MVmO4wqMwZc9YguH01gDMt5thhSkpeitxmZ3eJzlMD2uUBbew2\\nWCvnsYtFpfSD2KbibdPW1VZ42wKBgQDevmPZvxj8RWELh756vw4JKe9ZYOFZJFP1\\nfhLihcfye7IGFO3EJQa2camxol9EH09h+Ot4lZajtllW6WS8oJai+0qQsIi1H5Hr\\nh6HRzYAsJnyM7VtwKUSP0Vhtp8R0Zl6Gexb8bfdC2BwzMUPLvZ+vXXYZKNJ4qPD9\\nNS3NAFOT6QKBgBHkUWuKyPmBB/urvyuvXoH4gfUEvvPobi2Ih0MZ5aX4ivj2IVcS\\nC2GtBGPrtWZiOBNK96t7Fn8y7azg9lRYInqsGpDHbGJ1wEyos3YGBpMbboudGiYL\\nBb4vhXIs/LNhskwg+0bGbH2sg6RHbWHXw+evyo2saRoXvMCjPzh1L6BhAoGAWAjL\\nkg3zJBGPr2zxHbZRJ9IJJTwjFIZFIKu5bwoM4ot86uZuqq0voAAAX5KbMGNnjoNB\\nHaGRrhat7KnGBL87iiLjb5g2D8/wbjRnAnLEC68SXuiY0RWeYXEOEBjUjmS/S0tu\\n5EnaBfNAAgOgle/WIws/V+ZIeSPcS1cvSOyuG7ECgYBLbwb3TfGPIo+Kr2QRg84v\\nmrTSkTPeyWEdLYyxldPrfkyZFLAvDwjBhtSxUMTRQOswtXV2jOFa4K/ymo2PcTvC\\nOv3PAOjG8sgfjHvCnXLnOt2bngh80TrYjVH7azydOmMOAgc+BGwnV0EK/ZxW9bN0\\nksAkgPmSheUkFAP6WHVwHg==\\n-----END PRIVATE KEY-----\\n",
        "firebase-client_email: firebase-adminsdk-o2fq8@adventure-it-bc0b6.iam.gserviceaccount.com", "firebase-client_id: 100165871578157035845", "firebase-auth_uri: https://accounts.google.com/o/oauth2/auth",
        "firebase-token_uri: https://oauth2.googleapis.com/token", "firebase-auth_provider_x509_cert_url: https://www.googleapis.com/oauth2/v1/certs", "firebase-client_x509_cert_url: https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-o2fq8%40adventure-it-bc0b6.iam.gserviceaccount.com"})
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
        Assertions.assertEquals("User Controller is functional",this.restTemplate.getForObject("http://localhost:" + port + "/user/test", String.class));
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
        Assertions.assertNotNull(this.validUserUUID1);
        Assertions.assertNotNull(this.validUserUUID2);
        this.restTemplate.getForObject("http://localhost:" + port + "/user/deleteUser/" + this.validUserUUID1, void.class);
        this.restTemplate.getForObject("http://localhost:" + port + "/user/deleteUser/" + this.validUserUUID2, void.class);
    }



}
