package com.adventureit.mediaservice.service;

import com.adventureit.mediaservice.entity.*;
import com.adventureit.mediaservice.exceptions.NotFoundException;
import com.adventureit.mediaservice.exceptions.UnauthorisedException;
import com.adventureit.mediaservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class MediaServiceImplementation implements MediaService{
    @Autowired
    private final MediaRepository mediaRepository;
    @Autowired
    private final MediaInfoRepository mediaInfoRepository;
    @Autowired
    private final FileRepository fileRepository;
    @Autowired
    private final FileInfoRepository fileInfoRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentInfoRepository documentInfoRepository;

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
        return new ResponseEntity<>(storedMedia.getData(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> testFileUploaded(UUID file) {
        HttpHeaders headers = new HttpHeaders();
        File storedFile = fileRepository.findFileById(file);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        headers.setContentType(MediaType.parseMediaType(storedFile.getType()));
        return new ResponseEntity<>(storedFile.getData(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> testDocumentUploaded(UUID file) {
        HttpHeaders headers = new HttpHeaders();
        Document storedFile = documentRepository.findDocumentById(file);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue()); // disabling caching for client who requests the resource
        headers.setContentType(MediaType.parseMediaType(storedFile.getType()));
        return new ResponseEntity<>(storedFile.getData(), headers, HttpStatus.OK);
    }

    @Override
    public HttpStatus uploadMedia(MultipartFile file, UUID userId, UUID adventureId) {
        try {
            final byte[] content = file.getBytes();
            Media uploadedMedia = new Media(UUID.randomUUID(), file.getContentType(), file.getOriginalFilename(), "DESCRIPTION", adventureId , userId);
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

    @Override
    public HttpStatus uploadFile(MultipartFile file, UUID userId, UUID adventureId) {
        try {
            final byte[] content = file.getBytes();
            File uploadedFile = new File(UUID.randomUUID(), file.getContentType(), file.getOriginalFilename(), "DESCRIPTION", adventureId , userId);
            FileInfo uploadedFileInfo = new FileInfo(uploadedFile.getId(), uploadedFile.getType(), uploadedFile.getName(), uploadedFile.getDescription(), uploadedFile.getAdventureID(), uploadedFile.getOwner());
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
    public HttpStatus uploadDocument(MultipartFile file, UUID userId) {
        try {
            final byte[] content = file.getBytes();
            Document uploadedDoc = new Document(UUID.randomUUID(), file.getContentType(), file.getOriginalFilename(), "DESCRIPTION", userId);
            DocumentInfo uploadedDocInfo = new DocumentInfo(uploadedDoc.getId(), uploadedDoc.getType(), uploadedDoc.getName(), uploadedDoc.getDescription(), uploadedDoc.getOwner());
            uploadedDoc.setData(content);
            documentRepository.save(uploadedDoc);
            documentInfoRepository.save(uploadedDocInfo);
            return HttpStatus.OK;
        }
        catch(Exception e){
            e.printStackTrace();
            return HttpStatus.NO_CONTENT;
        }
    }

    @Override
    public void deleteFile(UUID id, UUID userID){
        File file = fileRepository.findFileById(id);
        FileInfo fileInfo = fileInfoRepository.findFileInfoById(id);

        if(file == null || fileInfo == null){
            throw new NotFoundException("Delete File: File does not exist");
        }

        if(!file.getOwner().equals(userID)){
            throw new UnauthorisedException("Delete File: User not Authorised");
        }

        fileRepository.delete(file);
        fileInfoRepository.delete(fileInfo);
    }

    @Override
    public void deleteMedia(UUID id, UUID userID){
        Media media = mediaRepository.findMediaById(id);
        MediaInfo mediaInfo = mediaInfoRepository.findMediaById(id);

        if(media == null || mediaInfo == null){
            throw new NotFoundException("Delete Media: Media does not exist");
        }

        if(!media.getOwner().equals(userID)){
            throw new UnauthorisedException("Delete Media: User not Authorised");
        }

        mediaRepository.delete(media);
        mediaInfoRepository.delete(mediaInfo);
    }

    @Override
    public void deleteDocument(UUID id, UUID userID){
        Document document = documentRepository.findDocumentById(id);
        DocumentInfo documentInfo = documentInfoRepository.findDocumentInfoById(id);

        if(document == null || documentInfo == null){
            throw new NotFoundException("Delete Document: Doc does not exist");
        }

        if(!document.getOwner().equals(userID)){
            throw new UnauthorisedException("Delete Document: User not Authorised");
        }

        documentRepository.delete(document);
        documentInfoRepository.delete(documentInfo);
    }
}
