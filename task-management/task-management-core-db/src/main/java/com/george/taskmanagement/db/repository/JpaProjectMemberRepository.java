package com.george.taskmanagement.db.repository;

import com.george.taskmanagement.db.entity.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaProjectMemberRepository
        extends JpaRepository<ProjectMemberEntity, Long> {

    List<ProjectMemberEntity> findByProjectId(Long projectId);

    Optional<ProjectMemberEntity> findByProjectIdAndUserId(
            Long projectId,
            Long userId
    );

    boolean existsByProjectIdAndUserId(
            Long projectId,
            Long userId
    );
}
