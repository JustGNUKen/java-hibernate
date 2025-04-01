// src/main/java/com/example/blog/controller/PostController.java
package com.karazin.blog.controller;

import com.karazin.blog.model.Follow;
import com.karazin.blog.model.Post;
import com.karazin.blog.model.User;
import com.karazin.blog.repository.FollowRepository;
import com.karazin.blog.repository.PostRepository;
import com.karazin.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private FollowRepository followRepository;

    private User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        }
        return null;
    }

    @GetMapping("/posts")
    public String viewPosts(Model model) {
        User loggedInUser = getLoggedInUser();

        var posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        if (loggedInUser != null) {
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("following", followRepository.findFollowedUsersByUser(loggedInUser));
        } else {
            model.addAttribute("loggedInUser", null);
            model.addAttribute("following", List.of());
        }

        return "posts";
    }

    @GetMapping("/create-post")
    public String showCreatePostForm() {
        return "create-post";
    }

    @PostMapping("/create-post")
    public String createPost(@RequestParam String title, @RequestParam String body) {
        User user = getLoggedInUser();

        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setUser(user);
        post.setStatus("PUBLISHED");

        postRepository.save(post);

        return "redirect:/posts";
    }
}
