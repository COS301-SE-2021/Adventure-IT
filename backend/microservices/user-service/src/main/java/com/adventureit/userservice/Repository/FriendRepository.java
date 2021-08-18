package com.adventureit.userservice.Repository;

import com.adventureit.userservice.Entities.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendRepository extends JpaRepository<Friend, UUID> {
    List<Friend> findByFirstUserEquals(UUID id);
    List<Friend> findBySecondUserEquals(UUID id);
    Friend findFriendById(UUID id);
}
