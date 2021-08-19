package com.adventureit.mediaservice.Service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MediaServiceImplementation implements MediaService{
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaInfoRepository mediaInfoRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    public MediaServiceImplementation(MediaRepository mediaRepository,MediaInfoRepository mediaInfoRepository,FileRepository fileRepository,FileInfoRepository fileInfoRepository){
        this.mediaRepository = mediaRepository;
        this.mediaInfoRepository = mediaInfoRepository;
        this.fileRepository = fileRepository;
        this.fileInfoRepository = fileInfoRepository;
    }


    @Override
    public ResponseEntity<byte[]> testMediaUploaded(UUID file) {
        HttpHeaders headers = new HttpHeaders();
        Media storedMedia = mediaRepository.findMediaById(file);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        headers.setContentType(MediaType.parseMediaType(storedMedia.getType()));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(storedMedia.getData(), headers, HttpStatus.OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<byte[]> testFileUploaded(UUID file) {
        HttpHeaders headers = new HttpHeaders();
        File storedFile = fileRepository.findFileById(file);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        headers.setContentType(MediaType.parseMediaType(storedFile.getType()));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(storedFile.getData(), headers, HttpStatus.OK);
        return responseEntity;
    }

    @Override
    public HttpStatus uploadMedia(MultipartFile file, UUID userId, UUID adventureId) {
        try {
            final byte[] content = file.getBytes();
            Media uploadedMedia = new Media(UUID.randomUUID(), file.getContentType(), file.getOriginalFilename(), "DESCRIPTION", adventureId , userId);
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

    @Override
    public HttpStatus uploadFile(MultipartFile file, UUID userId, UUID adventureId) {
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

    @Override
    public void changeMediaAccess(UUID id) {
        Media media = mediaRepository.findMediaById(id);
        MediaInfo mediaInfo = mediaInfoRepository.findMediaById(id);
        media.setPublicAccess(!media.getPublicAccess());
        mediaInfo.setPublicAccess(!mediaInfo.getPublicAccess());
        mediaRepository.save(media);
        mediaInfoRepository.save(mediaInfo);
    }

    @Override
    public void changeFileAccess(UUID id) {
        File file = fileRepository.findFileById(id);
        FileInfo fileInfo = fileInfoRepository.findFileInfoById(id);
        file.setPublicAccess(!file.getPublicAccess());
        fileInfo.setPublicAccess(!fileInfo.getPublicAccess());
        fileRepository.save(file);
        fileInfoRepository.save(fileInfo);
    }

    @Override
    public void deleteFile(UUID id, UUID userID) throws Exception {
        File file = fileRepository.findFileById(id);
        FileInfo fileInfo = fileInfoRepository.findFileInfoById(id);

        if(file == null || fileInfo == null){
            throw new Exception("File does not exist");
        }

        if(!file.getOwner().equals(userID)){
            throw new Exception("User not Authorised");
        }

        fileRepository.delete(file);
        fileInfoRepository.delete(fileInfo);
    }

    @Override
    public void deleteMedia(UUID id, UUID userID) throws Exception {
        Media media = mediaRepository.findMediaById(id);
        MediaInfo mediaInfo = mediaInfoRepository.findMediaById(id);

        if(media == null || mediaInfo == null){
            throw new Exception("Media does not exist");
        }

        if(!media.getOwner().equals(userID)){
            throw new Exception("User not Authorised");
        }

        mediaRepository.delete(media);
        mediaInfoRepository.delete(mediaInfo);
    }
}
