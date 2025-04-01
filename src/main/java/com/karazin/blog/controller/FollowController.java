package com.karazin.blog.controller;

import com.karazin.blog.dao.FollowDAO;
import com.karazin.blog.dao.UserDAO;
import com.karazin.blog.dao.impl.FollowDAOImpl;
import com.karazin.blog.dao.impl.UserDAOImpl;
import com.karazin.blog.model.Follow;
import com.karazin.blog.model.FollowId;
import com.karazin.blog.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FollowController {

    private final FollowDAO followDAO = new FollowDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    private User getLoggedInUser() throws Exception {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userDAO.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));
        }
        return null;
    }

    @PostMapping("/follow")
    public String followUser(@RequestParam Long userId) {
        try {
            User loggedInUser = getLoggedInUser();
            if (loggedInUser == null) {
                return "redirect:/login";
            }

            User userToFollow = userDAO.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User to follow not found"));

            Follow follow = new Follow(loggedInUser, userToFollow);
            followDAO.save(follow);

            return "redirect:/posts";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam Long userId) {
        try {
            User loggedInUser = getLoggedInUser();
            if (loggedInUser == null) {
                return "redirect:/login";
            }

            User userToUnfollow = userDAO.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

            FollowId followId = new FollowId(loggedInUser.getId(), userToUnfollow.getId());
            followDAO.delete(followId);

            return "redirect:/posts";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}