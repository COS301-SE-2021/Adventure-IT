package com.adventureit.userservice;


import com.adventureit.userservice.entities.Users;
import com.adventureit.userservice.exceptions.InvalidRequestException;
import com.adventureit.userservice.exceptions.InvalidUserEmailException;
import com.adventureit.userservice.exceptions.InvalidUserPasswordException;
import com.adventureit.userservice.exceptions.InvalidUserPhoneNumberException;
import com.adventureit.userservice.repository.FriendRepository;
import com.adventureit.userservice.repository.UserRepository;
import com.adventureit.shareddtos.user.requests.*;
import com.adventureit.shareddtos.user.responses.*;
import com.adventureit.userservice.service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;


import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserUnitTests {


    UserRepository repo = Mockito.mock(UserRepository.class);
    FriendRepository friendRepository = Mockito.mock(FriendRepository.class);


    private final UserServiceImplementation user =
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
    String firstName1 = "John";
    String lastName1 = "Doe";
    String validEmail = "u19024143@tuks.co.za";



    final UUID uuid1 = UUID.randomUUID();
    Users mockUser = new Users(uuid1,userName1,firstName1,lastName1,validEmail);


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
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void acceptFriendRequestTest(){
        //Given
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID freiendId = UUID.randomUUID();

        //When
        AcceptFriendRequest mockRequest = new AcceptFriendRequest(freiendId,user1,user2);

        //Then
        assertEquals(user1,mockRequest.getUserId1());
        assertEquals(user2,mockRequest.getUserId2());
        assertEquals(freiendId,mockRequest.getId());
    }

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void getUserByUUIDRequestTest(){
        //Given
        UUID user1 = UUID.randomUUID();

        //When
        GetUserByUUIDRequest mockRequest = new GetUserByUUIDRequest(user1);

        //Then
        assertEquals(user1,mockRequest.getUserID());
    }

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void updatePictureRequestTest(){
        //Given
        String mockId = "86e5edec-c8b1-4dbb-8c2d-8c39d8ebf2ed";
        String path = "MOCK PATH";

        //When
        UpdatePictureRequest mockRequest = new UpdatePictureRequest(path,mockId);

        //Then
        assertEquals(UUID.fromString(mockId),mockRequest.getId());
        assertEquals(path,mockRequest.getPath());
    }

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void loginUserRequestTest(){
        //Given
        String username = "user";
        String password = "password";

        //When
        LoginUserRequest mockRequest = new LoginUserRequest(username,password);

        //Then
        assertEquals(username,mockRequest.getUsername());
        assertEquals(password,mockRequest.getPassword());
    }

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void friendDTOTest(){
        UUID mockId = UUID.randomUUID();
        UUID mockFirstUser = UUID.randomUUID();
        UUID mockSecondUser = UUID.randomUUID();
        Date mockCreatedDate = new Date(Long.MIN_VALUE);
        boolean accepted = true;

        //When
        FriendDTO mockRequest = new FriendDTO(mockId,mockFirstUser,mockSecondUser,mockCreatedDate,accepted);

        //Then
        assertEquals(mockId,mockRequest.getId());
        assertEquals(mockFirstUser,mockRequest.getFirstUser());
        assertEquals(mockSecondUser,mockRequest.getSecondUser());
        assertEquals(mockCreatedDate,mockRequest.getCreatedDate());
        assertEquals(accepted,mockRequest.isAccepted());
    }

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void friendRequestResponseTest(){
        UUID mockId = UUID.randomUUID();
        UUID mockFirstUser = UUID.randomUUID();
        UUID mockSecondUser = UUID.randomUUID();
        Date mockCreatedDate = new Date(Long.MIN_VALUE);
        boolean accepted = true;
        String mockRequestername = "Username";

        UUID mockUserID = UUID.randomUUID();
        String mockUsername = "Username";
        String mockFirstname = "John";
        String mockLastname = "Doe";
        String mockEmail = "John@Doe";
        GetUserByUUIDDTO mockRequester = new GetUserByUUIDDTO(mockUserID,mockUsername,mockFirstname,mockLastname,mockEmail);


        //When
        GetFriendRequestsResponse mockRequest = new GetFriendRequestsResponse(mockId,mockFirstUser,mockSecondUser,mockCreatedDate,accepted,mockRequester);

        //Then
        assertEquals(mockId,mockRequest.getId());
        assertEquals(mockFirstUser,mockRequest.getFirstUser());
        assertEquals(mockSecondUser,mockRequest.getSecondUser());
        assertEquals(mockCreatedDate,mockRequest.getCreatedDate());
        assertEquals(accepted,mockRequest.isAccepted());
        assertEquals(mockRequestername,mockRequest.getRequester());
    }

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void getUserByUUIDDTOTest(){
        UUID mockUserID = UUID.randomUUID();
        String mockUsername = "Username";
        String mockFirstname = "John";
        String mockLastname = "Doe";
        String mockEmail = "John@Doe";

        //When
        GetUserByUUIDDTO mockRequest = new GetUserByUUIDDTO(mockUserID,mockUsername,mockFirstname,mockLastname,mockEmail);

        //Then
        assertEquals(mockUserID,mockRequest.getUserID());
        assertEquals(mockUsername,mockRequest.getUsername());
        assertEquals(mockFirstname,mockRequest.getFirstname());
        assertEquals(mockLastname,mockRequest.getLastname());
        assertEquals(mockEmail,mockRequest.getEmail());

    }

    @Test
    @Description("This test tests whether the correct information will be retrieved through the request object")
    void loginUserDTOTest(){
        boolean success = true;
        String message = "Message";

        //When
        LoginUserDTO mockRequest = new LoginUserDTO(success,message);

        //Then
        assertEquals(success,mockRequest.isSuccess());
        assertEquals(message,mockRequest.getMessage());
    }

    @Test
    @Description("This test tests whether the request object is null and throws correct exception")
     void TestInvalidRequest() throws InvalidUserEmailException {
        RegisterUserRequest req = null;
        Throwable thrown = assertThrows(InvalidRequestException.class , ()-> user.registerUser(req));
        assertNull(req);
        assertEquals("404 Bad Request", thrown.getMessage());
    }

    @Test
    @Description("This test tests whether the response object returned carries the correct information")
    void TestRegisterUserResponse() throws InvalidUserEmailException, InvalidUserPhoneNumberException, InvalidUserPasswordException, InvalidRequestException {
        RegisterUserRequest req = new RegisterUserRequest(uuid1, userName1,userlName1,username1,validEmail);
        assertNotNull(req);
        RegisterUserResponse response = user.registerUser(req);
        assertTrue(response.isSuccess());
        assertEquals("User "+userName1+" "+userlName1+" successfully Registered",response.getMessage());
    }

    @Test
    @Description("This test tests whether the response object returned carries the correct information")
    void getUserByUUIDTest(){

        Mockito.when(repo.getUserByUserID(uuid1)).thenReturn(mockUser);

        GetUserByUUIDDTO response = user.getUserByUUID(uuid1);

        assertEquals(mockUser.getUserID(),response.getUserID());
        assertEquals(mockUser.getUsername(),response.getUsername());
        assertEquals(mockUser.getFirstname(),response.getFirstname());
        assertEquals(mockUser.getLastname(),response.getLastname());
        assertEquals(mockUser.getEmail(),response.getEmail());
    }


}
