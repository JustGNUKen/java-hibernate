package com.karazin.blog.controller;

import com.karazin.blog.dao.UserDAO;
import com.karazin.blog.dao.impl.UserDAOImpl;
import com.karazin.blog.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserDAO userDAO = new UserDAOImpl();
    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            System.out.println("Attempting to register user: " + username);
            if (userDAO.findByUsername(username).isPresent()) {
                model.addAttribute("error", "Username already exists. Please choose a different username.");
                System.out.println("Username already exists: " + username);
                return "register";
            }

            User user = new User();
            user.setUsername(username);
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            user.setRole("USER");

            System.out.println("Encoded password: " + encodedPassword);

            userDAO.save(user);

            System.out.println("User registered successfully: " + username);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration.");
            System.err.println("Error during registration: " + e.getMessage());
            return "register";
        }
    }
}