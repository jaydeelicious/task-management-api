package com.george.taskmanagement.service;

import com.george.taskmanagement.domain.Task;
import com.george.taskmanagement.domain.TaskPriority;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task create(
            Long projectId,
            Long listId,
            String title,
            String description,
            int position,
            TaskPriority priority,
            LocalDate dueDate
    );

    Task findById(
            Long projectId,
            Long listId,
            Long taskId
    );

    List<Task> findByListId(
            Long projectId,
            Long listId
    );

    Task updateTitle(
            Long projectId,
            Long listId,
            Long id,
            String title
    );

    Task updateDescription(
            Long projectId,
            Long listId,
            Long taskId,
            String description
    );

    Task updatePriority(
            Long projectId,
            Long listId,
            Long taskId,
            TaskPriority priority
    );

    Task updateDueDate(
            Long projectId,
            Long listId,
            Long taskId,
            LocalDate dueDate
    );

    Task move(
            Long projectId,
            Long sourceListId,
            Long taskId,
            Long targetListId,
            int position
    );

    Task addLabel(
            Long projectId,
            Long listId,
            Long taskId,
            Long labelId
    );

    Task removeLabel(
            Long projectId,
            Long listId,
            Long taskId,
            Long labelId
    );

    void delete(
            Long projectId,
            Long listId,
            Long taskId
    );
}
