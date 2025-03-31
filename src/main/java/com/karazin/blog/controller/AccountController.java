package com.karazin.blog.controller;

import com.karazin.blog.model.User;
import com.karazin.blog.repository.FollowRepository;
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
public class AccountController {

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

    @GetMapping("/account")
    public String viewAccount(Model model) {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect anonymous users to login
        }

        // Fetch followers and following lists
        List<User> followers = followRepository.findFollowersByUser(loggedInUser);
        List<User> following = followRepository.findFollowedUsersByUser(loggedInUser);

        // Add data to the model
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("followers", followers);
        model.addAttribute("following", following);
        model.addAttribute("followersCount", followers.size());
        model.addAttribute("followingCount", following.size());

        return "account";
    }

    @PostMapping("/unfollow-from-account")
    public String unfollowFromAccount(@RequestParam Long userId) {
        User loggedInUser = getLoggedInUser();
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect anonymous users to login
        }

        User userToUnfollow = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        followRepository.deleteByFollowingUserAndFollowedUser(loggedInUser, userToUnfollow);

        return "redirect:/account";
    }
}