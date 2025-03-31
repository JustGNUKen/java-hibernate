// src/main/java/com/example/blog/controller/PostController.java
package com.karazin.blog.controller;

import com.karazin.blog.model.Post;
import com.karazin.blog.model.User;
import com.karazin.blog.repository.PostRepository;
import com.karazin.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "posts";
    }

    @GetMapping("/create-post")
    public String showCreatePostForm() {
        return "create-post";
    }

    @PostMapping("/posts")
    public String createPost(@RequestParam String title, @RequestParam String body) {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Default user not found. Please ensure a user exists in the database."));

        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setUser(user);
        post.setStatus("PUBLISHED");

        postRepository.save(post);

        return "redirect:/posts";
    }
}
