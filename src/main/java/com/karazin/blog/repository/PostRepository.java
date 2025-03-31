// src/main/java/com/example/blog/repository/PostRepository.java
package com.karazin.blog.repository;

import com.karazin.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
