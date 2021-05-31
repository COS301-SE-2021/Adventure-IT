package com.adventureit.userservice.Service;

import com.adventureit.userservice.Exceptions.InvalidUserEmailException;
import com.adventureit.userservice.Exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.Exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import org.springframework.stereotype.Service;

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
     * 6. Create and add new User to database
     *
     *
     * @return RegisterUserResponse Object which will indicate whether
     * registration was successful or if an error occured
     */
    @Override
    public RegisterUserResponse RegisterUser(RegisterUserRequest req) throws InvalidUserEmailException, InvalidUserPasswordException, InvalidUserPhoneNumberException {
        String firstName = req.getfName();
        String lastName = req.getlName();
        String email = req.getEmail();
        String password = req.getPassword();
        String phoneNum = req.getPhoneNum();
        String emailRegex = "^(.+)@(.+)$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        String phoneNumRegex = "^(\\+27|0)[6-8][0-9]{8}$";

        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);

        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);

        Pattern phoneNumPattern = Pattern.compile(phoneNumRegex);
        Matcher phoneNumMatcher = phoneNumPattern.matcher(phoneNum);

        if(!emailMatcher.matches()){
            throw new InvalidUserEmailException("User email is incorrect");
        }
        if(!passwordMatcher.matches()){
            throw new InvalidUserPasswordException("User password is incorrect");
        }
        if(!phoneNumMatcher.matches()){
            throw new InvalidUserPhoneNumberException("User phone number is incorrect");
        }

        return new RegisterUserResponse(true, "User"+firstName+" "+lastName+" Succesfully Registered");
    }

}
