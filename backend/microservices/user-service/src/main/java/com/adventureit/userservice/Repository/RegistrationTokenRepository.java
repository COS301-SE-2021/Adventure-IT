package com.adventureit.userservice.Repository;

import com.adventureit.userservice.Token.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, UUID> {
    Optional<RegistrationToken> findByToken(String token);
}
