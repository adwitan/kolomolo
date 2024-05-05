package com.github.kolomolo.service.openaiclient.repository;

import com.github.kolomolo.service.openaiclient.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
