package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.ProjectMember;
import com.george.taskmanagement.domain.ProjectRole;

import java.util.List;

public interface ProjectMemberService {

    ProjectMember addMember(
            Long projectId,
            Long userId,
            ProjectRole role
    );

    List<ProjectMember> findByProjectId(Long projectId);

    ProjectMember changeRole(
            Long projectId,
            Long userId,
            ProjectRole role
    );

    void removeMember(
            Long projectId,
            Long userId
    );
}
