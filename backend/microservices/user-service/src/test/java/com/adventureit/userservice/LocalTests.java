package com.adventureit.userservice;

import com.adventureit.userservice.Service.UserServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Test
    public void addProfilePictures() throws Exception {
        BufferedImage bImage = ImageIO.read(new File("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Pictures\\download (2).jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        MockMultipartFile file = new MockMultipartFile("pp1", data);
        userServiceImplementation.updateProfilePicture(file, UUID.fromString("af12cd2b-1ad9-431a-8534-121f1a61ba3b"));
    }


}
