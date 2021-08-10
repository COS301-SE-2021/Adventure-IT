package com.adventureit.mediaservice.Controller;

import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Repository.MediaRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaRepository mediaRepository;

    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/test/uploaded/{file}",
            produces = { "application/pdf" })
    public ResponseEntity<byte[]> testUploaded(@PathVariable String file){
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Fetching file " + file); // debug
        Media storedMedia = mediaRepository.findMediaById(UUID.fromString("a8d9ce1d-65ee-4eb2-8003-4ce996b89c8d"));
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(storedMedia.getData(), headers, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping(value = "/test/upload")
    public HttpStatus testUploaded(@RequestParam("file") MultipartFile file){
        try {
            final byte[] content = file.getBytes();
            Media uploadedMedia = new Media(UUID.fromString("a8d9ce1d-65ee-4eb2-8003-4ce996b89c8d"), "TEST TYPE", "TEST NAME", "TEST DESCRIPTION", UUID.fromString("a8d9ce1d-65ee-4eb2-8003-4ce996b89c8d"), UUID.fromString("a8d9ce1d-65ee-4eb2-8003-4ce996b89c8d"));
            uploadedMedia.setData(content);
            mediaRepository.save(uploadedMedia);
            return HttpStatus.OK;
        }
        catch(Exception e){
            e.printStackTrace();
            return HttpStatus.NO_CONTENT;
        }

    }
}
