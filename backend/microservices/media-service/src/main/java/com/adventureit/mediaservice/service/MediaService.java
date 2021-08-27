package com.adventureit.mediaservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MediaService {
    public ResponseEntity<byte[]> testMediaUploaded(UUID file) throws IOException;
    public ResponseEntity<byte[]> testFileUploaded(UUID file) throws IOException;
    public ResponseEntity<byte[]> testDocumentUploaded(UUID file) throws IOException;
    public HttpStatus uploadMedia(MultipartFile file, UUID userId,UUID adventureId);
    public HttpStatus uploadFile(MultipartFile file,UUID userId, UUID adventureId);
    public HttpStatus uploadDocument(MultipartFile file,UUID userId);
    public void deleteMedia(UUID id,UUID userID);
    public void deleteFile(UUID id,UUID userID);
    public void deleteDocument(UUID id,UUID userID);
}
