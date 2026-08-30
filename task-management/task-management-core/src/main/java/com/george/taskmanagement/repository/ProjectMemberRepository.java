package com.george.taskmanagement.repository;

import com.george.taskmanagement.domain.ProjectMember;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository {

    ProjectMember save(ProjectMember projectMember);

    Optional<ProjectMember> findById(Long id);

    List<ProjectMember> findByProjectId(Long id);

    Optional<ProjectMember> findByProjectIdAndUserId(
            Long projectId,
            Long userId
    );

    boolean existsByProjectIdAndUserId(
            Long projectId,
            Long userId
    );

    void deleteById(Long id);
}
