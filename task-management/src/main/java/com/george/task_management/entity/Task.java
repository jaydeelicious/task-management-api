package com.george.task_management.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

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

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // title
    @Column(nullable = false, length = 100)
    private String title;

    // description
    @Column(length = 500)
    private String description;

    // status
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    // priority
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority = TaskPriority.MEDIUM;

    // dueDate
    @Column(name = "due_date")
    private LocalDate dueDate;

    // createdAt
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // updatedAt
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // project relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    protected Task() {

    }

    public Task(
            String title,
            String description,
            Project project,
            TaskStatus taskStatus,
            TaskPriority taskPriority,
            LocalDate dueDate
    ) {
        this.title = title;
        this.description = description;
        this.project = project;

        this.status = taskStatus != null
                            ? taskStatus : TaskStatus.TODO;
        this.priority = taskPriority != null
                            ? taskPriority : TaskPriority.MEDIUM;
    }

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    //region setters

    // updateTile
    public void updateTitle(String title) {
        if (title == null || title.isBlank())
            throw new RuntimeException("Couldn't update, title was null or empty");

        this.title = title;
    }

    // updateDescription
    public void updateDescription(String description) {
        this.description = description;
    }

    // updateStatus
    public void updateStatus(TaskStatus taskStatus) {
        if (taskStatus == null)
            throw new RuntimeException("Couldn't update, status was null");

        this.status = taskStatus;
    }

    // updatePriority
    public void updatePriority(TaskPriority taskPriority) {
        if (taskPriority == null)
            throw new RuntimeException("Couldn't update, priority was null");

        this.priority = taskPriority;
    }

    // updateDueDate
    public void updateDueDate(LocalDate dueDate) {
        if (dueDate == null)
            throw new RuntimeException("Couldn't update, dueDate was null");

        this.dueDate = dueDate;
    }

    // updateProject
    public void updateProject(Project project) {
        if (project == null)
            throw new RuntimeException("Couldn't update, project was null");

        this.project = project;
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

    // getStatus
    public TaskStatus getStatus() {
        return status;
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

    // getProject
    public Project getProject() {
        return project;
    }

    //endregion
}
