// src/main/java/com/example/blog/controller/PostController.java
package com.karazin.blog.controller;

import com.karazin.blog.model.Post;
import com.karazin.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostController {

    private final PostRepository postRepository;

    @Autowired // Spring injects the PostRepository instance
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts") // Handles requests to http://localhost:8080/posts
    public String listPosts(Model model) {
        // Fetch all posts from the database
        List<Post> posts = postRepository.findAll();
        // Add the list of posts to the model, making it available to the template
        model.addAttribute("posts", posts);
        // Return the name of the template file (without .html extension)
        return "posts"; // This corresponds to src/main/resources/templates/posts.html
    }

    // Add more methods here later for creating, viewing single posts, etc.
}
