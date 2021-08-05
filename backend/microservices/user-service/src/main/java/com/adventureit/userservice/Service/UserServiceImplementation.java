package com.adventureit.userservice.Service;


import com.adventureit.userservice.Entities.Friend;
import com.adventureit.userservice.Entities.Users;
import com.adventureit.userservice.Exceptions.*;
import com.adventureit.userservice.Repository.FriendRepository;
import com.adventureit.userservice.Repository.RegistrationTokenRepository;
import com.adventureit.userservice.Repository.UserRepository;
import com.adventureit.userservice.Requests.LoginUserRequest;
import com.adventureit.userservice.Requests.RegisterUserRequest;
import com.adventureit.userservice.Responses.GetFriendRequestsResponse;
import com.adventureit.userservice.Responses.GetUserByUUIDDTO;
import com.adventureit.userservice.Responses.LoginUserDTO;
import com.adventureit.userservice.Responses.RegisterUserResponse;
import com.adventureit.userservice.Token.RegistrationToken;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("UserServiceImplementation")
public class UserServiceImplementation  {



    private  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository repo;
    private final RegistrationTokenRepository tokenrepo;
    private final FriendRepository friendRepository;

    @Autowired
    public UserServiceImplementation(UserRepository repo, RegistrationTokenRepository tokenrepo, FriendRepository friendRepository) {
        this.repo = repo;
        this.tokenrepo = tokenrepo;
        this.friendRepository = friendRepository;
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
        Users newUser = new Users(userId,username,firstName,lastName,email,passwordHashed,phoneNum);
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
    public GetUserByUUIDDTO GetUserByUUID(UUID req){
        UUID userId = req;
        Users newUser = repo.getUserByUserID(userId);
        if(newUser == null) {
            throw new UserDoesNotExistException("User does not exist - user is not registered as an Adventure-IT member");
        }
        return new GetUserByUUIDDTO(newUser.getUserID(),newUser.getUsername(),newUser.getFirstname(), newUser.getLastname(), newUser.getEmail(), newUser.getPhoneNumber());
    }


    public LoginUserDTO LoginUser(LoginUserRequest req){
        String username = req.getUsername();
        String password = req.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(15);


        assert repo != null;
        Users user = repo.getUserByUsername(username);
        if(user==null){
            throw new UserDoesNotExistException("User with username: "+username+" does not exist");
        }
        else if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidUserPasswordException("User password does not match email");
        }


        return new LoginUserDTO(true,"Login Successful: Welcome to Adventure-it");
    }

//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        Users user =  repo.getUserByUsername(s);
//        if(user ==null){
//           throw new UsernameNotFoundException("User not found");
//        }
//
//        return user;
//
//    }


    @Transactional
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

    public String updateProfilePicture(MultipartFile file, UUID id) throws Exception {
        if(file ==null){
            throw new Exception("File is null");
        }
        if(id ==null){
            throw new Exception("User ID not provided");
        }

        Users user = repo.getUserByUserID(id);
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

        Users user = repo.getUserByUserID(id);
        if(user == null){
            throw new Exception("User does not exist");
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(user.getProfilePicture());
        return ImageIO.read(bis);
    }

    public String removeImage(UUID id) throws Exception {
        if(id ==null){
            throw new Exception("User ID not provided");
        }

        Users user = repo.getUserByUserID(id);
        if(user == null){
            throw new Exception("User does not exist");
        }

        user.setProfilePicture(null);
        repo.save(user);
        return "Picture successfully removed";
    }

    public String createFriendRequest(UUID ID1, UUID ID2) throws Exception {
        if(repo.getUserByUserID(ID1) == null || repo.getUserByUserID(ID2) == null){
            throw new Exception("One or both of the users do not exist");
        }

        Friend friend = new Friend(ID1, ID2);
        friendRepository.save(friend);

        return "Friend request sent";
    }

    public String acceptFriendRequest(UUID id) throws Exception {
        Friend friend = friendRepository.findFriendById(id);

        if(friend == null){
            throw new Exception("Request Doesn't exist");
        }

        friend.setAccepted(true);
        friendRepository.save(friend);

        return saveFriends(id,friend.getFirstUser(),friend.getSecondUser());
    }

    public String saveFriends(UUID id,UUID ID1, UUID ID2) throws Exception {

        Friend friend = friendRepository.findFriendById(id);

        if(ID1.compareTo(ID2) > 0 ){
            friend.setFirstUser(ID2);
            friend.setSecondUser(ID1);
        }

        friendRepository.save(friend);
        return "Friends saved";
    }

    public List<UUID> getFriends(UUID id){

        List<Friend> friendsByFirstUser = friendRepository.findByFirstUserEquals(id);
        List<Friend> friendsBySecondUser = friendRepository.findBySecondUserEquals(id);
        List<UUID> friendUsers = new ArrayList<>();

        for (Friend friend : friendsByFirstUser) {
            if(friend.isAccepted()){
                friendUsers.add(friend.getSecondUser());
            }
        }
        for (Friend friend : friendsBySecondUser) {
            if(friend.isAccepted()) {
                friendUsers.add(friend.getFirstUser());
            }
        }
        return friendUsers;
    }

    public List<GetFriendRequestsResponse> getFriendRequests(UUID id){
        List<Friend> requests = friendRepository.findBySecondUserEquals(id);
        List<GetFriendRequestsResponse> list = new ArrayList<>();

        for (Friend f:requests) {
            if(!f.isAccepted()){
                list.add(new GetFriendRequestsResponse(f.getId(),f.getFirstUser(),f.getSecondUser(),f.getCreatedDate(),f.isAccepted()));
            }
        }

        return list;
    }

    public void deleteFriendRequest(UUID id) throws Exception {
        Friend request = friendRepository.findFriendById(id);
        if(request == null || request.isAccepted()){
            throw new Exception("Friend Request doesn't exist");
        }

        friendRepository.delete(request);
    }

    public String removeFriend(UUID id, UUID friend) throws Exception {
        List<Friend> friendList1 = friendRepository.findByFirstUserEquals(id);
        List<Friend> friendList2 = friendRepository.findBySecondUserEquals(id);
        boolean flag = false;

        for (Friend f:friendList1) {
            if(f.getSecondUser().equals(friend) && f.isAccepted()){
                friendRepository.delete(f);
                flag = true;
                break;
            }
        }
        for (Friend f:friendList2) {
            if(f.getFirstUser().equals(friend) && f.isAccepted()){
                friendRepository.delete(f);
                flag = true;
                break;
            }
        }

        if(flag == false){
            throw new Exception("Friend does not exist");
        }

        return "Friend removed";
    }

    public void mockFriendships()
    {
        Friend toSave = new Friend (UUID.fromString("1660bd85-1c13-42c0-955c-63b1eda4e90b"),UUID.fromString("69e8eb21-eb63-4c83-9187-181a648bb759"));
        toSave.setAccepted(true);
        friendRepository.save(toSave);

        toSave = new Friend (UUID.fromString("1660bd85-1c13-42c0-955c-63b1eda4e90b"),UUID.fromString("3f21ea6b-2288-42f3-9175-39adfafea9ab"));
        toSave.setAccepted(true);
        friendRepository.save(toSave);

        toSave = new Friend (UUID.fromString("3f21ea6b-2288-42f3-9175-39adfafea9ab"),UUID.fromString("86f26dff-8e17-4a82-a671-816ed611d712"));
        friendRepository.save(toSave);


    }
}
