package com.adventureit.mediaservice.Controller;

import com.adventureit.mediaservice.Requests.AddFileRequest;
import com.adventureit.mediaservice.Requests.AddMediaRequest;
import com.adventureit.mediaservice.Service.MediaServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    MediaServiceImplementation mediaServiceImplementation;

    @GetMapping("/test")
    String test(){
        return "Media Controller is functional";
    }

    @PostMapping("/addMedia")
    String addMedia(@RequestBody AddMediaRequest request) throws Exception {
        return mediaServiceImplementation.addMedia(request.getType(),request.getName(),request.getDescription(),request.getAdventureID(),request.getOwner(),request.getFile());
    }

    @PostMapping("/addFile")
    String addFile(@RequestBody AddFileRequest request) throws Exception {
        return mediaServiceImplementation.addFile(request.getType(),request.getName(),request.getDescription(),request.getAdventureID(),request.getOwner(),request.getFile());
    }

}
