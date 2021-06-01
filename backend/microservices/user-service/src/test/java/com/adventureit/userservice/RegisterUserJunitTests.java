package com.adventureit.userservice;


import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegisterUserJunitTests {

    private UserServiceImplementation user = new UserServiceImplementation();
    String userName1 = "User1";
    String userName2 = "User2";

    String userlName1 = "Surname1";
    String userlName2 = "Surname2";

    String validEmail = "u19024143@tuks.co.za";
    String invalidEmail = "InvalidEmail.com";

    String validPassword = "ValidPass123";
    String invalidPassword = "2short";

    String validPhoneNum = "0794083124";
    String invalidPhoneNum = "012345678";



    @Test
    void contextLoads() {
    }
    @BeforeEach
    void setup(){

    }

    @Test
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
    void TestRegisterUserRequestInvalidEmail() throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException {
        RegisterUserRequest req = new RegisterUserRequest(userName1,userlName1,invalidEmail,validPassword,validPhoneNum);
        assertNotNull(req);
        user.RegisterUser(req);
        Throwable thrown = Assertions.assertThrows(InvalidUserEmailException.class, ()-> user.RegisterUser(req));
        assertEquals("User email is incorrect - Unable to process registration", thrown.getMessage());
    }

}
