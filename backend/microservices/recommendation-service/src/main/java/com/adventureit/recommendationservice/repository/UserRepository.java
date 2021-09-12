package com.adventureit.recommendationservice.repository;

import com.adventureit.recommendationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByUserId(UUID id);
}
