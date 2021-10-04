package com.adventureit.mediaservice.service;

import com.adventureit.mediaservice.entity.*;
import com.adventureit.mediaservice.exceptions.NotFoundException;
import com.adventureit.mediaservice.exceptions.UnauthorisedException;
import com.adventureit.mediaservice.repository.*;
import com.adventureit.shareddtos.media.responses.MediaResponseDTO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MediaServiceImplementation implements MediaService{
    @Autowired
    private  MediaInfoRepository mediaInfoRepository;
    @Autowired
    private  DocumentInfoRepository documentInfoRepository;
    @Autowired
    private FileInfoRepository fileInfoRepository;

    private static final String DESCRIPTION = "DESCRIPTION";

    @Value("${firebase-type}")
    String type;
    @Value("${firebase-project_id}")
    String projectId;
    @Value("${firebase-private_key_id}")
    String privateKeyId;
    @Value("${firebase-private_key}")
    String privateKey;
    @Value("${firebase-client_email}")
    String clientEmail;
    @Value("${firebase-client_id}")
    String clientId;
    @Value("${firebase-auth_uri}")
    String authUri;
    @Value("${firebase-token_uri}")
    String tokenUri;
    @Value("${firebase-auth_provider_x509_cert_url}")
    String authProvider;
    @Value("${firebase-client_x509_cert_url}")
    String clientx509;

    private StorageOptions storageOptions;
    private String bucketName;
    private static final Logger logger = Logger.getLogger( MediaServiceImplementation.class.getName() );
    private static final String CREATED_CONST = "File created successfully";
    private static final String MEDIA_CONST = "media.json";
    private static final String OUTPUT_CONST = "output";

    @PostConstruct
    private void initializeFirebase() throws IOException {
        bucketName = "adventure-it-bc0b6.appspot.com";
        String projectId1 = "Adventure-IT";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type",type);
        jsonObject.put("project_id",projectId);
        jsonObject.put("private_key_id",privateKeyId);
        jsonObject.put("private_key",privateKey);
        jsonObject.put("client_email",clientEmail);
        jsonObject.put("client_id",clientId);
        jsonObject.put("auth_uri",authUri);
        jsonObject.put("token_uri",tokenUri);
        jsonObject.put("auth_provider_x509_cert_url",authProvider);
        jsonObject.put("client_x509_cert_url",clientx509);
        try (FileWriter file = new FileWriter(MEDIA_CONST)) {
            file.write(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        FileInputStream serviceAccount = new FileInputStream(MEDIA_CONST);
        this.storageOptions = StorageOptions.newBuilder().setProjectId(projectId1).setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        boolean flag = new File(MEDIA_CONST).delete();
        if(!flag){
            throw new NotFoundException("Initialize Firebase: File not found");
        }
    }

    @Override
    public MediaResponseDTO testMediaUploaded(UUID file) throws IOException {
        MediaInfo info = mediaInfoRepository.findMediaById(file);
        if(info == null){
            throw new NotFoundException("Test Uploaded Media: Media does not exist");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.parseMediaType(info.getType()));

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, file.toString()));
        ReadChannel reader = blob.reader();
        byte[] content = null;
        try (InputStream inputStream = Channels.newInputStream(reader)) {
            content = inputStream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MediaResponseDTO(content,headers);
    }

    @Override
    public MediaResponseDTO testFileUploaded(UUID file) throws IOException {
        FileInfo info = fileInfoRepository.findFileInfoById(file);
        if(info == null){
            throw new NotFoundException("Test Uploaded File: File does not exist");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.parseMediaType(info.getType()));

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, file.toString()));
        ReadChannel reader = blob.reader();
        byte[] content = null;
        try (InputStream inputStream = Channels.newInputStream(reader)) {
            content = inputStream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new MediaResponseDTO(content,headers);
    }

    @Override
    public MediaResponseDTO testDocumentUploaded(UUID file) throws IOException {
        DocumentInfo info = documentInfoRepository.findDocumentInfoById(file);
        if(info == null){
            throw new NotFoundException("Test Uploaded Document: Document does not exist");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(MediaType.parseMediaType(info.getType()));

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, file.toString()));
        ReadChannel reader = blob.reader();
        byte[] content = null;
        try (InputStream inputStream = Channels.newInputStream(reader)) {
            content = inputStream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MediaResponseDTO(content,headers);
    }

    @Override
    public HttpStatus uploadMedia(MultipartFile file, UUID userId, UUID adventureId) {
        try {
            UUID id = UUID.randomUUID();
            String fileName = file.getOriginalFilename();

            MediaInfo uploadedMedia = new MediaInfo(id, file.getContentType(), fileName, DESCRIPTION, adventureId , userId);
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

            FileInfo uploadedFile = new FileInfo(id, file.getContentType(), fileName, DESCRIPTION, adventureId , userId);
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

            DocumentInfo uploadedDoc = new DocumentInfo(id, file.getContentType(), fileName, DESCRIPTION, userId);
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

    @Override
    public long getMediaSize(UUID id) throws IOException {
        MediaInfo mediaInfo = mediaInfoRepository.findMediaById(id);
        if(mediaInfo == null){
            throw new NotFoundException("Get Media Size: Media does not exist");
        }

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, id.toString()));
        ReadChannel reader = blob.reader();



        File convFile = new File(OUTPUT_CONST);
        try(InputStream inputStream = Channels.newInputStream(reader)){
            byte[] content = inputStream.readAllBytes();
            Boolean check =convFile.createNewFile();
            if(check.equals(true)){
                logger.log(Level.WARNING, CREATED_CONST);
            }

            FileOutputStream fos = new FileOutputStream(convFile);
            try{
                fos.write(content);
            }finally{
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long size = convFile.length();
        Files.delete(Path.of(convFile.getPath()));
        return size;
    }

    @Override
    public long getFileSize(UUID id) throws IOException {
        FileInfo fileInfo = fileInfoRepository.findFileInfoById(id);
        if(fileInfo == null){
            throw new NotFoundException("Get File Size: File does not exist");
        }

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, id.toString()));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);
        try {
            byte[] content = inputStream.readAllBytes();

            File convFile = new File(OUTPUT_CONST);
            try {
                Boolean check = convFile.createNewFile();
                if(check.equals(true)){
                    logger.log(Level.WARNING, CREATED_CONST);
                }
                FileOutputStream fos = new FileOutputStream(convFile);
                try {
                    fos.write(content);
                }finally{
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        long size = convFile.length();
            Files.delete(Path.of(convFile.getPath()));
        return size;
        }finally {
            inputStream.close();
        }
    }

    @Override
    public long getDocumentSize(UUID id) throws IOException {
        DocumentInfo documentInfo = documentInfoRepository.findDocumentInfoById(id);
        if(documentInfo == null){
            throw new NotFoundException("Get Document Size: Document does not exist");
        }

        Storage storage = storageOptions.getService();
        Blob blob = storage.get(BlobId.of(bucketName, id.toString()));
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);
        try {
            byte[] content = inputStream.readAllBytes();

            File convFile = new File(OUTPUT_CONST);
            try {
                Boolean check = convFile.createNewFile();
                if(check.equals(true)){
                    logger.log(Level.WARNING, CREATED_CONST);
                }
                FileOutputStream fos = new FileOutputStream(convFile);
                try {
                    fos.write(content);
                }finally {
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            long size = convFile.length();
            Files.delete(Path.of(convFile.getPath()));
            return size;
        }finally {
            inputStream.close();
        }
    }

    @Override
    public void deleteAllByAdventure(UUID id) {
        List<MediaInfo> mediaInfoList = mediaInfoRepository.findAllByAdventureID(id);
        if(mediaInfoList != null && !mediaInfoList.isEmpty()){
            for (MediaInfo mediaInfo:mediaInfoList) {
                deleteMedia(mediaInfo.getId(),mediaInfo.getOwner());
            }
        }

        List<FileInfo> fileInfoList = fileInfoRepository.findAllByAdventureID(id);
        if(fileInfoList != null && !fileInfoList.isEmpty()){
            for (FileInfo fileInfo:fileInfoList) {
                deleteFile(fileInfo.getId(),fileInfo.getOwner());
            }
        }
    }
}
