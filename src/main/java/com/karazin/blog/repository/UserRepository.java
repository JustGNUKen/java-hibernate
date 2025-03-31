// src/main/java/com/example/blog/repository/UserRepository.java
package com.karazin.blog.repository;

import com.karazin.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Example custom query method
}
