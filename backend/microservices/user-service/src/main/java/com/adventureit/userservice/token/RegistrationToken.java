package com.adventureit.userservice.token;



import com.adventureit.userservice.entities.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RegistrationToken {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime timeCreated;
    private LocalDateTime timeExpires;
    private LocalDateTime timeConfirmed;

    @ManyToOne
    private Users user;

    public RegistrationToken( String token, LocalDateTime timeCreated, LocalDateTime timeExpires, LocalDateTime timeConfirmed, Users user) {

        this.token = token;
        this.timeCreated = timeCreated;
        this.timeExpires = timeExpires;
        this.timeConfirmed = timeConfirmed;
        this.user = user;
        this.id = UUID.randomUUID();
    }
}
