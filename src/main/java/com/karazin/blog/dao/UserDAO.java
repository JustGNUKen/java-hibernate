package com.karazin.blog.dao;

import com.karazin.blog.model.User;

import java.util.Optional;

public interface UserDAO {
    void save(User user) throws Exception;
    Optional<User> findById(Long id) throws Exception;
    Optional<User> findByUsername(String username) throws Exception;
}