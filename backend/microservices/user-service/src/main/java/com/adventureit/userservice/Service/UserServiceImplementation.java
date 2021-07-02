package com.adventureit.userservice.Service;

import com.adventureit.userservice.Entities.User;
import com.adventureit.userservice.Exceptions.*;
import com.adventureit.userservice.Repository.RegistrationTokenRepository;
import com.adventureit.userservice.Repository.UserRepository;
import com.adventureit.userservice.Requests.GetUserByUUIDRequest;
import com.adventureit.userservice.Requests.LoginUserRequest;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.GetUserByUUIDResponse;
import com.adventureit.userservice.Responses.LoginUserDTO;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Token.RegistrationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("UserServiceImplementation")
public class UserServiceImplementation implements UserDetailsService {



    private  BCryptPasswordEncoder encoder;
    private final UserRepository repo;
    private final RegistrationTokenRepository tokenrepo;

    @Autowired
    public UserServiceImplementation(UserRepository repo, RegistrationTokenRepository tokenrepo) {
        this.repo = repo;
        this.tokenrepo = tokenrepo;
    }

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
        String username = req.getUsername();
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
        if(repo.getUserByEmail(email)!=null){
            throw new InvalidRequestException("User already exists");
        }
        //TODO Decide on password encryption method

        String passwordHashed = encoder.encode(password);

        /*New User has been created*/
        User newUser = new User(userId,username,firstName,lastName,email,passwordHashed,phoneNum);
        repo.save(newUser);

        String token = UUID.randomUUID().toString();
        RegistrationToken regToken = new RegistrationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                null,
                newUser);

        tokenrepo.save(regToken);





        return new RegisterUserResponse(true,regToken ,"User "+firstName+" "+lastName+" successfully Registered");
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
        User newUser = repo.getUserByUserID(userId);
        if(newUser == null) {
            throw new UserDoesNotExistException("User does not exist - user is not registered as an Adventure-IT member");
        }
        return new GetUserByUUIDResponse(true, newUser);
    }


    public LoginUserDTO LoginUser(LoginUserRequest req){
        String email = req.getEmail();
        String password = req.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);


        assert repo != null;
        User user = repo.getUserByEmail(email);
        if(user==null){
            throw new UserDoesNotExistException("User with email: "+email+" does not exist");
        }
        else if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidUserPasswordException("User password does not match email");
        }


        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public String confirmToken(String token){
        RegistrationToken regToken = tokenrepo.findByToken(token);

        if(regToken == null){
            /*Throw token not found*/
        }

        if(regToken.getTimeConfirmed()!=null){
            /*throw email already confirmed*/
        }

        LocalDateTime expiredAt = regToken.getTimeExpires();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }


        tokenrepo.updateConfirmedAt(regToken.getToken(), LocalDateTime.now());
        regToken.getUser().setEnabled(true);

        return "confirmed";
    }

    @Transactional
    public String updateProfilePicture(MultipartFile file, UUID id) throws Exception {
        if(file ==null){
            throw new Exception("File is null");
        }
        if(id ==null){
            throw new Exception("User ID not provided");
        }

        User user = repo.getUserByUserID(id);
        if(user == null){
            throw new Exception("User does not exist");
        }

        try {
            user.setProfilePicture(file.getBytes());
            repo.save(user);
        } catch (Exception e) {
            return "error";
        }

        return "Profile Picture successfully updated!";
    }

    @Transactional
    public Image viewImage(UUID id) throws Exception {
        if(id ==null){
            throw new Exception("User ID not provided");
        }

        User user = repo.getUserByUserID(id);
        if(user == null){
            throw new Exception("User does not exist");
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(user.getProfilePicture());
        BufferedImage bImage = ImageIO.read(bis);
        return bImage;
//        ImageIO.write(bImage, "jpg", new File("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Pictures\\output.jpg"));
    }


}
