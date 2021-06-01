package com.adventureit.userservice;


import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegisterUserJunitTests {

    private UserServiceImplementation user = new UserServiceImplementation();
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

    String validEmail = "u19024143@tuks.co.za";
    String invalidEmail = "InvalidEmail.com";

    String validPassword = "ValidPass123!";
    String invalidPassword = "2short";

    String validPhoneNum = "0794083124";
    String invalidPhoneNum = "012345678986";


    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void TestRegisterUserRequestgetters(){
        RegisterUserRequest req = new RegisterUserRequest(userName1,userlName1,validEmail,validPassword,validPhoneNum);
        assertNotNull(req);
        assertEquals(userName1,req.getfName());
        assertEquals(userlName1,req.getlName());
        assertEquals(validEmail,req.getEmail());
        assertEquals(validPassword,req.getPassword());
        assertEquals(validPhoneNum,req.getPhoneNum());
    }

    @Test
    @Description("This test tests whether the correct exception is thrown if an invalid email is used")
    void TestRegisterUserRequestInvalidEmail() throws InvalidUserPhoneNumberException, InvalidUserPasswordException {
        RegisterUserRequest req = new RegisterUserRequest(userName1,userlName1,invalidEmail,validPassword,validPhoneNum);
        assertNotNull(req);
        Throwable thrown = assertThrows(InvalidUserEmailException.class , ()-> user.RegisterUser(req));
        assertEquals("User email is incorrect - Unable to process registration", thrown.getMessage());
    }

    @Test
    @Description("This test tests whether the correct exception is thrown if an invalid password is used")
    void TestRegisterUserRequestInvalidPassword() throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException {
        RegisterUserRequest req = new RegisterUserRequest(userName1,userlName1,validEmail,invalidPassword,validPhoneNum);
        assertNotNull(req);
        Throwable thrown = assertThrows(InvalidUserPasswordException.class , ()-> user.RegisterUser(req));
        assertEquals("User password is incorrect - Unable to process registration", thrown.getMessage());
    }

    @Test
    @Description("This test tests whether the correct exception is thrown if an invalid phone number is used")
    void TestRegisterUserRequestInvalidPhoneNum() throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException {
        RegisterUserRequest req = new RegisterUserRequest(userName1,userlName1,validEmail,validPassword,invalidPhoneNum);
        assertNotNull(req);
        Throwable thrown = assertThrows(InvalidUserPhoneNumberException.class , ()-> user.RegisterUser(req));
        assertEquals("User phone number is incorrect - Unable to process registration", thrown.getMessage());
    }

    @Test
    @Description("This test tests whether the request object is null and throws correct exception")
     void TestInvalidRequest() throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException {
        RegisterUserRequest req = null;
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> user.RegisterUser(req));
        assertNull(req);
        assertEquals("404 Bad Request", thrown.getMessage());
    }

    @Test
    @Description("This test tests whether the response object returned carries the correct information")
    void TestRegisterUserResponse() throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        RegisterUserRequest req = new RegisterUserRequest(userName1,userlName1,validEmail,validPassword,validPhoneNum);
        assertNotNull(req);
        RegisterUserResponse response = user.RegisterUser(req);
        assertEquals("200 OK",response.getToken());
        assertEquals(true,response.isSuccess());
        assertEquals("User "+userName1+" "+userlName1+" successfully Registered",response.getMessage());
    }


}
