package com.george.taskmanagement.db.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "lists",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_lists_project_position",
                        columnNames = {"project_id", "position"}
                )
        }
)
public class TaskListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private int position;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected TaskListEntity() {

    }

    public TaskListEntity(
            Long id,
            ProjectEntity project,
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

    //region getters

    public Long getId() {
        return id;
    }

    public ProjectEntity getProject() {
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
