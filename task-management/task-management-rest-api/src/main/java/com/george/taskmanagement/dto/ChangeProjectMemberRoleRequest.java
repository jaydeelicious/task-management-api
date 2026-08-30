package com.george.taskmanagement.dto;

import com.george.taskmanagement.domain.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record ChangeProjectMemberRoleRequest(
        @NotNull(message = "Role is required")
        ProjectRole role
) {
}
