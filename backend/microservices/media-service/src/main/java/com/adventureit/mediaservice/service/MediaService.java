package com.adventureit.mediaservice.service;

import com.adventureit.shareddtos.media.responses.MediaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MediaService {
    public MediaResponseDTO testMediaUploaded(UUID file) throws IOException;
    public MediaResponseDTO testFileUploaded(UUID file) throws IOException;
    public MediaResponseDTO testDocumentUploaded(UUID file) throws IOException;
    public HttpStatus uploadMedia(MultipartFile file, UUID userId,UUID adventureId);
    public HttpStatus uploadFile(MultipartFile file,UUID userId, UUID adventureId);
    public HttpStatus uploadDocument(MultipartFile file,UUID userId);
    public void deleteMedia(UUID id,UUID userID);
    public void deleteFile(UUID id,UUID userID);
    public void deleteDocument(UUID id,UUID userID);
}
