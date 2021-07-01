package com.adventureit.userservice;

import com.adventureit.userservice.Entities.User;
import com.adventureit.userservice.Repository.UserRepository;
import com.adventureit.userservice.Service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    UserServiceImplementation userServiceImplementation;
    @Autowired
    UserRepository userRepository;

    @Test
    public void addUser(){
        User user = new User();
        user.setUserID(UUID.randomUUID());
        userRepository.save(user);
    }

    @Test
    public void addPicture() throws Exception {
        File f = new File("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Pictures\\images.jpg");
        byte[] content = Files.readAllBytes(f.toPath());
        MockMultipartFile file = new MockMultipartFile("Profile Picture", "images.jpg", "jpg", content);
        userServiceImplementation.updateProfilePicture(file,UUID.fromString("77b2c3c3-0ea7-4ac8-b91e-b73606c533d6"));
    }

    @Test
    public void viewPicture() throws Exception {
        userServiceImplementation.viewImage(UUID.fromString("6abd17be-0a43-47ef-b16c-9881fb6f54f1"));
    }
}
