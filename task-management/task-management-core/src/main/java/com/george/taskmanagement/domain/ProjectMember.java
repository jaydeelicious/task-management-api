package com.george.taskmanagement.domain;

import java.time.Instant;

public class ProjectMember {

    private Long id;
    private Project project;
    private User user;
    private ProjectRole role;
    private Instant joinedAt;

    public ProjectMember(
            Project project,
            User user,
            ProjectRole role
    ) {
        if (project == null)
            throw new IllegalArgumentException("Project cannot be null");

        if (user == null)
            throw new IllegalArgumentException("User cannot be null");

        this.project = project;
        this.user = user;
        this.role = role != null ? role : ProjectRole.MEMBER;
        this.joinedAt = Instant.now();
    }

    public ProjectMember(
            Long id,
            Project project,
            User user,
            ProjectRole role,
            Instant joinedAt
    ) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public void changeRole(ProjectRole role) {
        if (role == null)
            throw new IllegalArgumentException("Role cannot be null");

        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    public ProjectRole getRole() {
        return role;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }
}
