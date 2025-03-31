// src/main/java/com/example/blog/BlogApplication.java
package com.karazin.blog;

import com.karazin.blog.model.User;
import com.karazin.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User defaultUser = new User();
                defaultUser.setUsername("default_user");
                defaultUser.setRole("USER");
                userRepository.save(defaultUser);
            }
        };
    }
}
