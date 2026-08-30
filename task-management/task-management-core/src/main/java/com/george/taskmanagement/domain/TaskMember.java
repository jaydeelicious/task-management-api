package com.george.taskmanagement.domain;

import java.time.Instant;

public class TaskMember {

    private Long id;
    private Task task;
    private User user;
    private Instant addedAt;

    public TaskMember(Task task, User user) {
        if (task == null)
            throw new IllegalArgumentException("Task cannot be null");

        if (user == null)
            throw new IllegalArgumentException("User cannot be null");

        this.task = task;
        this.user = user;
        this.addedAt = Instant.now();
    }

    public TaskMember(
            Long id,
            Task task,
            User user,
            Instant addedAt
    ) {
        this.id = id;
        this.task = task;
        this.user = user;
        this.addedAt = addedAt;
    }

    //region getters

    public Long getId() {
        return id;
    }

    public Task getTask() {
        return task;
    }

    public User getUser() {
        return user;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    //endregion
}
