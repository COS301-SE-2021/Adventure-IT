package com.adventureit.maincontroller.controller;


import com.adventureit.shareddtos.media.responses.DocumentInfoDTO;
import com.adventureit.shareddtos.media.responses.FileInfoDTO;
import com.adventureit.shareddtos.media.responses.MediaInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MainControllerMediaReroute {

    private final RestTemplate restTemplate = new RestTemplate();


    private final String IP = "http://localhost";
    private final String mediaPort = "9005";


    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/mediaUploaded/{file}")
    public ResponseEntity<byte[]> testMediaUploaded(@PathVariable UUID file) throws IOException {
        return restTemplate.getForObject(IP + ":" + mediaPort + "/mediaUploaded/"+file, ResponseEntity.class);
    }

    @GetMapping(value = "/fileUploaded/{file}")
    public ResponseEntity<byte[]> testFileUploaded(@PathVariable UUID file) throws IOException {
        return restTemplate.getForObject(IP + ":" + mediaPort + "/fileUploaded/"+file, ResponseEntity.class);
    }

    @GetMapping(value = "/documentUploaded/{file}")
    public ResponseEntity<byte[]> testDocumentUploaded(@PathVariable UUID file) throws IOException {
        return restTemplate.getForObject(IP + ":" + mediaPort + "/documentUploaded/"+file, ResponseEntity.class);
    }

    @GetMapping(value = "/getUserMediaList/{id}")
    public List<MediaInfoDTO> getUserMediaList(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + mediaPort + "/getUserMediaList/"+id, List.class);
    }

    @GetMapping(value = "/getUserFileList/{id}")
    public List<FileInfoDTO> getUserFileList(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + mediaPort + "/getUserFileList/"+id, List.class);
    }

    @GetMapping(value = "/getUserDocumentList/{id}")
    public List<DocumentInfoDTO> getUserDocumentList(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + mediaPort + "/getUserDocumentList/"+id, List.class);
    }

    @GetMapping(value = "/getAdventureMediaList/{id}")
    public List<MediaInfoDTO> getAdventureMediaList(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + mediaPort + "/getAdventureMediaList/"+id, List.class);
    }

    @GetMapping(value = "/getAdventureFileList/{id}")
    public List<FileInfoDTO> getAdventureFileList(@PathVariable UUID id){
        return restTemplate.getForObject(IP + ":" + mediaPort + "/getAdventureFileList/"+id, List.class);
    }

//    @PostMapping("/uploadMedia")
//    public HttpStatus uploadMedia(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
//        return restTemplate.postForObject(IP + ":" + mediaPort + "/uploadMedia/",, ResponseEntity.class);
//    }

//    @PostMapping("/uploadFile")
//    public HttpStatus uploadFile(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
//        return mediaServiceImplementation.uploadFile(file,userId,adventureId);
//    }
//
//    @PostMapping("/uploadDocument")
//    public HttpStatus uploadDocument(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId){
//        return restTemplate.postForObject(IP + ":" + mediaPort + "/uploadDocument/",file,userId,HttpStatus.class);
//    }

    @GetMapping("/deleteMedia/{id}/{userID}")
    public void deleteMedia(@PathVariable UUID id,@PathVariable UUID userID){
        restTemplate.getForObject(IP + ":" + mediaPort + "/deleteMedia/"+id+"/"+userID,String.class);
    }

    @GetMapping("/deleteFile/{id}/{userID}")
    public void deleteFile(@PathVariable UUID id,@PathVariable UUID userID){
        restTemplate.getForObject(IP + ":" + mediaPort + "/deleteFile/"+id+"/"+userID,String.class);
    }

    @GetMapping("/deleteDocument/{id}/{userID}")
    public void deleteDocument(@PathVariable UUID id,@PathVariable UUID userID){
        restTemplate.getForObject(IP + ":" + mediaPort + "/deleteDocument/"+id+"/"+userID,String.class);
    }

}
