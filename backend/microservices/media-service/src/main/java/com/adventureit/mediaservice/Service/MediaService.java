package com.adventureit.mediaservice.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MediaService {
    public String addMedia(String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) throws Exception;
    public String addFile(String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) throws Exception;
    public void getMedia(UUID id) throws Exception;
    public void getFile(UUID id) throws Exception;
//    public void getMediaByAdventureID(UUID id) throws Exception;
//    public void getMediaByOwnerID(UUID id) throws Exception;
//    public void getFileByAdventureID(UUID id) throws Exception;
//    public void getFileByOwnerID(UUID id) throws Exception;
}
