package com.adventureit.mediaservice;

import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Enumeration.MediaType;
import com.adventureit.mediaservice.Repository.MediaRepository;
import com.adventureit.mediaservice.Service.MediaService;
import com.adventureit.mediaservice.Service.MediaServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootTest
public class LocalTests {
    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    MediaServiceImplementation mediaServiceImplementation;

    @Test
    public void addMediaTest(){
        Media media = new Media(UUID.randomUUID(), MediaType.Video,"Mock","Mock",UUID.randomUUID(),UUID.randomUUID());
//        media.setId(UUID.randomUUID());
        mediaRepository.save(media);
    }

}
