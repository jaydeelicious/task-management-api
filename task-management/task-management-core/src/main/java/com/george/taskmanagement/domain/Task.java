package com.george.taskmanagement.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
Task entity
- id
- title
- description
- status
- priority
- dueDate
- createdAt
- updatedAt
- project
*/

public class Task {

    private Long id;
    private String title;
    private String description;
    private TaskList list;
    private int position;
    private TaskPriority priority = TaskPriority.MEDIUM;
    private LocalDate dueDate;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Label> labels = new ArrayList<>();

    public Task(
            String title,
            String description,
            TaskList list,
            int position,
            TaskPriority taskPriority,
            LocalDate dueDate
    ) {
        Instant now = Instant.now();

        this.title = title;
        this.description = description;
        this.list = list;
        this.position = position;
        this.priority = taskPriority != null
                            ? taskPriority : TaskPriority.MEDIUM;
        this.dueDate = dueDate;
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Task(
            Long id,
            String title,
            String description,
            TaskList list,
            int position,
            TaskPriority priority,
            LocalDate dueDate,
            Instant createdAt,
            Instant updatedAt,
            List<Label> labels
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.list = list;
        this.position = position;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.labels = labels != null
                ? new ArrayList<>(labels)
                : new ArrayList<>();
    }

    //region setters

    // updateTile
    public void updateTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Couldn't update, title was null or empty");

        this.title = title;
        this.updatedAt = Instant.now();
    }

    // updateDescription
    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public void moveToList(TaskList list) {
        if (list == null) {
            throw new IllegalArgumentException("Task list cannot be null");
        }

        this.list = list;
        this.updatedAt = Instant.now();
    }

    public void moveToPosition(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be negative");
        }

        this.position = position;
        this.updatedAt = Instant.now();
    }

    // updatePriority
    public void updatePriority(TaskPriority taskPriority) {
        if (taskPriority == null)
            throw new IllegalArgumentException("Couldn't update, priority was null");

        this.priority = taskPriority;
        this.updatedAt = Instant.now();
    }

    // updateDueDate
    public void updateDueDate(LocalDate dueDate) {
        if (dueDate == null)
            throw new IllegalArgumentException("Couldn't update, dueDate was null");

        this.dueDate = dueDate;
        this.updatedAt = Instant.now();
    }

    // clearDueDate
    public void clearDueDate() {
        this.dueDate = null;
        this.updatedAt = Instant.now();
    }

    public void addLabel(Label label) {
        if (label == null)
            throw new IllegalArgumentException("Label cannot be null");

        if (!labels.contains(label)) {
            labels.add(label);
            updatedAt = Instant.now();
        }
    }

    public void removeLabel(Label label) {
        if (label == null)
            throw new IllegalArgumentException("Label cannot be null");

        if (labels.remove(label)) {
            updatedAt = Instant.now();
        }
    }

    //endregion

    //region getters

    // getId
    public Long getId() {
        return id;
    }

    // getTitle
    public String getTitle() {
        return title;
    }

    // getDescription
    public String getDescription() {
        return description;
    }

    // getList
    public TaskList getList() {
        return list;
    }

    // getPosition
    public int getPosition() {
        return position;
    }

    // getPriority
    public TaskPriority getPriority() {
        return priority;
    }

    // getDueDate
    public LocalDate getDueDate() {
        return dueDate;
    }

    // getCreatedAt
    public Instant getCreatedAt() {
        return createdAt;
    }

    // getUpdatedAt
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public List<Label> getLabels() {
        return List.copyOf(labels);
    }

    //endregion
}
