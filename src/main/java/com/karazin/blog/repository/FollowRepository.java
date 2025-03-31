package com.karazin.blog.repository;

import com.karazin.blog.model.Follow;
import com.karazin.blog.model.FollowId;
import com.karazin.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    // Get the list of users the logged-in user is following
    @Query("SELECT f.followedUser FROM Follow f WHERE f.followingUser = :user")
    List<User> findFollowedUsersByUser(@Param("user") User user);

    // Get the list of users who are following the logged-in user
    @Query("SELECT f.followingUser FROM Follow f WHERE f.followedUser = :user")
    List<User> findFollowersByUser(@Param("user") User user);
}