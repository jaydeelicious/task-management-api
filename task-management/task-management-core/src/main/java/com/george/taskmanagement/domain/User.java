package com.george.taskmanagement.domain;

import java.time.Instant;

public class User {

    private Long id;
    private String username;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;

    // new-user constructor
    public User(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username cannot be null or blank"
            );
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Email cannot be null or blank"
            );
        }

        Instant now = Instant.now();

        this.username = username;
        this.email = email;
        this.createdAt = now;
        this.updatedAt = now;
    }

    // reconstruction constructor
    public User(Long id,
                String username,
                String email,
                Instant createdAt,
                Instant updatedAt
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // update methods
    public void updateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }

        this.username = username;
        this.updatedAt = Instant.now();
    }

    public void updateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }

        this.email = email;
        this.updatedAt = Instant.now();
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
