// src/main/java/com/example/blog/repository/PostRepository.java
package com.karazin.blog.repository;

import com.karazin.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId); // Find posts by a specific user
    List<Post> findByStatusOrderByCreatedAtDesc(String status); // Find posts by status, ordered
}
