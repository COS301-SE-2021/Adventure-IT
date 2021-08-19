package com.adventureit.mediaservice.Controller;

import com.adventureit.mediaservice.Entity.DocumentInfo;
import com.adventureit.mediaservice.Entity.FileInfo;
import com.adventureit.mediaservice.Entity.MediaInfo;
import com.adventureit.mediaservice.Repository.*;
import com.adventureit.mediaservice.Service.MediaServiceImplementation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    MediaRepository mediaRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileInfoRepository fileInfoRepository;

    @Autowired
    DocumentInfoRepository documentInfoRepository;

    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/mediaUploaded/{file}")
    public ResponseEntity<byte[]> testMediaUploaded(@PathVariable UUID file){
        return mediaServiceImplementation.testMediaUploaded(file);
    }

    @GetMapping(value = "/fileUploaded/{file}")
    public ResponseEntity<byte[]> testFileUploaded(@PathVariable UUID file){
        return mediaServiceImplementation.testFileUploaded(file);
    }

    @GetMapping(value = "/getUserMediaList/{id}")
    public List<MediaInfo> getUserMediaList(@PathVariable UUID id){
        return mediaInfoRepository.findAllByOwner(id);
    }

    @GetMapping(value = "/getAdventureMediaList/{id}")
    public List<MediaInfo> getAdventureMediaList(@PathVariable UUID id){
        return mediaInfoRepository.findAllByAdventureID(id);
    }

    @GetMapping(value = "/getUserFileList/{id}")
    public List<FileInfo> getUserFileList(@PathVariable UUID id){
        return fileInfoRepository.findAllByOwner(id);
    }

    @GetMapping(value = "/getAdventureFileList/{id}")
    public List<FileInfo> getAdventureFileList(@PathVariable UUID id){
        return fileInfoRepository.findAllByAdventureID(id);
    }

    @PostMapping("/uploadMedia")
    public HttpStatus uploadMedia(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        return mediaServiceImplementation.uploadMedia(file,userId,adventureId);
    }

    @PostMapping("/uploadMediaTest")
    public void uploadMediaTest(@RequestBody Object req){
        System.out.println(req.toString());
    }

    @PostMapping("/uploadFile")
    public HttpStatus uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        return mediaServiceImplementation.uploadFile(file,userId,adventureId);
    }

    @PostMapping("/uploadDocument")
    public HttpStatus uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("userid") UUID userId){
        return mediaServiceImplementation.uploadDocument(file,userId);
    }
    
    @PostMapping("/printJson")
    public String printJson(@RequestBody JSONObject jsonObject){
        return jsonObject.toString();
    }

    @GetMapping("/deleteFile/{id}/{userID}")
    public void deleteFile(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        mediaServiceImplementation.deleteFile(id,userID);
    }

    @GetMapping("/deleteMedia/{id}/{userID}")
    public void deleteMedia(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        mediaServiceImplementation.deleteMedia(id,userID);
    }

    @GetMapping("/deleteDocument/{id}/{userID}")
    public void deleteDocument(@PathVariable UUID id,@PathVariable UUID userID) throws Exception {
        mediaServiceImplementation.deleteDocument(id,userID);
    }

    @GetMapping(value = "/getUserDocumentList/{id}")
    public List<DocumentInfo> getUserDocumentList(@PathVariable UUID id){
        return documentInfoRepository.findAllByOwner(id);
    }
}
