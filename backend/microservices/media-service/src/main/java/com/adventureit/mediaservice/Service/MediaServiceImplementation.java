package com.adventureit.mediaservice.Service;

import com.adventureit.mediaservice.Entity.File;
import com.adventureit.mediaservice.Entity.Media;
import com.adventureit.mediaservice.Repository.FileRepository;
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
    FileRepository fileRepository;


    @Autowired
    public MediaServiceImplementation(MediaRepository mediaRepository,FileRepository fileRepository){
        this.mediaRepository = mediaRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public String addMedia(String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) throws Exception{
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

        try {
            Media media = new Media(type,name,description,adventureID,owner);
            media.setData(file.getBytes());
            mediaRepository.save(media);
        } catch (Exception e) {
            return "error";
        }

        return "Media successfully added!";
    }

    @Override
    public String addFile(String type, String name, String description, UUID adventureID, UUID owner, MultipartFile file) throws Exception {
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

        try {
            File f = new File(type,name,description,adventureID,owner);
            f.setData(file.getBytes());
            fileRepository.save(f);
        } catch (Exception e) {
            return "error";
        }

        return "File successfully added!";
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
        if(media.getType().equals("Image")){
            FileOutputStream out = new FileOutputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Media\\imageoutput.jpg");
            out.write(media.getData());
            out.close();
        }
    }

    @Override
    public void getFile(UUID id) throws Exception {
        if(id == null){
            throw new Exception("ID not provided");
        }

        File file = fileRepository.findFileById(id);
        if(file == null){
            throw new Exception("File does not exist");
        }

        if(file.getType().equals("Document")){
            FileOutputStream out = new FileOutputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Media\\documentoutput.pdf");
            out.write(file.getData());
            out.close();
        }
        if(file.getType().equals("Image")){
            FileOutputStream out = new FileOutputStream("C:\\Users\\sgood\\Documents\\CS\\SEM 1\\COS301\\Capstone\\Media\\imageoutput.jpg");
            out.write(file.getData());
            out.close();
        }
    }

}
