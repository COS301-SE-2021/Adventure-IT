package com.adventureit.mediaservice;

import com.adventureit.mediaservice.Entity.File;
import com.adventureit.mediaservice.Entity.FileInfo;
import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Entity.MediaInfo;
import com.adventureit.mediaservice.Repository.FileInfoRepository;
import com.adventureit.mediaservice.Repository.FileRepository;
import com.adventureit.mediaservice.Repository.MediaInfoRepository;
import com.adventureit.mediaservice.Repository.MediaRepository;
import com.adventureit.mediaservice.Service.MediaServiceImplementation;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.UUID;

class MediaServiceUnitTests {
    private final MediaRepository mockMediaRepository = Mockito.mock(MediaRepository.class);
    private final MediaInfoRepository mockMediaInfoRepository = Mockito.mock(MediaInfoRepository.class);
    private final FileRepository mockFileRepository = Mockito.mock(FileRepository.class);
    private final FileInfoRepository mockFileInfoRepository = Mockito.mock(FileInfoRepository.class);
    private final MediaServiceImplementation sut = new MediaServiceImplementation(mockMediaRepository,mockMediaInfoRepository,mockFileRepository,mockFileInfoRepository);

    final UUID validMediaID1 = UUID.randomUUID();
    final UUID validFileID1 = UUID.randomUUID();
    final UUID validAdventureID1 = UUID.randomUUID();
    final UUID validUserID1 = UUID.randomUUID();

    Media mockMedia = new Media(validMediaID1, "image/jpeg", "Mock Media", "Mock", validAdventureID1, validUserID1);
    MediaInfo mockMediaInfo = new MediaInfo(validMediaID1, "image/jpeg", "Mock Media", "Mock", validAdventureID1, validUserID1);
    File mockFile = new File(validFileID1, "application/pdf", "Mock File", "Mock", validAdventureID1, validUserID1);
    FileInfo mockFileInfo = new FileInfo(validFileID1, "application/pdf", "Mock File", "Mock", validAdventureID1, validUserID1);

    @Test
    @Description("Ensure a user can upload media")
    void uploadMedia_ReturnHttpStatus(){
        Assertions.assertEquals(HttpStatus.OK, sut.uploadMedia(new MockMultipartFile("Mock",new byte[1]) ,UUID.randomUUID(),UUID.randomUUID()));
    }

    @Test
    @Description("Ensure a user can upload a file")
    void uploadFile_ReturnHttpStatus(){
        Assertions.assertEquals(HttpStatus.OK, sut.uploadFile(new MockMultipartFile("Mock",new byte[1]) ,UUID.randomUUID(),UUID.randomUUID()));
    }

    @Test
    @Description("Ensure media is uploaded")
    void testMediaUploaded_ReturnResponseEntity(){
        Mockito.when(mockMediaRepository.findMediaById(validMediaID1)).thenReturn(mockMedia);
        ResponseEntity<byte[]> responseEntity = sut.testMediaUploaded(validMediaID1);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Description("Ensure file is uploaded")
    void testFileUploaded_ReturnResponseEntity(){
        Mockito.when(mockFileRepository.findFileById(validFileID1)).thenReturn(mockFile);
        ResponseEntity<byte[]> responseEntity = sut.testFileUploaded(validFileID1);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @Description("Ensure user can delete media")
    void deleteMediaValid() throws Exception {
        Mockito.when(mockMediaRepository.findMediaById(validMediaID1)).thenReturn(mockMedia);
        Mockito.when(mockMediaInfoRepository.findMediaById(validMediaID1)).thenReturn(mockMediaInfo);
        sut.deleteMedia(validMediaID1,validUserID1);
    }

    @Test
    @Description("Ensure only owner can delete media")
    void deleteMediaInvalid_ThrowsException() throws Exception {
        Mockito.when(mockMediaRepository.findMediaById(validMediaID1)).thenReturn(mockMedia);
        Mockito.when(mockMediaInfoRepository.findMediaById(validMediaID1)).thenReturn(mockMediaInfo);
        Assertions.assertThrows(Exception.class, ()->{
            sut.deleteMedia(validMediaID1,UUID.randomUUID());
        });
    }

    @Test
    @Description("Ensure user can delete a file")
    void deleteFileValid() throws Exception {
        Mockito.when(mockFileRepository.findFileById(validFileID1)).thenReturn(mockFile);
        Mockito.when(mockFileInfoRepository.findFileInfoById(validFileID1)).thenReturn(mockFileInfo);
        sut.deleteFile(validFileID1,validUserID1);
    }

    @Test
    @Description("Ensure only owner can delete a file")
    void deleteFileInvalid_ThrowsException() throws Exception {
        Mockito.when(mockFileRepository.findFileById(validFileID1)).thenReturn(mockFile);
        Mockito.when(mockFileInfoRepository.findFileInfoById(validFileID1)).thenReturn(mockFileInfo);
        Assertions.assertThrows(Exception.class, ()->{
            sut.deleteFile(validFileID1,UUID.randomUUID());
        });
    }
}
