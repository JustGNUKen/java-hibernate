package com.karazin.blog.controller;

import com.karazin.blog.dao.FollowDAO;
import com.karazin.blog.dao.UserDAO;
import com.karazin.blog.dao.impl.FollowDAOImpl;
import com.karazin.blog.dao.impl.UserDAOImpl;
import com.karazin.blog.model.User;
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

    private final FollowDAO followDAO = new FollowDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    private User getLoggedInUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userDAO.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Anonymous user
    }

    @GetMapping("/account")
    public String viewAccount(Model model) {
        try {
            User loggedInUser = getLoggedInUser();
            if (loggedInUser == null) {
                return "redirect:/login"; // Redirect anonymous users to login
            }

            // Delegate to FollowDAO
            List<User> followers = followDAO.findFollowersByUser(loggedInUser);
            List<User> following = followDAO.findFollowedUsersByUser(loggedInUser);

            // Add data to the model
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("followers", followers);
            model.addAttribute("following", following);
            model.addAttribute("followersCount", followers.size());
            model.addAttribute("followingCount", following.size());

            return "account";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/unfollow-from-account")
    public String unfollowFromAccount(@RequestParam Long userId) {
        try {
            User loggedInUser = getLoggedInUser();
            if (loggedInUser == null) {
                return "redirect:/login"; // Redirect anonymous users to login
            }

            User userToUnfollow = userDAO.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

            // Delegate to FollowDAO
            followDAO.deleteByFollowingUserAndFollowedUser(loggedInUser, userToUnfollow);

            return "redirect:/account";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}