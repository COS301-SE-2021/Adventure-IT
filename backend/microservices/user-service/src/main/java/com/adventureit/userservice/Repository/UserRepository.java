package com.adventureit.userservice.Repository;

import com.adventureit.userservice.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUserID(UUID userID);
    User getUserByEmail(String email);
}
