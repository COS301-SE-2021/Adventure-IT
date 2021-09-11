package com.adventureit.itinerary.responses;

import java.time.LocalDateTime;

public class StartDateEndDateResponseDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public StartDateEndDateResponseDTO(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public StartDateEndDateResponseDTO() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
