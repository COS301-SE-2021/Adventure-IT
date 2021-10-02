package com.adventureit.maincontroller.Threads;

import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class CascadingDeleteThread implements Runnable{
    private final RestTemplate restTemplate = new RestTemplate();
    private String INTERNET_PORT;
    private String PORT;
    private String endpoint;
    Thread t;
    UUID id;

    public CascadingDeleteThread() {
    }

    public CascadingDeleteThread(String INTERNET_PORT, String PORT, String endpoint, UUID id){
        this.id = id;
        this.PORT = PORT;
        this.INTERNET_PORT = INTERNET_PORT;
        this.endpoint = endpoint;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        System.out.println(t.getName() + " Is Running");
        restTemplate.getForObject(INTERNET_PORT + ":" + PORT + endpoint +id, String.class);
    }
}
