package com.adventureit.mediaservice.Controller;

import com.adventureit.mediaservice.Entity.File;
import com.adventureit.mediaservice.Entity.FileInfo;
import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Entity.MediaInfo;
import com.adventureit.mediaservice.Repository.FileInfoRepository;
import com.adventureit.mediaservice.Repository.FileRepository;
import com.adventureit.mediaservice.Repository.MediaInfoRepository;
import com.adventureit.mediaservice.Repository.MediaRepository;
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

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @GetMapping("/test")
    public String test(){
        return "Media Controller is functional";
    }

    @GetMapping(value = "/mediaUploaded/{file}")
    public ResponseEntity<byte[]> testMediaUploaded(@PathVariable UUID file){
        HttpHeaders headers = new HttpHeaders();
        Media storedMedia = mediaRepository.findMediaById(file);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        headers.setContentType(MediaType.parseMediaType(storedMedia.getType()));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(storedMedia.getData(), headers, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(value = "/fileUploaded/{file}")
    public ResponseEntity<byte[]> testFileUploaded(@PathVariable UUID file){
        HttpHeaders headers = new HttpHeaders();
        File storedFile = fileRepository.findFileById(file);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        headers.setContentType(MediaType.parseMediaType(storedFile.getType()));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(storedFile.getData(), headers, HttpStatus.OK);
        return responseEntity;
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
    public HttpStatus uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        try {
            final byte[] content = file.getBytes();
            Media uploadedMedia = new Media(UUID.randomUUID(), file.getContentType(), file.getName(), "DESCRIPTION", adventureId , userId);
            MediaInfo uploadedMediaInfo = new MediaInfo(uploadedMedia.getId(), uploadedMedia.getType(), uploadedMedia.getName(), uploadedMedia.getDescription(), uploadedMedia.getAdventureID(), uploadedMedia.getOwner(),uploadedMedia.getPublicAccess());
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

    @PostMapping("/uploadFile")
    public HttpStatus uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userid") UUID userId, @RequestParam("adventureid") UUID adventureId){
        try {
            final byte[] content = file.getBytes();
            File uploadedFile = new File(UUID.randomUUID(), file.getContentType(), file.getName(), "DESCRIPTION", adventureId , userId);
            FileInfo uploadedFileInfo = new FileInfo(uploadedFile.getId(), uploadedFile.getType(), uploadedFile.getName(), uploadedFile.getDescription(), uploadedFile.getAdventureID(), uploadedFile.getOwner(),uploadedFile.getPublicAccess());
            uploadedFile.setData(content);
            fileRepository.save(uploadedFile);
            fileInfoRepository.save(uploadedFileInfo);
            return HttpStatus.OK;
        }
        catch(Exception e){
            e.printStackTrace();
            return HttpStatus.NO_CONTENT;
        }
    }

    @GetMapping("/changeMediaAccess/{id}")
    public void changeMediaAccess(@PathVariable UUID id) {
        Media media = mediaRepository.findMediaById(id);
        MediaInfo mediaInfo = mediaInfoRepository.findMediaById(id);
        media.setPublicAccess(!media.getPublicAccess());
        mediaInfo.setPublicAccess(!mediaInfo.getPublicAccess());
        mediaRepository.save(media);
        mediaInfoRepository.save(mediaInfo);
    }

    @GetMapping("/changeFileAccess/{id}")
    public void changeFileAccess(@PathVariable UUID id) {
        File file = fileRepository.findFileById(id);
        FileInfo fileInfo = fileInfoRepository.findFileInfoById(id);
        file.setPublicAccess(!file.getPublicAccess());
        fileInfo.setPublicAccess(!fileInfo.getPublicAccess());
        fileRepository.save(file);
        fileInfoRepository.save(fileInfo);
    }
}
