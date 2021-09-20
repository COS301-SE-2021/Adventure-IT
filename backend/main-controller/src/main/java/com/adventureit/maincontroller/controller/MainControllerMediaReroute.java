package com.adventureit.maincontroller.controller;


import com.adventureit.maincontroller.exceptions.StorageException;
import com.adventureit.shareddtos.media.responses.DocumentInfoDTO;
import com.adventureit.shareddtos.media.responses.FileInfoDTO;
import com.adventureit.shareddtos.media.responses.MediaInfoDTO;
import com.adventureit.shareddtos.media.responses.MediaResponseDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/media")
public class MainControllerMediaReroute {

    private RestTemplate restTemplate = new RestTemplate();

    private static final String INTERNET_PORT = "http://localhost";
    private static final String MEDIA_PORT = "9005";
    private static final String USER_PORT = "9002";
    private static final String STORAGE_EXCEEDED = "Upload Media: User has exceeded storage available";
    private static final String SET_STORAGE = "/user/setStorageUsed/";
    private static final String USERID = "userid";
    private static final String GET_STORAGE = "/user/getStorageUsed/";
    private static final Logger logger = Logger.getLogger( MainControllerMediaReroute.class.getName() );

    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/mediaUploaded/{file}")
    public ResponseEntity<byte[]> testMediaUploaded(@PathVariable UUID file) {
        MediaResponseDTO responseDTO = restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/mediaUploaded/" +file, MediaResponseDTO.class);
        assert responseDTO != null;
        return new ResponseEntity<>(responseDTO.getContent(), responseDTO.getHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/fileUploaded/{file}")
    public ResponseEntity<byte[]> testFileUploaded(@PathVariable UUID file) {
        MediaResponseDTO responseDTO = restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/fileUploaded/"+file, MediaResponseDTO.class);
        assert responseDTO != null;
        return new ResponseEntity<>(responseDTO.getContent(), responseDTO.getHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/documentUploaded/{file}")
    public ResponseEntity<byte[]> testDocumentUploaded(@PathVariable UUID file) {
        MediaResponseDTO responseDTO = restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/documentUploaded/"+file, MediaResponseDTO.class);
        assert responseDTO != null;
        return new ResponseEntity<>(responseDTO.getContent(), responseDTO.getHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserMediaList/{id}")
    public List<MediaInfoDTO> getUserMediaList(@PathVariable UUID id){
        return restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getUserMediaList/"+id, List.class);
    }

    @GetMapping(value = "/getUserFileList/{id}")
    public List<FileInfoDTO> getUserFileList(@PathVariable UUID id){
        return restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getUserFileList/"+id, List.class);
    }

    @GetMapping(value = "/getUserDocumentList/{id}")
    public List<DocumentInfoDTO> getUserDocumentList(@PathVariable UUID id){
        return restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getUserDocumentList/"+id, List.class);
    }

    @GetMapping(value = "/getAdventureMediaList/{id}")
    public List<MediaInfoDTO> getAdventureMediaList(@PathVariable UUID id){
        return restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getAdventureMediaList/"+id, List.class);
    }

    @GetMapping(value = "/getAdventureFileList/{id}")
    public List<FileInfoDTO> getAdventureFileList(@PathVariable UUID id){
        return restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getAdventureFileList/"+id, List.class);
    }

    @PostMapping("/uploadMedia")
    public HttpStatus uploadMedia(@RequestPart MultipartFile file, @RequestParam(USERID) UUID userId, @RequestParam("adventureid") UUID adventureId) throws IOException {
        long storageUsed = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_STORAGE + userId, long.class);
        long limit = 5000000000L;
        if((storageUsed + file.getSize()) > limit){
            throw new StorageException(STORAGE_EXCEEDED);
        }
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + SET_STORAGE + userId + "/" + (file.getSize() + storageUsed), String.class);

        File convFile = convertFile(file);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(convFile));
        bodyMap.add(USERID, userId.toString());
        bodyMap.add("adventureid", adventureId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        restTemplate = new RestTemplate();

        HttpStatus status = restTemplate.postForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/uploadMedia/",requestEntity, HttpStatus.class);

        Files.delete(Path.of(convFile.getPath()));
        return status;
    }

    @PostMapping("/uploadFile")
    public HttpStatus uploadFile(@RequestPart MultipartFile file, @RequestParam(USERID) UUID userId, @RequestParam("adventureid") UUID adventureId) throws IOException {
        long storageUsed = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_STORAGE + userId, long.class);
        long limit = 5000000000L;
        if((storageUsed + file.getSize()) > limit){
            throw new StorageException(STORAGE_EXCEEDED);
        }
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + SET_STORAGE + userId + "/" + (file.getSize() + storageUsed), String.class);

        File convFile = convertFile(file);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(convFile));
        bodyMap.add(USERID, userId.toString());
        bodyMap.add("adventureid", adventureId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        restTemplate = new RestTemplate();

        HttpStatus status = restTemplate.postForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/uploadFile/",requestEntity, HttpStatus.class);
        Files.delete(Path.of(convFile.getPath()));
        return status;
    }

    @PostMapping("/uploadDocument")
    public HttpStatus uploadDocument(@RequestPart MultipartFile file, @RequestParam(USERID) UUID userId) throws IOException {
        long storageUsed = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_STORAGE + userId, long.class);
        long limit = 5000000000L;
        if((storageUsed + file.getSize()) > limit){
            throw new StorageException(STORAGE_EXCEEDED);
        }
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + SET_STORAGE + userId + "/" + (file.getSize() + storageUsed), String.class);

        File convFile = convertFile(file);
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(convFile));
        bodyMap.add(USERID, userId.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        restTemplate = new RestTemplate();

        HttpStatus status = restTemplate.postForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/uploadDocument/",requestEntity,HttpStatus.class);
        Files.delete(Path.of(convFile.getPath()));
        return status;
    }

    @GetMapping("/deleteMedia/{id}/{userID}")
    public void deleteMedia(@PathVariable UUID id,@PathVariable UUID userID){
        long storageUsed = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_STORAGE + userID, long.class);
        long mediaSize = restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getMediaSize/" + id, long.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/deleteMedia/"+id+"/"+userID,String.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + SET_STORAGE + userID + "/" + (storageUsed - mediaSize), String.class);
    }

    @GetMapping("/deleteFile/{id}/{userID}")
    public void deleteFile(@PathVariable UUID id,@PathVariable UUID userID){
        long storageUsed = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_STORAGE + userID, long.class);
        long mediaSize = restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getFileSize/" + id, long.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/deleteFile/"+id+"/"+userID,String.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + SET_STORAGE + userID + "/" + (storageUsed - mediaSize), String.class);
    }

    @GetMapping("/deleteDocument/{id}/{userID}")
    public void deleteDocument(@PathVariable UUID id,@PathVariable UUID userID){
        long storageUsed = restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + GET_STORAGE + userID, long.class);
        long mediaSize = restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/getDocumentSize/" + id, long.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + MEDIA_PORT + "/media/deleteDocument/"+id+"/"+userID,String.class);
        restTemplate.getForObject(INTERNET_PORT + ":" + USER_PORT + SET_STORAGE + userID + "/" + (storageUsed - mediaSize), String.class);
    }

    public File convertFile(MultipartFile file){
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            Boolean bool = convFile.createNewFile();
            if (bool.equals(true)) {
                logger.log(Level.WARNING, "File successfully created");
            }
            fos.write(file.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return convFile;
    }
}
