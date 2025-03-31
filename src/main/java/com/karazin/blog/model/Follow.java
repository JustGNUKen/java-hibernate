// src/main/java/com/example/blog/model/Follow.java
package com.karazin.blog.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "follows")
public class Follow {

    @EmbeddedId
    private FollowId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followingUserId") // Maps the 'followingUserId' part of the embedded ID
    @JoinColumn(name = "following_user_id")
    private User followingUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("followedUserId") // Maps the 'followedUserId' part of the embedded ID
    @JoinColumn(name = "followed_user_id")
    private User followedUser;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Follow() {
    }

    public Follow(User followingUser, User followedUser) {
        this.followingUser = followingUser;
        this.followedUser = followedUser;
        this.id = new FollowId(followingUser.getId(), followedUser.getId());
    }

    // Getters and Setters

    public FollowId getId() {
        return id;
    }

    public void setId(FollowId id) {
        this.id = id;
    }

    public User getFollowingUser() {
        return followingUser;
    }

    public void setFollowingUser(User followingUser) {
        this.followingUser = followingUser;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow follow = (Follow) o;
        return Objects.equals(id, follow.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
