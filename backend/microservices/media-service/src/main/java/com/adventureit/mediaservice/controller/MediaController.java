package com.adventureit.mediaservice.controller;

import com.adventureit.mediaservice.entity.DocumentInfo;
import com.adventureit.mediaservice.entity.FileInfo;
import com.adventureit.mediaservice.entity.MediaInfo;
import com.adventureit.mediaservice.repository.*;
import com.adventureit.mediaservice.service.MediaServiceImplementation;
import org.json.JSONObject;
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

    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/mediaUploaded/{file}")
    public ResponseEntity<byte[]> testMediaUploaded(@PathVariable UUID file) throws IOException {
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

    @PostMapping("/uploadMedia")
    public HttpStatus uploadMedia(@RequestPart MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        return mediaServiceImplementation.uploadMedia(file,userId,adventureId);
    }

    @PostMapping("/uploadMediaTest")
    public void uploadMediaTest(@RequestBody Object req){
        System.out.println(req.toString());
    }
    
    @PostMapping("/printJson")
    public String printJson(@RequestBody JSONObject jsonObject){
        return jsonObject.toString();
    }


    @GetMapping("/deleteMedia/{id}/{userID}")
    public void deleteMedia(@PathVariable UUID id,@PathVariable UUID userID){
        mediaServiceImplementation.deleteMedia(id,userID);
    }
}
