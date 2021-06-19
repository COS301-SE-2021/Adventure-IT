package com.adventureit.userservice.Service;

import com.adventureit.userservice.Entities.User;
import com.adventureit.userservice.Exceptions.InvalidRequestException;
import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.GetUserByUUIDRequest;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDResponse;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("UserServiceImplementation")
public class UserServiceImplementation implements UserService {

    /**
     *
     * @param req
     * Attributes which will be attained from the req param will include:
     * First Name of the User (fName)
     * Last Name of the User (lName)
     * Email of the User (email)
     * Phone number of the User (phoneNum)
     * Password set by the User (password)
     *
     * Using the request object the RegisterUser Service will:
     * 1. Validate user email
     * 2. Validate user password
     * 3. Validate user phone number
     * 4. Check that user isn't already stored in the database
     * 5. Encrypt User password
     * 6. New UserId will be generated
     * 7. Create and add new User to database
     *
     *
     * @return RegisterUserResponse Object which will indicate whether
     * registration was successful or if an error occurred
     */
    @Override
    public RegisterUserResponse RegisterUser(RegisterUserRequest req) {

        /*Exception handling for invalid Request*/
        if(req==null){
            throw new InvalidRequestException("404 Bad Request");
        }
        UUID userId = UUID.randomUUID();
        String firstName = req.getfName();
        String lastName = req.getlName();
        String email = req.getEmail();
        String password = req.getPassword();
        String phoneNum = req.getPhoneNum();
        /*generate Regex for email, password and phone number*/
        String emailRegex = "^(.+)@(.+)$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
        String phoneNumRegex = "^(\\+27|0)[6-8][0-9]{8}$";

        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        Pattern phoneNumPattern = Pattern.compile(phoneNumRegex);
        Matcher phoneNumMatcher = phoneNumPattern.matcher(phoneNum);

        /*Exception handling for invalid email,password or phone number*/
        if(!emailMatcher.matches()){
            throw new InvalidUserEmailException("User email is incorrect - Unable to process registration");
        }
        if(!passwordMatcher.matches()){
            throw new InvalidUserPasswordException("User password is incorrect - Unable to process registration");
        }
        if(!phoneNumMatcher.matches()){
            throw new InvalidUserPhoneNumberException("User phone number is incorrect - Unable to process registration");
        }
        //TODO Decide on password encryption method

        /*New User has been created*/
        User newUser = new User(userId,firstName,lastName,email,password,phoneNum);

        return new RegisterUserResponse(true,"200 OK" ,"User "+firstName+" "+lastName+" successfully Registered");
    }

    /**
     * Get User By UUID currently a mock service with returns a set user until a persistence layer is created
     * The service will acquire the UserId from the request object then return a user with a set name, password, email
     * phone number.
     *
     * NB: This currently only for testing purposes.
     *
     * When the persistence layer is created the Service will search the database for a specific User.
     * @param req a GetUserByUUID request will be sent in with the user Id of the user that should be retrieved
     * @return returns a GetUserByUUID response which currently is a set user for testing purposes
     */
    public GetUserByUUIDResponse GetUserByUUID(GetUserByUUIDRequest req){
        UUID userId = req.getUserID();
        String name = "Kevin";
        String surname = "Potter";
        String email = "u19024143@tuks.co.za";
        String password = "AstrongPassword123!!";
        String phoneNum = "0794083122";
        User newUser = new User(userId,name,surname,email,password,phoneNum);
        return new GetUserByUUIDResponse(true, newUser);
    }


}
