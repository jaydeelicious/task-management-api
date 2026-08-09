package com.george.taskmanagement.domain;

import java.time.Instant;

public class TaskList {

    private Long id;
    private Project project;
    private String name;
    private int position;
    private Instant createdAt;
    private Instant updatedAt;

    public TaskList(
            Project project,
            String name,
            int position
    ) {
        if (project == null)
            throw new IllegalArgumentException("Project cannot be null");

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("List name cannot be null or blank");
        }

        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        }

        Instant now = Instant.now();

        this.project = project;
        this.name = name;
        this.position = position;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public TaskList(
            Long id,
            Project project,
            String name,
            int position,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.position = position;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void rename(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("List name cannot be null or blank");
        }

        this.name = name;
        this.updatedAt = Instant.now();
    }

    public void moveToPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        }

        this.position = position;
        this.updatedAt = Instant.now();
    }

    //region getters

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //endregion
}
