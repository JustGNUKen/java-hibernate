package com.karazin.blog.controller;

import com.karazin.blog.model.Follow;
import com.karazin.blog.model.FollowId;
import com.karazin.blog.model.User;
import com.karazin.blog.repository.FollowRepository;
import com.karazin.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FollowController {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    private User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        }
        return null; // Anonymous user
    }

    @PostMapping("/follow")
    public String followUser(@RequestParam Long userId) {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect anonymous users to login
        }

        User userToFollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        Follow follow = new Follow();
        follow.setId(new FollowId());
        follow.setFollowingUser(loggedInUser);
        follow.setFollowedUser(userToFollow);

        followRepository.save(follow);

        return "redirect:/posts";
    }

    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam Long userId) {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect anonymous users to login
        }

        User userToUnfollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        FollowId followId = new FollowId();
        followId.setFollowingUserId(loggedInUser.getId());
        followId.setFollowedUserId(userToUnfollow.getId());

        followRepository.deleteById(followId);

        return "redirect:/posts";
    }
}