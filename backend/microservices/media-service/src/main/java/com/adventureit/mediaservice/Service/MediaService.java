package com.adventureit.mediaservice.Service;

import com.adventureit.mediaservice.Enumeration.MediaType;
import com.adventureit.mediaservice.Requests.AddMediaRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MediaService {
    public String addMedia(UUID id, MediaType type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) throws Exception;
    public void getMedia(UUID id) throws Exception;
}
