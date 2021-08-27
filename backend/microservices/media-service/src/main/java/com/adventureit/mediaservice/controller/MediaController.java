package com.adventureit.mediaservice.controller;

import com.adventureit.mediaservice.entity.DocumentInfo;
import com.adventureit.mediaservice.entity.FileInfo;
import com.adventureit.mediaservice.entity.MediaInfo;
import com.adventureit.mediaservice.repository.*;
import com.adventureit.mediaservice.service.MediaServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaServiceImplementation mediaServiceImplementation;
    @Autowired
    MediaInfoRepository mediaInfoRepository;
    @Autowired
    DocumentInfoRepository documentInfoRepository;
    @Autowired
    FileInfoRepository fileInfoRepository;

    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/mediaUploaded/{file}")
    public ResponseEntity<byte[]> testMediaUploaded(@PathVariable UUID file) throws IOException {
        return mediaServiceImplementation.testMediaUploaded(file);
    }

    @GetMapping(value = "/fileUploaded/{file}")
    public ResponseEntity<byte[]> testFileUploaded(@PathVariable UUID file) throws IOException {
        return mediaServiceImplementation.testFileUploaded(file);
    }

    @GetMapping(value = "/documentUploaded/{file}")
    public ResponseEntity<byte[]> testDocumentUploaded(@PathVariable UUID file) throws IOException {
        return mediaServiceImplementation.testDocumentUploaded(file);
    }

    @GetMapping(value = "/getUserMediaList/{id}")
    public List<MediaInfo> getUserMediaList(@PathVariable UUID id){
        return mediaInfoRepository.findAllByOwner(id);
    }

    @GetMapping(value = "/getUserFileList/{id}")
    public List<FileInfo> getUserFileList(@PathVariable UUID id){
        return fileInfoRepository.findAllByOwner(id);
    }

    @GetMapping(value = "/getUserFileList/{id}")
    public List<DocumentInfo> getUserDocumentList(@PathVariable UUID id){
        return documentInfoRepository.findAllByOwner(id);
    }

    @GetMapping(value = "/getAdventureMediaList/{id}")
    public List<MediaInfo> getAdventureMediaList(@PathVariable UUID id){
        return mediaInfoRepository.findAllByAdventureID(id);
    }

    @GetMapping(value = "/getAdventureFileList/{id}")
    public List<FileInfo> getAdventureFileList(@PathVariable UUID id){
        return fileInfoRepository.findAllByAdventureID(id);
    }

    @PostMapping("/uploadMedia")
    public HttpStatus uploadMedia(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        return mediaServiceImplementation.uploadMedia(file,userId,adventureId);
    }

    @PostMapping("/uploadFile")
    public HttpStatus uploadFile(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        return mediaServiceImplementation.uploadFile(file,userId,adventureId);
    }

    @PostMapping("/uploadDocument")
    public HttpStatus uploadDocument(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId){
        return mediaServiceImplementation.uploadDocument(file,userId);
    }

    @GetMapping("/deleteMedia/{id}/{userID}")
    public void deleteMedia(@PathVariable UUID id,@PathVariable UUID userID){
        mediaServiceImplementation.deleteMedia(id,userID);
    }

    @GetMapping("/deleteFile/{id}/{userID}")
    public void deleteFile(@PathVariable UUID id,@PathVariable UUID userID){
        mediaServiceImplementation.deleteFile(id,userID);
    }

    @GetMapping("/deleteDocument/{id}/{userID}")
    public void deleteDocument(@PathVariable UUID id,@PathVariable UUID userID){
        mediaServiceImplementation.deleteDocument(id,userID);
    }
}
