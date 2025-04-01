package com.karazin.blog.service;

import com.karazin.blog.model.User;
import com.karazin.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            System.out.println("Loaded user: " + username); // Debugging log
            System.out.println("Encoded password from DB: " + user.getPassword()); // Debugging log

            // Use Spring Security's User class
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword()) // Encoded password from the database
                    .roles(user.getRole()) // Use the role from the database
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error loading user by username", e);
        }
    }
}