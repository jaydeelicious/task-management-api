package com.george.taskmanagement.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record MoveTaskListRequest(
        @PositiveOrZero(message = "Position cannot be negative")
        int position
) {
}
