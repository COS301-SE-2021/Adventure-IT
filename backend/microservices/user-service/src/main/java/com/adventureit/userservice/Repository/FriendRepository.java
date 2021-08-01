package com.adventureit.userservice.Repository;

import com.adventureit.userservice.Entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    List<Friend> findByFirstUserEquals(UUID id);
    List<Friend> findBySecondUserEquals(UUID id);
}
