package com.adventureit.userservice;


import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Repository.FriendRepository;
import com.adventureit.userservice.Repository.RegistrationTokenRepository;
import com.adventureit.userservice.Repository.UserRepository;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegisterUserJunitTests {

    @Mock
    UserRepository repo;

    @Mock
    RegistrationTokenRepository tokenRepo;

    @Mock
    FriendRepository friendRepository;


    private UserServiceImplementation user =
            new UserServiceImplementation(repo,friendRepository);
    /**
     * Generate mock data to handle Junit testing with
     * Mock data includes:
     *
     * userName1 : Mock User first name for testing
     * userlName1 : Mock User last name for testing
     *
     * validEmail : Mock valid email for testing
     * invalidEmail: Mock invalid email for testing
     *
     * validPassword : Mock valid password for testing
     * invalidPassword : Mock invalid password for testing
     *
     * validPhoneNum : Mock valid phone number for testing
     * invalidPhoneNum : Mock invalid phone number for testing
     */

    String userName1 = "User1"; 
    String userlName1 = "Surname1";
    String username1 ="username1";

    String validEmail = "u19024143@tuks.co.za";
    String invalidEmail = "InvalidEmail.com";

    final UUID uuid1 = UUID.randomUUID();


    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void TestRegisterUserRequestgetters(){
        RegisterUserRequest req = new RegisterUserRequest(uuid1, userName1,userlName1,username1,validEmail);
        assertNotNull(req);
        assertEquals(uuid1,req.getUserID());
        assertEquals(userName1,req.getfName());
        assertEquals(userlName1,req.getlName());
        assertEquals(validEmail,req.getEmail());
    }

    @Test
    @Description("This test tests whether the correct exception is thrown if an invalid email is used")
    void TestRegisterUserRequestInvalidEmail() throws InvalidUserPhoneNumberException, InvalidUserPasswordException {
        RegisterUserRequest req = new RegisterUserRequest(uuid1, userName1,userlName1,username1,invalidEmail);
        assertNotNull(req);
        Throwable thrown = assertThrows(InvalidUserEmailException.class , ()-> user.RegisterUser(req));
        assertEquals("User email is incorrect - Unable to process registration", thrown.getMessage());
    }

    @Test
    @Description("This test tests whether the request object is null and throws correct exception")
     void TestInvalidRequest() throws InvalidUserEmailException {
        RegisterUserRequest req = null;
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> user.RegisterUser(req));
        assertNull(req);
        assertEquals("404 Bad Request", thrown.getMessage());
    }

//    @Test
//    @Description("This test tests whether the response object returned carries the correct information")
//    void TestRegisterUserResponse() throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
//        RegisterUserRequest req = new RegisterUserRequest(uuid1, userName1,userlName1,username1,validEmail);
//        assertNotNull(req);
//        RegisterUserResponse response = user.RegisterUser(req);
//        assertEquals(true,response.isSuccess());
//        assertEquals("User "+userName1+" "+userlName1+" successfully Registered",response.getMessage());
//    }


}
