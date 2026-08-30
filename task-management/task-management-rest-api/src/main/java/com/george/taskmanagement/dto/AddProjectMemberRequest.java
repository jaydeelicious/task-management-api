package com.george.taskmanagement.dto;

import com.george.taskmanagement.domain.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record AddProjectMemberRequest(
        @NotNull(message = "User id is required")
        Long userId,

        @NotNull(message = "Role is required")
        ProjectRole role
) {

}
