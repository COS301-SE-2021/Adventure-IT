package com.adventureit.userservice.repository;


import com.adventureit.userservice.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    Users getUserByUserID(UUID userID);
    Users getUserByEmail(String email);
    Users getUserByUsername(String username);
}
