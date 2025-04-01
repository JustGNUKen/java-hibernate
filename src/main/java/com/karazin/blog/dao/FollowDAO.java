package com.karazin.blog.dao;

import com.karazin.blog.model.Follow;
import com.karazin.blog.model.FollowId;
import com.karazin.blog.model.User;

import java.util.List;

public interface FollowDAO {
    void save(Follow follow) throws Exception;
    void delete(FollowId followId) throws Exception;
    List<User> findFollowedUsersByUser(User user) throws Exception;
    List<User> findFollowersByUser(User user) throws Exception;
}