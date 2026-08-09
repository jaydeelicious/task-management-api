package com.george.taskmanagement.domain;

import java.time.Instant;

public class Comment {

    private Long id;
    private Task task;
    private User author;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;

    public Comment(
            Task task,
            User author,
            String content
    ) {
        if (task == null)
            throw new IllegalArgumentException("Task cannot be null");

        if (author == null)
            throw new IllegalArgumentException("Author cannot be null");

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment content cannot be null or blank");
        }

        Instant now = Instant.now();

        this.task = task;
        this.author = author;
        this.content = content;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Comment(
            Long id,
            Task task,
            User author,
            String content,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.task = task;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Comment content cannot be null or blank");
        }

        this.content = content;
        this.updatedAt = Instant.now();
    }

    //region getters

    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //endregion
}
