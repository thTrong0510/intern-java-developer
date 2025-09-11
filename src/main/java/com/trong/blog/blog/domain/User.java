package com.trong.blog.blog.domain;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @NotBlank(message = "Email can not be blank")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    Instant createdAt;
    Instant updatedAt;

    // Quan hệ 1-nhiều: User có nhiều Posts
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<Post> fetchPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User() {
    }

    public User(long id, String name, @NotBlank(message = "Email can not be blank") String email,
            @NotBlank(message = "Password can not be blank") String password, String refreshToken, Instant createdAt,
            Instant updatedAt, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.posts = posts;
    }

    @PrePersist
    private void beforeCreate() {
        // this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
        // ? SecurityUtil.getCurrentUserLogin().get()
        // : "";
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void beforeUpdate() {
        // this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
        // ? SecurityUtil.getCurrentUserLogin().get()
        // : "";
        this.updatedAt = Instant.now();
    }

}
