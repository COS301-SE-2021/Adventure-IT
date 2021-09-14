package com.adventureit.mediaservice.service;

import com.adventureit.mediaservice.entity.*;
import com.adventureit.mediaservice.exceptions.NotFoundException;
import com.adventureit.mediaservice.exceptions.UnauthorisedException;
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
import java.util.UUID;

@Service
public class MediaServiceImplementation implements MediaService{
    @Autowired
    private  MediaInfoRepository mediaInfoRepository;
    @Autowired
    private  DocumentInfoRepository documentInfoRepository;
    @Autowired
    private  FileInfoRepository fileInfoRepository;

    private StorageOptions storageOptions;
    private String bucketName;

    @PostConstruct
    private void initializeFirebase() throws IOException {
        bucketName = "adventure-it-bc0b6.appspot.com";
        String projectId = "Adventure-IT";
        FileInputStream serviceAccount = new FileInputStream("D:\\UP\\Coding\\adventure-it-bc0b6-firebase-adminsdk-o2fq8-ad3a51fb5e.json");
        this.storageOptions = StorageOptions.newBuilder().setProjectId(projectId).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
    }

    @Override
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

    @Override
    public ResponseEntity<byte[]> testFileUploaded(UUID file) throws IOException {
        FileInfo info = fileInfoRepository.findFileInfoById(file);
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

    @Override
    public ResponseEntity<byte[]> testDocumentUploaded(UUID file) throws IOException {
        DocumentInfo info = documentInfoRepository.findDocumentInfoById(file);
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

    @Override
    public HttpStatus uploadMedia(MultipartFile file, UUID userId, UUID adventureId) {
        try {
            UUID id = UUID.randomUUID();
            String fileName = file.getOriginalFilename();

            MediaInfo uploadedMedia = new MediaInfo(id, file.getContentType(), fileName, "DESCRIPTION", adventureId , userId);
            mediaInfoRepository.save(uploadedMedia);

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

    @Override
    public HttpStatus uploadFile(MultipartFile file, UUID userId, UUID adventureId) {
        try {
            UUID id = UUID.randomUUID();
            String fileName = file.getOriginalFilename();

            FileInfo uploadedFile = new FileInfo(id, file.getContentType(), fileName, "DESCRIPTION", adventureId , userId);
            fileInfoRepository.save(uploadedFile);

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

    @Override
    public HttpStatus uploadDocument(MultipartFile file, UUID userId) {
        try {
            UUID id = UUID.randomUUID();
            String fileName = file.getOriginalFilename();

            DocumentInfo uploadedDoc = new DocumentInfo(id, file.getContentType(), fileName, "DESCRIPTION" , userId);
            documentInfoRepository.save(uploadedDoc);

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

    @Override
    public void deleteMedia(UUID id, UUID userID){
        MediaInfo mediaInfo = mediaInfoRepository.findMediaById(id);
        Storage storage = storageOptions.getService();

        if(mediaInfo == null){
            throw new NotFoundException("Delete Media: Media does not exist");
        }

        if(!mediaInfo.getOwner().equals(userID)){
            throw new UnauthorisedException("Delete Media: User not Authorised");
        }

        storage.get(BlobId.of(bucketName, id.toString())).delete();
        mediaInfoRepository.delete(mediaInfo);
    }

    @Override
    public void deleteFile(UUID id, UUID userID){
        FileInfo fileInfo = fileInfoRepository.findFileInfoById(id);
        Storage storage = storageOptions.getService();

        if(fileInfo == null){
            throw new NotFoundException("Delete File: Media does not exist");
        }

        if(!fileInfo.getOwner().equals(userID)){
            throw new UnauthorisedException("Delete File: User not Authorised");
        }

        storage.get(BlobId.of(bucketName, id.toString())).delete();
        fileInfoRepository.delete(fileInfo);
    }

    @Override
    public void deleteDocument(UUID id, UUID userID){
        DocumentInfo documentInfo = documentInfoRepository.findDocumentInfoById(id);
        Storage storage = storageOptions.getService();

        if(documentInfo == null){
            throw new NotFoundException("Delete Document: Media does not exist");
        }

        if(!documentInfo.getOwner().equals(userID)){
            throw new UnauthorisedException("Delete Document: User not Authorised");
        }

        storage.get(BlobId.of(bucketName, id.toString())).delete();
        documentInfoRepository.delete(documentInfo);
    }
}
