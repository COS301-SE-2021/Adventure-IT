package com.adventureit.mediaservice.service;

import com.adventureit.mediaservice.entity.*;
import com.adventureit.mediaservice.repository.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
public class MediaServiceImplementation{
    @Autowired
    private  MediaInfoRepository mediaInfoRepository;

    private StorageOptions storageOptions;
    private String bucketName;
    private String projectId;

    @PostConstruct
    private void initializeFirebase() throws Exception {
        bucketName = "adventure-it-bc0b6.appspot.com";
        projectId = "Adventure-IT";
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 2\\COS301\\adventure-it-bc0b6-firebase-adminsdk-o2fq8-ad3a51fb5e.json");

        this.storageOptions = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

    }

    public ResponseEntity<byte[]> testMediaUploaded(UUID file) throws IOException {
        MediaInfo info = mediaInfoRepository.findMediaById(file);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.parseMediaType(info.getType()));

        Storage storage = storageOptions.getService();

        Blob blob = storage.get(BlobId.of(bucketName, file.toString()));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);

        byte[] content = inputStream.readAllBytes();

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> testFileUploaded(UUID file) {
//        HttpHeaders headers = new HttpHeaders();
//        Files storedFile = fileRepository.findFileById(file);
//        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
//        headers.setContentType(MediaType.parseMediaType(storedFile.getType()));
//        return new ResponseEntity<>(storedFile.getData(), headers, HttpStatus.OK);
        return null;
    }

    public HttpStatus uploadMedia(MultipartFile file, UUID userId, UUID adventureId) {
        try {
            UUID id = UUID.randomUUID();
            String fileName = file.getOriginalFilename();

            MediaInfo uploadedMedia = new MediaInfo(id, file.getContentType(), fileName, "DESCRIPTION", adventureId , userId);
            mediaInfoRepository.save(uploadedMedia);

            File newFile = convertMultiPartToFile(file);
            Path filePath = newFile.toPath();
            Storage storage = storageOptions.getService();
            BlobId blobId = BlobId.of(bucketName, id.toString());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, Files.readAllBytes(filePath));

            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NO_CONTENT;
        }
    }

    public void deleteMedia(UUID id, UUID userID){
//        Media media = mediaRepository.findMediaById(id);
//        MediaInfo mediaInfo = mediaInfoRepository.findMediaById(id);
//
//        if(media == null || mediaInfo == null){
//            throw new NotFoundException("Delete Media: Media does not exist");
//        }
//
//        if(!media.getOwner().equals(userID)){
//            throw new UnauthorisedException("Delete Media: User not Authorised");
//        }
//
//        mediaRepository.delete(media);
//        mediaInfoRepository.delete(mediaInfo);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

}
