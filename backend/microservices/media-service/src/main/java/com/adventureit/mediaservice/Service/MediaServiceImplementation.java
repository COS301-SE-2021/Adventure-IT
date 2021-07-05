package com.adventureit.mediaservice.Service;

import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class MediaServiceImplementation implements MediaService{
    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImplementation(MediaRepository mediaRepository){
        this.mediaRepository = mediaRepository;
    }

    @Override
    public String addMedia(UUID id, String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) throws Exception{
        if(id == null){
            throw new Exception("ID not provided");
        }
        if(name == null || name.equals("")){
            throw new Exception("Name not provided");
        }
        if(adventureID == null){
            throw new Exception("Adventure ID not provided");
        }
        if(owner == null){
            throw new Exception("Owner ID not provided");
        }
        if(description == null || description.equals("")){
            throw new Exception("Description not provided");
        }
        if(type == null){
            throw new Exception("Type not provided");
        }
        if(file == null) {
            throw new Exception("File not provided");
        }

        if(mediaRepository.findMediaById(id) != null){
            throw new Exception("Media already exists");
        }

        try {
            Media media = new Media(id,type,name,description,adventureID,owner);
            media.setData(file.getBytes());
            mediaRepository.save(media);
        } catch (Exception e) {
            return "error";
        }

        return "Media successfully added!";
    }

    @Override
    @Transactional
    public void getMedia(UUID id) throws Exception {
        if(id == null){
            throw new Exception("ID not provided");
        }

        Media media = mediaRepository.findMediaById(id);
        if(media == null){
            throw new Exception("Media does not exist");
        }

        if(media.getType().equals("Video")){
            FileOutputStream out = new FileOutputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Media\\videooutput.mp4");
            out.write(media.getData());
            out.close();
        }
        if(media.getType().equals("Audio")){
            FileOutputStream out = new FileOutputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Media\\audiooutput.mp3");
            out.write(media.getData());
            out.close();
        }
        if(media.getType().equals("Document")){
            FileOutputStream out = new FileOutputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Media\\documentoutput.pdf");
            out.write(media.getData());
            out.close();
        }
        if(media.getType().equals("Image")){
            FileOutputStream out = new FileOutputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Media\\imageoutput.jpg");
            out.write(media.getData());
            out.close();
        }
    }
}
