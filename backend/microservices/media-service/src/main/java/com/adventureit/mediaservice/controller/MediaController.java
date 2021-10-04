package com.adventureit.mediaservice.controller;

import com.adventureit.mediaservice.entity.DocumentInfo;
import com.adventureit.mediaservice.entity.FileInfo;
import com.adventureit.mediaservice.entity.MediaInfo;
import com.adventureit.mediaservice.repository.*;
import com.adventureit.mediaservice.service.MediaServiceImplementation;
import com.adventureit.shareddtos.media.responses.DocumentInfoDTO;
import com.adventureit.shareddtos.media.responses.FileInfoDTO;
import com.adventureit.shareddtos.media.responses.MediaInfoDTO;
import com.adventureit.shareddtos.media.responses.MediaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    public MediaResponseDTO testMediaUploaded(@PathVariable UUID file) throws IOException {
        return mediaServiceImplementation.testMediaUploaded(file);
    }

    @GetMapping(value = "/fileUploaded/{file}")
    public MediaResponseDTO testFileUploaded(@PathVariable UUID file) throws IOException {
        return mediaServiceImplementation.testFileUploaded(file);
    }

    @GetMapping(value = "/documentUploaded/{file}")
    public MediaResponseDTO testDocumentUploaded(@PathVariable UUID file) throws IOException {
        return mediaServiceImplementation.testDocumentUploaded(file);
    }

    @GetMapping(value = "/getUserMediaList/{id}")
    public List<MediaInfoDTO> getUserMediaList(@PathVariable UUID id){
        List<MediaInfo> response = mediaInfoRepository.findAllByOwner(id);
        List<MediaInfoDTO> returnList = new ArrayList<>();
        for(int i = 0;i<response.size();i++){
            returnList.add(new MediaInfoDTO(response.get(i).getId(),response.get(i).getType(),response.get(i).getName(),response.get(i).getDescription(),response.get(i).getAdventureID(),response.get(i).getOwner()));
        }
        return returnList;
    }

    @GetMapping(value = "/getUserFileList/{id}")
    public List<FileInfoDTO> getUserFileList(@PathVariable UUID id){
        List<FileInfo> response = fileInfoRepository.findAllByOwner(id);
        List<FileInfoDTO> returnList = new ArrayList<>();
        for(int i = 0;i<response.size();i++){
            returnList.add(new FileInfoDTO(response.get(i).getId(),response.get(i).getType(),response.get(i).getName(),response.get(i).getDescription(),response.get(i).getAdventureID(),response.get(i).getOwner()));
        }
        return returnList;
    }

    @GetMapping(value = "/getUserDocumentList/{id}")
    public List<DocumentInfoDTO> getUserDocumentList(@PathVariable UUID id){
        List<DocumentInfo> response =  documentInfoRepository.findAllByOwner(id);
        List<DocumentInfoDTO> returnList = new ArrayList<>();
        for(int i = 0;i<response.size();i++){
            returnList.add(new DocumentInfoDTO(response.get(i).getId(),response.get(i).getType(),response.get(i).getName(),response.get(i).getDescription(),response.get(i).getOwner()));
        }
        return returnList;
    }

    @GetMapping(value = "/getAdventureMediaList/{id}")
    public List<MediaInfoDTO> getAdventureMediaList(@PathVariable UUID id){
        List<MediaInfo> response = mediaInfoRepository.findAllByAdventureID(id);
        List<MediaInfoDTO> returnList = new ArrayList<>();
        for(int i = 0;i<response.size();i++){
            returnList.add(new MediaInfoDTO(response.get(i).getId(),response.get(i).getType(),response.get(i).getName(),response.get(i).getDescription(),response.get(i).getAdventureID(),response.get(i).getOwner()));
        }
        return returnList;
    }

    @GetMapping(value = "/getAdventureFileList/{id}")
    public List<FileInfoDTO> getAdventureFileList(@PathVariable UUID id){
        List<FileInfo> response = fileInfoRepository.findAllByAdventureID(id);
        List<FileInfoDTO> returnList = new ArrayList<>();
        for(int i = 0;i<response.size();i++){
            returnList.add(new FileInfoDTO(response.get(i).getId(),response.get(i).getType(),response.get(i).getName(),response.get(i).getDescription(),response.get(i).getAdventureID(),response.get(i).getOwner()));
        }
        return returnList;
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

    @GetMapping("/getMediaSize/{id}")
    public long getMediaSize(@PathVariable UUID id) throws IOException {
        return mediaServiceImplementation.getMediaSize(id);
    }

    @GetMapping("/getFileSize/{id}")
    public long getFileSize(@PathVariable UUID id) throws IOException {
        return mediaServiceImplementation.getFileSize(id);
    }

    @GetMapping("/getDocumentSize/{id}")
    public long getDocumentSize(@PathVariable UUID id) throws IOException {
        return mediaServiceImplementation.getDocumentSize(id);
    }

    @GetMapping("/deleteAllByAdventure/{id}")
    public void deleteAllByAdventure(@PathVariable UUID id){
        mediaServiceImplementation.deleteAllByAdventure(id);
    }
}
