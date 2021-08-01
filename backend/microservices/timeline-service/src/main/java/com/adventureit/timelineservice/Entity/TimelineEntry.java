package com.adventureit.timelineservice.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimelineEntry{
    @Id
    private UUID timelineID;
    private UUID adventureID;
    private UUID userID;
    private String description;
    private LocalDateTime timestamp;




}
