package com.adventureit.recommendationservice.repository;

import com.adventureit.recommendationservice.entity.RecommendedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecommendedUserRepository extends JpaRepository<RecommendedUser, UUID> {
    RecommendedUser findUserByUserId(UUID id);
}
