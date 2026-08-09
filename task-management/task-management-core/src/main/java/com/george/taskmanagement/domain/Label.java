package com.george.taskmanagement.domain;

import java.time.Instant;

public class Label {

    private Long id;
    private Project project;
    private String name;
    private String color;
    private Instant createdAt;
    private Instant updatedAt;

    public Label(
            Project project,
            String name,
            String color
    ) {
        if (project == null)
            throw new IllegalArgumentException("Project cannot be null");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Label name cannot be null or blank");

        Instant now = Instant.now();

        this.project = project;
        this.name = name;
        this.color = color;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Label(
            Long id,
            Project project,
            String name,
            String color,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.color = color;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void rename(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Label name cannot be null or blank");

        this.name = name;
        this.updatedAt = Instant.now();
    }

    public void changeColor(String color) {
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    //endregion
}
