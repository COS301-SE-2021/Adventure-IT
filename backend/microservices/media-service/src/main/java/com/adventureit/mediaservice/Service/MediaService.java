package com.adventureit.mediaservice.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MediaService {
    public String addMedia(UUID id, String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) throws Exception;
    public void getMedia(UUID id) throws Exception;
}
