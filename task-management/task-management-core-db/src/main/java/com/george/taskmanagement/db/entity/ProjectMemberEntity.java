package com.george.taskmanagement.db.entity;

import com.george.taskmanagement.domain.ProjectRole;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "project_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_project_members_project_user",
                        columnNames = {"project_id", "user_id"}
                )
        }
)
public class ProjectMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectRole role;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private Instant joinedAt;

    protected ProjectMemberEntity() {

    }


    public ProjectMemberEntity(
            Long id,
            ProjectEntity project,
            UserEntity user,
            ProjectRole role,
            Instant joinedAt
    ) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public Long getId() {
        return id;
    }

     public ProjectEntity getProject() {
        return project;
     }

     public UserEntity getUser() {
        return user;
     }

     public ProjectRole getRole() {
        return role;
     }

     public Instant getJoinedAt() {
        return joinedAt;
     }
}