package com.adventureit.userservice.Token;


import com.adventureit.userservice.Entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationToken {

    private UUID id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime timeCreated;
    private LocalDateTime timeExpires;
    private LocalDateTime timeConfirmed;

    @ManyToOne
    private User user;

    public RegistrationToken(UUID id, String token, LocalDateTime timeCreated, LocalDateTime timeExpires, LocalDateTime timeConfirmed, User user) {
        this.id = id;
        this.token = token;
        this.timeCreated = timeCreated;
        this.timeExpires = timeExpires;
        this.timeConfirmed = timeConfirmed;
        this.user = getUser();
    }
}
