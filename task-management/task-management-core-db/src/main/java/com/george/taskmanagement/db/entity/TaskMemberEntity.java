package com.george.taskmanagement.db.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name ="task_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_task_members_task_user",
                        columnNames = {"task_id", "user_id"}
                )
        }
)
public class TaskMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "added_at", nullable = false, updatable = false)
    private Instant addedAt;

    protected TaskMemberEntity() {
        // Required by JPA
    }

    public TaskMemberEntity(
            Long id,
            TaskEntity task,
            UserEntity user,
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

    public TaskEntity getTask() {
        return task;
    }

    public UserEntity getUser() {
        return user;
    }

    public Instant getAddedAt() {
        return addedAt;
    }

    //endregion
}
