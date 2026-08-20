package com.george.taskmanagement.dto;

import com.george.taskmanagement.domain.ProjectRole;

import java.time.Instant;

public record ProjectMemberResponse(
        Long id,
        Long projectId,
        Long userId,
        String username,
        ProjectRole role,
        Instant joinedAt
) {
}
