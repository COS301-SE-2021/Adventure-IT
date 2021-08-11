package com.adventureit.mediaservice.Controller;

import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Entity.MediaInfo;
import com.adventureit.mediaservice.Repository.MediaInfoRepository;
import com.adventureit.mediaservice.Repository.MediaRepository;
import com.netflix.discovery.converters.Auto;
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
    private MediaRepository mediaRepository;

    @Autowired
    private MediaInfoRepository mediaInfoRepository;

    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/uploaded/{file}")
    public ResponseEntity<byte[]> testUploaded(@PathVariable UUID file){
        HttpHeaders headers = new HttpHeaders();
        Media storedMedia = mediaRepository.findMediaById(file);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        headers.setContentType(MediaType.parseMediaType(storedMedia.getType()));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(storedMedia.getData(), headers, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(value = "/getUserMediaList/{id}")
    public List<MediaInfo> getUserMediaList(@PathVariable UUID id){
        return mediaInfoRepository.findAllByOwner(id);
    }

    @GetMapping(value = "/getAdventureFileList/{id}")
    public List<MediaInfo> getAdventureFileList(@PathVariable UUID id){
        return mediaInfoRepository.findAllByAdventureID(id);
    }

    @PostMapping("/upload")
    public HttpStatus uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        try {
            final byte[] content = file.getBytes();
            Media uploadedMedia = new Media(UUID.randomUUID(), file.getContentType(), file.getName(), "DESCRIPTION", adventureId , userId);
            MediaInfo uploadedMediaInfo = new MediaInfo(uploadedMedia.getId(), uploadedMedia.getType(), uploadedMedia.getName(), uploadedMedia.getDescription(), uploadedMedia.getAdventureID(), uploadedMedia.getOwner());
            uploadedMedia.setData(content);
            mediaRepository.save(uploadedMedia);
            mediaInfoRepository.save(uploadedMediaInfo);
            return HttpStatus.OK;
        }
        catch(Exception e){
            e.printStackTrace();
            return HttpStatus.NO_CONTENT;
        }

    }
}
