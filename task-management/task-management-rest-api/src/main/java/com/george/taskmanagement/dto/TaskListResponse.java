package com.george.taskmanagement.dto;

import java.time.Instant;

public record TaskListResponse(
        Long id,
        Long projectId,
        String name,
        int position,
        Instant createdAt,
        Instant updatedAt
) {
}
