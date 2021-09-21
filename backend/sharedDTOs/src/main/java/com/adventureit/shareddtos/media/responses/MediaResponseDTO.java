package com.adventureit.shareddtos.media.responses;

import org.springframework.http.HttpHeaders;

import java.io.Serializable;

public class MediaResponseDTO implements Serializable {
    byte[] content;
    HttpHeaders headers;

    public MediaResponseDTO(){}

    public MediaResponseDTO(byte[] content, HttpHeaders headers) {
        this.content = content;
        this.headers = headers;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }
}
