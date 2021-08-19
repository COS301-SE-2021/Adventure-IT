package com.adventureit.mediaservice;

import com.adventureit.mediaservice.Controller.MediaController;
import com.adventureit.mediaservice.Entity.File;
import com.adventureit.mediaservice.Entity.FileInfo;
import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Entity.MediaInfo;
import com.adventureit.mediaservice.Repository.FileInfoRepository;
import com.adventureit.mediaservice.Repository.FileRepository;
import com.adventureit.mediaservice.Repository.MediaInfoRepository;
import com.adventureit.mediaservice.Repository.MediaRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MediaServiceIntegrationTests {

    @Autowired
    private MediaController mediaController;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaInfoRepository mediaInfoRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Description("Ensure that the Media Controller loads")
    public void mediaControllerLoads() throws Exception {
        Assertions.assertNotNull(mediaController);
    }

    @Test
    @Description("Ensure that the controller is accepting traffic and responding")
    public void httpTest_returnResponse(){
        Assertions.assertEquals(this.restTemplate.getForObject("http://localhost:" + port + "/media/test", String.class),"Media Controller is functional");
    }

    @Test
    @Description("Ensure that the delete media function works")
    public void httpDeleteMedia_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Media media = new Media(id, "image/jpeg", "Mock", "Mock", adventureID, ownerID);
        MediaInfo mediaInfo = new MediaInfo(id, "image/jpeg", "Mock", "Mock", adventureID, ownerID);
        mediaRepository.saveAndFlush(media);
        mediaInfoRepository.saveAndFlush(mediaInfo);
        this.restTemplate.getForObject("http://localhost:" + port + "/media/deleteMedia/{id}/{userID}", String.class, id,ownerID);
    }

    @Test
    @Description("Ensure that the delete file function works")
    public void httpDeleteFile_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        File file = new File(id, "application/pdf", "Mock", "Mock", adventureID, ownerID);
        FileInfo fileInfo = new FileInfo(id, "image/jpeg", "Mock", "Mock", adventureID, ownerID);
        fileRepository.saveAndFlush(file);
        fileInfoRepository.saveAndFlush(fileInfo);
        this.restTemplate.getForObject("http://localhost:" + port + "/media/deleteFile/{id}/{userID}", String.class, id,ownerID);
    }

    @Test
    @Description("Ensure that the get media by adventure function works")
    public void httpGetAdventureMediaList_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        Media media = new Media(id, "image/jpeg", "Mock", "Mock", adventureID, ownerID);
        MediaInfo mediaInfo = new MediaInfo(id, "image/jpeg", "Mock", "Mock", adventureID, ownerID);
        mediaRepository.saveAndFlush(media);
        mediaInfoRepository.saveAndFlush(mediaInfo);
        List<FileInfo> list = this.restTemplate.getForObject("http://localhost:" + port + "/media/getAdventureMediaList/{id}", List.class, adventureID);
        Assertions.assertTrue(!list.isEmpty());
    }

    @Test
    @Description("Ensure that the get files by adventure function works")
    public void httpGetAdventureFileList_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        File file = new File(id, "application/pdf", "Mock", "Mock", adventureID, ownerID);
        FileInfo fileInfo = new FileInfo(id, "image/jpeg", "Mock", "Mock", adventureID, ownerID);
        fileRepository.saveAndFlush(file);
        fileInfoRepository.saveAndFlush(fileInfo);
        List<FileInfo> list = this.restTemplate.getForObject("http://localhost:" + port + "/media/getAdventureFileList/{id}", List.class, adventureID);
        Assertions.assertTrue(!list.isEmpty());
    }

    @Test
    @Description("Ensure that the get private files by User function works")
    public void httpGetUserFileList_returnResponse(){
        UUID id = UUID.randomUUID();
        UUID adventureID = UUID.randomUUID();
        UUID ownerID = UUID.randomUUID();
        File file = new File(id, "application/pdf", "Mock", "Mock", adventureID, ownerID);
        FileInfo fileInfo = new FileInfo(id, "image/jpeg", "Mock", "Mock", adventureID, ownerID);
        fileRepository.saveAndFlush(file);
        fileInfoRepository.saveAndFlush(fileInfo);
        List<FileInfo> list = this.restTemplate.getForObject("http://localhost:" + port + "/media/getUserFileList/{id}", List.class, ownerID);
        Assertions.assertTrue(!list.isEmpty());
    }
}
