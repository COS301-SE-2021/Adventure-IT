package com.adventureit.mediaservice.Controller;

import com.adventureit.mediaservice.Entity.FileInfo;
import com.adventureit.mediaservice.Entity.MediaInfo;
import com.adventureit.mediaservice.Repository.FileInfoRepository;
import com.adventureit.mediaservice.Repository.FileRepository;
import com.adventureit.mediaservice.Repository.MediaInfoRepository;
import com.adventureit.mediaservice.Repository.MediaRepository;
import com.adventureit.mediaservice.Service.MediaServiceImplementation;
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
        return fileInfoRepository.findAllByOwnerAndPublicAccessEquals(id,true);
    }

    @GetMapping(value = "/getUserPrivateFileList/{id}")
    public List<FileInfo> getUserPrivateFileList(@PathVariable UUID id){
        return fileInfoRepository.findAllByOwnerAndPublicAccessEquals(id,false);
    }

    @GetMapping(value = "/getAdventureFileList/{id}")
    public List<FileInfo> getAdventureFileList(@PathVariable UUID id){
        return fileInfoRepository.findAllByAdventureID(id);
    }

    @PostMapping("/uploadMedia")
    public HttpStatus uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        return mediaServiceImplementation.uploadMedia(file,userId,adventureId);
    }

    @PostMapping("/uploadFile")
    public HttpStatus uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        return mediaServiceImplementation.uploadFile(file,userId,adventureId);
    }

    @GetMapping("/changeMediaAccess/{id}")
    public void changeMediaAccess(@PathVariable UUID id) {
        mediaServiceImplementation.changeMediaAccess(id);
    }

    @GetMapping("/changeFileAccess/{id}")
    public void changeFileAccess(@PathVariable UUID id) {
        mediaServiceImplementation.changeFileAccess(id);
    }
}
