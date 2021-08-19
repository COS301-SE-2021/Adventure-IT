package com.adventureit.mediaservice.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MediaService {
    public ResponseEntity<byte[]> testMediaUploaded(UUID file);
    public ResponseEntity<byte[]> testFileUploaded(UUID file);
    public ResponseEntity<byte[]> testDocumentUploaded(UUID file);
    public HttpStatus uploadMedia(MultipartFile file, UUID userId,UUID adventureId);
    public HttpStatus uploadFile(MultipartFile file,UUID userId, UUID adventureId);
    public HttpStatus uploadDocument(MultipartFile file,UUID userId);
    public void deleteMedia(UUID id,UUID userID) throws Exception;
    public void deleteFile(UUID id,UUID userID) throws Exception;
    public void deleteDocument(UUID id,UUID userID) throws Exception;
}
