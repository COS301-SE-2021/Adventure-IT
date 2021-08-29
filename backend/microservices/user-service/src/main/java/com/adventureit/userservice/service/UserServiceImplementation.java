package com.adventureit.userservice.service;


import com.adventureit.userservice.entities.Friend;
import com.adventureit.userservice.entities.PictureInfo;
import com.adventureit.userservice.entities.Users;
import com.adventureit.userservice.exceptions.*;
import com.adventureit.userservice.repository.FriendRepository;
import com.adventureit.userservice.repository.PictureInfoRepository;
import com.adventureit.userservice.repository.UserRepository;
import com.adventureit.userservice.requests.RegisterUserRequest;
import com.adventureit.userservice.responses.FriendDTO;
import com.adventureit.userservice.responses.GetFriendRequestsResponse;
import com.adventureit.userservice.responses.GetUserByUUIDDTO;
import com.adventureit.userservice.responses.RegisterUserResponse;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("UserServiceImplementation")
public class UserServiceImplementation  {

    private final UserRepository repo;
    private final FriendRepository friendRepository;

    @Autowired
    PictureInfoRepository pictureInfoRepository;

    private StorageOptions storageOptions;
    private String bucketName;

    @Autowired
    public UserServiceImplementation(UserRepository repo, FriendRepository friendRepository) {
        this.repo = repo;
        this.friendRepository = friendRepository;
    }

    @PostConstruct
    private void initializeFirebase() throws IOException {
        bucketName = "adventure-it-bc0b6.appspot.com";
        String projectId = "Adventure-IT";
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 2\\COS301\\adventure-it-bc0b6-firebase-adminsdk-o2fq8-ad3a51fb5e.json");
        this.storageOptions = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
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

    public RegisterUserResponse registerUser(RegisterUserRequest req) {

        /*Exception handling for invalid Request*/
        if(req==null){
            throw new InvalidRequestException("404 Bad Request");
        }
        UUID userId = req.getUserID();
        String firstName = req.getfName();
        String lastName = req.getlName();
        String email = req.getEmail();
        String username = req.getUsername();
        /*generate Regex for email, password and phone number*/
        String emailRegex = "^(.+)@(.+)$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
        String phoneNumRegex = "^(\\+27|0)[6-8][0-9]{8}$";

        Pattern emailPattern = Pattern.compile(emailRegex);
        //Matcher emailMatcher = emailPattern.matcher(email);

//        /*Exception handling for invalid email,password or phone number*/
//        if(!emailMatcher.matches()){
//            throw new InvalidUserEmailException("User email is incorrect - Unable to process registration");
//        }
//        if(repo.getUserByEmail(email)!=null){
//            throw new InvalidRequestException("User already exists");
//        }

        /*New User has been created*/
        Users newUser = new Users(userId,username,firstName,lastName,email);
        repo.save(newUser);

        return new RegisterUserResponse(true,"User "+firstName+" "+lastName+" successfully Registered");
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
    public GetUserByUUIDDTO getUserByUUID(UUID req){
        Users newUser = repo.getUserByUserID(req);
        if(newUser == null) {
            throw new UserDoesNotExistException("User does not exist - user is not registered as an Adventure-IT member");
        }
        return new GetUserByUUIDDTO(newUser.getUserID(),newUser.getUsername(),newUser.getFirstname(), newUser.getLastname(), newUser.getEmail());
    }

    public HttpStatus updateProfilePicture(MultipartFile file, UUID userId){
        try {
            UUID id = UUID.randomUUID();
            String fileName = file.getOriginalFilename();

            PictureInfo uploadedPicture = new PictureInfo(id, file.getContentType(), fileName, userId);
            pictureInfoRepository.save(uploadedPicture);

            Storage storage = storageOptions.getService();
            BlobId blobId = BlobId.of(bucketName, id.toString());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());

            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NO_CONTENT;
        }
    }

    @Transactional
    public ResponseEntity<byte[]> viewImage(UUID id) throws IOException {
        PictureInfo info = pictureInfoRepository.findPictureInfoByOwner(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.parseMediaType(info.getType()));

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, info.getId().toString()));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);
        byte[] content = inputStream.readAllBytes();

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    public void removeImage(UUID id){
        PictureInfo pictureInfo = pictureInfoRepository.findPictureInfoByOwner(id);
        Storage storage = storageOptions.getService();

        if(pictureInfo == null){
            throw new NotFoundException("Remove Image: Image does not exist");
        }

        storage.get(BlobId.of(bucketName, pictureInfo.getId().toString())).delete();
        pictureInfoRepository.delete(pictureInfo);
    }

    public String createFriendRequest(String userId1, String userId2){
        if(repo.getUserByUserID(UUID.fromString(userId1)) == null || repo.getUserByUserID(UUID.fromString(userId2)) == null){
            throw new InvalidRequestException("One or both of the users do not exist");
        }

        Friend friend = new Friend(UUID.fromString(userId1), UUID.fromString(userId2));
        friendRepository.save(friend);

        return "Friend request sent";
    }

    public String acceptFriendRequest(UUID id){
        Friend friend = friendRepository.findFriendById(id);

        if(friend == null){
            throw new InvalidRequestException("Request Doesn't exist");
        }

        friend.setAccepted(true);
        friendRepository.save(friend);

        return saveFriends(id,friend.getFirstUser(),friend.getSecondUser());
    }

    public String saveFriends(UUID id,UUID userId1, UUID userId2){

        Friend friend = friendRepository.findFriendById(id);

        if(userId1.compareTo(userId2) > 0 ){
            friend.setFirstUser(userId2);
            friend.setSecondUser(userId1);
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

    public List<GetUserByUUIDDTO> getFriendProfiles(UUID id){

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

        List<GetUserByUUIDDTO> profileList = new ArrayList<>();

        for (UUID friendUser : friendUsers) {
            GetUserByUUIDDTO toAdd = this.getUserByUUID(friendUser);
            if (toAdd != null){
                profileList.add(toAdd);
            }
        }

        return profileList;

    }

    public List<GetFriendRequestsResponse> getFriendRequests(UUID id){
        List<Friend> requests = friendRepository.findBySecondUserEquals(id);
        List<GetFriendRequestsResponse> list = new ArrayList<>();
        Users user;

        for (Friend f:requests) {
            if(!f.isAccepted()){
                user = repo.getUserByUserID(f.getFirstUser());
                list.add(new GetFriendRequestsResponse(f.getId(),f.getFirstUser(),f.getSecondUser(),f.getCreatedDate(),f.isAccepted(),new GetUserByUUIDDTO(user.getUserID(),user.getUsername(),user.getFirstname(),user.getLastname(),user.getEmail())));
            }
        }

        return list;
    }


    public void deleteFriendRequest(UUID id){
        Friend request = friendRepository.findFriendById(id);
        if(request == null || request.isAccepted()){
            throw new InvalidRequestException("Friend Request doesn't exist");
        }

        friendRepository.delete(request);
    }

    public String removeFriend(UUID id, UUID friend){
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

        if(!flag){
            throw new InvalidRequestException("Friend does not exist");
        }

        return "Friend removed";
    }

    public UUID getUserIDByUserName(String userName){
        Users user = repo.getUserByUsername(userName);
        return  user.getUserID();
    }

    public FriendDTO getFriendRequest(UUID id){
        Friend request = friendRepository.findFriendById(id);
        if(request == null || request.isAccepted()){
            throw new InvalidRequestException("Friend Request doesn't exist");
        }

        return new FriendDTO(request.getId(),request.getFirstUser(), request.getSecondUser(),request.getCreatedDate(),request.isAccepted());
    }


    public void deleteUser(UUID id){
        Users found = repo.getUserByUserID(id);
        repo.delete(found);
    }
}
