package com.george.taskmanagement.db.entity;

import com.george.taskmanagement.domain.TaskPriority;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    private TaskListEntity list;

    @Column(nullable = false)
    private int position;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_labels",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<LabelEntity> labels = new ArrayList<>();

    protected TaskEntity() {
        // Required by JPA
    }


    public TaskEntity(
            Long id,
            String title,
            String description,
            TaskListEntity list,
            int position,
            TaskPriority priority,
            LocalDate dueDate,
            Instant createdAt,
            Instant updatedAt,
            List<LabelEntity> labels
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

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public List<LabelEntity> getLabels() {
        return labels;
    }

    public TaskListEntity getList() {
        return list;
    }

    public int getPosition() {
        return position;
    }
}