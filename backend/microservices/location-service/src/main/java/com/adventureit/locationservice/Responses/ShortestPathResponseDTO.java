package com.adventureit.locationservice.Responses;

import java.util.ArrayList;
import java.util.List;

public class ShortestPathResponseDTO {
    private List<String> order = new ArrayList();
    private String distance;
    private String duration;

    public ShortestPathResponseDTO(){}

    public ShortestPathResponseDTO(List<String> order, String distance, String duration){
        this.order = order;
        this.distance =distance;
        this.duration = duration;
    }

    public List<String> getOrder() {
        return order;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }
}
