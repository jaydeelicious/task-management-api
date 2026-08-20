package com.george.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record TaskListCreateRequest(
        @NotBlank(message = "List name is required")
        @Size(max = 100, message = "List name must not exceed 100 characters")
        String name,

        @PositiveOrZero(message = "Position cannot be negative")
        int position
) {
}
