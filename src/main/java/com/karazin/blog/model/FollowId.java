// src/main/java/com/example/blog/model/FollowId.java
package com.karazin.blog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FollowId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "following_user_id")
    private Long followingUserId;

    @Column(name = "followed_user_id")
    private Long followedUserId;

    public FollowId() {
    }

    public FollowId(Long followingUserId, Long followedUserId) {
        this.followingUserId = followingUserId;
        this.followedUserId = followedUserId;
    }

    // Getters and Setters

    public Long getFollowingUserId() {
        return followingUserId;
    }

    public void setFollowingUserId(Long followingUserId) {
        this.followingUserId = followingUserId;
    }

    public Long getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(Long followedUserId) {
        this.followedUserId = followedUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(followingUserId, followId.followingUserId) &&
               Objects.equals(followedUserId, followId.followedUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followingUserId, followedUserId);
    }
}
