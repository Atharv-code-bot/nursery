package com.sakshi.nursery.repository;
import com.sakshi.nursery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    List<User> findByName(String name);
    User findById(Long id);

    Optional<User> findById(UUID id);

}
